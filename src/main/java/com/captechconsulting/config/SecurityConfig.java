package com.captechconsulting.config;

import com.captechconsulting.facade.Versions;
import com.captechconsulting.security.HeaderAuthenticationFilter;
import com.captechconsulting.security.HeaderUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.GeneralSecurityException;

@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String ACCESS_DENIED_JSON = "{\"message\":\"You are not privileged to request this resource.\", \"access-denied\":true,\"cause\":\"AUTHORIZATION_FAILURE\"}";
    private static final String UNAUTHORIZED_JSON = "{\"message\":\"Full authentication is required to access this resource.\", \"access-denied\":true,\"cause\":\"NOT AUTHENTICATED\"}";

    @Autowired
    private HeaderUtil headerUtil;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().

                withUser("user").password("password").roles("USER").

                and().

                withUser("admin").password("password").roles("USER", "ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        CustomAuthenticationSuccessHandler successHandler = new CustomAuthenticationSuccessHandler();
        successHandler.headerUtil(headerUtil);

        http.
                addFilterBefore(authenticationFilter(), LogoutFilter.class).

                csrf().disable().

                formLogin().successHandler(successHandler).
                loginProcessingUrl("/login").

                and().

                logout().
                logoutSuccessUrl("/logout").

                and().

                sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).

                and().

                exceptionHandling().
                accessDeniedHandler(new CustomAccessDeniedHandler()).
                authenticationEntryPoint(new CustomAuthenticationEntryPoint()).

                and().

                authorizeRequests().
                antMatchers(HttpMethod.POST, "/login").permitAll().
                antMatchers(HttpMethod.POST, "/logout").authenticated().
                antMatchers(HttpMethod.GET, "/**").hasRole("USER").
                antMatchers(HttpMethod.POST, "/**").hasRole("ADMIN").
                antMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN").
                anyRequest().authenticated();

    }

    private Filter authenticationFilter() {
        HeaderAuthenticationFilter headerAuthenticationFilter = new HeaderAuthenticationFilter();
        headerAuthenticationFilter.userDetailsService(userDetailsService());
        headerAuthenticationFilter.headerUtil(headerUtil);
        return headerAuthenticationFilter;
    }

    private static class CustomAccessDeniedHandler implements AccessDeniedHandler {
        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

            response.setContentType(Versions.V1_0);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            PrintWriter out = response.getWriter();
            out.print(ACCESS_DENIED_JSON);
            out.flush();
            out.close();

        }
    }

    private static class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

            response.setContentType(Versions.V1_0);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            PrintWriter out = response.getWriter();
            out.print(UNAUTHORIZED_JSON);
            out.flush();
            out.close();
        }
    }

    private static class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

        private HeaderUtil headerUtil;

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                            Authentication authentication) throws ServletException, IOException {
            try {
                String token = headerUtil.createAuthToken(((User) authentication.getPrincipal()).getUsername());
                ObjectMapper mapper = new ObjectMapper();
                ObjectNode node = mapper.createObjectNode().put("token", token);
                PrintWriter out = response.getWriter();
                out.print(node.toString());
                out.flush();
                out.close();
            } catch (GeneralSecurityException e) {
                throw new ServletException("Unable to create the auth token", e);
            }
            clearAuthenticationAttributes(request);

        }

        private void headerUtil(HeaderUtil headerUtil) {
            this.headerUtil = headerUtil;
        }
    }

}
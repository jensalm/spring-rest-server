package com.captechconsulting.config;

import com.captechconsulting.facade.Versions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().

                withUser("user").password("password").roles("USER").

                and().

                withUser("admin").password("password").roles("USER", "ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.
                csrf().disable().
                httpBasic().realmName("CapTech Rest API").

                and().

                exceptionHandling().accessDeniedHandler(new CustomeAccessDeniedHandler()).

                and().

                authorizeRequests().
                antMatchers(HttpMethod.GET, "/**").hasRole("USER").
                antMatchers(HttpMethod.POST, "/**").hasRole("ADMIN").
                antMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN").
                anyRequest().authenticated();

    }

    public static class CustomeAccessDeniedHandler implements AccessDeniedHandler {
        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

            response.setContentType(Versions.V1_0);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());
            response.flushBuffer();
        }
    }

}
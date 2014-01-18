package com.captechconsulting.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CookieAuthenticationFilter extends GenericFilterBean {

    private UserDetailsService userDetailsService;

    private HeaderUtil headerUtil = new HeaderUtil();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        UserDetails userDetails = loadUserDetails((HttpServletRequest) request);
        SecurityContext contextBeforeChainExecution = createSecurityContext(userDetails);

        try {
            SecurityContextHolder.setContext(contextBeforeChainExecution);
            if (contextBeforeChainExecution.getAuthentication() != null && contextBeforeChainExecution.getAuthentication().isAuthenticated()) {
                String userName = (String) contextBeforeChainExecution.getAuthentication().getPrincipal();
                headerUtil.addHeader((HttpServletResponse) response, userName);
            }
            filterChain.doFilter(request, response);
        }
        finally {
            // Clear the context and free the thread local
            SecurityContextHolder.clearContext();
        }
    }

    private SecurityContext createSecurityContext(UserDetails userDetails) {
        if (userDetails != null) {
            SecurityContextImpl securityContext = new SecurityContextImpl();
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
            securityContext.setAuthentication(authentication);
            return securityContext;
        }
        return SecurityContextHolder.createEmptyContext();
    }

    private UserDetails loadUserDetails(HttpServletRequest request) {
        String userName = headerUtil.getUserName(request);

        return userName != null
                ? userDetailsService.loadUserByUsername(userName)
                : null;
    }

    public void userDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public void headerUtil(HeaderUtil headerUtil) {
        this.headerUtil = headerUtil;
    }
}

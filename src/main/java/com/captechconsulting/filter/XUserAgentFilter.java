package com.captechconsulting.filter;

import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class XUserAgentFilter extends OncePerRequestFilter {

    private static final String X_USER_AGENT = "X-User-Agent";

    private String errorJson;

    public XUserAgentFilter() {
        this.errorJson = "{ " +
                "error: \"Missing header\", " +
                "cause : \"HTTP header '" + X_USER_AGENT + "' is required.\" }";
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (request.getHeader(X_USER_AGENT) == null) {
            response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
            response.getWriter().println(errorJson);
        } else {
            filterChain.doFilter(request, response);
        }
    }

}
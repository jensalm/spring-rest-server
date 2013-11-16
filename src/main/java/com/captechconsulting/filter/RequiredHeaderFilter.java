package com.captechconsulting.filter;

import com.captechconsulting.facade.Versions;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RequiredHeaderFilter extends OncePerRequestFilter {

    private static final String[] REQIURED_HEADERS = new String[] {"Accept", "X-User-Agent"};

    public RequiredHeaderFilter() {
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        for (String header : REQIURED_HEADERS) {
            if (request.getHeader(header) == null) {
                response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
                response.setContentType(Versions.ACCEPT_HEADER_V1_0);
                response.getWriter().println(getErrorJson(header));
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getErrorJson(String header) {
        return "{ " +
                "\"error\": \"Missing header\", " +
                "\"cause\": \"HTTP header '" + header + "' is required.\" }";
    }
}
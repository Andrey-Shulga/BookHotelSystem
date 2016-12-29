package com.epam.as.bookhotel.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * Set encoding UTF-8 for all request
 */

public class EncodingFilter implements Filter {

    private static final String ENCODE_INIT_PARAM = "encoding";
    private String encoding = "utf-8";

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        request.setCharacterEncoding(encoding);
        filterChain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfig) throws ServletException {

        String encodingParam = filterConfig.getInitParameter(ENCODE_INIT_PARAM);
        if (encodingParam != null) encoding = encodingParam;

    }

    public void destroy() {
    }
}
package com.example.cygyl.sys.common;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @description:
 * @author: Tanglie
 * @time: 2021/3/11
 */
public class xssFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        XssHttpServletRequsetWrapper xssRequest = new XssHttpServletRequsetWrapper((HttpServletRequest) servletRequest);
        filterChain.doFilter(xssRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}

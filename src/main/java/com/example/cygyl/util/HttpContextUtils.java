package com.example.cygyl.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

import javax.servlet.http.HttpServletRequest;


/**
 * @description:
 * @author: Tanglie
 * @time: 2021/3/10
 */
public class HttpContextUtils {

    public static HttpServletRequest getHttpServletRequest(){
        System.out.println("HttpContextUtils->getHttpServletRequest " + ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest());
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static String getDomain(){
        HttpServletRequest request = getHttpServletRequest();
            StringBuffer url = request.getRequestURL();

            return url.delete(url.length() - request.getRequestURL().length(),url.length()).toString();
    }

    public static String getOrigin(){
        HttpServletRequest request = getHttpServletRequest();
        return request.getHeader("Origin");
    }
}

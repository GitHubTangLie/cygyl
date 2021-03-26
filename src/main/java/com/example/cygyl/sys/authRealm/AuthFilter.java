package com.example.cygyl.sys.authRealm;

import com.example.cygyl.util.HttpContextUtils;
import com.example.cygyl.util.R;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: Tanglie
 * @time: 2021/3/10
 */
public class AuthFilter extends AuthenticatingFilter {

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        //获取请求的token
        String token = getRequestToken((HttpServletRequest) request);

        System.out.println("AuthFilter 取到token值 " + token);
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return new AuthToken(token);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        // 放行OPTIONS请求
        if (((HttpServletRequest) request).getMethod().equals(RequestMethod.OPTIONS.name())) {
            return true;
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //获取请求的token
        String token = getRequestToken((HttpServletRequest) request);
        if (StringUtils.isBlank(token)) {

            //如果token 无效则返回前端错误状态码以及错误消息
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletResponse.setCharacterEncoding("utf-8");
            httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpServletResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtils.getOrigin());
            String json = new Gson().toJson(R.error(HttpStatus.UNAUTHORIZED,"无效的Token请重新登录!!!"));
            httpServletResponse.getWriter().print(json);
            return false;
        }
        return executeLogin(request, response);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtils.getOrigin());

        try {
            Throwable throwable = e.getCause() == null ? e : e.getCause();
            R r = R.error(HttpStatus.UNAUTHORIZED, throwable.getMessage());

            String json = new Gson().toJson(r);
            httpServletResponse.getWriter().print(json);
        } catch (IOException e1) {

        }

        return false;

    }

    /**
     * 获取请求的token
     *
     * @param request
     * @return token
     */
    private String getRequestToken(HttpServletRequest request) {
        //从header取出token
        String token = request.getHeader("token");

        //如果header中不存在则从StringUtils中获取
        if (StringUtils.isBlank(token)) {
            request.getParameter("token");
        }
        return token;

    }
}

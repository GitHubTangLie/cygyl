package com.example.cygyl.exception;


import com.example.cygyl.util.R;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

public class SysHandlerException{

    /**
     *   无权限异常捕获
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler({AuthorizationException.class, UnauthorizedException.class})
    public R NotPermissionException(Exception e){
        return R.error(HttpStatus.INTERNAL_SERVER_ERROR,"您没有该权限,无法访问该资源!");
    }
}

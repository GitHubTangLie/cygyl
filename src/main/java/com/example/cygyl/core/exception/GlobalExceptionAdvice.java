package com.example.cygyl.core.exception;

import com.example.cygyl.core.response.UnifyResponse;
import com.example.cygyl.exception.HttpException;
import com.example.cygyl.util.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
/**
 * @author 黎源
 * @date 2021/3/2 17:44
 */
/**
 * 全局异常处理
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {
    @Autowired
    @Qualifier(value = "exceptionCopeConfiguration")
    private ExceptionCopeConfiguration exceptionCode;

    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public UnifyResponse ExceptionHandler(HttpServletRequest request, Exception e) {
        log.error(e.getMessage());
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        return UnifyResponse.builder()
                .code(9999)
                .request(method+":"+requestURI)
                .message(exceptionCode.getMessage(9999))
                .build();
    }


    @ExceptionHandler(value = HttpException.class)
    public ResponseEntity<UnifyResponse> HttpExceptionHandler(HttpServletRequest request, HttpException e) {
        log.error(e.getMessage());
        String reqUrl = request.getRequestURI();
        String method = request.getMethod();
        UnifyResponse unifyResponse = UnifyResponse.builder()
                .code(e.getCode())
                .message(exceptionCode.getMessage(e.getCode()))
                .request(method + " " + reqUrl)
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpStatus status = HttpStatus.resolve(e.getHttpStatusCode());
        ResponseEntity<UnifyResponse> r = new ResponseEntity<>(unifyResponse, headers, status);
        return r;
    }

    @ExceptionHandler(MultipartException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public UnifyResponse MultipartExceptionHandler(HttpServletRequest request, MultipartException e) {
        log.error(e.getMessage());
        String reqUrl = request.getRequestURI();
        String method = request.getMethod();
        return UnifyResponse.builder()
                .code(10005)
                .message(exceptionCode.getMessage(10005))
                .request(method + ":" + reqUrl)
                .build();
    }


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public UnifyResponse MethodArgumentNotValidExceptionHandler(HttpServletRequest request, MethodArgumentNotValidException e) {
        String reqUrl = request.getRequestURI();
        String method = request.getMethod();
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        return UnifyResponse.builder()
                .code(9999)
                .message(formatAllErrorsToMessageByList(errors))
                .request( method+ " " + reqUrl)
                .build();
    }

    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public UnifyResponse ConstraintViolationExceptionHandler(HttpServletRequest req, ConstraintViolationException e) {
        String reqUrl = req.getRequestURI();
        String method = req.getMethod();
        Set<ConstraintViolation<?>> errors = e.getConstraintViolations();
        return UnifyResponse.builder()
                .code(9999)
                .message(formatAllErrorsToMessageBySet(errors))
                .request(method + " " + reqUrl)
                .build();
    }

    private String formatAllErrorsToMessageByList(List<ObjectError> errors) {
        StringBuffer message = new StringBuffer();
        errors.forEach(error -> {
            message.append(error.getDefaultMessage()).append(";");
        });
        return message.toString();
    }

    private String formatAllErrorsToMessageBySet(Set<? extends ConstraintViolation> errors) {
        StringBuffer message = new StringBuffer();
        errors.forEach(error -> {
            message.append(error.getMessage()).append(";");
        });
        return message.toString();
    }

    /**
     *  无权限异常处理
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler({AuthorizationException.class, UnauthorizedException.class})
    public R NotPermissionException(Exception e){
        return R.error(HttpStatus.INTERNAL_SERVER_ERROR,"您没有该权限,无法访问该资源!");
    }
}

package com.example.cygyl.sys.common;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/** 
* @Description:  Xss 过滤处理
* @Param: 
* @return: 
* @Author: TangLie
* @Date: 2021/3/11
*/
public class XssHttpServletRequsetWrapper extends HttpServletRequestWrapper {

    //没被包装过的HttpServletRequset 需要自己过滤
    HttpServletRequest orRequest;

    //html 的过滤
    private final static HTMLFilter htmlFilter = new HTMLFilter();

    public XssHttpServletRequsetWrapper(HttpServletRequest request) {
        super(request);
        orRequest = request;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        //不是json 直接返回
        if (MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(super.getHeader(HttpHeaders.CONTENT_TYPE))){
            return super.getInputStream();
        }

        //如果为空直接返回
        String json = IOUtils.toString(super.getInputStream(),"utf-8");
        if (StringUtils.isBlank(json)){
            return super.getInputStream();
        }


        //xss过滤
        json = getXssEncode(json);

        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(json.getBytes("utf-8"));
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return true;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        };


    }


    @Override
    public String getParameter(String name) {
        String value = super.getParameter(getXssEncode(name));
        if (StringUtils.isNotBlank(value)){
            value = getXssEncode(value);
        }
        return value;
    }


    @Override
    public String[] getParameterValues(String name) {
        String [] parameters = super.getParameterValues(name);
        if (parameters == null || parameters.length == 0){
            return null;
        }

        for (int i = 0; i < parameters.length;i++){
            parameters[i] =  getXssEncode(parameters[i]);
        }

        return parameters;
    }


    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String,String[]> map = new LinkedHashMap<>();
        Map<String,String[]> parameters = super.getParameterMap();

        for (String key : parameters.keySet()){
            String[] values = parameters.get(key);
            for (int i = 0 ; i < values.length; i++){
                values[i] = getXssEncode(values[i]);
            }
            map.put(key,values);
        }
        return map;
    }

    @Override
    public String getHeader(String name) {

        String value = super.getHeader(getXssEncode(name));
        if (StringUtils.isNotBlank(value)){
            value = getXssEncode(value);
        }
        return value;
    }

    public String getXssEncode(String input){

        return htmlFilter.filter(input);
    }

    /**
     *  获取最原始的request
     * @return
     */
    public HttpServletRequest getOrgRequest(){
        return orRequest;
    }

    /**
     *  获取最原始的request
     * @param request
     * @return
     */
    public static HttpServletRequest getOrgRequest(HttpServletRequest request){
        if (request instanceof XssHttpServletRequsetWrapper){
            return ((XssHttpServletRequsetWrapper) request).getOrgRequest();
        }
        return request;
    }
}


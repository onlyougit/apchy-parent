package com.sptwin.apchy.web.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice(basePackages = "com.sptwin.apchy.web")
public class GlobalExceptionHandler {
    //这里是返回json，当然也可以返回ModelAndView
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Map<String, Object> exceptionHandler(HttpServletRequest request, Exception e){
        //这里可以打印异常信息，实际项目中，要将错误记录到日志中，使用Mongdb
        e.printStackTrace();
        Map map = new HashMap();
        map.put("errorCode", "500");
        map.put("errorMsg", "系统异常！");
        return map;
    }
}

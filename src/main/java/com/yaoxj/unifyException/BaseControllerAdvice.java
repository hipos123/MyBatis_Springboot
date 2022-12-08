package com.yaoxj.unifyException;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @Author: bssc
 * @CreateTime: 2022-12-07
 * @Description: BaseControllerAdvice
 * @Version: 1.0
 */
@Slf4j
@ControllerAdvice
public class BaseControllerAdvice {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> processArithmeticException(HttpServletRequest request, HandlerMethod method, RuntimeException exception) {
        if (log.isWarnEnabled()) {
            log.warn("ArithmeticException :", exception);
        }
        ExceptionResponse er = new ExceptionResponse();
        er.setFailed(Boolean.TRUE);
        er.setCode("err.arithmetic");
        er.setType("error："+getStackTrace(exception));
        er.setMessage("算术异常: " + exception+",method:"+method);
        log.error("Exception:{}", JSONObject.toJSONString(er));
        return new ResponseEntity<>(er, HttpStatus.OK);
    }

    public static String getStackTrace(Throwable throwable)
    {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try
        {
            throwable.printStackTrace(pw);
            return sw.toString();
        } finally
        {
            pw.close();

        }
    }

}

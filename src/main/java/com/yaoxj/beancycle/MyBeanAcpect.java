package com.yaoxj.beancycle;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-07-23 16:48
 **/
@Component
@Aspect
public class MyBeanAcpect {
    private Logger logger = LoggerFactory.getLogger(MyBeanAcpect.class);

    @Pointcut("execution(public String com.yaoxj.beancycle.MyBean.*(..))")
    public void myPointCut(){}

    @Before("myPointCut()")
    public void logStart(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        System.out.println(joinPoint.getSignature().getName()+"开始记录日志。。。。参数是{"+Arrays.asList(args)+"}");

    }

    @After("myPointCut()")
    public void logEnd(){
        System.out.println("日志记录结束。。。。");
    }


    //JoinPoint joinPoint必须放在前面
    @AfterReturning(value="myPointCut()",returning="result1")
    public void logReturn(JoinPoint joinPoint,Object result1){
        System.out.println("日志返回的参数是。。。{"+result1+"}");
    }

    @AfterThrowing(value="myPointCut()",throwing="exception")
    public void logException(JoinPoint joinPoint,Exception exception){
        System.out.println("日志异常信息。。。。异常信息{"+exception+"}");
    }
}

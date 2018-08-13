package com.ray.monitor.core.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:日志处理
 */
@Aspect
@Component
public class LogAspect {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /**
     * @Description: 声明切面，规定切面所拦截controller下的所有的类的方法
     * @param:
     * @return:
     */
    @Pointcut("execution(* com.ray.monitor.controller.*.*(..))")
    public void log() {}

    /**
     * @Description: 打印拦截前的信息
     * @param: joinPoint
     * @return:
     */
    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        LOGGER.info(request.getRequestURL().toString());// 打印url
        LOGGER.info(joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName());// 类名+方法名
        LOGGER.info(String.valueOf(joinPoint.getArgs()));// 参数
    }
    /**
     * @Description: 打印拦截信息
     * @param: result
     * @return:
     */
    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterRuturn(Object result) {
        LOGGER.info("Result : {}", result);
    }

}

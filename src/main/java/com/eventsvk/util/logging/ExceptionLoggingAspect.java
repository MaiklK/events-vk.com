package com.eventsvk.util.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class ExceptionLoggingAspect {
    @AfterThrowing(pointcut = "execution(* com.eventsvk..*(..))", throwing = "exception")
    public void exceptionLogging(JoinPoint joinPoint, Exception exception) {
        log.error("Метод {} в классе {}, выдал ошибку {}",
                joinPoint.getSignature().getName(),
                joinPoint.getTarget().getClass().getSimpleName(),
                Arrays.toString(exception.getStackTrace()));
    }
}

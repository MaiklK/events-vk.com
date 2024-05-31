package com.eventsvk.util.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class DebugAllMethods {
    @Pointcut("execution(* com.eventsvk..*(..))")
    public void allMethods() {
    }

    @Before("allMethods() && args(..)")
    public void logBefore(JoinPoint joinPoint) {
        log.debug("Подготовка метода {} в классе {}, с аргументами: {}",
                joinPoint.getSignature().getName(),
                joinPoint.getTarget().getClass().getSimpleName(),
                Arrays.toString(joinPoint.getArgs()));
    }

//    @AfterReturning(value = "allMethods()", returning = "returnValue")
//    public void logAfterReturning(JoinPoint joinPoint, Object returnValue) {
//        log.debug("Метод {} в классе {}, отработал и вернул: {}",
//                joinPoint.getSignature().getName(),
//                joinPoint.getTarget().getClass().getSimpleName(),
//                returnValue.toString().substring(0,
//                        Math.min(returnValue.toString().length(), 1000)));
//    }
}

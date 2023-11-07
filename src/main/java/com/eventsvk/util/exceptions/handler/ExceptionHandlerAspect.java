package com.eventsvk.util.exceptions.handler;

import com.eventsvk.util.TimeUtils;
import com.vk.api.sdk.exceptions.ApiTooManyException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
@AllArgsConstructor
public class ExceptionHandlerAspect {

    @AfterThrowing(pointcut = "execution(* com.eventsvk..*(..))", throwing = "exception")
    public void exceptionLogging(JoinPoint joinPoint, Exception exception) {
        if (exception instanceof ApiTooManyException) {
            TimeUtils.pauseRequest();
        }
        log.error("Метод {} в классе {}, выдал ошибку {}",
                joinPoint.getSignature().getName(),
                joinPoint.getTarget().getClass().getSimpleName(),
                Arrays.toString(exception.getStackTrace()));
    }
}

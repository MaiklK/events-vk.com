package com.eventsvk.util.logging;

import com.vk.api.sdk.exceptions.ApiAuthException;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.exceptions.OAuthException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class VkServiceLogging {

    @Pointcut("within(com.eventsvk.services.VkontakteService)")
    public void isVkService() {
    }

    public void logError(JoinPoint joinPoint, Exception exception) {
        log.error("Ошибка метода {} в классе {}, с аргументами {}, сообщение об ошибке: {}",
                joinPoint.getSignature().getName(),
                joinPoint.getTarget().getClass().getSimpleName(),
                Arrays.toString(joinPoint.getArgs()),
                exception.getMessage());
    }

    @AfterThrowing(pointcut = "isVkService()", throwing = "exception")
    public void logApiException(JoinPoint joinPoint, ApiException exception) {
        logError(joinPoint, exception);
    }

    @AfterThrowing(pointcut = "isVkService()", throwing = "exception")
    public void logClientException(JoinPoint joinPoint, ClientException exception) {
        logError(joinPoint, exception);
    }

    @AfterThrowing(pointcut = "isVkService()", throwing = "exception")
    public void logOAuthException(JoinPoint joinPoint, OAuthException exception) {
        logError(joinPoint, exception);
    }

    @AfterThrowing(pointcut = "isVkService()", throwing = "exception")
    public void logApiAuthException(JoinPoint joinPoint, ApiAuthException exception) {
        logError(joinPoint, exception);
    }
}

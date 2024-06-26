package com.eventsvk.exception;

import com.eventsvk.util.TimeUtils;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ApiTooManyException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.exceptions.OAuthException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class VkServiceHandler {

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


    @Around("isVkService()")
    public Object handleException(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (ApiTooManyException exception) {
            TimeUtils.pauseRequest();
            logError(joinPoint, exception);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            logError(joinPoint, exception);
        } catch (OAuthException exception) {
            logError(joinPoint, exception);
            log.error("При авторизации произошла ошибка {}, ошибка {}", exception.getRedirectUri(), exception.getMessage());
        } catch (ClientException | ApiException exception) {
            logError(joinPoint, exception);
        }
        return null; // Возвращаемое значение в случае перехвата исключения
    }
}

package com.eventsvk.exception;

import com.eventsvk.services.VkontakteService;
import com.eventsvk.util.TimeUtils;
import com.vk.api.sdk.exceptions.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class VkServiceLogging {
    private final VkontakteService vkontakteService;

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

package ru.eleron.osa.lris.schedule.utils.aop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggerAspect {

    private final static Logger logger = LogManager.getLogger();

    @Around("loggingForAnnotationLogging()")
    public void loggingAnnotationLogging(ProceedingJoinPoint point)
    {
        try {
            logger.info("method start: " + point.getSignature() + " in class " + point.getTarget());
            point.proceed();
            logger.info("method end: " + point.getSignature());
        } catch (Throwable throwable) {
            logger.error("method error: " + point.getSignature(), throwable);
        }
    }

    @Pointcut("@annotation(ru.eleron.osa.lris.schedule.utils.aop.Logging)")
    public void loggingForAnnotationLogging(){}
}

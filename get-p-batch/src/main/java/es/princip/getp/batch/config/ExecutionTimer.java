package es.princip.getp.batch.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ExecutionTimer {

    @Pointcut("@annotation(es.princip.getp.batch.config.ExtendsWithExecutionTimer)")
    private void timer() {}

    @Around("timer()")
    public Object AssumeExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        final long start = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        } finally {
            final long finish = System.currentTimeMillis();
            final long executionTime = finish - start;
            final String signature = joinPoint.getSignature().toShortString();
            log.info("execution time of {}: {}ms", signature, executionTime);
        }
    }
}
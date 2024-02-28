package es.princip.getp.global.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
public class TimeTraceAop {

    @Around("execution(* es.princip.getp.domain.people..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        try{
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            log.info("END:{} {}ms",joinPoint.toString(), timeMs);
        }
    }
}

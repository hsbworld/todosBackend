package com.husqvarna.todoBackend.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class LoggerAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggerAspect.class);

    @Pointcut("execution(* com.husqvarna.todoBackend.controllers.*.*(..))")
    private void allControllerMethods() { }

    @Before(value = "allControllerMethods()")
    public void beforeMethodExecution(JoinPoint jp) {

        // Before the control enters any controller method
        logger.info("Entering method: " + jp.getSignature().getName());
        logger.info("Method signature: " + jp.getSignature());

        String args = (String) Arrays.stream(jp.getArgs()).reduce("", (accumulator, element) -> accumulator.toString() + " " + element.toString());
        logger.info("Method Arguments: " + args);

    }

    @AfterReturning(pointcut = "allControllerMethods()",
            returning = "result" )
    public void afterMethodExecution(JoinPoint jp, ResponseEntity result) {

        // After the control exits any controller method - success scenario
        logger.info("Result: " + result.getBody().toString());
        logger.info("Exiting method: " + jp.getSignature().getName());

    }

    @AfterThrowing(pointcut = "allControllerMethods()", throwing = "ex")
    public void afterMethodException(JoinPoint jp, Throwable ex) {

        // After the control exits any controller method - exception scenario
        logger.info(ex + " exception was thrown");
        logger.info("Exiting method: " + jp.getSignature().getName());

    }

}

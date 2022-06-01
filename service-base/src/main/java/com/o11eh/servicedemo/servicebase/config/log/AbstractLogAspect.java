package com.o11eh.servicedemo.servicebase.config.log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class AbstractLogAspect {
    protected static final ObjectMapper MAPPER;

    static {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        MAPPER = new ObjectMapper();
        MAPPER.registerModule(javaTimeModule);
    }

    @Around(value = "@annotation(logAnnotation)")
    public Object around(ProceedingJoinPoint joinPoint, Log logAnnotation) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result;
        Exception e = null;
        String params = null;
        Object[] args = joinPoint.getArgs();
        if (args.length == 1) {
            params = MAPPER.writeValueAsString(args[0]);
        } else if (args.length > 1) {
            params = MAPPER.writeValueAsString(args);
        }
        try {
            result = joinPoint.proceed();
        } catch (Exception ex) {
            e = ex;
            throw e;
        } finally {
            int timeCost = Math.toIntExact(System.currentTimeMillis() - startTime);
            doSaveLog(params, logAnnotation, joinPoint, timeCost, e);
        }
        return result;
    }

    abstract public void doSaveLog(String params, Log annotation, ProceedingJoinPoint joinPoint, int timeCost, Exception e);
}

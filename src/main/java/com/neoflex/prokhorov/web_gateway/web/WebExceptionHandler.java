package com.neoflex.prokhorov.web_gateway.web;

import feign.FeignException;
import jakarta.validation.ConstraintViolationException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class WebExceptionHandler {
    private static final String MSG = "message";
    private static final String TIMESTAMP = "timestamp";

    @ExceptionHandler(FeignException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @SneakyThrows
    public Map<String, Object> handleFeignException(RuntimeException e) {
        log.warn("Запрос в микросервис вернул ошибку {}", e.getMessage());
        Map<String, Object> attrs = new HashMap<>(Map.of(MSG, e.getMessage()));
        attrs.put(TIMESTAMP, LocalDateTime.now());
        return attrs;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String, Object> handleConstraintViolationException(RuntimeException e) {
        Map<String, Object> attrs = new HashMap<>(Map.of(MSG, e.getMessage()));
        attrs.put(TIMESTAMP, LocalDateTime.now());
        return attrs;
    }
}

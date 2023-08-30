package ru.practicum.service.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.ApiError;
import ru.practicum.Constant;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler(ValidateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleStatsException(ValidateException e) {
        log.warn(e.getMessage());
        return new ApiError(e.getMessage(), "Действие невозможно.", HttpStatus.BAD_REQUEST.getReasonPhrase(), LocalDateTime.now().format(Constant.FORMATTER));
    }
}

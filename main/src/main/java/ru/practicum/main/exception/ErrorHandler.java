package ru.practicum.main.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.ApiError;
import ru.practicum.Constant;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(NotFoundException e) {
        return new ApiError(e.getMessage(), "Данные не найдены.", HttpStatus.NOT_FOUND.getReasonPhrase(), LocalDateTime.now().format(Constant.FORMATTER));
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleInvalidDataException(ValidationException e) {
        return new ApiError(e.getMessage(), "Данные некорректны.", HttpStatus.BAD_REQUEST.getReasonPhrase(), LocalDateTime.now().format(Constant.FORMATTER));
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleSomethingWentWrongException(ConflictException e) {
        return new ApiError(e.getMessage(), "Конфликт данных", HttpStatus.CONFLICT.getReasonPhrase(), LocalDateTime.now().format(Constant.FORMATTER));
    }
}

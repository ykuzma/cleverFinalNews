package by.clevertec.news.api.entity.exception;

import by.clevertec.starter.exception.handle.annotation.ExceptionStatus;
import org.springframework.http.HttpStatus;

import java.util.UUID;

@ExceptionStatus(HttpStatus.NOT_FOUND)
public class NotFoundDataException extends RuntimeException{
    public NotFoundDataException() {
    }

    public NotFoundDataException(UUID id, Class<?> clazz) {
        super(String.format("%s with id: %s not found", clazz.getSimpleName(), id));
    }

    public NotFoundDataException(String message, Throwable cause) {
        super(message, cause);
    }
}

package me.mepv.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserNameNotAvailableException extends RuntimeException {
    public UserNameNotAvailableException(String message) {
        super(message);
    }
}

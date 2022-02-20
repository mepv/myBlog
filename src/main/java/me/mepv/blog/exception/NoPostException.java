package me.mepv.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class NoPostException extends RuntimeException {

    public NoPostException(String s) {
        super(s);
    }
}

package org.example.skladok.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NotEnoughSocksException extends RuntimeException {

    public NotEnoughSocksException() {
    }

    public NotEnoughSocksException(String message) {
        super(message);
    }

}

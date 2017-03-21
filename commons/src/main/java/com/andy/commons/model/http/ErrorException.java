package com.andy.commons.model.http;

/**
 * Created by andy.wang on 2017/2/3.
 */

public class ErrorException extends RuntimeException {
    public ErrorException() {
    }

    public ErrorException(String message) {
        super(message);
    }

    public ErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorException(Throwable cause) {
        super(cause);
    }
}

package com.views.tools.reflect;

/**
 */
@SuppressWarnings("unused")
public class RefException extends Exception {
    public RefException() {
        super();
    }

    public RefException(String detailMessage) {
        super(detailMessage);
    }

    public RefException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public RefException(Throwable throwable) {
        super(throwable);
    }
}
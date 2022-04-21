package com.evivv.store.service.ex;

public class DuplicateSeckillException extends ServiceException{
    public DuplicateSeckillException() {
        super();
    }

    public DuplicateSeckillException(String message) {
        super(message);
    }

    public DuplicateSeckillException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateSeckillException(Throwable cause) {
        super(cause);
    }

    protected DuplicateSeckillException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

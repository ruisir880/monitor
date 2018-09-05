package com.ray.monitor.exception;

/**
 * Created by Ray Rui on 9/5/2018.
 */
public class SonRecordFoundException extends BusinessLogicException {
    public SonRecordFoundException(String msg) {
        super(msg);
    }

    public SonRecordFoundException(Throwable cause) {
        super(cause);
    }
}

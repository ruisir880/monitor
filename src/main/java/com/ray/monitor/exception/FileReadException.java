package com.ray.monitor.exception;

/**
 * Created by rui on 2018/9/2.
 */
public class FileReadException extends BusinessLogicException {
    public FileReadException(String msg) {
        super(msg);
    }

    public FileReadException(Throwable cause) {
        super(cause);
    }
}

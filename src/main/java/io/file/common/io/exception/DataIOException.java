package io.file.common.io.exception;

public class DataIOException extends RuntimeException {
    private static final long serialVersionUID = 6340681008460201233L;

    public DataIOException() {
    }

    public DataIOException(String message) {
        super(message);
    }

    public DataIOException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataIOException(Throwable cause) {
        super(cause);
    }

    public DataIOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

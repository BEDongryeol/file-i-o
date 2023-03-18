package io.whatap.common.io.exception;

/**
 * Copyright whatap Inc since 2023/03/17
 * Created by Larry on 2023/03/17
 * Email : inwoo.server@gmail.com
 */
public class IllegalInputStreamException extends RuntimeException {
    private static final long serialVersionUID = 3815303472642108066L;

    public IllegalInputStreamException(String message) {
        super(message);
    }

    public IllegalInputStreamException(Throwable cause) {
        super(cause);
    }
}

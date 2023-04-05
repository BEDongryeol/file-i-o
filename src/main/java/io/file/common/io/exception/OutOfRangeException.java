package io.file.common.io.exception;

/**
 * Copyright whatap Inc since 2023/03/08
 * Created by Larry on 2023/03/08
 * Email : inwoo.server@gmail.com
 */
public class OutOfRangeException extends RuntimeException {
    private static final long serialVersionUID = -3587296036438642948L;

    public OutOfRangeException(String message) {
        super(message);
    }
}
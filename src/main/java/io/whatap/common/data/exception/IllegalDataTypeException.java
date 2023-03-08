package io.whatap.common.data.exception;

/**
 * Copyright whatap Inc since 2023/03/08
 * Created by Larry on 2023/03/08
 * Email : inwoo.server@gmail.com
 */
public class IllegalDataTypeException extends RuntimeException {
    private static final long serialVersionUID = 3415312357432203299L;

    public IllegalDataTypeException(String message) {
        super(message);
    }
}
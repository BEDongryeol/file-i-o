package io.whatap.common.io.exception;

/**
 * Copyright whatap Inc since 2023/03/07
 * Created by Larry on 2023/03/07
 * Email : inwoo.server@gmail.com
 */
public class FileLoadException extends RuntimeException{
    private static final long serialVersionUID = 5452452085738082455L;

    public FileLoadException(String message) {
        super(message);
    }
}
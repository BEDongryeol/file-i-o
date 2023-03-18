package io.whatap.common.data.exception;

/**
 * Copyright whatap Inc since 2023/03/16
 * Created by Larry on 2023/03/16
 * Email : inwoo.server@gmail.com
 */
public class NotFoundLogException extends RuntimeException {
    private static final long serialVersionUID = -5830634939069010299L;

    public NotFoundLogException(String message) {
        super(message);
    }
}

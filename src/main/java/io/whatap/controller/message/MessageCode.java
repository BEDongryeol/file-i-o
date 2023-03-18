package io.whatap.controller.message;

import lombok.Getter;

import java.util.function.Function;

/**
 * Copyright whatap Inc since 2023/03/07
 * Created by Larry on 2023/03/07
 * Email : inwoo.server@gmail.com
 */
@Getter
public enum MessageCode {

    SPRING_LAYER_EXCEPTION(4040, message -> "SERVLET_LAYER_EXCEPTION : " + message),
    REPOSITORY_LAYER_EXCEPTION(4041, message -> "REPOSITORY_LAYER_EXCEPTION : " + message),
    SERVICE_LAYER_EXCEPTION(4042, message -> "SERVICE_LAYER_EXCEPTION : " + message),
    PRESENTATION_LAYER_EXCEPTION(4043, message -> "PRESENTATION_LAYER_EXCEPTION : " + message);

    private final int code;
    private final Function<String, String> expressionMessage;

    MessageCode(int code, Function<String, String> expressionMessage) {
        this.code = code;
        this.expressionMessage = expressionMessage;
    }

    public String applyMessage(String message) {
        return this.expressionMessage.apply(message);
    }
}

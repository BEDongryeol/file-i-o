package io.whatap.controller.message.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.whatap.common.util.DateTimeUtil;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Copyright whatap Inc since 2023/03/07
 * Created by Larry on 2023/03/07
 * Email : inwoo.server@gmail.com
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorMessage implements ResponseMessage {

    private final int code;
    private final String dateTime;
    private final String message;

    public ErrorMessage(int code, String message) {
        this.code = code;
        this.dateTime = DateTimeUtil.toString(LocalDateTime.now());
        this.message = message;
    }

    public ErrorMessage(int code) {
        this.code = code;
        this.dateTime = DateTimeUtil.toString(LocalDateTime.now());
        this.message = null;
    }
}

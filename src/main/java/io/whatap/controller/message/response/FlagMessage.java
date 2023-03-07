package io.whatap.controller.message.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

/**
 * Copyright whatap Inc since 2023/03/07
 * Created by Larry on 2023/03/07
 * Email : inwoo.server@gmail.com
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FlagMessage {
    private final Boolean flag;

    public FlagMessage(Boolean flag) {
        this.flag = flag;
    }
}

package io.whatap.dto;

import lombok.Getter;

/**
 * Copyright whatap Inc since 2023/03/06
 * Created by Larry on 2023/03/06
 * Email : inwoo.server@gmail.com
 */
@Getter
public class RequestPackDTO extends AbstractPackDTO {

    private int status;
    private long responseTime;

}
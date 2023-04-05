package io.file.controller.message.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.List;

/**
 * Copyright whatap Inc since 2023/03/07
 * Created by Larry on 2023/03/07
 * Email : inwoo.server@gmail.com
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContentMessage<T> {

    private final T content;
    private final List<T> contents;
    private final Integer totalSize;

    public ContentMessage(T content) {
        this.content = content;
        this.contents = null;
        this.totalSize = null;
    }

    public ContentMessage(List<T> contents) {
        this.content = null;
        this.contents = contents;
        this.totalSize = contents.size();
    }
}

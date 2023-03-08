package io.whatap.io.support;

import lombok.Getter;

/**
 * Copyright whatap Inc since 2023/03/08
 * Created by Larry on 2023/03/08
 * Email : inwoo.server@gmail.com
 */
@Getter
public enum FileAccessMode {

    READ_ONLY("r"),
    READ_WRITE("rw"),
    SYNC("rws"),
    DSYNC("rwd");

    private final String mode;

    FileAccessMode(String mode) {
        this.mode = mode;
    }
}

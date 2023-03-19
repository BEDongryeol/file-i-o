package io.whatap.common.io.exception;

/**
 * Copyright whatap Inc since 2023/03/19
 * Created by Larry on 2023/03/19
 * Email : inwoo.server@gmail.com
 */
public class RetryFailedException extends RuntimeException {
    private static final long serialVersionUID = -1746142776098121776L;

    private static final String message = "재시도 횟수를 초과하였습니다.";

    public RetryFailedException() {
        super(message);
    }
}

package io.file.common.io.support;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

/**
 * Copyright whatap Inc since 2023/03/16
 * Created by Larry on 2023/03/16
 * Email : inwoo.server@gmail.com
 */
class ByteBufferPoolTest {

    @Test
    @DisplayName("사용 중이지 않은 buffer를 return")
    void byteBufferPoolTest() throws Exception {
        // given
        byte[] bytes = new byte[32];

        // when
        ByteBuffer byteBuffer1 = ByteBufferPool.getByteBuffer();
        byteBuffer1.put(bytes);
        ByteBuffer byteBuffer2 = ByteBufferPool.getByteBuffer();

        // then
        Assertions.assertNotEquals(byteBuffer1, byteBuffer2);
    }

}
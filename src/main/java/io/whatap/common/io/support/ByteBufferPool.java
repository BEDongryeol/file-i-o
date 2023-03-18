package io.whatap.common.io.support;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright whatap Inc since 2023/03/16
 * Created by Larry on 2023/03/16
 * Email : inwoo.server@gmail.com
 */
public class ByteBufferPool {

    private ByteBufferPool() {
    }

    private static final int BUFFER_COUNT = 10000;
    public static final int BUFFER_SIZE = 32 << 10;
    private static final List<ByteBuffer> BYTE_BUFFERS = new ArrayList<>();

    static {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(BUFFER_COUNT * BUFFER_SIZE);

        int position = 0;
        for (int i = 0; i < BUFFER_COUNT; i++) {
            int max = position + BUFFER_SIZE;
            byteBuffer.limit(max);
            BYTE_BUFFERS.add(byteBuffer.slice());
            position = max;
            byteBuffer.position(position);
        }
    }

    public static ByteBuffer getByteBuffer() {
        return BYTE_BUFFERS.stream()
                .filter(byteBuffer -> byteBuffer.position() == 0)
                .findAny()
                .orElseGet(ByteBufferPool::getByteBuffer);
    }
}

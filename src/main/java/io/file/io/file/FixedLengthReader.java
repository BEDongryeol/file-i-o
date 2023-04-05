package io.file.io.file;

import io.file.common.io.exception.OutOfRangeException;
import io.file.common.io.support.ByteBufferPool;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Copyright whatap Inc since 2023/03/18
 * Created by Larry on 2023/03/18
 * Email : inwoo.server@gmail.com
 */
@Slf4j
public class FixedLengthReader {

    public static byte[] readByIndex(RandomAccessFile randomAccessFile, int index, int byteLength) {
        byte[] bytes = new byte[byteLength];
        int position = byteLength * index;

        try {
            randomAccessFile.seek(position);
            randomAccessFile.readFully(bytes);
        } catch (IOException e) {
            throw new OutOfRangeException("파일의 읽을 수 있는 범위를 초과하였습니다. --- 요청 position : " + position);
        }

        return bytes;
    }

    public static byte[] readByPosition(RandomAccessFile randomAccessFile, int position, int byteLength) {
        byte[] bytes = new byte[byteLength];

        try {
            randomAccessFile.seek(position);
            randomAccessFile.readFully(bytes);
        } catch (IOException e) {
            throw new OutOfRangeException("파일의 읽을 수 있는 범위를 초과하였습니다. --- 요청 position : " + position);
        }

        return bytes;
    }

    public static byte[] readAllFromIndexTo(FileChannel fileChannel, int startIndex, int count, int byteLength) {
        long startPosition = (long) startIndex * byteLength;
        int totalLength = count * byteLength;
        return readAllByPosition(fileChannel, startPosition, totalLength);
    }

    public static byte[] readAllByPosition(FileChannel fileChannel, long startPosition, int totalLength) {
        ByteBuffer byteBuffer = ByteBufferPool.getByteBuffer();

        try {
            fileChannel.read(byteBuffer, startPosition);
        } catch (IOException e) {
            throw new OutOfRangeException("파일의 읽을 수 있는 범위를 초과하였습니다. --- 요청 position : " + startPosition);
        }

        return readByteBuffer(byteBuffer, totalLength);
    }

    private static byte[] readByteBuffer(ByteBuffer byteBuffer, int totalLength) {
        byte[] bytes = getValidByteArray(byteBuffer, totalLength);

        byteBuffer.flip();
        byteBuffer.get(bytes);
        byteBuffer.compact();

        return bytes;
    }

    private static byte[] getValidByteArray(ByteBuffer byteBuffer, int totalLength) {
        int readBytes = byteBuffer.position();

        if (readBytes < totalLength) {
            log.warn("[BUFFER_UNDER_FLOW] 최대 1,024개의 데이터만 조회 가능합니다.");
            return new byte[readBytes];
        }

        return new byte[totalLength];
    }
}

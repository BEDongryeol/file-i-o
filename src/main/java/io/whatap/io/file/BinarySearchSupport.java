package io.whatap.io.file;

import io.whatap.common.data.exception.NotFoundLogException;
import io.whatap.common.io.exception.IllegalInputStreamException;
import io.whatap.common.io.support.ByteLengthProvider;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Optional;

/**
 * Copyright whatap Inc since 2023/03/17
 * Created by Larry on 2023/03/17
 * Email : inwoo.server@gmail.com
 */
@Slf4j
public class BinarySearchSupport {

    private static final int ZERO = 0;

    private final RandomAccessFile randomAccessFile;
    private final long maxPosition;
    private final int byteLength;

    private BinarySearchSupport(RandomAccessFile randomAccessFile, int byteLength) throws IOException {
        this.randomAccessFile = randomAccessFile;
        this.maxPosition = randomAccessFile.length() - 1;
        this.byteLength = byteLength;
    }

    public static BinarySearchSupport valueOf(RandomAccessFile randomAccessFile, int byteLength) {
        try {
            return new BinarySearchSupport(randomAccessFile, byteLength);
        } catch (IOException e) {
            throw new IllegalInputStreamException(e);
        }
    }

    public byte[] readLogsByTimeBetween(long startTime, long endTime) {

        int midPosition = getMiddlePosition();
        long midTime = getMiddleTime(midPosition);

        int startPosition, endPosition;
        if (startTime < midTime) {
            if (endTime < midTime) {
                startPosition = getPosition(startTime, ZERO, midPosition);
                endPosition = getPosition(endTime, startPosition, midPosition);
            } else {
                startPosition = getPosition(startTime, ZERO, midPosition);
                endPosition = getPosition(endTime, midPosition, maxPosition);
            }
        } else {
            startPosition = getPosition(startTime, midPosition, maxPosition);
            endPosition = getPosition(endTime, startPosition, maxPosition);
        }

        int numberOfLog = (endPosition - startPosition) / byteLength + 1;
        if (numberOfLog > ZERO) {
            int totalBytes = byteLength * numberOfLog;
            return FixedLengthReader.readAllByPosition(randomAccessFile.getChannel(), startPosition, totalBytes);
        }

        return new byte[0];
    }

    public int getMiddlePosition() {
        return (int) ((maxPosition + 1 - byteLength) >> 1);
    }

    private long getMiddleTime(int midPosition) {
        try {
            randomAccessFile.seek(midPosition);
            return randomAccessFile.readLong();
        } catch (IOException e) {
            throw new IllegalInputStreamException(e);
        }
    }

    public byte[] readByTime(long targetTime)  {
        int position = getPosition(targetTime, ZERO, maxPosition);
        return FixedLengthReader.readByPosition(randomAccessFile, position, ByteLengthProvider.REQUEST_LOG_PACK);
    }

    private int getPosition(long targetTime, long low, long high)  {
        return searchPositionWithBinary(targetTime, low, high)
                .orElseThrow(() -> new NotFoundLogException("존재하지 않는 데이터 입니다. ---- time : " + targetTime));
    }

    private Optional<Integer> searchPositionWithBinary(long targetTime, long low, long high) {
        long cur;

        try {
            while (high >= low) {
                long mid = (low + high) / 2;
                int seek = (int) ((mid / byteLength) * byteLength);

                randomAccessFile.seek(seek);
                cur = randomAccessFile.readLong();

                if (targetTime < cur) {
                    high = mid - 1;
                } else if (targetTime > cur) {
                    low = mid + 1;
                } else {
                    return Optional.of(seek);
                }
            }
        } catch (IOException e) {
            throw new IllegalInputStreamException("[FILE_READER] RandomAccessFile 읽기에 실패하였습니다.");
        }
        return Optional.empty();
    }

}

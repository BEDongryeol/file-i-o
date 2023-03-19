package io.whatap.repository;

import io.whatap.data.RequestLogPack;
import io.whatap.io.data.DataReader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Copyright whatap Inc since 2023/03/13
 * Created by Larry on 2023/03/13
 * Email : inwoo.server@gmail.com
 */
public class FileRepositoryTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(FileRepository.class);
    FileRepository fileRepository = ac.getBean(FileRepository.class);
    String REQUEST_FILE_NAME = "log-request.db";

    // 고정 데이터의 총 byte 크기
    int fixedLength = 32;
    int longSize = 8;
    byte[] objectBytes = new byte[fixedLength];
    // 시간을 담을 배열
    ByteBuffer buffer = ByteBuffer.allocate(longSize);
    long target = 1645887944445L;

    long start;
    long end;


    @Before
    public void setUp() throws Exception {
        start = System.currentTimeMillis();
    }

    @After
    public void tearDown() throws Exception {
        end = System.currentTimeMillis();

        long time = end - start;
        System.out.println("time = " + time);
        start = 0L;
        end = 0L;
    }

    @Test
    public void createFileTest() throws IOException {

        String fileName = "test-create-file.db";
        File file1 = new File(fileName);


        if (!file1.exists()) {
            if (file1.createNewFile()) {
                System.out.println("성공");
            }
        }

    }

    @Test
    public void testing() throws Exception {
        // given
        RandomAccessFile raf = fileRepository.getRandomAccessFile(REQUEST_FILE_NAME);

        long high = raf.length();
        long totalDataSize = high / fixedLength;

        // when
        System.out.println("totalDataSize = " + totalDataSize);

        long index = binarySearch(raf, target);
        raf.seek(index);
        raf.readFully(objectBytes);

        DataReader dataReader = DataReader.typeOfByteArray(objectBytes);
        RequestLogPack requestLogPack = RequestLogPack.create(dataReader);
        System.out.println("requestLogPack = " + requestLogPack);
    }

    @Test
    public void binarySearchTest() throws Exception {
        // given

        RandomAccessFile raf = fileRepository.getRandomAccessFile(REQUEST_FILE_NAME);

        long high = raf.length();
        long totalDataSize = high / fixedLength;
        System.out.println("totalDataSize = " + totalDataSize);

        // when
        long index = binarySearchWithChannel(raf, target);
        raf.seek(index);
        raf.readFully(objectBytes);

        DataReader dataReader = DataReader.typeOfByteArray(objectBytes);
        RequestLogPack requestLogPack = RequestLogPack.create(dataReader);
        System.out.println("requestLogPack = " + requestLogPack);
    }

    private long binarySearch(RandomAccessFile raf, long target) throws IOException {

        long low = 0;
        long high = raf.length() - 1;
        long cur;

        while (high >= low) {
            // 1. 범위 중간에 있는 데이터 탐색
            long mid = (low + high) / 2;
            long seek = ((mid / 32) * 32);  // 32 : 고정 데이터 크기, seek : 0, 32... 32바이트 데이터의 시작점

            // 2. 시간데이터 조회
            raf.seek(seek);
            cur = raf.readLong();

            if (target < cur) {
                high = mid - 1;
            } else if (target > cur) {
                low = mid + 1;
            } else {
                return seek;
            }
        }

        return -1;
    }

    private long binarySearchWithChannel(RandomAccessFile raf, long target) throws IOException {

        long index = -1;

        FileChannel channel = raf.getChannel();
        long fileSize = channel.size();
        long left = 0;
        long right = fileSize - 1;

        while (left <= right) {
            long mid = (left + right) / 2;
            long seek = ((mid / fixedLength) * fixedLength);

            channel.read(buffer, seek);
            buffer.flip();

            long time = buffer.getLong();
            if (time == target) {
                buffer.compact();
                index = seek;
                break;
            } else if (time < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }

            buffer.compact();
        }

        return index;
    }
}

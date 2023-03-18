package io.whatap.service;

import io.whatap.common.io.support.ByteLengthProvider;
import io.whatap.io.file.BinarySearchSupport;
import io.whatap.io.file.FixedLengthReader;
import io.whatap.io.file.SequentialReader;
import io.whatap.repository.FileRepository;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;

/**
 * Copyright whatap Inc since 2023/03/08
 * Created by Larry on 2023/03/08
 * Email : inwoo.server@gmail.com
 */
@Service
public class FileService {

    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public byte[] readAll(String fileName) throws IOException {
        File file = fileRepository.getFile(fileName);
        return SequentialReader.readAll(Files.newInputStream(file.toPath()));
    }

    public byte[] readRequestLogByIndex(String fileName, int index) {
        RandomAccessFile randomAccessFile = fileRepository.getRandomAccessFile(fileName);
        return FixedLengthReader.readByIndex(randomAccessFile, index, ByteLengthProvider.REQUEST_LOG_PACK);
    }

    public byte[] readRequestLogByTime(String fileName, long time) {
        RandomAccessFile randomAccessFile = fileRepository.getRandomAccessFile(fileName);
        BinarySearchSupport binarySearchSupport = BinarySearchSupport.valueOf(randomAccessFile, ByteLengthProvider.REQUEST_LOG_PACK);
        return binarySearchSupport.readByTime(time);
    }

    public byte[] readRequestLogsFromIndexTo(String fileName, int startIndex, int count) {
        RandomAccessFile randomAccessFile = fileRepository.getRandomAccessFile(fileName);
        return FixedLengthReader.readAllFromIndexTo(randomAccessFile.getChannel(), startIndex, count, ByteLengthProvider.REQUEST_LOG_PACK);
    }

    public byte[] readRequestLogsByTimeBetween(String fileName, long startTime, long endTime) {
        RandomAccessFile randomAccessFile = fileRepository.getRandomAccessFile(fileName);
        BinarySearchSupport binarySearchSupport = BinarySearchSupport.valueOf(randomAccessFile, ByteLengthProvider.REQUEST_LOG_PACK);
        return binarySearchSupport.readLogsByTimeBetween(startTime, endTime);
    }

}

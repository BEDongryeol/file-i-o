package io.whatap.service;

import io.whatap.data.RequestLogPack;
import io.whatap.data.ServerLogPack;
import io.whatap.io.FileReader;
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
        File file = fileRepository.loadFileByName(fileName);
        return FileReader.readAll(Files.newInputStream(file.toPath()));
    }

    public byte[] readRequestLogAt(String fileName, int index) {
        RandomAccessFile randomAccessFile = fileRepository.loadRandomAccessFileByNameReadOnly(fileName);
        return FileReader.readBytesAt(randomAccessFile, index, RequestLogPack.class);
    }

    public byte[] readServerLogAt(String fileName, int index) {
        RandomAccessFile randomAccessFile = fileRepository.loadRandomAccessFileByNameReadOnly(fileName);
        return FileReader.readBytesAt(randomAccessFile, index, ServerLogPack.class);
    }

}

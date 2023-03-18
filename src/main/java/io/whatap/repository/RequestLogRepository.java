package io.whatap.repository;

import io.whatap.data.RequestLogPackLength;
import io.whatap.io.data.DataReader;
import io.whatap.io.data.DataWriter;
import io.whatap.io.file.FileWriter;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Copyright whatap Inc since 2023/03/06
 * Created by Larry on 2023/03/06
 * Email : inwoo.server@gmail.com
 */
@Repository
public class RequestLogRepository {

    private final FileRepository fileRepository;

    public RequestLogRepository(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public Boolean save(String fileName, RequestLogPackLength log) {
        File file = fileRepository.getFile(fileName);

        DataWriter dataWriter = DataWriter.typeOfByteArray();
        log.write(dataWriter);

        FileWriter.save(file, dataWriter.toByteArray(), true);
        return file.exists();
    }

    public RequestLogPackLength read(byte[] bytes) {
        DataReader dataReader = DataReader.typeOfByteArray(bytes);
        return RequestLogPackLength.create(dataReader);
    }

    public List<RequestLogPackLength> readLogs(byte[] bytes) {
        DataReader dataReader = DataReader.typeOfByteArray(bytes);
        return RequestLogPackLength.createLogs(dataReader);
    }

}
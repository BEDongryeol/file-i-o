package io.whatap.repository;

import io.whatap.data.RequestLogPack;
import io.whatap.io.data.DataReader;
import io.whatap.io.data.DataWriter;
import io.whatap.io.file.FileWriter;
import org.springframework.stereotype.Repository;

import java.io.File;
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

    public Boolean save(String fileName, RequestLogPack log) {
        File file = fileRepository.getFileByName(fileName);

        DataWriter dataWriter = DataWriter.typeOfByteArray();
        log.write(dataWriter);

        return FileWriter.save(file, dataWriter.toByteArray(), true);
    }

    public RequestLogPack read(byte[] bytes) {
        DataReader dataReader = DataReader.typeOfByteArray(bytes);
        return RequestLogPack.create(dataReader);
    }

    public List<RequestLogPack> readLogs(byte[] bytes) {
        DataReader dataReader = DataReader.typeOfByteArray(bytes);
        return RequestLogPack.createLogs(dataReader);
    }

}
package io.whatap.repository;

import io.whatap.data.RequestLogPack;
import io.whatap.io.DataReader;
import io.whatap.io.DataWriter;
import io.whatap.io.FileWriter;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;

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
        File file = fileRepository.loadFileByName(fileName);

        DataWriter dataWriter = DataWriter.typeOfByteArray();
        log.write(dataWriter);

        FileWriter.save(file, dataWriter.toByteArray(), true);
        return file.exists();
    }

    public RequestLogPack read(byte[] bytes) throws IOException {
        DataReader dataReader = DataReader.typeOfByteArray(bytes);
        return RequestLogPack.create(dataReader);
    }

}
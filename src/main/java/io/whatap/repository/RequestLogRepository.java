package io.whatap.repository;

import io.whatap.data.RequestLogPack;
import io.whatap.io.DataReader;
import io.whatap.io.DataWriter;
import io.whatap.io.FileReader;
import io.whatap.io.FileWriter;
import io.whatap.io.support.FileLoader;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Copyright whatap Inc since 2023/03/06
 * Created by Larry on 2023/03/06
 * Email : inwoo.server@gmail.com
 */
@Repository
public class RequestLogRepository {

    public void save(String fileName, RequestLogPack log) {
        File file = FileLoader.loadByFileName(fileName);
        DataWriter dataWriter = DataWriter.typeOfByteArray();
        log.write(dataWriter);

        FileWriter.save(file, dataWriter.toByteArray(), true);
    }

    public RequestLogPack read(String requestFileName) throws IOException {
        File file = FileLoader.loadByFileName(requestFileName);

        byte[] bytes = FileReader.readAll(Files.newInputStream(file.toPath()));
        DataReader dataReader = DataReader.typeOfByteArray(bytes);
        return RequestLogPack.create(dataReader);
    }
}
package io.file.repository;

import io.file.data.ServerLogPack;
import io.file.io.data.DataReader;
import io.file.io.data.DataWriter;
import io.file.io.file.FileWriter;
import io.file.io.file.SequentialReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Repository
public class ServerLogRepository {

    public void save(String fileName, ServerLogPack log) throws IOException {
        DataWriter dataWriter = DataWriter.typeOfByteArray();
        log.write(dataWriter);
        ClassPathResource resource = new ClassPathResource(fileName);
        File file = resource.getFile();
        System.out.println(file.getAbsoluteFile());
        FileWriter.save(file, dataWriter.toByteArray(), true);
    }

    public ServerLogPack read(String fileName) throws IOException {
        ClassPathResource resource = new ClassPathResource(fileName);
        File file = resource.getFile();
        byte[] bytes = SequentialReader.readAll(Files.newInputStream(file.toPath()));
        DataReader dataReader = DataReader.typeOfByteArray(bytes);
        return ServerLogPack.create(dataReader);
    }
}

package io.whatap.repository;

import io.whatap.data.ApplicationLogPack;
import io.whatap.io.DataReader;
import io.whatap.io.DataWriter;
import io.whatap.io.FileReader;
import io.whatap.io.FileWriter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Repository
public class ApplicationLogRepository {
    public void save(String fileName, ApplicationLogPack log) throws IOException {
        DataWriter dataWriter = DataWriter.typeOfByteArray();
        log.write(dataWriter);
        ClassPathResource resource = new ClassPathResource(fileName);
        File file = resource.getFile();
        FileWriter.save(file, dataWriter.toByteArray(), true);
    }

    public ApplicationLogPack read(String fileName) throws IOException {
        ClassPathResource resource = new ClassPathResource(fileName);
        File file = resource.getFile();
        byte[] bytes = FileReader.readAll(Files.newInputStream(file.toPath()));
        DataReader dataReader = DataReader.typeOfByteArray(bytes);
        return ApplicationLogPack.create(dataReader);
    }

}

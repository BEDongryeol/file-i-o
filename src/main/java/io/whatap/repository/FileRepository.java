package io.whatap.repository;

import io.whatap.common.io.exception.FileLoadException;
import io.whatap.common.io.support.FileAccessMode;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Copyright whatap Inc since 2023/03/07
 * Created by Larry on 2023/03/07
 * Email : inwoo.server@gmail.com
 */
@Repository
public class FileRepository {

    public File getFile(String fileName) {
        File file;
        ClassPathResource resource = new ClassPathResource(fileName);

        try {
            file = resource.getFile();
        } catch (IOException e) {
            throw new FileLoadException("해당 파일이 존재하지 않습니다. fileName : " + fileName);
        }

        return file;
    }

    public RandomAccessFile getRandomAccessFile(String fileName) {

        RandomAccessFile randomAccessFile;

        try {
            randomAccessFile = new RandomAccessFile(getFile(fileName), FileAccessMode.READ_ONLY.getMode());
        } catch (FileNotFoundException e) {
            throw new FileLoadException("해당 파일이 존재하지 않습니다. fileName : " + fileName);
        }

        return randomAccessFile;
    }

}

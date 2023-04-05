package io.file.repository;

import io.file.common.io.exception.FileLoadException;
import io.file.common.io.exception.RetryFailedException;
import io.file.common.io.support.FileAccessMode;
import io.file.io.file.FileWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.*;

/**
 * Copyright whatap Inc since 2023/03/07
 * Created by Larry on 2023/03/07
 * Email : inwoo.server@gmail.com
 */
@Repository
@Slf4j
public class FileRepository {

    public File getFileByName(String fileName) {
        try {
            ClassPathResource resource = new ClassPathResource(fileName);
            return resource.getFile();
        } catch (IOException e) {
            return createAndGetFile(fileName);
        }
    }

    public File createAndGetFile(String fileName) {
        int maxRetry = 5;

        while (maxRetry-- > 0) {
            log.info("파일이 존재하지 않아 생성을 시도합니다. --- fileName : " + fileName);

            if (FileWriter.createFile(fileName)) {
                log.info("파일이 성공적으로 생성되었습니다. --- fileName : " + fileName);
                return getFileByName(fileName);
            }
        }

        throw new RetryFailedException();
    }

    public RandomAccessFile getRandomAccessFile(String fileName) {
        try {
            return new RandomAccessFile(getFileByName(fileName), FileAccessMode.READ_ONLY.getMode());
        } catch (FileNotFoundException e) {
            throw new FileLoadException("해당 파일이 존재하지 않습니다. fileName : " + fileName);
        }
    }


}

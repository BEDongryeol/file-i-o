package io.whatap.io.file;

import io.whatap.common.io.exception.DataIOException;
import io.whatap.common.io.exception.RetryFailedException;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class FileWriter {

    private static final String ABSOLUTE_PATH = "target/classes/";

    private FileWriter(){}

    public static Boolean save(File file, byte[] bytes, boolean append){
        if (file.exists() == false) {
            file.mkdirs();
        }
        try (FileOutputStream fos = new FileOutputStream(file, append)) {
            fos.write(bytes);
            fos.flush();
        } catch (IOException e) {
            throw new DataIOException(e);
        }
        return Boolean.TRUE;
    }

    public static Boolean createFile(String fileName) {
        try {
            File file = new File(ABSOLUTE_PATH + fileName);
            if (!file.exists()) return file.createNewFile();
        } catch (IOException e) {
            log.error("파일 생성에 실패하였습니다. --- fileName : " + fileName);
        }
        return Boolean.FALSE;
    }
}

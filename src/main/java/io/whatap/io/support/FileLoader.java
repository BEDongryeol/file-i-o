package io.whatap.io.support;

import io.whatap.io.exception.FileLoadException;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

/**
 * Copyright whatap Inc since 2023/03/07
 * Created by Larry on 2023/03/07
 * Email : inwoo.server@gmail.com
 */
public class FileLoader {

    public static File loadByFileName(String fileName) {
        File file;
        ClassPathResource resource = new ClassPathResource(fileName);

        try {
            file = resource.getFile();
        } catch (IOException e) {
            throw new FileLoadException("데이터를 저장할 파일이 존재하지 않습니다. fileName : " + fileName);
        }
        return file;
    }
}

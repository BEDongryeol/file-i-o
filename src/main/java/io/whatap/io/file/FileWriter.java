package io.whatap.io.file;

import io.whatap.common.io.exception.DataIOException;

import java.io.*;

public class FileWriter {

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
}

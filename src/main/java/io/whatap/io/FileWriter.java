package io.whatap.io;

import io.whatap.common.io.exception.DataIOException;

import java.io.*;

public class FileWriter {

    private FileWriter(){}

    public static void save(File file, byte[] bytes, boolean append){
        if (file.exists() == false) {
            file.mkdirs();
        }
        try (FileOutputStream fos = new FileOutputStream(file, append)) {
            fos.write(bytes);
            fos.flush();
        } catch (IOException e) {
            throw new DataIOException(e);
        }
    }
}

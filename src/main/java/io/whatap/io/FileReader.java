package io.whatap.io;

import io.whatap.io.exception.DataIOException;

import java.io.*;

public class FileReader {

    private FileReader(){}

    public static byte[] readAll(InputStream fin){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buff = new byte[1024 * 4];
        try {
            int n = fin.read(buff);
            while (n >= 0) {
                bos.write(buff, 0, n);
                n = fin.read(buff);
            }
        } catch (IOException e) {
            throw new DataIOException(e);
        }
        return bos.toByteArray();
    }

    public static byte[] read(InputStream fin, int len){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buff = new byte[Math.min(1024 * 4, len)];
        int remain = len;
        try {
            while (remain > 0 ) {
                int n = fin.read(buff);
                bos.write(buff, 0, n);
                remain -= n;
            }
        } catch (IOException e) {
            throw new DataIOException(e);
        }
        return bos.toByteArray();
    }
}

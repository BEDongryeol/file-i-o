package io.whatap.io;

import io.whatap.common.io.exception.DataIOException;
import io.whatap.common.io.exception.OutOfRangeException;
import io.whatap.common.io.support.ByteArrayProvider;
import io.whatap.data.AbstractPack;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

public class FileReader {

    private FileReader() {}

    public static byte[] readAll(InputStream fin) {
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

    public static byte[] read(InputStream fin, int len) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buff = new byte[Math.min(1024 * 4, len)];
        int remain = len;
        try {
            while (remain > 0) {
                int n = fin.read(buff);
                bos.write(buff, 0, n);
                remain -= n;
            }
        } catch (IOException e) {
            throw new DataIOException(e);
        }
        return bos.toByteArray();
    }

    public static byte[] readBytesAt(RandomAccessFile file, int index, Class<? extends AbstractPack> type) {
        byte[] bytes = ByteArrayProvider.getByteArray(type);

        try {
            file.seek((long) bytes.length * index);
            file.readFully(bytes);
        } catch (IOException e) {
            throw new OutOfRangeException("파일의 읽을 수 있는 범위를 초과하였습니다. --- 요청 index : " + index);
        }
        return bytes;
    }
}

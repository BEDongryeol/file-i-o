package io.file.io.data;

import io.file.common.io.exception.DataIOException;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class DataReader {
    private int offset = 0;
    private DataInputStream din;
    private DataInput inner;

    private DataReader(){
    }

    public static DataReader typeOfByteArray(byte[] buff){
        DataReader dataReader = new DataReader();
        dataReader.din = new DataInputStream(new BufferedInputStream(new ByteArrayInputStream(buff)));
        dataReader.inner = dataReader.din;
        return dataReader;
    }

    public static DataReader typeOfRandomAccess(RandomAccessFile file){
        DataReader dataWriter = new DataReader();
        dataWriter.din = null;
        dataWriter.inner = file;
        return dataWriter;
    }

    public boolean readBoolean(){
        this.offset++;
        try {
            return this.inner.readBoolean();
        } catch (IOException e) {
            throw new DataIOException(e);
        }
    }

    public byte readByte(){
        this.offset++;
        try {
            return this.inner.readByte();
        } catch (IOException e) {
            throw new DataIOException(e);
        }
    }

    public short readShort(){
        this.offset += 2;
        try {
            return this.inner.readShort();
        } catch (IOException e) {
            throw new DataIOException(e);
        }
    }

    public int readInt(){
        this.offset += 4;
        try {
            return this.inner.readInt();
        } catch (IOException e) {
            throw new DataIOException(e);
        }
    }

    public float readFloat(){
        this.offset += 4;
        try {
            return this.inner.readFloat();
        } catch (IOException e) {
            throw new DataIOException(e);
        }
    }

    public long readLong(){
        this.offset += 8;
        try {
            return this.inner.readLong();
        } catch (IOException e) {
            throw new DataIOException(e);
        }
    }

    public double readDouble(){
        this.offset += 8;
        try {
            return this.inner.readDouble();
        } catch (IOException e) {
            throw new DataIOException(e);
        }
    }

    final private static String EMPTY = "";
    public String readString(){
        try {
            this.offset += ByteWriter.STRING_VALUE_MAX_HEADER_SIZE;
            int len = this.inner.readInt();
            byte[] buff = this.readBlob(len);
            if (buff.length == 0) {
                return EMPTY;
            }
            return new String(buff, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new DataIOException(e);
        }
    }

    public byte[] readBlob(int len){
        this.offset += len;
        byte[] buff = new byte[len];
        try {
            this.inner.readFully(buff);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return buff;
    }

    public void close() {
        try {
            if (this.inner instanceof RandomAccessFile) {
                ((RandomAccessFile) this.inner).close();
            } else if (this.inner instanceof InputStream) {
                ((InputStream) this.inner).close();
            }
        } catch (IOException e) {
            throw new DataIOException(e);
        }
    }

    public int skipBytes(int n){
        this.offset += n;
        try {
            return this.inner.skipBytes(n);
        } catch (IOException e) {
            throw new DataIOException(e);
        }
    }

    public int available(){
        try {
            return this.din == null ? 0 : this.din.available();
        } catch (IOException e) {
            throw new DataIOException(e);
        }
    }

    public int getOffset() {
        return offset;
    }
}

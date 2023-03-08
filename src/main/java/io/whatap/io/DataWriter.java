package io.whatap.io;

import io.whatap.common.io.exception.DataIOException;

import java.io.*;

public class DataWriter {
    private int written = 0;
    private ByteArrayOutputStream bout;
    private DataOutput inner;

    private DataWriter(){
    }

    public static DataWriter typeOfByteArray(){
        DataWriter dataWriter = new DataWriter();
        dataWriter.bout = new ByteArrayOutputStream();
        dataWriter.inner = new DataOutputStream(dataWriter.bout);
        return dataWriter;
    }

    public static DataWriter typeOfRandomAccess(RandomAccessFile file) {
        DataWriter dataWriter = new DataWriter();
        dataWriter.bout = null;
        dataWriter.inner = file;
        return dataWriter;
    }

    public DataWriter writeBoolean(boolean value){
        this.written++;
        try {
            this.inner.write(ByteWriter.toBytes(value));
        } catch (IOException e) {
            throw new DataIOException(e);
        }
        return this;
    }

    public DataWriter writeByte(int value){
        this.written++;
        try {
            this.inner.write(ByteWriter.toBytes((byte) value));
        } catch (IOException e) {
            throw new DataIOException(e);
        }
        return this;
    }

    public DataWriter writeShort(int value){
        this.written += 2;
        try {
            this.inner.write(ByteWriter.toBytes((short) value));
        } catch (IOException e) {
            throw new DataIOException(e);
        }
        return this;
    }

    public DataWriter writeInt(int value){
        this.written += 4;
        try {
            this.inner.write(ByteWriter.toBytes(value));
        } catch (IOException e) {
            throw new DataIOException(e);
        }
        return this;
    }

    public DataWriter writeLong(long value){
        this.written += 8;
        try {
            this.inner.write(ByteWriter.toBytes(value));
        } catch (IOException e) {
            throw new DataIOException(e);
        }
        return this;
    }

    public DataWriter writeFloat(float value){
        this.written += 4;
        try {
            this.inner.write(ByteWriter.toBytes(value));
        } catch (IOException e) {
            throw new DataIOException(e);
        }
        return this;
    }

    public DataWriter writeDouble(double value){
        this.written += 8;
        try {
            this.inner.write(ByteWriter.toBytes(value));
        } catch (IOException e) {
            throw new DataIOException(e);
        }
        return this;
    }

    public DataWriter writeString(String value){
        byte[] bytes = ByteWriter.toBytes(value);
        return writeBlob(bytes);
    }

    public DataWriter writeBlob(byte[] value){
        try {
            this.written += value.length;
            this.inner.write(value);
        } catch (IOException e) {
            throw new DataIOException(e);
        }
        return this;
    }

    public void close(){
        try {
            if (this.inner instanceof RandomAccessFile){
                ((RandomAccessFile) this.inner).close();
            }else if (this.inner instanceof OutputStream){
                ((OutputStream) this.inner).close();
            }
        } catch (IOException e) {
            throw new DataIOException(e);
        }
    }

    public byte[] toByteArray(){
        return bout.toByteArray();
    }

    public void flush(){
        try {
            if (this.inner instanceof OutputStream){
                ((OutputStream)this.inner).flush();
            }
        } catch (IOException e) {
            throw new DataIOException(e);
        }
    }
}

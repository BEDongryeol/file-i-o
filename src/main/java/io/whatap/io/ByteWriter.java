package io.whatap.io;

import java.nio.charset.StandardCharsets;

public class ByteWriter {

    private ByteWriter(){}

    public static byte[] toBytes(byte value){
        byte[] buf = new byte[1];
        buf[0] = value;
        return buf;
    }

    public static byte[] toBytes(boolean value){
        byte[] buf = new byte[1];
        buf[0] = value ? (byte) 1 : (byte) 0;
        return buf;
    }

    public static byte[] toBytes(short value){
        byte[] buf = new byte[2];
        buf[0] = (byte) ((value >>> 8) & 0xFF);
        buf[1] = (byte) ((value >>> 0) & 0xFF);
        return buf;
    }

    public static byte[] toBytes(int value){
        byte[] buf = new byte[4];
        buf[0] = (byte) ((value >>> 24) & 0xFF);
        buf[1] = (byte) ((value >>> 16) & 0xFF);
        buf[2] = (byte) ((value >>> 8) & 0xFF);
        buf[3] = (byte) ((value >>> 0) & 0xFF);
        return buf;
    }

    public static byte[] toBytes(long value){
        byte[] buf = new byte[8];
        buf[0] = (byte) ((value >>> 56) & 0xFF);
        buf[1] = (byte) ((value >>> 48) & 0xFF);
        buf[2] = (byte) ((value >>> 40) & 0xFF);
        buf[3] = (byte) ((value >>> 32) & 0xFF);
        buf[4] = (byte) ((value >>> 24) & 0xFF);
        buf[5] = (byte) ((value >>> 16) & 0xFF);
        buf[6] = (byte) ((value >>> 8) & 0xFF);
        buf[7] = (byte) ((value >>> 0) & 0xFF);
        return buf;
    }

    static final int STRING_VALUE_MAX_HEADER_SIZE = 4;
    public static byte[] toBytes(String value){
        byte[] valueArray = value.getBytes(StandardCharsets.UTF_8);
        byte[] buf = new byte[STRING_VALUE_MAX_HEADER_SIZE + valueArray.length];
        byte[] headerArray = ByteWriter.toBytes(valueArray.length);
        System.arraycopy(headerArray, 0, buf, 0, STRING_VALUE_MAX_HEADER_SIZE);
        System.arraycopy(valueArray, 0, buf, 4, valueArray.length);
        return buf;
    }

    public static byte[] toBytes(float value){
        return toBytes(Float.floatToRawIntBits(value));
    }

    public static byte[] toBytes(double value){
        return toBytes(Double.doubleToLongBits(value));
    }

}

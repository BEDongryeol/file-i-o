package io.whatap.data.support;

import io.whatap.data.AbstractPack;
import io.whatap.data.ApplicationLogPack;
import io.whatap.data.RequestLogPack;
import io.whatap.data.ServerLogPack;
import io.whatap.data.exception.IllegalDataTypeException;
import lombok.Getter;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.function.Function;

/**
 * Copyright whatap Inc since 2023/03/08
 * Created by Larry on 2023/03/08
 * Email : inwoo.server@gmail.com
 */
@Getter
public enum ByteArrayProvider {

    REQUEST_LOG(RequestLogPack.class, byte[]::new),
    APPLICATION_LOG(ApplicationLogPack.class, byte[]::new),
    SERVER_LOG(ServerLogPack.class, byte[]::new);

    private static final int SUPER_CLASS_BYTES = getDataLength(AbstractPack.class);

    private final Object type;
    private final Function<Integer, byte[]> provider;

    ByteArrayProvider(Object type, Function<Integer, byte[]> provider) {
        this.type = type;
        this.provider = provider;
    }

    public static byte[] getByteArray(Class<? extends AbstractPack> type) {
        return Arrays.stream(values())
                .filter(provider -> provider.type.equals(type))
                .findFirst()
                .orElseThrow(() -> new IllegalDataTypeException("일치하는 객체가 존재하지 않습니다. type : " + type.getName()))
                .provider.apply(getDataLength(type) + SUPER_CLASS_BYTES);
    }

    private static int getDataLength(Class<? extends AbstractPack> type) {
        return Arrays.stream(type.getDeclaredFields())
                .map(Field::getType)
                .map(DataTypeSize::getBytes)
                .reduce(0, Integer::sum);
    }

    @Getter
    private enum DataTypeSize {
        BYTE(byte.class, Byte.BYTES),
        SHORT(short.class, Short.BYTES),
        INT(int.class, Integer.BYTES),
        LONG(long.class, Long.BYTES);

        private final Object type;
        private final int size;

        DataTypeSize(Object type, int size) {
            this.type = type;
            this.size = size;
        }

        private static int getBytes(Object type) {
            return Arrays.stream(values())
                    .filter(dataTypeSize -> dataTypeSize.type.equals(type))
                    .findFirst()
                    .orElseThrow(() -> new IllegalDataTypeException("일치하는 자료형이 존재하지 않습니다. type : " + type.getClass().getName()))
                    .size;
        }
    }
}

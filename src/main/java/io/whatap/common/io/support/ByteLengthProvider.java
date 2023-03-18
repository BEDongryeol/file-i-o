package io.whatap.common.io.support;

import io.whatap.common.data.exception.IllegalDataTypeException;
import io.whatap.data.AbstractPack;
import io.whatap.data.FixedLengthAbstractPack;
import io.whatap.data.RequestLogPackLength;
import lombok.Getter;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Copyright whatap Inc since 2023/03/16
 * Created by Larry on 2023/03/16
 * Email : inwoo.server@gmail.com
 */
public class ByteLengthProvider {

    private static final int SUPER_CLASS = getAbstractPackDataLength();
    public static final int REQUEST_LOG_PACK = getDataLength(RequestLogPackLength.class);

    private static int getDataLength(Class<? extends FixedLengthAbstractPack> type) {
        Integer byteSize = Arrays.stream(type.getDeclaredFields())
                .map(Field::getType)
                .map(DataTypeSize::getBytes)
                .reduce(0, Integer::sum);

        return byteSize + SUPER_CLASS;
    }

    private static int getAbstractPackDataLength() {
        return Arrays.stream(AbstractPack.class.getDeclaredFields())
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

        public static int getBytes(Object type) {
            return Arrays.stream(values())
                    .filter(dataTypeSize -> dataTypeSize.type.equals(type))
                    .findFirst()
                    .orElseThrow(() -> new IllegalDataTypeException("일치하는 자료형이 존재하지 않습니다. type : " + type.getClass().getName()))
                    .size;
        }
    }
}


package io.whatap.common.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Supplier;

/**
 * Copyright whatap Inc since 2023/03/07
 * Created by Larry on 2023/03/07
 * Email : inwoo.server@gmail.com
 */
public class DateTimeUtil {
    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String toString(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
    }

    public static Long toLong(LocalDateTime dateTime) {
        return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static LocalDateTime toLocalDateTime(long epochTime) {
        return Instant.ofEpochMilli(epochTime)
                .atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static Long getNowUnixTime() {
        return toLong(currentTimeSupplier().get());
    }

    private static Supplier<LocalDateTime> currentTimeSupplier() {
        return LocalDateTime::now;
    }
}

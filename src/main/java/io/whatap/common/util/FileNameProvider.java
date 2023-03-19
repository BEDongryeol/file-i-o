package io.whatap.common.util;

import lombok.Getter;

import java.time.LocalDate;

/**
 * Copyright whatap Inc since 2023/03/19
 * Created by Larry on 2023/03/19
 * Email : inwoo.server@gmail.com
 */
@Getter
public class FileNameProvider {

    private static final String EXTENSION = ".db";

    public static String application(long time) {
        return createFileName(FileName.APPLICATION.getFileName(), time);
    }

    public static String request(long time) {
        return createFileName(FileName.REQUEST.getFileName(), time);
    }

    public static String server(long time) {
        return createFileName(FileName.SERVER.getFileName(), time);
    }

    private static String createFileName(String fileName, long time) {
        String result = fileName + dateToString(time) + EXTENSION;

        return result.intern();

    }

    private static String dateToString(long time) {
        LocalDate localDate = DateTimeUtil.toLocalDate(time);
        return DateTimeUtil.toString(localDate);
    }

    @Getter
    private enum FileName {

        APPLICATION("log-application-"),
        REQUEST("log-request-"),
        SERVER("log-server-");

        private final String fileName;

        FileName(String fileName) {
            this.fileName = fileName;
        }
    }
}

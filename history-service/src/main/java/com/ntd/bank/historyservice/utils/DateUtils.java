package com.ntd.bank.historyservice.utils;

import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
}

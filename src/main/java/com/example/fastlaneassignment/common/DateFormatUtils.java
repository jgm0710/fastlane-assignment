package com.example.fastlaneassignment.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormatUtils {

    public static String format(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }

}

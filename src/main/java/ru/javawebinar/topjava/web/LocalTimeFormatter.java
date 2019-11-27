package ru.javawebinar.topjava.web;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

public class LocalTimeFormatter implements Formatter<LocalTime> {
    @Override
    public LocalTime parse(String time, Locale locale) throws ParseException {
        return parseLocalTime(time);
    }

    @Override
    public String print(LocalTime time, Locale locale) {
        return time.format(DateTimeFormatter.ISO_LOCAL_TIME);
    }
}

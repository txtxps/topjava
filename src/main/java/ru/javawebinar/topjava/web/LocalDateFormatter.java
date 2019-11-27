package ru.javawebinar.topjava.web;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;

public class LocalDateFormatter implements Formatter<LocalDate> {
    @Override
    public LocalDate parse(String date, Locale locale) throws ParseException {
        return parseLocalDate(date);
    }

    @Override
    public String print(LocalDate date, Locale locale) {
        return date.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}

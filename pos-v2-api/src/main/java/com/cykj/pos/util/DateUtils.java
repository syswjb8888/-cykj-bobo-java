package com.cykj.pos.util;

import com.cykj.common.constant.Constants;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DateUtils {

    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
    public static String localeDateTime2String(LocalDateTime date,DateTimeFormatter formatter) {
        return date.format(formatter);
    }
    public static String localeDate2String(LocalDate date,DateTimeFormatter formatter) {
        return date.format(formatter);
    }
    public static String Date2String(Date date,String formatterString) {
        LocalDateTime localDateTime = DateUtils.asLocalDateTime(date);
        return localDateTime.format(DateTimeFormatter.ofPattern(formatterString));
    }
    public static LocalDate stringToLocalDate(String time,DateTimeFormatter formatter) {
        return LocalDate.parse(time, formatter);
    }
    public static LocalDateTime stringToLocalDateTime(String time,DateTimeFormatter formatter) {
        return LocalDateTime.parse(time, formatter);
    }
    public static String getCaculateYearAndMonth(String thisMonth,String formatter){
        LocalDate localDate = LocalDate.now();
        if("last".equals(thisMonth)){
            localDate = localDate.minusMonths(1);
        }
        if(formatter.contains("-")){
            return localDate.format(DateTimeFormatter.ofPattern(formatter)).substring(0,7);
        }
       return localDate.format(DateTimeFormatter.ofPattern(formatter)).substring(0,6);
    }
    public static void main(String[] args){
        /*LocalDateTime localDateTime = LocalDateTime.now();
        String startTime = DateUtils.localeDateTime2String(localDateTime, Constants.DATETIME_FORMATTER);
        System.out.println(startTime);
        LocalDateTime endLocalTime = localDateTime.plusMinutes(5);
        String endTime = DateUtils.localeDateTime2String(endLocalTime, Constants.DATETIME_FORMATTER);
        System.out.println(endTime);*/
        String formatedDate = DateUtils.getCaculateYearAndMonth("this","yyyy-MM");
        System.out.println(formatedDate);
    }
}

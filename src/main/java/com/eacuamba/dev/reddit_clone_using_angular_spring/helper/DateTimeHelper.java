package com.eacuamba.dev.reddit_clone_using_angular_spring.helper;

import org.springframework.stereotype.Component;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

@Component
public class DateTimeHelper {
    public Instant getCurrentInstant(){
        return Instant.now();
    }
    public Date getCurrenteDate(){
        return new Date();
    }
    public Date convertLocalDateToDate(LocalDate localDate){
        Instant instant = Instant.from(localDate.atStartOfDay().atOffset(ZoneOffset.UTC));
        return Date.from(instant);
    }
    public LocalDate getCurrentLocalDate(){
        return LocalDate.now();
    }

    public String formatDateTime(LocalDateTime localDateTime) {
        if (localDateTime == null)
            return "";
        return localDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'ás' HH:mm:ss"));
    }

    public String formatDateTimeForFileName(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("dd'-'MM'-'yyyy 'ás' HH'.'mm'.'ss"));
    }

    public String formatDate(LocalDate localDate) {
        if (localDate == null)
            return "";
        return localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public String formatToSQLQuery(LocalDate localDate) {
        if (localDate == null)
            return "";
        return localDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
    }

    public String formatTime(LocalTime localTime){
        if (localTime == null)
            return "";
        return localTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public LocalDateTime dateToLocalDateTime(Date date){
        if(Objects.isNull(date))return null;
        Instant instant = date.toInstant();
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
}

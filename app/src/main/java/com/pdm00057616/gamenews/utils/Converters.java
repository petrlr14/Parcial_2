package com.pdm00057616.gamenews.utils;

import android.arch.persistence.room.TypeConverter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Converters {

    @TypeConverter
    public static Integer fromDate(String date) {
        if (date == null) {
            return null;
        } else {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
            long lon;
            try {
                lon = df.parse(date).getTime();
            } catch (Exception e) {
                e.printStackTrace();
                lon = (long) 0;
            }
            return (int) lon;
        }

    }
}

package it.saimao.maonote.entities;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {
    @TypeConverter
    public Date longToDate(Long timestamp) {
        return new Date(timestamp);
    }

    @TypeConverter
    public Long dateToLong(Date date) {
        return date.getTime();
    }

}

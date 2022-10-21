package com.openclassrooms.realestatemanager.MVVM.databases.room;

import android.util.Log;
import androidx.room.TypeConverter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Converters implements Serializable {

    //----------------
    // DATE CONVERTER
    //----------------
    @TypeConverter
    public static Date toDate(Long dateLong) {
        return dateLong == null ? null : new Date(dateLong);
    }

    @TypeConverter
    public static Long fromDate(Date date) {
        return date == null ? null : date.getTime();
    }

    //-----------------------------
    // ARRAYLIST<STRING> CONVERTER
    //-----------------------------
    @TypeConverter
    public List<String> gettingListFromString(String genreIds) {
        List<String> list = new ArrayList<>();

        String[] array = genreIds.split(",");
        for (String s : array) {
            if (!s.isEmpty()) {
                int Position = 0;
                s = s.substring(0, Position) + s.substring(Position + 1);
                s = s.replaceAll("(^\\[|]$)", "");
                list.add(s);
                Log.d("The list contains ", "" + s);
            }
        }
        return list;
    }

    @TypeConverter
    public String writingStringFromListInt(List<String> list) {
        StringBuilder genreIds = new StringBuilder();
        for (String i : list) {
            int Position = 0;
            i = i.substring(0, Position) + i.substring(Position + 1);
            i = i.replaceAll("(^\\[|]$)", "");
            genreIds.append(",").append(i);
            Log.d("GenreIds contains ", "" + i);
        }
        return genreIds.toString();
    }

    //-----------------------------
    // ARRAYLIST<INT> CONVERTER
    //-----------------------------
    @TypeConverter
    public List<Integer> gettingListIntFromString(String genreIds) {
        List<Integer> list = new ArrayList<>();

        String[] array = genreIds.split(",");

        for (String s : array) {
            if (!s.isEmpty()) {
                list.add(Integer.parseInt(s));
            }
        }
        return list;
    }

    @TypeConverter
    public String writingStringFromList(List<Integer> list) {
        StringBuilder genreIds = new StringBuilder();
        for (int i : list) {
            genreIds.append(",").append(i);
        }
        return genreIds.toString();
    }
}





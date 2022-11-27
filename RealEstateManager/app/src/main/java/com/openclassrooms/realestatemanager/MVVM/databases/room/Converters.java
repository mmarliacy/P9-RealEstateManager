package com.openclassrooms.realestatemanager.MVVM.databases.room;

import android.util.Log;
import androidx.room.TypeConverter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Converters implements Serializable {

    //-----------------------------
    // ARRAYLIST<STRING> CONVERTER
    //-----------------------------
    @TypeConverter
    public List<String> gettingListFromString(String genreIds) {
        List<String> list = new ArrayList<>();
        CharSequence pattern = "[";
        CharSequence pattern2 = "]";
        String[] array = genreIds.split(",");
        if (genreIds.contains(pattern) && genreIds.contains(pattern2)) {
            for (String s : array) {
                if (!s.isEmpty()) {
                    int Position = 0;
                    s = s.substring(0, Position) + s.substring(Position + 1);
                    s = s.replaceAll("(^\\[|]$)", "");
                    list.add(s);
                    Log.d("The list contains ", "" + s);
                }
            }
        } else {
            for (String s : array) {
                if (!s.isEmpty()) {
                    list.add(s);
                }
            }
        }

        return list;
    }

    @TypeConverter
    public String writingStringFromListInt(List<String> list) {
        String genreIds = "";
        if (list != null) {
            for (String i : list) {
                genreIds += "," + i;
            }
        }else {
                genreIds = "";
            }
            return genreIds;
    }
}





package com.openclassrooms.realestatemanager;

import static org.junit.Assert.assertEquals;
import com.openclassrooms.realestatemanager.utils.Utils;
import org.junit.Test;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UtilsUnitTest {

    @Test
    public void convertDollarToEuroTest(){
        int dollars = 300000;
        int euros = Utils.convertDollarToEuro(dollars);
        int eurosAfterConversion = (int) (dollars * 0.812);
        assertEquals(euros, eurosAfterConversion);
    }

    @Test
    public void converseDate(){
        String invertedDate = Utils.getTodayDate();
        String invertedDate2 = "2022/11/27";
        assertEquals(invertedDate, invertedDate2);
        String convertedDate = "27/11/2022";
        String OnConvertedDate = parseDateToddMMYyyy(invertedDate2);
        assertEquals(convertedDate, OnConvertedDate);
    }

    public String parseDateToddMMYyyy(String time) {
        String inputPattern = "yyyy/MM/dd";
        String outputPattern = "dd/MM/yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        Date date;
        String str = null;
        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
}
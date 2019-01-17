package msk.android.academy.javatemplate.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String formatDateFromDb(long sDate) {
//        Log.d("DateUtils", "date before:" + sDate);
        String input = String.valueOf(sDate);
        Date d = null;
        try {
            d = new SimpleDateFormat("yyyyMMdd").parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String output = new SimpleDateFormat("dd.MM.yyyy").format(d);

//        Log.d("DateUtils", "date after:" + output);
        return output;
    }
}


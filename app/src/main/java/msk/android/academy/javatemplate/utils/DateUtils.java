package msk.android.academy.javatemplate.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String formatDateFromDb(long sDate) {

//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
//        Date date = null;
//        try {
//            date = formatter.parse(sDate.toString());
//        } catch (ParseException e) {
//            Log.d("dateUtils","e.printStackTrace()");
//        }

        String input = String.valueOf(sDate);
        Date d = null;
        try {
            d = new SimpleDateFormat("yyyyMMdd").parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);

        return output;
    }
}


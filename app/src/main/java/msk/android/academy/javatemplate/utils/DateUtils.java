package msk.android.academy.javatemplate.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static Date formatDateFromDb(String sDate) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
        Date date = null;
        try {
            date = formatter.parse(sDate);
        } catch (ParseException e) {
            Log.d("d","e.printStackTrace()");
        }

        return date;
    }
}


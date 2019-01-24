package msk.android.academy.javatemplate.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static String formatDateFromDb(long sDate){
        Date d = new Date(sDate);

        String output = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).format(d);

        return output;
    }
}


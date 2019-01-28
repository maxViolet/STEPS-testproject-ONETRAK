package msk.android.academy.Steps.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static String formatDateFromDb(long sDate){
        Date d = new Date(sDate);
        return new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).format(d);
    }
}


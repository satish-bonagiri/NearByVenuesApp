package com.satti.fs.android.nbv.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by satish on 12/07/18.
 */

public class DateUtil {

    public static String currentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}

package com.gilazovdeveloper.vkbesedas.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ruslan on 14.03.16.
 */

public class Utils {
    public static String getHumanReadDate (long time){
        return new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date(time*1000));

    }

    public static String getHumanReadTime (long time){
        return  new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date(time*1000));

    }
}

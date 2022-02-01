package org.xpdojo.bank;

import java.util.Calendar;

public class DateTimeHelper {
    public static String getDate(Calendar cal) {
        return cal.get(Calendar.YEAR)+"-"+pad00(cal.get(Calendar.MONTH))+"-"+pad00(cal.get(Calendar.DAY_OF_MONTH));
    }

    public static String getTime(Calendar cal) {
        return pad00(cal.get(Calendar.HOUR))+"-"+pad00(cal.get(Calendar.MINUTE))+"-"+pad00(cal.get(Calendar.SECOND));
    }

    private static String pad00(int i) {
        if(i<10) return "0"+i;
        return ""+i;
    }


}

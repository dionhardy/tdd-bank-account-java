package org.xpdojo.bank;

import java.util.Calendar;

public class DateTimeHelper {
    public static String getDate(Calendar cal) {
        return cal.get(Calendar.YEAR)+"-"+cal.get(Calendar.MONTH)+"-"+cal.get(Calendar.DAY_OF_MONTH);
    }

    public static String getTime(Calendar cal) {
        return cal.get(Calendar.HOUR)+"-"+cal.get(Calendar.MINUTE)+"-"+cal.get(Calendar.SECOND);
    }
}

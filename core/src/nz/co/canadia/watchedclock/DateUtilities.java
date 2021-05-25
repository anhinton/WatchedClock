package nz.co.canadia.watchedclock;

import java.util.Date;

public interface DateUtilities {
    String formatDate(String pattern, Date date);

    Date parseDate(String pattern, String text);

    String zeroPadMinutes(int minutes);

    Date addDays(Date date, int i);
}

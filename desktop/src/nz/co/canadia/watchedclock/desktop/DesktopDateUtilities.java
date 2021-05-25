package nz.co.canadia.watchedclock.desktop;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import nz.co.canadia.watchedclock.DateUtilities;

public class DesktopDateUtilities implements DateUtilities {
    @Override
    public String formatDate(String pattern, Date date) {
        return new SimpleDateFormat(pattern, Locale.US).format(date);
    }

    @Override
    public String zeroPadMinutes(int minutes) {
        return String.format(Locale.getDefault(), "%02d", minutes);
    }

    @Override
    public Date addDays(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, i);
        return cal.getTime();
    }

    @Override
    public Date parseDate(String pattern, String text) {
        return new SimpleDateFormat(pattern, Locale.US).parse(text, new ParsePosition(0));
    }
}

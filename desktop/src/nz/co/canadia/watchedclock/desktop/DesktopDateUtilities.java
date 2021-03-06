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
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(date);
    }

    @Override
    public String zeroPadMinutes(int minutes) {
        return String.format(Locale.getDefault(), "%02d", minutes);
    }

    @Override
    public String zeroPadMilliseconds(int milliseconds) {
        return String.format(Locale.getDefault(), "%03d", milliseconds);
    }

    @Override
    public Date addDays(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, i);
        return cal.getTime();
    }

    @Override
    public Date calculateTimerTarget(int hours, int minutes, int seconds) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR_OF_DAY, hours);
        cal.add(Calendar.MINUTE, minutes);
        cal.add(Calendar.SECOND, seconds);
        return cal.getTime();
    }

    @Override
    public Date parseDate(String pattern, String text) {
        return new SimpleDateFormat(pattern, Locale.getDefault()).parse(text, new ParsePosition(0));
    }
}

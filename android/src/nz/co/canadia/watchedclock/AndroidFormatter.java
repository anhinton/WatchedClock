package nz.co.canadia.watchedclock;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AndroidFormatter implements Formatter {
    @Override
    public String formatCurrentTime(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.CLOCK_TIME_FORMAT, Locale.getDefault());
        return formatter.format(date);
    }

    @Override
    public String zeroPadMinutes(int minutes) {
        return String.format(Locale.getDefault(), "%02d", minutes);
    }
}

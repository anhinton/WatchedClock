package nz.co.canadia.watchedclock;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class IOSFormatter implements Formatter {
    @Override
    public String formatCurrentTime(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.CLOCK_TIME_FORMAT, Locale.getDefault());
        return formatter.format(date);
    }
}

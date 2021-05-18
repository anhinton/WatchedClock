package nz.co.canadia.watchedclock.desktop;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import nz.co.canadia.watchedclock.Constants;
import nz.co.canadia.watchedclock.Formatter;

public class DesktopFormatter implements Formatter {
    @Override
    public String formatCurrentTime(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.CLOCK_TIME_FORMAT, Locale.getDefault());
        return formatter.format(date);
    }
}

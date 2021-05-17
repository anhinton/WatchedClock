package nz.co.canadia.watchedclock.desktop;

import java.text.DateFormat;
import java.util.Date;

import nz.co.canadia.watchedclock.Formatter;

public class DesktopFormatter implements Formatter {
    @Override
    public String formatCurrentTime(Date date) {
        return DateFormat.getTimeInstance(DateFormat.SHORT).format(date);
    }
}

package nz.co.canadia.watchedclock;

import java.text.DateFormat;
import java.util.Date;

public class IOSFormatter implements Formatter {
    @Override
    public String formatCurrentTime(Date date) {
        return DateFormat.getTimeInstance(DateFormat.SHORT).format(date);
    }
}

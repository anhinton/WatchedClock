package nz.co.canadia.watchedclock;

import java.util.Date;

public interface Formatter {
    String formatCurrentTime(Date date);

    String zeroPadMinutes(int minutes);
}

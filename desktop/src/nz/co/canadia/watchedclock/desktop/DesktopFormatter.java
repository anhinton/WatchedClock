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

    @Override
    public String zeroPadMinutes(int minutes) {
        return String.format(Locale.getDefault(), "%02d", minutes);
    }

    @Override
    public String getAlarmHour(Date alarmTime) {
        return new SimpleDateFormat("h", Locale.US).format(alarmTime);
    }

    @Override
    public String getAlarmMinute(Date alarmTime) {
        return new SimpleDateFormat("mm", Locale.US).format(alarmTime);
    }

    @Override
    public String getAlarmPeriod(Date alarmTime) {
        return new SimpleDateFormat("a", Locale.US).format(alarmTime);
    }
}

package nz.co.canadia.watchedclock;

import java.util.Date;

public interface Formatter {
    String formatCurrentTime(Date date);

    String zeroPadMinutes(int minutes);

    String getAlarmHour(Date alarmTime);

    String getAlarmMinute(Date alarmTime);

    String getAlarmPeriod(Date alarmTime);
}

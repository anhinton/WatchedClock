package nz.co.canadia.watchedclock.client;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;

import java.util.Date;

import nz.co.canadia.watchedclock.Formatter;

public class HtmlFormatter implements Formatter {
    @Override
    public String formatCurrentTime(Date date) {
        DateTimeFormat timeFormat = DateTimeFormat.getFormat("h:mm a");
        return timeFormat.format(date);
    }

    @Override
    public String zeroPadMinutes(int minutes) {
        return NumberFormat.getFormat("00").format(minutes);
    }
}

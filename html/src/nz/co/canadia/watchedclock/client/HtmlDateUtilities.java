package nz.co.canadia.watchedclock.client;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.i18n.shared.DateTimeFormat;

import java.util.Date;

import nz.co.canadia.watchedclock.Constants;
import nz.co.canadia.watchedclock.DateUtilities;

public class HtmlDateUtilities implements DateUtilities {
    @Override
    public String formatDate(String pattern, Date date) {
        return DateTimeFormat.getFormat(pattern).format(date);
    }

    @Override
    public Date parseDate(String pattern, String text) {
        return DateTimeFormat.getFormat(pattern).parse(text);
    }

    @Override
    public String zeroPadMinutes(int minutes) {
        return NumberFormat.getFormat("00").format(minutes);
    }

    @Override
    public String zeroPadMilliseconds(int milliseconds) {
        return NumberFormat.getFormat("000").format(milliseconds);
    }

    @Override
    public Date addDays(Date date, int i) {
        String newDate = formatDate("yyyy.MM.", date)
                + zeroPadMinutes(Integer.parseInt(formatDate("dd", date)) + i)
                + formatDate(" h:mm a", date);
        return parseDate(Constants.DATE_TIME_FORMAT, newDate);
    }

}

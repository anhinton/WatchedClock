package nz.co.canadia.watchedclock;

public class Constants {
    public static final String GAME_NAME = "A Watched Clock Never Boils";
    public static final String PREFERENCES_PATH = "nz.co.canadia.watchedclock.preferences";

    public static final int DESKTOP_WIDTH = 640;
    public static final int DESKTOP_HEIGHT = 480;

    public static final int HTML_WIDTH = DESKTOP_WIDTH;
    public static final int HTML_HEIGHT = DESKTOP_HEIGHT;

    public static final int WORLD_WIDTH = 360;
    public static final int WORLD_HEIGHT = 480;

    public static final String CLOCK_TIME_FORMAT = "h:mm a";
    public static final String DATE_FORMAT = "yyyy.MM.dd";
    public static final String DATE_TIME_FORMAT = DATE_FORMAT + " " + CLOCK_TIME_FORMAT;

    public static final String ALARM_HOUR_DEFAULT = "12";
    public static final String ALARM_MINUTE_DEFAULT = "00";
    public static final String ALARM_PERIOD_DEFAULT = "AM";
    public static final boolean ALARM_IS_SET_DEFAULT = false;
}

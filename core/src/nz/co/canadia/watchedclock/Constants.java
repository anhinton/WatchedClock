package nz.co.canadia.watchedclock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Constants {
    public static final String GAME_NAME = "A Watched Clock Never Boils";
    public static final String PREFERENCES_PATH = "nz.co.canadia.watchedclock.preferences";

    public static final int DESKTOP_WIDTH = 405;
    public static final int DESKTOP_HEIGHT = 720;

    public static final int HTML_WIDTH = DESKTOP_WIDTH;
    public static final int HTML_HEIGHT = DESKTOP_HEIGHT;

    public static final int WORLD_WIDTH = Gdx.graphics.getBackBufferWidth();
    public static final int WORLD_HEIGHT = Gdx.graphics.getBackBufferHeight();

    public static final Color BACKGROUND_COLOR = Color.valueOf("ea9d7eff");
    public static final Color FONT_COLOR = Color.valueOf("1c2833ff");
    public static final Color BUTTON_FONT_COLOR = Color.WHITE;
    public static final Color BUTTON_FONT_SHADOW_COLOR = FONT_COLOR;
    public static final Color SELECTBOX_FONT_COLOR = Color.WHITE;
    public static final float TIME_LABEL_FONT_SIZE = .15f;
    public static final float BOX_LIST_FONT_SIZE = .075f;
    public static final float MENU_BUTTON_FONT_SIZE = .058f;
    public static final float ALARM_LABEL_FONT_SIZE = .058f;
    public static final float CREDITS_LABEL_FONT_SIZE = .04f;
    public static final float BUTTON_SHADOW_SIZE = .005f;
    public static final float UI_PADDING = .03f;
    public static final float MENU_BUTTON_WIDTH = .2f;
    public static final float CONTROL_BUTTON_WIDTH = .4f;
    public static final float BUTTON_HEIGHT = .1f;
    public static final float INFO_ICON_SIZE = .058f;
    public static final String CHARACTERS = FreeTypeFontGenerator.DEFAULT_CHARS + "\u2022";

    public static final String CLOCK_TIME_FORMAT = "h:mm:ss a";
    public static final String SHORT_TIME_FORMAT = "h:mm a";
    public static final String DATE_FORMAT = "yyyy.MM.dd";
    public static final String DATE_TIME_FORMAT = DATE_FORMAT + " " + SHORT_TIME_FORMAT;
    public static final String TIMER_TARGET_FORMAT = DATE_FORMAT + " H:mm:ss";

    public static final boolean ALARM_IS_SET_DEFAULT = false;
}

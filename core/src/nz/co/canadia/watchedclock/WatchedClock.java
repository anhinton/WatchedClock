package nz.co.canadia.watchedclock;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Date;

public class WatchedClock extends Game {
	AssetManager manager;
	SpriteBatch batch;
	I18NBundle bundle;
	DateUtilities dateUtilities;
	Preferences preferences;
	Skin skin;
	private Date currentTime;
	private long stopwatchTime;
	private long timerRemaining;
	private int padding;

	public WatchedClock(DateUtilities dateUtilities) {
		this.dateUtilities = dateUtilities;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		manager = new AssetManager();
		preferences = Gdx.app.getPreferences(Constants.PREFERENCES_PATH);
		padding = MathUtils.round(Constants.UI_PADDING * Constants.WORLD_WIDTH);

		currentTime = new Date();
		// Alarm
		Date alarmTime = new Date(preferences.getLong("alarmTime", 0));
		boolean alarmIsSet = preferences.getBoolean("alarmIsSet", Constants.ALARM_IS_SET_DEFAULT);
		// Stopwatch
		Date stopwatchStartTime = new Date(preferences.getLong("stopwatchStartTime", 0));
		long stopwatchElapsedTime = preferences.getLong("stopwatchElapsedTime", 0);
		boolean stopwatchIsRunning = preferences.getBoolean("stopwatchIsRunning", false);
		if (stopwatchIsRunning) {
			stopwatchTime = currentTime.getTime() - stopwatchStartTime.getTime() + stopwatchElapsedTime;
		} else {
			stopwatchTime = stopwatchElapsedTime;
		}
		// Timer
		Date timerTarget = new Date(preferences.getLong("timerTarget", 0));
		boolean timerIsRunning = preferences.getBoolean("timerIsRunning", false);
		if (timerIsRunning) {
			timerRemaining = timerTarget.getTime() - new Date().getTime();
		} else {
			timerRemaining = preferences.getLong("timerRemaining", 0);
		}

		FreeTypeFontGenerator inconsolataGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Inconsolata-VariableFont_wdth,wght.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter timeLabelParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		timeLabelParameter.size = MathUtils.round(Constants.TIME_LABEL_FONT_SIZE * Constants.WORLD_WIDTH);
		BitmapFont timeLabelFont = inconsolataGenerator.generateFont(timeLabelParameter);
		inconsolataGenerator.dispose();

		FreeTypeFontGenerator podkovaGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Podkova-VariableFont_wght.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter menuButtonParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		menuButtonParameter.size = MathUtils.round(Constants.MENU_BUTTON_FONT_SIZE * Constants.WORLD_WIDTH);
		menuButtonParameter.color = Constants.BUTTON_FONT_COLOR;
		menuButtonParameter.shadowColor = Constants.BUTTON_FONT_SHADOW_COLOR;
		menuButtonParameter.shadowOffsetX = MathUtils.round(Constants.BUTTON_SHADOW_SIZE * Constants.WORLD_WIDTH);
		menuButtonParameter.shadowOffsetY = MathUtils.round(Constants.BUTTON_SHADOW_SIZE * Constants.WORLD_WIDTH);
		BitmapFont menuButtonFont = podkovaGenerator.generateFont(menuButtonParameter);
		podkovaGenerator.dispose();

		manager.load("skin/uiskin.json", Skin.class);
		manager.load("i18n/Bundle", I18NBundle.class);
		manager.finishLoading();

		bundle = manager.get("i18n/Bundle", I18NBundle.class);
		skin = manager.get("skin/uiskin.json", Skin.class);
		// Create labels
		skin.add("time", new Label.LabelStyle(timeLabelFont, Constants.FONT_COLOR), Label.LabelStyle.class);
		// Create TextButtons
		TextButton.TextButtonStyle menuTextButtonStyle = new TextButton.TextButtonStyle(skin.get("default", TextButton.TextButtonStyle.class));
		menuTextButtonStyle.font = menuButtonFont;
		skin.add("menu", menuTextButtonStyle);

		Screen screen;
		if (currentTime.after(alarmTime) && alarmIsSet) {
			screen = new AlarmScreen(this);
		} else if (timerIsRunning && timerRemaining <= 0) {
			screen = new TimerScreen(this);
		} else if (stopwatchIsRunning) {
			screen = new StopwatchScreen(this);
		} else {
			switch (preferences.getString("currentScreen", "ClockScreen")) {
				case "AlarmScreen":
					screen = new AlarmScreen(this);
					break;
				case "StopwatchScreen":
					screen = new StopwatchScreen(this);
					break;
				case "TimerScreen":
					screen = new TimerScreen(this);
					break;
				default:
					screen = new ClockScreen(this);
					break;
			}
		}
		this.setScreen(screen);
	}

	@Override
	public void render () {
		super.render(); // important!
	}

	@Override
	public void setScreen(Screen screen) {
		super.setScreen(screen);
		preferences.putString("currentScreen", screen.getClass().getSimpleName());
		preferences.flush();
	}

	@Override
	public void resume() {
		currentTime = new Date();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		manager.dispose();
	}

	public int getPadding() {
		return padding;
	}

	public Date getCurrentTime() {
		return currentTime;
	}

	public long getStopwatchTime() {
		return stopwatchTime;
	}

	public long getTimerRemaining() {
		return timerRemaining;
	}

	public void setTimerRemaining(long timerRemaining) {
		this.timerRemaining = timerRemaining;
	}
}

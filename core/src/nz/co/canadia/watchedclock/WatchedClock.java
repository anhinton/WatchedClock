package nz.co.canadia.watchedclock;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Date;

public class WatchedClock extends Game {
	AssetManager manager;
	DateUtilities dateUtilities;
	FontLoader fontLoader;
	I18NBundle bundle;
	Preferences preferences;
	Skin skin;
	SpriteBatch batch;
	private Date currentTime;
	private long stopwatchTime;
	private long timerRemaining;
	private int padding;
	private float menuButtonWidth;
	private float controlButtonWidth;
	private float buttonHeight;

	public WatchedClock(DateUtilities dateUtilities, FontLoader fontLoader) {
		this.dateUtilities = dateUtilities;
		this.fontLoader = fontLoader;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		manager = new AssetManager();
		preferences = Gdx.app.getPreferences(Constants.PREFERENCES_PATH);
		padding = MathUtils.round(Constants.UI_PADDING * Constants.WORLD_WIDTH);
		menuButtonWidth = Constants.MENU_BUTTON_WIDTH * Constants.WORLD_WIDTH;
		controlButtonWidth = Constants.CONTROL_BUTTON_WIDTH * Constants.WORLD_WIDTH;
		buttonHeight = Constants.BUTTON_HEIGHT * Constants.WORLD_WIDTH;

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

		manager.load("skin/uiskin.json", Skin.class);
		manager.load("i18n/Bundle", I18NBundle.class);
		fontLoader.loadTimeLabelFont(manager);
		fontLoader.loadAlarmLabelFont(manager);
		fontLoader.loadAlarmListFont(manager);
		fontLoader.loadMenuButtonFont(manager);
		manager.finishLoading();

		bundle = manager.get("i18n/Bundle", I18NBundle.class);
		skin = manager.get("skin/uiskin.json", Skin.class);
		// Labels
		skin.add("time", new Label.LabelStyle(fontLoader.getTimeLabelFont(manager),
				Constants.FONT_COLOR), Label.LabelStyle.class);
		skin.add("alarm", new Label.LabelStyle(fontLoader.getAlarmLabelFont(manager),
				Constants.FONT_COLOR), Label.LabelStyle.class);
		// TextButtons
		TextButton.TextButtonStyle menuTextButtonStyle = new TextButton.TextButtonStyle(skin.get("toggle", TextButton.TextButtonStyle.class));
		menuTextButtonStyle.font = fontLoader.getMenuButtonFont(manager);
		skin.add("menu", menuTextButtonStyle);
		TextButton.TextButtonStyle controlTextButtonStyle = new TextButton.TextButtonStyle(skin.get("default", TextButton.TextButtonStyle.class));
		controlTextButtonStyle.font = fontLoader.getMenuButtonFont(manager);
		skin.add("control", controlTextButtonStyle);

		// SelectBox
		SelectBox.SelectBoxStyle alarmSelectBoxStyle = new SelectBox.SelectBoxStyle(skin.get("default", SelectBox.SelectBoxStyle.class));
		alarmSelectBoxStyle.font = fontLoader.getTimeLabelFont(manager);
		alarmSelectBoxStyle.fontColor = Constants.SELECTBOX_FONT_COLOR;
		alarmSelectBoxStyle.listStyle.font = fontLoader.getAlarmListFont(manager);
		alarmSelectBoxStyle.listStyle.fontColorSelected = Constants.SELECTBOX_FONT_COLOR;
		alarmSelectBoxStyle.listStyle.fontColorUnselected = Constants.SELECTBOX_FONT_COLOR;
		skin.add("alarm", alarmSelectBoxStyle);
		SelectBox.SelectBoxStyle timerSelectBoxStyle = new SelectBox.SelectBoxStyle(skin.get("default", SelectBox.SelectBoxStyle.class));
		timerSelectBoxStyle.font = fontLoader.getAlarmLabelFont(manager);
		timerSelectBoxStyle.fontColor = Constants.SELECTBOX_FONT_COLOR;
		timerSelectBoxStyle.listStyle.font = fontLoader.getAlarmListFont(manager);
		timerSelectBoxStyle.listStyle.fontColorSelected = Constants.SELECTBOX_FONT_COLOR;
		timerSelectBoxStyle.listStyle.fontColorUnselected = Constants.SELECTBOX_FONT_COLOR;
		skin.add("timer", timerSelectBoxStyle);

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

	public float getMenuButtonWidth() {
		return menuButtonWidth;
	}

	public float getControlButtonWidth() {
		return controlButtonWidth;
	}

	public float getButtonHeight() {
		return buttonHeight;
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

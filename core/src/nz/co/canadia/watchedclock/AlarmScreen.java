package nz.co.canadia.watchedclock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AlarmScreen implements Screen {
    private final Stage stage;
    private final SelectBox<String> hourSelectBox;
    private final SelectBox<String> minuteSelectBox;
    private final SelectBox<String> periodSelectBox;
    private final WatchedClock game;
    private final TextButton setAlarmButton;
    private String alarmHour;
    private String alarmMinute;
    private String alarmPeriod;
    private boolean alarmIsSet;

    public AlarmScreen(WatchedClock game) {
        this.game = game;
        alarmHour = game.preferences.getString("alarmHour", Constants.ALARM_HOUR_DEFAULT);
        alarmMinute = game.preferences.getString("alarmMinute", Constants.ALARM_MINUTE_DEFAULT);
        alarmPeriod = game.preferences.getString("alarmPeriod", Constants.ALARM_PERIOD_DEFAULT);
        alarmIsSet = game.preferences.getBoolean("alarmIsSet", Constants.ALARM_IS_SET_DEFAULT);

        // TODO: move this to Formatter as SimpleDateFormat is not supported in GWT
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.US);
        String alarmString = dateFormat.format(new Date()) + " " + alarmHour + ":" + alarmMinute + " " + alarmPeriod;
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat(Constants.DATE_TIME_FORMAT, Locale.US);
        Date alarmTime = dateTimeFormat.parse(alarmString, new ParsePosition(0));

        Viewport viewport = new FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        stage = new Stage(viewport);
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // ALARM
        hourSelectBox = new SelectBox<>(game.skin, "default");
        Array<String> hourStringArray = new Array<>(12);
        hourStringArray.add("12");
        for (int i = 1; i < 12; i++) {
            hourStringArray.add(String.valueOf(i));
        }
        hourSelectBox.setItems(hourStringArray);
        hourSelectBox.setSelected(alarmHour);
        table.add(hourSelectBox);

        minuteSelectBox = new SelectBox<>(game.skin, "default");
        Array<String> minuteStringArray = new Array<>(12);
        for (int i = 0; i < 12; i++) {
            minuteStringArray.add(game.formatter.zeroPadMinutes(i * 5));
        }
        minuteSelectBox.setItems(minuteStringArray);
        minuteSelectBox.setSelected(alarmMinute);
        table.add(minuteSelectBox);

        periodSelectBox = new SelectBox<>(game.skin, "default");
        periodSelectBox.setItems("AM", "PM");
        periodSelectBox.setSelected(alarmPeriod);
        table.add(periodSelectBox);
        table.row();

        String alarmButtonText;
        if (alarmIsSet) {
            alarmButtonText = "Disable alarm";
        } else {
            alarmButtonText = "Set alarm";
        }
        setAlarmButton = new TextButton(alarmButtonText, game.skin, "default");
        setAlarmButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                toggleAlarm();
            }
        });

        table.add(setAlarmButton).colspan(3);

        Gdx.input.setInputProcessor(stage);

        if (game.getCurrentTime().after(alarmTime) && alarmIsSet) {
            playAlarm();
        } else {
            Gdx.app.log("AlarmScreen", "EVERYTHING IS NORMAL");
        }
    }

    private void toggleAlarm() {
        if (alarmIsSet) {
            unsetAlarm();
        } else {
            setAlarmTime();
        }
    }

    private void playAlarm() {
        unsetAlarm();
        Gdx.app.log("AlarmScreen", "THIS IS AN ALARM");
    }

    private void setAlarmTime() {
        alarmHour = hourSelectBox.getSelected();
        alarmMinute = minuteSelectBox.getSelected();
        alarmPeriod = periodSelectBox.getSelected();
        alarmIsSet = true;
        setAlarmButton.setText("Disable alarm");
        game.preferences.putString("alarmHour", alarmHour);
        game.preferences.putString("alarmMinute", alarmMinute);
        game.preferences.putString("alarmPeriod", alarmPeriod);
        game.preferences.putBoolean("alarmIsSet", alarmIsSet);
        game.preferences.flush();
    }

    private void unsetAlarm() {
        alarmIsSet = false;
        setAlarmButton.setText("Set alarm");
        game.preferences.putBoolean("alarmIsSet", alarmIsSet);
        game.preferences.flush();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 0, 0, 1);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}

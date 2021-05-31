package nz.co.canadia.watchedclock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Date;

public class AlarmScreen implements Screen {
    private final Stage stage;
    private final SelectBox<String> hourSelectBox;
    private final SelectBox<String> minuteSelectBox;
    private final SelectBox<String> periodSelectBox;
    private final WatchedClock game;
    private final TextButton setAlarmButton;
    private final Table table;
    private Date alarmTime;
    private boolean alarmIsSet;

    public AlarmScreen(final WatchedClock game) {
        this.game = game;
        alarmTime = new Date(game.preferences.getLong("alarmTime", 0));
        alarmIsSet = game.preferences.getBoolean("alarmIsSet", Constants.ALARM_IS_SET_DEFAULT);

        Viewport viewport = new FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        stage = new Stage(viewport);
        table = new Table();
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
        hourSelectBox.setSelected(game.dateUtilities.formatDate("h", alarmTime));
        table.add(hourSelectBox);

        minuteSelectBox = new SelectBox<>(game.skin, "default");
        Array<String> minuteStringArray = new Array<>(12);
        for (int i = 0; i < 12; i++) {
            minuteStringArray.add(game.dateUtilities.zeroPadMinutes(i * 5));
        }
        minuteSelectBox.setItems(minuteStringArray);
        minuteSelectBox.setSelected(game.dateUtilities.formatDate("mm", alarmTime));
        table.add(minuteSelectBox);

        periodSelectBox = new SelectBox<>(game.skin, "default");
        periodSelectBox.setItems(game.bundle.get("alarmAm"), game.bundle.get("alarmPm"));
        periodSelectBox.setSelected(game.dateUtilities.formatDate("a", alarmTime));
        table.add(periodSelectBox);
        table.row();

        String setAlarmButtonText;
        if (alarmIsSet) {
            setAlarmButtonText = game.bundle.get("alarmButtonDisable");
        } else {
            setAlarmButtonText = game.bundle.get("alarmButtonSet");
        }
        setAlarmButton = new TextButton(setAlarmButtonText, game.skin, "default");
        setAlarmButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                toggleAlarm();
            }
        });

        table.add(setAlarmButton).colspan(3);
        table.row();

        table.add(new MenuButtons(game)).colspan(3);

        Gdx.input.setInputProcessor(stage);

        if (game.getCurrentTime().after(alarmTime) && alarmIsSet) {
            playAlarm();
        } else {
            Label alarmLabel = new Label("EVERYTHING IS NORMAL", game.skin, "default");
            table.row();
            table.add(alarmLabel).colspan(3);
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
        Label alarmLabel = new Label("THIS IS AN ALARM", game.skin, "default");
        table.row();
        table.add(alarmLabel).colspan(3);
        Gdx.app.log("AlarmScreen", "THIS IS AN ALARM");
    }

    private void setAlarmTime() {
        String alarmText = game.dateUtilities.formatDate(Constants.DATE_FORMAT, new Date())
                + " " + hourSelectBox.getSelected()
                + ":" + minuteSelectBox.getSelected()
                + " " + periodSelectBox.getSelected();
        alarmTime = game.dateUtilities.parseDate(Constants.DATE_TIME_FORMAT, alarmText);
        if (alarmTime.before(game.getCurrentTime())) {
            alarmTime = game.dateUtilities.addDays(alarmTime, 1);
        }
        alarmIsSet = true;
        setAlarmButton.setText(game.bundle.get("alarmButtonDisable"));
        game.preferences.putLong("alarmTime", alarmTime.getTime());
        game.preferences.putBoolean("alarmIsSet", alarmIsSet);
        game.preferences.flush();
    }

    private void unsetAlarm() {
        alarmIsSet = false;
        setAlarmButton.setText(game.bundle.get("alarmButtonSet"));
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

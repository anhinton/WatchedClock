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
    private SelectBox<String> hourSelectBox;
    private SelectBox<String> minuteSelectBox;
    private SelectBox<String> periodSelectBox;
    private final WatchedClock game;
    private TextButton setAlarmButton;
    private final Table contentTable;
    private Date alarmTime;
    private boolean alarmIsSet;

    public AlarmScreen(final WatchedClock game) {
        this.game = game;
        alarmTime = new Date(game.preferences.getLong("alarmTime", 0));
        alarmIsSet = game.preferences.getBoolean("alarmIsSet", Constants.ALARM_IS_SET_DEFAULT);

        Viewport viewport = new FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        stage = new Stage(viewport);
        Table table = new Table();
        table.setFillParent(true);
        table.pad(game.getPadding());
        stage.addActor(table);

        contentTable = new Table();
        table.add(contentTable)
                .expand()
                .space(game.getPadding());

        setDisplay();

        table.row();
        table.add(new MenuButtons(game, "Alarm"));

        Gdx.input.setInputProcessor(stage);
    }

    private void setDisplay() {
        if (game.getCurrentTime().after(alarmTime) && alarmIsSet) {
            playAlarm();
        } else {
            showInputBoxes();
        }
    }

    private void showInputBoxes() {
        contentTable.clear();

        // ALARM
        hourSelectBox = new SelectBox<>(game.skin, "alarm");
        Array<String> hourStringArray = new Array<>(12);
        hourStringArray.add("12");
        for (int i = 1; i < 12; i++) {
            hourStringArray.add(String.valueOf(i));
        }
        hourSelectBox.setItems(hourStringArray);
        hourSelectBox.setSelected(game.dateUtilities.formatDate("h", alarmTime));
        hourSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setAlarmTime(true);
            }
        });
        contentTable.add(hourSelectBox).space(game.getPadding());

        minuteSelectBox = new SelectBox<>(game.skin, "alarm");
        Array<String> minuteStringArray = new Array<>(60);
        for (int i = 0; i < 60; i++) {
            minuteStringArray.add(game.dateUtilities.zeroPadMinutes(i));
        }
        minuteSelectBox.setItems(minuteStringArray);
        minuteSelectBox.setSelected(game.dateUtilities.formatDate("mm", alarmTime));
        minuteSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setAlarmTime(true);
            }
        });
        contentTable.add(minuteSelectBox).space(game.getPadding());

        periodSelectBox = new SelectBox<>(game.skin, "alarm");
        periodSelectBox.setItems(game.bundle.get("alarmAm"), game.bundle.get("alarmPm"));
        periodSelectBox.setSelected(game.dateUtilities.formatDate("a", alarmTime));
        periodSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setAlarmTime(true);
            }
        });
        contentTable.add(periodSelectBox).space(game.getPadding());
        contentTable.row();

        String setAlarmButtonText;
        if (alarmIsSet) {
            setAlarmButtonText = game.bundle.get("alarmButtonDisable");
        } else {
            setAlarmButtonText = game.bundle.get("alarmButtonSet");
        }
        setAlarmButton = new TextButton(setAlarmButtonText, game.skin, "control");
        setAlarmButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                toggleAlarm();
            }
        });
        contentTable.add(setAlarmButton)
                .colspan(3)
                .prefSize(game.getControlButtonWidth(), game.getButtonHeight())
                .space(game.getPadding());
        contentTable.row();
    }

    private void toggleAlarm() {
        if (alarmIsSet) {
            setAlarmButton.setText(game.bundle.get("alarmButtonSet"));
            disableAlarm();
        } else {
            setAlarmButton.setText(game.bundle.get("alarmButtonDisable"));
            enableAlarm();
        }
    }

    private void playAlarm() {
        contentTable.clear();

        disableAlarm();

        Label alarmPlayingLabel = new Label(game.bundle.get("alarmPlaying"), game.skin, "alarm");
        contentTable.add(alarmPlayingLabel).space(game.getPadding());
        contentTable.row();

        Label alarmTimeLabel = new Label(
                game.dateUtilities.formatDate(Constants.SHORT_TIME_FORMAT, alarmTime),
                game.skin, "time");
        contentTable.add(alarmTimeLabel).space(game.getPadding());
        contentTable.row();

        TextButton alarmStopButton = new TextButton(game.bundle.get("alarmStop"), game.skin, "control");
        alarmStopButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showInputBoxes();
            }
        });
        contentTable.add(alarmStopButton)
                .prefSize(game.getControlButtonWidth(), game.getButtonHeight())
                .space(game.getPadding());
        contentTable.row();
    }

    private void enableAlarm() {
        setAlarmTime(false);
        alarmIsSet = true;
        game.preferences.putBoolean("alarmIsSet", alarmIsSet);
        game.preferences.flush();
    }

    private void setAlarmTime(boolean flush) {
        String alarmText = game.dateUtilities.formatDate(Constants.DATE_FORMAT, new Date())
                + " " + hourSelectBox.getSelected()
                + ":" + minuteSelectBox.getSelected()
                + " " + periodSelectBox.getSelected();
        alarmTime = game.dateUtilities.parseDate(Constants.DATE_TIME_FORMAT, alarmText);
        if (alarmTime.before(game.getCurrentTime())) {
            alarmTime = game.dateUtilities.addDays(alarmTime, 1);
        }
        game.preferences.putLong("alarmTime", alarmTime.getTime());
        if (flush) {
            game.preferences.flush();
        }
    }

    private void disableAlarm() {
        alarmIsSet = false;
        game.preferences.putBoolean("alarmIsSet", alarmIsSet);
        game.preferences.flush();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Constants.BACKGROUND_COLOR);

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
        game.updateTimes();
        setDisplay();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}

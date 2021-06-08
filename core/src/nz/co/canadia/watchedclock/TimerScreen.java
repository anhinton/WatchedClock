package nz.co.canadia.watchedclock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Date;

public class TimerScreen implements Screen {
    private final Stage stage;
    private final Table contentTable;
    private SelectBox<String> hourSelectBox;
    private SelectBox<String> minuteSelectBox;
    private SelectBox<String> secondSelectBox;
    private final WatchedClock game;
    private boolean timerIsRunning;
    private int timerHours;
    private int timerMinutes;
    private int timerSeconds;
    private Date timerTarget;
    private TextButton timerPauseButton;

    public TimerScreen(final WatchedClock game) {
        this.game = game;
        timerHours = game.preferences.getInteger("timerHours", 0);
        timerMinutes = game.preferences.getInteger("timerMinutes", 0);
        timerSeconds = game.preferences.getInteger("timerSeconds", 0);
        timerTarget = new Date(game.preferences.getLong("timerTarget", 0));
        timerIsRunning = game.preferences.getBoolean("timerIsRunning", false);

        Viewport viewport = new FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        stage = new Stage(viewport);
        Table table = new Table();
        table.setFillParent(true);
        table.pad(game.getPadding());
        stage.addActor(table);

        contentTable = new Table();

        setDisplay();

        table.add(contentTable).expand();
        table.row();
        table.add(new MenuButtons(game, "Timer")).colspan(2);

        Gdx.input.setInputProcessor(stage);
    }

    private void setDisplay() {
        long timerRemaining = game.getTimerRemaining();
        if (timerIsRunning) {
            if (timerRemaining > 0) {
                showTimer();
            } else {
                playAlarm();
            }
        } else {
            if (timerRemaining > 0) {
                showTimer();
            } else {
                showInputBoxes();
            }
        }
    }

    private void playAlarm() {
        contentTable.clear();

        timerIsRunning = false;
        game.setTimerRemaining(0);
        game.preferences.putBoolean("timerIsRunning", timerIsRunning);
        game.preferences.putLong("timerRemaining", game.getTimerRemaining());
        game.preferences.flush();

        Label timerFinishedLabel = new Label(game.bundle.get("timerFinished"), game.skin, "alarm");
        contentTable.add(timerFinishedLabel).space(game.getPadding());
        contentTable.row();

        Label timerRemainingLabel = new Label("0:00:00", game.skin, "time");
        contentTable.add(timerRemainingLabel).space(game.getPadding());
        contentTable.row();

        TextButton timerResetButton = new TextButton(game.bundle.get("timerReset"),
                game.skin, "control");
        timerResetButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showInputBoxes();
            }
        });
        contentTable.add(timerResetButton)
                .prefSize(game.getControlButtonWidth(), game.getButtonHeight())
                .space(game.getPadding());
    }

    private void showInputBoxes() {
        contentTable.clear();

        hourSelectBox = new SelectBox<>(game.skin, "timer");
        final Array<String> hourStringArray = new Array<>(24);
        for (int i = 0; i < 24; i++) {
            hourStringArray.add(String.valueOf(i));
        }
        hourSelectBox.setItems(hourStringArray);
        hourSelectBox.setSelected(String.valueOf(timerHours));
        contentTable.add(hourSelectBox).space(game.getPadding());

        Label hourLabel = new Label(game.bundle.get("timerHours"), game.skin, "alarm");
        contentTable.add(hourLabel).space(game.getPadding());

        minuteSelectBox = new SelectBox<>(game.skin, "timer");
        Array<String> minuteStringArray = new Array<>(60);
        for (int i = 0; i < 60; i++) {
            minuteStringArray.add(String.valueOf(i));
        }
        minuteSelectBox.setItems(minuteStringArray);
        minuteSelectBox.setSelected(String.valueOf(timerMinutes));
        contentTable.add(minuteSelectBox).space(game.getPadding());

        Label minuteLabel = new Label(game.bundle.get("timerMinutes"), game.skin, "alarm");
        contentTable.add(minuteLabel).space(game.getPadding());

        secondSelectBox = new SelectBox<>(game.skin, "timer");
        secondSelectBox.setItems(minuteStringArray);
        secondSelectBox.setSelected(String.valueOf(timerSeconds));
        contentTable.add(secondSelectBox).space(game.getPadding());

        Label secondLabel = new Label(game.bundle.get("timerSeconds"), game.skin, "alarm");
        contentTable.add(secondLabel).space(game.getPadding());

        contentTable.row();

        TextButton timerStartButton = new TextButton(game.bundle.get("timerStart"), game.skin, "control");
        timerStartButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                startTimer();
            }
        });
        contentTable.add(timerStartButton)
                .colspan(6)
                .prefSize(game.getControlButtonWidth(), game.getButtonHeight())
                .space(game.getPadding());
    }

    private void startTimer() {
        timerHours = Integer.parseInt(hourSelectBox.getSelected());
        timerMinutes = Integer.parseInt(minuteSelectBox.getSelected());
        timerSeconds = Integer.parseInt(secondSelectBox.getSelected());
        timerTarget = game.dateUtilities.calculateTimerTarget(timerHours, timerMinutes, timerSeconds);
        long timerRemaining = timerTarget.getTime() - new Date().getTime();

        if (timerRemaining > 0) {
            timerIsRunning = true;
            game.setTimerRemaining(timerRemaining);
            game.preferences.putBoolean("timerIsRunning", timerIsRunning);
            showTimer();
        } else {
            timerSeconds = 1;
            secondSelectBox.setSelected(String.valueOf(timerSeconds));
        }

        game.preferences.putInteger("timerHours", timerHours);
        game.preferences.putInteger("timerMinutes", timerMinutes);
        game.preferences.putInteger("timerSeconds", timerSeconds);
        game.preferences.putLong("timerTarget", timerTarget.getTime());
        game.preferences.putLong("timerRemaining", timerRemaining);
        game.preferences.flush();
    }

    private void showTimer() {
        contentTable.clear();

        long timerRemaining = MathUtils.clamp(game.getTimerRemaining(), 0, game.getTimerRemaining());

        int seconds = (int) ((timerRemaining / 1000) % 60);
        int minutes = (int) (timerRemaining / 60000) % 60;
        int hours = (int) (timerRemaining / 3600000);
        final String timerRemainingText = hours + ":" + game.dateUtilities.zeroPadMinutes(minutes) + ":"
                + game.dateUtilities.zeroPadMinutes(seconds);

        Label timerLabel = new Label(timerRemainingText, game.skin, "time");
        contentTable.add(timerLabel)
                .colspan(2)
                .space(game.getPadding());
        contentTable.row();

        TextButton timerCancelButton = new TextButton(game.bundle.get("timerCancel"), game.skin, "control");
        timerCancelButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                timerIsRunning = false;
                game.setTimerRemaining(0);
                game.preferences.putBoolean("timerIsRunning", timerIsRunning);
                game.preferences.putLong("timerRemaining", game.getTimerRemaining());
                game.preferences.flush();
                showInputBoxes();
            }
        });
        contentTable.add(timerCancelButton)
                .align(Align.right)
                .prefSize(game.getControlButtonWidth(), game.getButtonHeight())
                .space(game.getPadding());

        String timerPauseButtonText;
        if (timerIsRunning) {
            timerPauseButtonText = game.bundle.get("timerPause");
        } else {
            timerPauseButtonText = game.bundle.get("timerResume");
        }
        timerPauseButton = new TextButton(timerPauseButtonText, game.skin, "control");
        timerPauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                toggleTimer();
            }
        });
        contentTable.add(timerPauseButton)
                .align(Align.left)
                .prefSize(game.getControlButtonWidth(), game.getButtonHeight())
                .space(game.getPadding());
    }

    private void toggleTimer() {
        if (timerIsRunning) {
            timerPauseButton.setText(game.bundle.get("timerResume"));
            long timerRemaining = timerTarget.getTime() - new Date().getTime();
            game.preferences.putLong("timerRemaining", timerRemaining);
        } else {
            timerPauseButton.setText(game.bundle.get("timerPause"));
            timerTarget = new Date(new Date().getTime() + game.getTimerRemaining());
            game.preferences.putLong("timerTarget", timerTarget.getTime());
        }
        timerIsRunning = !timerIsRunning;
        game.preferences.putBoolean("timerIsRunning", timerIsRunning);
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

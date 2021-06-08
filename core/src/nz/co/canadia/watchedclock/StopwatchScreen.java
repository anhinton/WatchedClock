package nz.co.canadia.watchedclock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Date;

public class StopwatchScreen implements Screen {
    private final Stage stage;
    private final WatchedClock game;
    private final TextButton stopwatchStartButton;
    private final Label stopwatchLabel;
    private long stopwatchElapsedTime;
    private Date stopwatchStartTime;
    private boolean stopwatchIsRunning;

    public StopwatchScreen(final WatchedClock game) {
        this.game = game;
        stopwatchIsRunning = game.preferences.getBoolean("stopwatchIsRunning", false);
        stopwatchStartTime = new Date(game.preferences.getLong("stopwatchStartTime", 0));
        stopwatchElapsedTime = game.preferences.getLong("stopwatchElapsedTime", 0);

        Viewport viewport = new FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        stage = new Stage(viewport);
        Table table = new Table();
        table.setFillParent(true);
        table.pad(game.getPadding());
        stage.addActor(table);

        Table contentTable = new Table();

        stopwatchLabel = new Label("", game.skin, "time");
        updateStopwatchLabel();
        contentTable.add(stopwatchLabel)
                .colspan(2)
                .space(game.getPadding());
        contentTable.row();

        String stopwatchStartButtonText;
        if (stopwatchIsRunning) {
            stopwatchStartButtonText = game.bundle.get("stopwatchPause");
        } else {
            stopwatchStartButtonText = game.bundle.get("stopwatchStart");
        }
        stopwatchStartButton = new TextButton(stopwatchStartButtonText,
                game.skin, "control");
        stopwatchStartButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                toggleStopwatch();
            }
        });
        contentTable.add(stopwatchStartButton)
                .align(Align.right)
                .prefSize(game.getControlButtonWidth(), game.getButtonHeight())
                .space(game.getPadding());

        TextButton stopwatchRestartButton = new TextButton(game.bundle.get("stopwatchRestart"),
                game.skin, "control");
        stopwatchRestartButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                resetStopwatch();
            }
        });
        contentTable.add(stopwatchRestartButton)
                .align(Align.left)
                .prefSize(game.getControlButtonWidth(), game.getButtonHeight())
                .space(game.getPadding());

        table.add(contentTable).expand();
        table.row();
        table.add(new MenuButtons(game, this)).colspan(2);

        Gdx.input.setInputProcessor(stage);
    }

    private void updateStopwatchLabel() {
        stopwatchLabel.setText(formatStopwatchTime(game.getStopwatchTime()));
    }

    private String formatStopwatchTime(long time) {
        int milliseconds = (int) (time % 1000);
        int seconds = (int) ((time / 1000) % 60);
        int minutes = (int) (time / 60000);
        return minutes + ":" + game.dateUtilities.zeroPadMinutes(seconds)
                + "." + game.dateUtilities.zeroPadMilliseconds(milliseconds);
    }

    private void resetStopwatch() {
        game.resetStopwatch();
        stopwatchElapsedTime = 0;
        game.preferences.putLong("stopwatchElapsedTime", stopwatchElapsedTime);
        stopwatchStartTime = new Date();
        game.preferences.putLong("stopwatchStartTime", stopwatchStartTime.getTime());
        game.preferences.flush();
        stopwatchLabel.setText(formatStopwatchTime(0));
    }

    private void toggleStopwatch() {
        if (stopwatchIsRunning) {
            stopwatchIsRunning = false;
            game.preferences.putBoolean("stopwatchIsRunning", false);
            stopwatchElapsedTime = new Date().getTime() - stopwatchStartTime.getTime() + stopwatchElapsedTime;
            game.preferences.putLong("stopwatchElapsedTime", stopwatchElapsedTime);
            game.preferences.flush();
            stopwatchStartButton.setText(game.bundle.get("stopwatchStart"));
        } else {
            stopwatchIsRunning = true;
            game.preferences.putBoolean("stopwatchIsRunning", true);
            stopwatchStartTime = new Date();
            game.preferences.putLong("stopwatchStartTime", stopwatchStartTime.getTime());
            game.preferences.flush();
            stopwatchStartButton.setText(game.bundle.get("stopwatchPause"));
        }
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
        updateStopwatchLabel();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}

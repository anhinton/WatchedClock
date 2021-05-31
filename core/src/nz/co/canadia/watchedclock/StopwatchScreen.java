package nz.co.canadia.watchedclock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
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

        long stopwatchTime;
        if (stopwatchIsRunning) {
            stopwatchTime = game.getCurrentTime().getTime() - stopwatchStartTime.getTime() + stopwatchElapsedTime;
        } else {
            stopwatchTime = stopwatchElapsedTime;
        }
        String stopwatchTimeText = formatStopwatchTime(stopwatchTime);

        Viewport viewport = new FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        stage = new Stage(viewport);
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        stopwatchLabel = new Label(stopwatchTimeText, game.skin, "default");
        table.add(stopwatchLabel).colspan(2);
        table.row();

        String stopwatchStartButtonText;
        if (stopwatchIsRunning) {
            stopwatchStartButtonText = game.bundle.get("stopwatchPause");
        } else {
            stopwatchStartButtonText = game.bundle.get("stopwatchStart");
        }
        stopwatchStartButton = new TextButton(stopwatchStartButtonText, game.skin, "default");
        stopwatchStartButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                toggleStopwatch();
            }
        });
        table.add(stopwatchStartButton);

        TextButton stopwatchRestartButton = new TextButton(game.bundle.get("stopwatchRestart"),
                game.skin, "default");
        stopwatchRestartButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                resetStopwatch();
            }
        });
        table.add(stopwatchRestartButton);
        table.row();

        table.add(new MenuButtons(game)).colspan(2);

        Gdx.input.setInputProcessor(stage);
    }

    private String formatStopwatchTime(long time) {
        int milliseconds = (int) (time % 1000);
        int seconds = (int) ((time / 1000) % 60);
        int minutes = (int) (time / 60000);
        return minutes + ":" + game.dateUtilities.zeroPadMinutes(seconds)
                + "." + game.dateUtilities.zeroPadMilliseconds(milliseconds);
    }

    private void resetStopwatch() {
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

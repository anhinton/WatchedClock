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
    private Date stopwatchStartTime;
    private boolean stopwatchIsRunning;

    public StopwatchScreen(final WatchedClock game) {
        this.game = game;
        stopwatchIsRunning = game.preferences.getBoolean("stopwatchIsRunning", false);
        stopwatchStartTime = new Date(game.preferences.getLong("stopwatchStartTime", 0));

        long stopwatchTime = game.getCurrentTime().getTime() - stopwatchStartTime.getTime();

        String stopwatchTimeText;
        if (stopwatchIsRunning) {
            int milliseconds = (int) (stopwatchTime % 1000);
            int seconds = (int) ((stopwatchTime / 1000) % 60);
            int minutes = (int) (stopwatchTime / 60000);
            stopwatchTimeText = minutes + ":" + game.dateUtilities.zeroPadMinutes(seconds)
                    + "." + game.dateUtilities.zeroPadMilliseconds(milliseconds);
        } else {
            stopwatchTimeText = "0:00.000";
        }

        Viewport viewport = new FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        stage = new Stage(viewport);
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label stopwatchStartTimeLabel = new Label(
                game.dateUtilities.formatDate(Constants.DATE_TIME_FORMAT, stopwatchStartTime),
                game.skin, "default");
        table.add(stopwatchStartTimeLabel);
        table.row();

        Label stopwatchLabel = new Label(stopwatchTimeText, game.skin, "default");
        table.add(stopwatchLabel);
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
        table.row();

        table.add(new MenuButtons(game));

        Gdx.input.setInputProcessor(stage);
    }

    private void toggleStopwatch() {
        if (stopwatchIsRunning) {
            stopwatchIsRunning = false;
            game.preferences.putBoolean("stopwatchIsRunning", false);
            game.preferences.flush();
            stopwatchStartButton.setText(game.bundle.get("stopwatchStart"));
        } else {
            stopwatchIsRunning = true;
            game.preferences.putBoolean("stopwatchIsRunning", true);
            stopwatchStartTime = game.getCurrentTime();
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

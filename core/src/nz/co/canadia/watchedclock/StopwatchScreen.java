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

        String stopwatchTimeText = formatStopwatchTime(game.getStopwatchTime());

        Viewport viewport = new FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        stage = new Stage(viewport);
        Table table = new Table();
        table.setFillParent(true);
        table.pad(game.getPadding());
        stage.addActor(table);

        stopwatchLabel = new Label(stopwatchTimeText, game.skin, "time");
        table.add(stopwatchLabel)
                .colspan(2)
                .space(game.getPadding());
        table.row();

        String stopwatchStartButtonText;
        if (stopwatchIsRunning) {
            stopwatchStartButtonText = game.bundle.get("stopwatchPause");
        } else {
            stopwatchStartButtonText = game.bundle.get("stopwatchStart");
        }
        stopwatchStartButton = new TextButton(stopwatchStartButtonText,
                game.skin, "menu");
        stopwatchStartButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                toggleStopwatch();
            }
        });
        table.add(stopwatchStartButton)
                .align(Align.right)
                .prefSize(game.getButtonWidth(), game.getButtonHeight())
                .space(game.getPadding());

        TextButton stopwatchRestartButton = new TextButton(game.bundle.get("stopwatchRestart"),
                game.skin, "menu");
        stopwatchRestartButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                resetStopwatch();
            }
        });
        table.add(stopwatchRestartButton)
                .align(Align.left)
                .prefSize(game.getButtonWidth(), game.getButtonHeight())
                .space(game.getPadding());
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

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}

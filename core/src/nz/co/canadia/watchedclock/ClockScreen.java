package nz.co.canadia.watchedclock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ClockScreen implements Screen {
    private final Stage stage;
    private final WatchedClock game;

    public ClockScreen(WatchedClock game) {
        this.game = game;

        Viewport viewport = new FitViewport(Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
        stage = new Stage(viewport);

        Label clockLabel = new Label(game.formatter.formatCurrentTime(game.getCurrentTime()), game.skin, "default");
        clockLabel.setPosition(100, 100);
        stage.addActor(clockLabel);
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

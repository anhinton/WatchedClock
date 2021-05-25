package nz.co.canadia.watchedclock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ClockScreen implements Screen {
    private final Stage stage;

    public ClockScreen(WatchedClock game) {

        Viewport viewport = new FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        stage = new Stage(viewport);
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // CLOCK
        Label clockLabel = new Label(
                game.dateUtilities.formatDate(Constants.CLOCK_TIME_FORMAT, game.getCurrentTime()),
                game.skin, "default");
        table.add(clockLabel).colspan(3);
        table.row();

        table.add(new MenuButtons(game));

        Gdx.input.setInputProcessor(stage);
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

package nz.co.canadia.watchedclock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ClockScreen implements Screen {
    private final Stage stage;
    private final Label clockLabel;
    private final WatchedClock game;

    public ClockScreen(final WatchedClock game) {
        this.game = game;

        Viewport viewport = new FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        stage = new Stage(viewport);
        Table table = new Table();
        table.setFillParent(true);
        table.pad(game.getPadding());
        stage.addActor(table);

        ImageButton infoButton = new ImageButton(game.skin, "info");
        infoButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new InfoScreen(game, ClockScreen.this.getClass().getSimpleName()));
                dispose();
            }
        });
        table.add(infoButton)
                .align(Align.right)
                .prefSize(game.getButtonHeight())
                .space(game.getPadding());
        table.row();

        // CLOCK
        clockLabel = new Label(
                game.dateUtilities.formatDate(Constants.CLOCK_TIME_FORMAT, game.getCurrentTime()),
                game.skin, "time");
        table.add(clockLabel)
                .expand()
                .space(game.getPadding());
        table.row();

        table.add(new MenuButtons(game, this));

        Gdx.input.setInputProcessor(stage);
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
        clockLabel.setText(game.dateUtilities.formatDate(Constants.CLOCK_TIME_FORMAT, game.getCurrentTime()));
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}

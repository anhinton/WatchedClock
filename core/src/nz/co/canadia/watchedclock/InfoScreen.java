package nz.co.canadia.watchedclock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class InfoScreen implements Screen {
    private final Stage stage;

    public InfoScreen(final WatchedClock game, final String lastScreen) {
        Viewport viewport = new FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        stage = new Stage(viewport);
        Table table = new Table();
        table.setFillParent(true);
        table.pad(game.getPadding());
        stage.addActor(table);

        FileHandle file = Gdx.files.internal(game.bundle.get("credits"));
        Label creditsLabel = new Label(file.readString("utf-8"), game.skin, "credits");
        creditsLabel.setWrap(true);
        ScrollPane creditsPane = new ScrollPane(creditsLabel, game.skin, "credits");
        creditsPane.setFadeScrollBars(false);
        table.add(creditsPane).prefWidth(Constants.WORLD_WIDTH).space(game.getPadding());
        table.row();

        TextButton backButton = new TextButton("Back", game.skin, "control");
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Screen screen;
                switch (lastScreen) {
                    case "AlarmScreen":
                        screen = new AlarmScreen(game);
                        break;
                    case "StopwatchScreen":
                        screen = new StopwatchScreen(game);
                        break;
                    case "TimerScreen":
                        screen = new TimerScreen(game);
                        break;
                    case "ClockScreen":
                    default:
                        screen = new ClockScreen(game);
                        break;
                }
                game.setScreen(screen);
            }
        });
        table.add(backButton)
                .prefSize(game.getControlButtonWidth(), game.getButtonHeight())
                .space(game.getPadding());

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

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}

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

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ClockScreen implements Screen {
    private final Stage stage;
    private String alarmTime;

    public ClockScreen(WatchedClock game) {

        Viewport viewport = new FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        stage = new Stage(viewport);
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // CLOCK
        Label clockLabel = new Label(game.formatter.formatCurrentTime(game.getCurrentTime()), game.skin, "default");
        table.add(clockLabel).colspan(3);
        table.row();

        // ALARM
        final SelectBox<String> hourSelectBox = new SelectBox<>(game.skin, "default");
        Array<String> hourStringArray = new Array<>(12);
        hourStringArray.add("12");
        for (int i = 1; i < 12; i++) {
            hourStringArray.add(String.valueOf(i));
        }
        hourSelectBox.setItems(hourStringArray);
        table.add(hourSelectBox);

        final SelectBox<String> minuteSelectBox = new SelectBox<>(game.skin, "default");
        Array<String> minuteStringArray = new Array<>(12);
        for (int i = 0; i < 12; i++) {
            minuteStringArray.add(game.formatter.zeroPadMinutes(i * 5));
        }
        minuteSelectBox.setItems(minuteStringArray);
        table.add(minuteSelectBox);

        final SelectBox<String> am_pmSelectBox = new SelectBox<>(game.skin, "default");
        am_pmSelectBox.setItems("AM", "PM");
        table.add(am_pmSelectBox);
        table.row();

        TextButton setAlarmButton = new TextButton("Set alarm", game.skin, "default");
        setAlarmButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String alarmTime =
                        hourSelectBox.getSelected() + ":" + minuteSelectBox.getSelected() + " " + am_pmSelectBox.getSelected();
                setAlarmTime(alarmTime);
            }
        });


        table.add(setAlarmButton).colspan(3);

        Gdx.input.setInputProcessor(stage);
    }

    private void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
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

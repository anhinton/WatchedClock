package nz.co.canadia.watchedclock;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MenuButtons extends Table  {

    public MenuButtons(final WatchedClock game) {
        TextButton clockButton = new TextButton(game.bundle.get("clockButton"), game.skin, "default");
        clockButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new ClockScreen(game));
            }
        });
        this.add(clockButton);

        TextButton alarmButton = new TextButton(game.bundle.get("alarmButton"), game.skin, "default");
        alarmButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new AlarmScreen(game));
            }
        });
        this.add(alarmButton);

        TextButton stopwatchButton = new TextButton(game.bundle.get("stopwatchButton"), game.skin, "default");
        stopwatchButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new StopwatchScreen(game));
            }
        });
        this.add(stopwatchButton);
    }
}

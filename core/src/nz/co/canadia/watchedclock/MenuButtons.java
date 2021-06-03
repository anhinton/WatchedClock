package nz.co.canadia.watchedclock;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MenuButtons extends Table  {

    public MenuButtons(final WatchedClock game) {
        TextButton clockButton = new TextButton(game.bundle.get("clockButton"), game.skin, "menu");
        clockButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new ClockScreen(game));
            }
        });
        this.add(clockButton).prefSize(game.getButtonWidth(), game.getButtonHeight()).space(game.getPadding());

        TextButton alarmButton = new TextButton(game.bundle.get("alarmButton"), game.skin, "menu");
        alarmButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new AlarmScreen(game));
            }
        });
        this.add(alarmButton).prefSize(game.getButtonWidth(), game.getButtonHeight()).space(game.getPadding());

        TextButton stopwatchButton = new TextButton(game.bundle.get("stopwatchButton"), game.skin, "menu");
        stopwatchButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new StopwatchScreen(game));
            }
        });
        this.add(stopwatchButton).prefSize(game.getButtonWidth(), game.getButtonHeight()).space(game.getPadding());

        TextButton timerButton = new TextButton(game.bundle.get("timerButton"), game.skin, "menu");
        timerButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new TimerScreen(game));
            }
        });
        this.add(timerButton).prefSize(game.getButtonWidth(), game.getButtonHeight()).space(game.getPadding());

    }
}

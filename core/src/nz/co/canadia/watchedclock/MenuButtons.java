package nz.co.canadia.watchedclock;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MenuButtons extends Table  {

    public MenuButtons(final WatchedClock game, String selected) {
        TextButton clockButton = new TextButton(game.bundle.get("clockButton"), game.skin, "menu");
        TextButton alarmButton = new TextButton(game.bundle.get("alarmButton"), game.skin, "menu");
        TextButton stopwatchButton = new TextButton(game.bundle.get("stopwatchButton"), game.skin, "menu");
        TextButton timerButton = new TextButton(game.bundle.get("timerButton"), game.skin, "menu");

        switch (selected) {
            case "Alarm":
                alarmButton.setChecked(true);
                break;
            case "Stopwatch":
                stopwatchButton.setChecked(true);
                break;
            case "Timer":
                timerButton.setChecked(true);
                break;
            default:
                clockButton.setChecked(true);
                break;
        }

        clockButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new ClockScreen(game));
            }
        });
        this.add(clockButton).prefSize(game.getMenuButtonWidth(), game.getButtonHeight()).space(game.getPadding());

        alarmButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new AlarmScreen(game));
            }
        });
        this.add(alarmButton).prefSize(game.getMenuButtonWidth(), game.getButtonHeight()).space(game.getPadding());

        stopwatchButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new StopwatchScreen(game));
            }
        });
        this.add(stopwatchButton).prefSize(game.getMenuButtonWidth(), game.getButtonHeight()).space(game.getPadding());

        timerButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new TimerScreen(game));
            }
        });
        this.add(timerButton).prefSize(game.getMenuButtonWidth(), game.getButtonHeight()).space(game.getPadding());
    }
}

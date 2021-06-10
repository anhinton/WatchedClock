package nz.co.canadia.watchedclock;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MenuButtons extends Table  {

    public MenuButtons(final WatchedClock game, String currentScreen) {
        TextButton clockButton = new TextButton(game.bundle.get("clockButton"), game.skin, "menu");
        TextButton alarmButton = new TextButton(game.bundle.get("alarmButton"), game.skin, "menu");
        TextButton stopwatchButton = new TextButton(game.bundle.get("stopwatchButton"), game.skin, "menu");
        TextButton timerButton = new TextButton(game.bundle.get("timerButton"), game.skin, "menu");

        switch (currentScreen) {
            case "AlarmScreen":
                alarmButton.setChecked(true);
                break;
            case "StopwatchScreen":
                stopwatchButton.setChecked(true);
                break;
            case "TimerScreen":
                timerButton.setChecked(true);
                break;
            default:
                clockButton.setChecked(true);
                break;
        }

        clockButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.getScreen().dispose();
                game.setScreen(new ClockScreen(game), "ClockScreen");
            }
        });
        this.add(clockButton).prefSize(game.getMenuButtonWidth(), game.getButtonHeight()).space(game.getPadding());

        alarmButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.getScreen().dispose();
                game.setScreen(new AlarmScreen(game), "AlarmScreen");
            }
        });
        this.add(alarmButton).prefSize(game.getMenuButtonWidth(), game.getButtonHeight()).space(game.getPadding());

        stopwatchButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.getScreen().dispose();
                game.setScreen(new StopwatchScreen(game), "StopwatchScreen");
            }
        });
        this.add(stopwatchButton).prefSize(game.getMenuButtonWidth(), game.getButtonHeight()).space(game.getPadding());

        timerButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.getScreen().dispose();
                game.setScreen(new TimerScreen(game), "TimerScreen");
            }
        });
        this.add(timerButton).prefSize(game.getMenuButtonWidth(), game.getButtonHeight()).space(game.getPadding());
    }
}

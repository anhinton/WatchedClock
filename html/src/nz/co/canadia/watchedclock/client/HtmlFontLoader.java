package nz.co.canadia.watchedclock.client;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import nz.co.canadia.watchedclock.FontLoader;

public class HtmlFontLoader implements FontLoader {
    @Override
    public void loadTimeLabelFont(AssetManager manager) {
        manager.load("fonts/timeLabel.fnt", BitmapFont.class);
    }

    @Override
    public BitmapFont getTimeLabelFont(AssetManager manager) {
        return manager.get("fonts/timeLabel.fnt", BitmapFont.class);
    }

    @Override
    public void loadAlarmLabelFont(AssetManager manager) {
        manager.load("fonts/alarmLabel.fnt", BitmapFont.class);
    }

    @Override
    public BitmapFont getAlarmLabelFont(AssetManager manager) {
        return manager.get("fonts/alarmLabel.fnt", BitmapFont.class);
    }

    @Override
    public void loadMenuButtonFont(AssetManager manager) {
        manager.load("fonts/menuButton.fnt", BitmapFont.class);
    }

    @Override
    public BitmapFont getMenuButtonFont(AssetManager manager) {
        return manager.get("fonts/menuButton.fnt", BitmapFont.class);
    }

    @Override
    public void loadBoxListFont(AssetManager manager) {
        manager.load("fonts/boxList.fnt", BitmapFont.class);
    }

    @Override
    public BitmapFont getBoxListFont(AssetManager manager) {
        return manager.get("fonts/boxList.fnt", BitmapFont.class);
    }

    @Override
    public void loadCreditsLabelFont(AssetManager manager) {
        manager.load("fonts/creditsLabel.fnt", BitmapFont.class);
    }

    @Override
    public BitmapFont getCreditsLabelFont(AssetManager manager) {
        return manager.get("fonts/creditsLabel.fnt", BitmapFont.class);
    }
}

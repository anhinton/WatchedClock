package nz.co.canadia.watchedclock;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public interface FontLoader {
    void loadTimeLabelFont(AssetManager manager);

    BitmapFont getTimeLabelFont(AssetManager manager);

    void loadAlarmLabelFont(AssetManager manager);

    BitmapFont getAlarmLabelFont(AssetManager manager);

    void loadMenuButtonFont(AssetManager manager);

    BitmapFont getMenuButtonFont(AssetManager manager);

    void loadBoxListFont(AssetManager manager);

    BitmapFont getBoxListFont(AssetManager manager);
}

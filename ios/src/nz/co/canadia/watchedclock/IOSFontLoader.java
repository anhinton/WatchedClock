package nz.co.canadia.watchedclock;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.math.MathUtils;

public class IOSFontLoader implements FontLoader {
    private final InternalFileHandleResolver resolver;

    public IOSFontLoader() {
        resolver = new InternalFileHandleResolver();
    }

    private void setLoader(AssetManager manager) {
        manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
    }

    @Override
    public void loadTimeLabelFont(AssetManager manager) {
        setLoader(manager);
        FreetypeFontLoader.FreeTypeFontLoaderParameter timeLabelFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        timeLabelFont.fontFileName = "fonts/Inconsolata-VariableFont_wdth,wght.ttf";
        timeLabelFont.fontParameters.size = MathUtils.round(Constants.TIME_LABEL_FONT_SIZE * Constants.WORLD_WIDTH);
        manager.load("fonts/InconsolataTimeLabel.ttf", BitmapFont.class, timeLabelFont);
    }

    @Override
    public BitmapFont getTimeLabelFont(AssetManager manager) {
        return manager.get("fonts/InconsolataTimeLabel.ttf", BitmapFont.class);
    }

    @Override
    public void loadAlarmLabelFont(AssetManager manager) {
        setLoader(manager);
        FreetypeFontLoader.FreeTypeFontLoaderParameter alarmLabelFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        alarmLabelFont.fontFileName = "fonts/Inconsolata-VariableFont_wdth,wght.ttf";
        alarmLabelFont.fontParameters.size = MathUtils.round(Constants.ALARM_LABEL_FONT_SIZE * Constants.WORLD_WIDTH);
        manager.load("fonts/InconsolataAlarmLabel.ttf", BitmapFont.class, alarmLabelFont);
    }

    @Override
    public BitmapFont getAlarmLabelFont(AssetManager manager) {
        return manager.get("fonts/InconsolataAlarmLabel.ttf", BitmapFont.class);
    }

    @Override
    public void loadMenuButtonFont(AssetManager manager) {
        setLoader(manager);
        FreetypeFontLoader.FreeTypeFontLoaderParameter menuButtonFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        menuButtonFont.fontFileName = "fonts/Podkova-VariableFont_wght.ttf";
        menuButtonFont.fontParameters.size = MathUtils.round(Constants.MENU_BUTTON_FONT_SIZE * Constants.WORLD_WIDTH);
        menuButtonFont.fontParameters.color = Constants.BUTTON_FONT_COLOR;
        menuButtonFont.fontParameters.shadowColor = Constants.BUTTON_FONT_SHADOW_COLOR;
        menuButtonFont.fontParameters.shadowOffsetX = MathUtils.round(Constants.BUTTON_SHADOW_SIZE * Constants.WORLD_WIDTH);
        menuButtonFont.fontParameters.shadowOffsetY = MathUtils.round(Constants.BUTTON_SHADOW_SIZE * Constants.WORLD_WIDTH);
        manager.load("fonts/PodkovaMenuButton.ttf", BitmapFont.class, menuButtonFont);
    }

    @Override
    public BitmapFont getMenuButtonFont(AssetManager manager) {
        return manager.get("fonts/PodkovaMenuButton.ttf", BitmapFont.class);
    }

    @Override
    public void loadBoxListFont(AssetManager manager) {
        setLoader(manager);
        FreetypeFontLoader.FreeTypeFontLoaderParameter boxListFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        boxListFont.fontFileName = "fonts/Inconsolata-VariableFont_wdth,wght.ttf";
        boxListFont.fontParameters.size = MathUtils.round(Constants.BOX_LIST_FONT_SIZE * Constants.WORLD_WIDTH);
        manager.load("fonts/InconsolataBoxList.ttf", BitmapFont.class, boxListFont);
    }

    @Override
    public BitmapFont getBoxListFont(AssetManager manager) {
        return manager.get("fonts/InconsolataBoxList.ttf", BitmapFont.class);
    }
}

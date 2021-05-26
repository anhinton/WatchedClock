package nz.co.canadia.watchedclock;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Date;

public class WatchedClock extends Game {
	AssetManager manager;
	SpriteBatch batch;
	I18NBundle bundle;
	DateUtilities dateUtilities;
	Preferences preferences;
	Skin skin;
	private Date currentTime;

	public WatchedClock(DateUtilities dateUtilities) {
		this.dateUtilities = dateUtilities;
	}

	@Override
	public void create () {
		currentTime = new Date();

		batch = new SpriteBatch();
		manager = new AssetManager();
		preferences = Gdx.app.getPreferences(Constants.PREFERENCES_PATH);

		manager.load("skin/uiskin.json", Skin.class);
		manager.load("i18n/Bundle", I18NBundle.class);
		manager.finishLoading();

		bundle = manager.get("i18n/Bundle", I18NBundle.class);
		skin = manager.get("skin/uiskin.json", Skin.class);

		this.setScreen(new StopwatchScreen(this));
	}

	@Override
	public void render () {
		super.render(); // important!
	}

	@Override
	public void resume() {
		currentTime = new Date();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		manager.dispose();
	}

	public Date getCurrentTime() {
		return currentTime;
	}
}

package nz.co.canadia.watchedclock;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.Date;

public class WatchedClock extends Game {
	AssetManager manager;
	SpriteBatch batch;
	Formatter formatter;
	Preferences preferences;
	Skin skin;
	private Date currentTime;

	public WatchedClock(Formatter formatter) {
		this.formatter = formatter;
	}

	@Override
	public void create () {
		currentTime = new Date();

		batch = new SpriteBatch();
		manager = new AssetManager();
		preferences = Gdx.app.getPreferences(Constants.PREFERENCES_PATH);

		manager.load("skin/uiskin.json", Skin.class);
		manager.finishLoading();

		skin = manager.get("skin/uiskin.json", Skin.class);

		this.setScreen(new AlarmScreen(this));
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

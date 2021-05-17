package nz.co.canadia.watchedclock;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Date;

public class WatchedClock extends ApplicationAdapter {
	private final Formatter formatter;
	SpriteBatch batch;
	private BitmapFont font;
	private Date currentTime;

	public WatchedClock(Formatter formatter) {
		this.formatter = formatter;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();

		font = new BitmapFont();

		currentTime = new Date();
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		font.draw(batch, formatter.formatCurrentTime(currentTime), 100, 100);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}
}

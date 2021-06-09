package nz.co.canadia.watchedclock.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import nz.co.canadia.watchedclock.Constants;
import nz.co.canadia.watchedclock.WatchedClock;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle(Constants.GAME_NAME);
		config.setWindowedMode(Constants.DESKTOP_WIDTH, Constants.DESKTOP_HEIGHT);
		config.setResizable(false);
		config.setWindowIcon("desktopIcons/icon_128.png",
				"desktopIcons/icon_32.png",
				"desktopIcons/icon_16.png");

		new Lwjgl3Application(new WatchedClock(new DesktopDateUtilities(), new DesktopFontloader()), config);
	}
}

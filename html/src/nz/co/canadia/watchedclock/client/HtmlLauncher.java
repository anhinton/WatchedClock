package nz.co.canadia.watchedclock.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

import nz.co.canadia.watchedclock.Constants;
import nz.co.canadia.watchedclock.WatchedClock;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(Constants.HTML_WIDTH, Constants.HTML_HEIGHT);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new WatchedClock();
        }
}
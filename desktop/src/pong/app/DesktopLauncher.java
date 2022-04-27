package pong.app;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import pong.app.PongMain;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static final float WINDOW_WIDTH = 1200;
	public static final float WINDOW_HEIGHT = 900;

	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Pong");
		config.setForegroundFPS(60);
		config.setWindowedMode(1200, 900);
		new Lwjgl3Application(new PongMain(), config);
	}
}

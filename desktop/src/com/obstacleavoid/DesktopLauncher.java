package com.obstacleavoid;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.obstacleavoid.config.GameConfig;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("obstacle-avoid");
		int width = (int) GameConfig.WIDTH;
		int height =  (int) GameConfig.HEIGHT;
		config.setWindowedMode(width, height);
		config.setWindowPosition(0, 30);
		new Lwjgl3Application(new ObstacleAvoidGame(), config);
	}
}

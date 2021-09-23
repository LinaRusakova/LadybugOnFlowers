package com.gmail.xlinaris.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gmail.xlinaris.LadybugOnFlowers;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height=800;
		config.width=600;
		config.resizable=false;
		config.addIcon("textures/ic_launcher.png", Files.FileType.Internal);
		new LwjglApplication(new LadybugOnFlowers(), config);
	}
}

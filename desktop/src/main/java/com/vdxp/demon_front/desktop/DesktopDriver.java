package com.vdxp.demon_front.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.vdxp.demon_front.core.Game;

public class DesktopDriver {

	public static void main(final String[] args) throws Exception {
		final Game game = Game.instance();

		final LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Game.WIDTH;
		config.height = Game.HEIGHT;
		config.useGL20 = true;
		config.resizable = false;
		config.fullscreen = false;
		config.title = "Demon Front";

		new LwjglApplication(game, config);
	}

}

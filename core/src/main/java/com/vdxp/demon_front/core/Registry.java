package com.vdxp.demon_front.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Logger;

/** Registry and initializer of global resources */
public class Registry {

	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;

	public final Game game;
	public final Logger log;
	public final AssetManager assetManager;

	public final PlatformSupport platformSupport;

	public Registry(final PlatformSupport platformSupport) {
		// This happens before Gdx initialization
		this.game = new Game(this);
		this.log = new Logger("demon_front", Logger.DEBUG);
		this.assetManager = new AssetManager();
		this.platformSupport = platformSupport;
	}

	public Preferences getPreferences() {
		return Gdx.app.getPreferences("com.vdxp.demon_front.demon_front");
	}

	public String getClientId() {
		final Preferences preferences = getPreferences();

		String clientId = preferences.getString("clientId", "");
		if (clientId.isEmpty()) {
			clientId = generateClientId();
			preferences.putString("clientId", clientId);
			preferences.flush();
		}
		return clientId;
	}

	private static String generateClientId() {
		//noinspection SpellCheckingInspection
		final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		final int numChars = chars.length();

		final StringBuilder sb = new StringBuilder(16);
		for (int i = 0; i < 16; i++) {
			final int c = (int) Math.floor(Math.random() * numChars);
			sb.append(chars.charAt(c));
		}

		return sb.toString();
	}

}

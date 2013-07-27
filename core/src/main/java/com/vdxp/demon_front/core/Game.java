package com.vdxp.demon_front.core;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.SerializationException;

public class Game extends com.badlogic.gdx.Game {
	private static final Game instance = new Game();
	private String version = "";
	private Runnable postInit = new EmptyRunnable();

	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;

    private AssetManager assetManager;

    private static Sounds soundMan = new Sounds();

	private Game() {
	}

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		assetManager = new AssetManager();
		version = loadVersion();
		setScreen(new LoadingScreen(this).setNextScreen(new SplashScreen(this)));
		postInit.run();
	}

	public static Game instance() {
		return instance;
	}

	public AssetManager assetManager() {
		return assetManager;
	}

	public String getVersion() {
		return version;
	}

	public void setPostInit(final Runnable postInit) {
		this.postInit = postInit;
	}

	private static class EmptyRunnable implements Runnable {
		@Override
		public void run() {
		}
	}

	private static String loadVersion() {
		try {
			final FileHandle versionFile = Gdx.files.internal("version.json");
			final JsonValue versionRoot = new JsonReader().parse(versionFile);
			final String versionNumber = versionRoot.get("version").asString();
			final String buildNumber = versionRoot.get("buildNumber").asString();
			final String buildDate = versionRoot.get("buildDate").asString();
			final String commit = versionRoot.get("commit").asString();

			String version;

			if (buildNumber != null && !buildNumber.isEmpty() && !buildNumber.contains("${")) {
				version = "Build " + buildNumber;
			} else if (commit != null && !commit.isEmpty() && !commit.contains("${")) {
				version = "Commit " + commit;
			} else {
				version = "Version " + versionNumber;
			}

			if (buildDate != null && !buildDate.isEmpty() && !buildDate.contains("${")) {
				version += " (" + buildDate + ")";
			}

			return version;

		} catch (SerializationException e) {
			Gdx.app.log("Demon Front", "Exception", e);
			return "(unknown version)";
		}
	}

}

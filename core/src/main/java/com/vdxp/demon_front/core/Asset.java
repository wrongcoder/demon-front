package com.vdxp.demon_front.core;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Asset {

	public static final String mono16Font = "fonts/liberation-mono-16.fnt";
	public static final String sans16Font = "fonts/liberation-sans-16.fnt";
	public static final String sans24Font = "fonts/liberation-sans-24.fnt";
	public static final String sans48Font = "fonts/liberation-sans-48.fnt";
	public static final String sans28boldFont = "fonts/liberation-sans-28-bold.fnt";


	public static final String loadingAtlas = "textures/loading.atlas";
	public static final String loadingText = "text";
	public static final String loadingBorder = "border";

	public static final String libgdxLogo = "libgdx-logo.png";

	public static final String spritesAtlas = "textures/sprites.atlas";

	public static void queueAssets(final AssetManager assetManager) {
		// Assets for LoadingScreen should be loaded first
		assetManager.load(loadingAtlas, TextureAtlas.class);

		// Remaining assets
		assetManager.load(libgdxLogo, Texture.class);
		assetManager.load(mono16Font, BitmapFont.class);
		assetManager.load(sans16Font, BitmapFont.class);
		assetManager.load(sans24Font, BitmapFont.class);
		assetManager.load(sans48Font, BitmapFont.class);
		assetManager.load(sans28boldFont, BitmapFont.class);
		assetManager.load(spritesAtlas, TextureAtlas.class);
	}

}

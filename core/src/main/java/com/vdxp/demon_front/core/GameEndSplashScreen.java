package com.vdxp.demon_front.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GameEndSplashScreen extends Screen {

	private BitmapFont bigFont;
	private BitmapFont normalFont;
	private BitmapFont smallFont;
	private Color fontColour;

	private SpriteBatch batch;

	private static final float maxFadeTimerBucket = 0.5f;
	private static final float blankTime = 0.05f;

	private boolean proceed = false;
	private float fadeTimerBucket = 0;

	public GameEndSplashScreen(final Registry r) {
		super(r);
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		bigFont = r.assetManager.get(Asset.sans48Font);
		normalFont = r.assetManager.get(Asset.sans24Font);
		smallFont = r.assetManager.get(Asset.sans16Font);
		fontColour = new Color(1, 1, 1, 1);
		Gdx.input.setInputProcessor(new GameEndSplashScreenInputHandler());
	}

	@Override
	public void render(final float delta) {
		if (proceed) {
			if (fadeTimerBucket > maxFadeTimerBucket + blankTime) {
				r.game.setScreen(new SpriteTestScreen(r));
				return;
			} else {
				fadeTimerBucket += delta;
				fontColour.a = Math.max(0, 1 - (fadeTimerBucket / maxFadeTimerBucket));
			}
		}

		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		bigFont.setColor(fontColour);
		normalFont.setColor(fontColour);
		smallFont.setColor(fontColour);

		batch.begin();
		showMessage(batch, bigFont, normalFont, smallFont);
		drawCentred(smallFont, batch, "Click any button to play again.", Registry.WIDTH / 2, 150);
		batch.end();
	}

	public abstract void showMessage(final SpriteBatch batch, final BitmapFont bigFont, final BitmapFont normalFont, final BitmapFont smallFont);

	private class GameEndSplashScreenInputHandler extends InputAdapter {

		@Override
		public boolean touchDown(final int screenX, final int screenY, final int pointer, final int button) {
			proceed = true;
			return false;
		}
	}
}

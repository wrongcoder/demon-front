package com.vdxp.demon_front.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.vdxp.demon_front.core.Util.drawCentred;

public class SplashScreen extends Screen {

	private BitmapFont bigFont;
	private BitmapFont normalFont;
	private BitmapFont smallFont;
	private Color fontColour;

	private SpriteBatch batch;

	private static final float maxDisplayTimerBucket = 2.5f;
	private static final float maxFadeTimerBucket = 0.8f;

	private float displayTimerBucket = 0;
	private float fadeTimerBucket = 0;

	public SplashScreen(final Game game) {
		super(game);
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		bigFont = assetManager().get(Asset.sans48Font);
		normalFont = assetManager().get(Asset.sans24Font);
		smallFont = assetManager().get(Asset.sans16Font);
		fontColour = new Color(1, 1, 1, 1);
	}

	@Override
	public void render(final float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);


		if (displayTimerBucket > maxDisplayTimerBucket) {
			if (fadeTimerBucket > maxFadeTimerBucket) {
				game().setScreen(new SpriteTestScreen(game()));
				return;
			} else {
				fadeTimerBucket += delta;
				fontColour.a = 1 - (fadeTimerBucket / maxFadeTimerBucket);
			}
		} else {
			displayTimerBucket += delta;
		}

		bigFont.setColor(fontColour);
		normalFont.setColor(fontColour);
		smallFont.setColor(fontColour);

		batch.begin();
		drawCentred("Demon Front", bigFont, batch, 470);
		drawCentred("A MiniLD 44 Game", normalFont, batch, 395);
		drawCentred("Controls:", normalFont, batch, 250);
		drawCentred("Arrow Keys", normalFont, batch, 210);
		drawCentred("(We were too lazy to implement mouse controls)", smallFont, batch, 160);
		batch.end();
	}

}

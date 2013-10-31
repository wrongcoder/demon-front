package com.vdxp.demon_front.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TitleSplashScreen extends Screen {

	private BitmapFont bigFont;
	private BitmapFont normalFont;
	private BitmapFont smallFont;
	private Color fontColour;

	private SpriteBatch batch;

	private static final float maxDisplayTimerBucket = 2f;
	private static final float maxFadeTimerBucket = 0.5f;
	private static final float blankTime = 0.05f;

	private float displayTimerBucket = 0;
	private float fadeTimerBucket = 0;

	public TitleSplashScreen(final Registry r) {
		super(r);
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		bigFont = r.assetManager.get(Asset.sans48Font);
		normalFont = r.assetManager.get(Asset.sans24Font);
		smallFont = r.assetManager.get(Asset.sans16Font);
		fontColour = new Color(1, 1, 1, 1);
		Gdx.input.setInputProcessor(new TitleSplashScreenInputHandler());

        r.game.getMusicMan().playUIStageIntro();

    }

	@Override
	public void render(final float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);


		if (displayTimerBucket > maxDisplayTimerBucket) {
			if (fadeTimerBucket > maxFadeTimerBucket + blankTime) {
				r.game.setScreen(new IntroSplashScreen1(r));
				return;
			} else {
				fadeTimerBucket += delta;
				fontColour.a = Math.max(0, 1 - (fadeTimerBucket / maxFadeTimerBucket));
			}
		} else {
			displayTimerBucket += delta;
		}

		bigFont.setColor(fontColour);
		normalFont.setColor(fontColour);
		smallFont.setColor(fontColour);

		batch.begin();
		drawCentred(bigFont, batch, "Demon Front", Registry.WIDTH / 2, 430);
		drawCentred(normalFont, batch, "A MiniLD 44 Game", Registry.WIDTH / 2, 280);
		drawCentred(smallFont, batch, "by citizenlion and wrongcoder", Registry.WIDTH / 2, 235);
		batch.end();
	}

	private class TitleSplashScreenInputHandler extends InputAdapter {
		@Override
		public boolean keyDown(final int keycode) {
			displayTimerBucket = maxDisplayTimerBucket;
			return false;
		}

		@Override
		public boolean touchDown(final int screenX, final int screenY, final int pointer, final int button) {
			displayTimerBucket = maxDisplayTimerBucket;
			return false;
		}
	}
}

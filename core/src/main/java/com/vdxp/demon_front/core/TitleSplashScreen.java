package com.vdxp.demon_front.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.vdxp.demon_front.core.Util.drawCentred;

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

	public TitleSplashScreen(final Game game) {
		super(game);
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		bigFont = assetManager().get(Asset.sans48Font);
		normalFont = assetManager().get(Asset.sans24Font);
		smallFont = assetManager().get(Asset.sans16Font);
		fontColour = new Color(1, 1, 1, 1);
		Gdx.input.setInputProcessor(new TitleSplashScreenInputHandler());

        game().getMusicMan().playUIStageIntro();

    }

	@Override
	public void render(final float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);


		if (displayTimerBucket > maxDisplayTimerBucket) {
			if (fadeTimerBucket > maxFadeTimerBucket + blankTime) {
				game().setScreen(new IntroSplashScreen1(game()));
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
		drawCentred("Demon Front", bigFont, batch, 430);
		drawCentred("A MiniLD 44 Game", normalFont, batch, 280);
		drawCentred("by citizenlion and wrongcoder", smallFont, batch, 235);
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

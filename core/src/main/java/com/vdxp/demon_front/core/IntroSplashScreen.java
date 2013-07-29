package com.vdxp.demon_front.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static com.vdxp.demon_front.core.Util.drawCentredOn;

public abstract class IntroSplashScreen extends Screen {

	private BitmapFont bigFont;
	private BitmapFont normalFont;
	private BitmapFont smallFont;
	private Color colour;

	private SpriteBatch batch;

	private Sprite keyW, keyA, keyS, keyD;
	private Sprite keyUp, keyLeft, keyRight, keyDown;

	private static final float maxDisplayTimerBucket = 5f;
	private static final float maxFadeTimerBucket = 0.5f;
	private static final float blankTime = 0.05f;

	private final float wasdBaseX = 550 - 32;
	private final float wasdBaseY = 80;
	private final float arrowsBaseX = 250;
	private final float arrowsBaseY = 80;

	private final Screen nextScreen;

	private float displayTimerBucket = 0;
	private float fadeTimerBucket = 0;

	public IntroSplashScreen(final Game game, final Screen nextScreen) {
		super(game);
		this.nextScreen = nextScreen;
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		bigFont = assetManager().get(Asset.sans48Font);
		normalFont = assetManager().get(Asset.sans24Font);
		smallFont = assetManager().get(Asset.sans16Font);
		colour = new Color(1, 1, 1, 1);
		Gdx.input.setInputProcessor(new IntroSplashScreenInputHandler());

		final TextureAtlas spritesAtlas = assetManager().get(Asset.spritesAtlas);

		keyW = getSprite("keyW", spritesAtlas);
		keyA = getSprite("keyA", spritesAtlas);
		keyS = getSprite("keyS", spritesAtlas);
		keyD = getSprite("keyD", spritesAtlas);

		keyUp = getSprite("keyUp", spritesAtlas);
		keyLeft = getSprite("keyLeft", spritesAtlas);
		keyRight = getSprite("keyRight", spritesAtlas);
		keyDown = getSprite("keyDown", spritesAtlas);

		keyW.setPosition(wasdBaseX - 4, wasdBaseY); // it's funny
		keyA.setPosition(wasdBaseX - 32, wasdBaseY - 32);
		keyS.setPosition(wasdBaseX, wasdBaseY - 32);
		keyD.setPosition(wasdBaseX + 32, wasdBaseY - 32);

		keyUp.setPosition(arrowsBaseX, arrowsBaseY);
		keyLeft.setPosition(arrowsBaseX - 32, arrowsBaseY - 32);
		keyDown.setPosition(arrowsBaseX, arrowsBaseY - 32);
		keyRight.setPosition(arrowsBaseX + 32, arrowsBaseY - 32);
	}

	private static Sprite getSprite(final String region, final TextureAtlas spritesAtlas) {
		return new Sprite(spritesAtlas.findRegion(region));
	}

	protected BitmapFont getBigFont() {
		return bigFont;
	}

	protected BitmapFont getNormalFont() {
		return normalFont;
	}

	protected BitmapFont getSmallFont() {
		return smallFont;
	}

	protected SpriteBatch getBatch() {
		return batch;
	}

	@Override
	public void render(final float delta) {
		if (displayTimerBucket > maxDisplayTimerBucket) {
			if (nextScreen instanceof IntroSplashScreen || fadeTimerBucket > maxFadeTimerBucket + blankTime) {
				game().setScreen(nextScreen);
				return;
			} else {
				fadeTimerBucket += delta;
				colour.a = Math.max(0, 1 - (fadeTimerBucket / maxFadeTimerBucket));
			}
		} else {
			displayTimerBucket += delta;
		}

		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		bigFont.setColor(colour);
		normalFont.setColor(colour);
		smallFont.setColor(colour);

		keyW.setColor(colour);
		keyA.setColor(colour);
		keyS.setColor(colour);
		keyD.setColor(colour);
		keyUp.setColor(colour);
		keyLeft.setColor(colour);
		keyRight.setColor(colour);
		keyDown.setColor(colour);

		batch.begin();
		drawCentredOn("Move Hero", smallFont, batch, arrowsBaseX + 16, arrowsBaseY + 50);
		drawCentredOn("Command Orcs", smallFont, batch, wasdBaseX + 16, wasdBaseY + 50);
		keyW.draw(batch);
		keyA.draw(batch);
		keyS.draw(batch);
		keyD.draw(batch);
		keyUp.draw(batch);
		keyLeft.draw(batch);
		keyRight.draw(batch);
		keyDown.draw(batch);
		batch.end();
	}

	private class IntroSplashScreenInputHandler extends InputAdapter {
		/*
		@Override
		public boolean keyDown(final int keycode) {
			displayTimerBucket = maxDisplayTimerBucket;
			return false;
		}
		*/

		@Override
		public boolean touchDown(final int screenX, final int screenY, final int pointer, final int button) {
			displayTimerBucket = maxDisplayTimerBucket;
			return false;
		}
	}

}

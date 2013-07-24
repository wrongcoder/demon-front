package com.vdxp.demon_front.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** static members only! */
public class Util {

	public static float interpolate(final float prev, final float curr, final float alpha) {
		return (prev * (1 - alpha)) + (curr * alpha);
	}

	public static void drawCentred(final String text, final BitmapFont font, final SpriteBatch batch, final float y) {
		final BitmapFont.TextBounds bounds = font.getBounds(text);
		final float centreX = (Game.WIDTH - bounds.width) / 2;
		font.draw(batch, text, centreX, y);
	}

	public static void log(final String message) {
		Gdx.app.log("DF", message);
	}

	public static void log(final String message, final Exception exception) {
		Gdx.app.log("DF", message, exception);
	}
}

package com.vdxp.demon_front.core;

import com.badlogic.gdx.Gdx;

/** static members only! */
public class Util {

	public static float interpolate(final float prev, final float curr, final float alpha) {
		return (prev * (1 - alpha)) + (curr * alpha);
	}

	public static void log(final String message) {
		Gdx.app.log("DF", message);
	}

	public static void log(final String message, final Exception exception) {
		Gdx.app.log("DF", message, exception);
	}
}

package com.vdxp.demon_front.core;

/** static members only! */
public class Util {

	public static float interpolate(final float prev, final float curr, final float alpha) {
		return (prev * (1 - alpha)) + (curr * alpha);
	}

}

package com.vdxp.demon_front.core.map;

import com.vdxp.demon_front.core.Drawable;

public abstract class MapTile implements Drawable {
	private float x;
	private float y;
	private float width;
	private float height;

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}
}

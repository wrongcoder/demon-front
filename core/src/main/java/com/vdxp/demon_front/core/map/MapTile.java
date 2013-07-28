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

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}

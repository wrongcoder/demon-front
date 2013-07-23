package com.vdxp.demon_front.core;

public class Toon {

	private float x = 400;
	private float y = 300;
	private float prevX = 400;
	private float prevY = 400;
	private float dx = 0;
	private float dy = 0;

	public float getX() {
		return x;
	}

	public float getPrevX() {
		return prevX;
	}

	public void setX(final float x) {
		this.prevX = this.x;
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public float getPrevY() {
		return prevY;
	}

	public void setY(final float y) {
		this.prevY = this.y;
		this.y = y;
	}

	public float getDx() {
		return dx;
	}

	public void setDx(final float dx) {
		this.dx = dx;
	}

	public float getDy() {
		return dy;
	}

	public void setDy(final float dy) {
		this.dy = dy;
	}

}

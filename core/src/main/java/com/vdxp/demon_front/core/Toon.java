package com.vdxp.demon_front.core;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.vdxp.demon_front.core.Util.interpolate;

public class Toon {

	private final Animation animation;
	private float stateTime = 0;

	private float x = 400;
	private float y = 300;
	private float prevX = 400;
	private float prevY = 400;
	private float dx = 0;
	private float dy = 0;

	public Toon(final TextureAtlas spritesAtlas) {
		final TextureAtlas.AtlasRegion frame1 = spritesAtlas.findRegion("invader1_1of2");
		final TextureAtlas.AtlasRegion frame2 = spritesAtlas.findRegion("invader1_2of2");
		animation = new Animation(0.25f, frame1, frame2);
		animation.setPlayMode(Animation.LOOP);
	}

	public void draw(final float delta, final float alpha, final SpriteBatch batch) {
		stateTime += delta;
		final TextureRegion frame = animation.getKeyFrame(stateTime);
		batch.draw(frame, interpolate(prevX, x, alpha), interpolate(prevY, y, alpha));
	}

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

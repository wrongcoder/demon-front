package com.vdxp.demon_front.core;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.vdxp.demon_front.core.Util.interpolate;

public class Hero implements Drawable {

	private final Animation animation;
	private float stateTime = 0;

	// x/y: model position, map-relative
	// prevX/prevY: model position during previous physics tick, map-relative
	// drawX/drawY: visible position during previous frame, map-relative
	// dx/dy: current velocity, map-relative

	private float x = 400;
	private float y = 300;
	private float prevX = 400;
	private float prevY = 400;
	private float drawX = 400;
	private float drawY = 300;

	private float dx = 0;
	private float dy = 0;

	public Hero(final TextureAtlas spritesAtlas) {
		final TextureAtlas.AtlasRegion frame1 = spritesAtlas.findRegion("invader1_1of2");
		final TextureAtlas.AtlasRegion frame2 = spritesAtlas.findRegion("invader1_2of2");
		animation = new Animation(0.25f, frame1, frame2);
		animation.setPlayMode(Animation.LOOP);
	}

	@Override
	public void draw(final SpriteBatch batch, final float viewportX, final float viewportY, final float delta, final float alpha) {
		stateTime += delta;
		drawX = interpolate(prevX, x, alpha);
		drawY = interpolate(prevY, y, alpha);

		final TextureRegion frame = animation.getKeyFrame(stateTime);
		batch.draw(frame, drawX - viewportX, drawY - viewportY);
	}

	public float getX() {
		return x;
	}

	public float getPrevX() {
		return prevX;
	}

	public float getDrawX() {
		return drawX;
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

	public float getDrawY() {
		return drawY;
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

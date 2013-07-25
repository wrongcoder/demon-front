package com.vdxp.demon_front.core.units;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.vdxp.demon_front.core.Drawable;
import com.vdxp.demon_front.core.Viewport;

import static com.vdxp.demon_front.core.Util.interpolate;

public abstract class Unit implements Drawable {

	// x/y: model position, map-relative
	// prevX/prevY: model position during previous physics tick, map-relative
	// drawX/drawY: visible position during previous frame, map-relative
	// dx/dy: current velocity, map-relative

	protected Animation animation;
	protected float stateTime = 0;
	protected float drawOffsetX = 16;
	protected float drawOffsetY = 16;

	protected float x = 0;
	protected float y = 0;
	protected float prevX = 0;
	protected float prevY = 0;
	protected float drawX = 0;
	protected float drawY = 0;

	protected float dx = 0;
	protected float dy = 0;

	public Unit(final float x, final float y) {
		this.x = x;
		this.prevX = x;
		this.drawX = x;
		this.y = y;
		this.prevY = y;
		this.drawY = y;
	}

	@Override
	public void draw(final SpriteBatch batch, final Viewport viewport, final float delta, final float alpha) {
		stateTime += delta;
		drawX = interpolate(prevX, x, alpha);
		drawY = interpolate(prevY, y, alpha);

		final TextureRegion frame = animation.getKeyFrame(stateTime);
		batch.draw(frame, drawX - viewport.viewportX - drawOffsetX, drawY - viewport.viewportY - drawOffsetY);
	}

	public float getX() {
		return x;
	}

	public void setX(final float x) {
		this.prevX = this.x;
		this.x = x;
	}

	public float getY() {
		return y;
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

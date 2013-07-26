package com.vdxp.demon_front.core.units;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.vdxp.demon_front.core.Drawable;
import com.vdxp.demon_front.core.Viewport;
import com.vdxp.demon_front.core.map.MapTile;

import java.util.Set;

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

	protected float width = 32;
	protected float height = 32;

	@Override
	public void draw(final SpriteBatch batch, final Viewport viewport, final float delta, final float alpha) {
		stateTime += delta;
		drawX = interpolate(prevX, x, alpha);
		drawY = interpolate(prevY, y, alpha);

		final TextureRegion frame = animation.getKeyFrame(stateTime);
		batch.draw(frame, drawX - viewport.viewportX - drawOffsetX, drawY - viewport.viewportY - drawOffsetY);
	}

	public void setDimensions(final float x, final float y, final float height, final float width) {
		this.x = x;
		this.prevX = x;
		this.drawX = x;
		this.y = y;
		this.prevY = y;
		this.drawY = y;
		this.height = height;
		this.drawOffsetX = height / 2f;
		this.width = width;
		this.drawOffsetY = width / 2f;
	}

	protected boolean tryMove(final float targetX, final float targetY, final Set<Unit> activeCollidables, final Set<MapTile> inactiveCollidables) {
		this.prevX = this.x;
		this.prevY = this.y;

		Rectangle.tmp.set(targetX, targetY, this.width, this.height);

		for (final Unit other : activeCollidables) {
			if (other == this) {
				continue;
			}
			Rectangle.tmp2.set(other.x, other.y, other.width, other.height);
			if (Rectangle.tmp.overlaps(Rectangle.tmp2)) {
				return false;
			}
		}
		for (final MapTile other : inactiveCollidables) {
			Rectangle.tmp2.set(other.getX(), other.getY(), other.getWidth(), other.getHeight());
			if (Rectangle.tmp.overlaps(Rectangle.tmp2)) {
				return false;
			}
		}

		this.x = targetX;
		this.y = targetY;
		return true;
	}

	public float getSpeed() {
		return 60; // pixels per second
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public void physics(final float delta, final Set<Unit> activeCollidables, final Set<MapTile> inactiveCollidables) {
		final float angle = (float) (Math.PI * 2 * Math.random());
		final float deltaX = (float) (getSpeed() * Math.sin(angle)) * delta;
		final float deltaY = (float) (getSpeed() * Math.cos(angle)) * delta;
		tryMove(x + deltaX, y + deltaY, activeCollidables, inactiveCollidables);
	}

}

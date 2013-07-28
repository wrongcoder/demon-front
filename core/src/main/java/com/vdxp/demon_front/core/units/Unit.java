package com.vdxp.demon_front.core.units;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
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

	private Animation animation;
	private boolean animated = true;
	private float stateTime = 0;
	private float drawOffsetX = 16;
	private float drawOffsetY = 16;

	private float x = 0;
	private float y = 0;
	private float prevX = 0;
	private float prevY = 0;
	private float drawX = 0;
	private float drawY = 0;

	private float width = 32;
	private float height = 32;

	public Unit() {
		this.animated = true;
	}

	@Override
	public void draw(final SpriteBatch batch, final Viewport viewport, final float delta, final float alpha) {
		if (animated) {
			stateTime += delta;
		}

		drawX = interpolate(prevX, x, alpha);
		drawY = interpolate(prevY, y, alpha);

		final TextureRegion frame = animation.getKeyFrame(stateTime);
		batch.draw(frame, drawX - viewport.viewportX - drawOffsetX, drawY - viewport.viewportY - drawOffsetY);
	}

	public void setDimensions(final float x, final float y, final float width, final float height) {
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

	public void setAnimation(final Animation animation) {
		setAnimation(animation, false);
	}

	public void setAnimation(final Animation animation, final boolean resetTimer) {
		this.animation = animation;
		if (resetTimer) {
			stateTime = 0;
		}
	}

	public void setAnimated(final boolean animated) {
		this.animated = animated;
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

	public float getDrawX() {
		return drawX;
	}

	public float getDrawY() {
		return drawY;
	}

	public void physics(final float delta, final Set<Unit> activeCollidables, final Set<MapTile> inactiveCollidables) {
		final float angle = (float) (Math.PI * 2 * Math.random());
		final float deltaX = (float) (getSpeed() * Math.sin(angle)) * delta;
		final float deltaY = (float) (getSpeed() * Math.cos(angle)) * delta;
		tryMove(x + deltaX, y + deltaY, activeCollidables, inactiveCollidables);
	}

	public static Animation buildAnimation(final float frameDuration, final TextureAtlas spritesAtlas, final int playType, final String... spriteNames) {
		return buildAnimation(frameDuration, spritesAtlas, playType, false, spriteNames);
	}

	public static Animation buildAnimation(final float frameDuration, final TextureAtlas spritesAtlas, final int playType, final boolean flipX, final String... spriteNames) {
		final Array<TextureRegion> sprites = new Array<TextureRegion>(spriteNames.length);
		for (final String spriteName : spriteNames) {
			final TextureAtlas.AtlasRegion sprite = spritesAtlas.findRegion(spriteName);
			sprite.flip(flipX, false);
			sprites.add(sprite);
		}
		return new Animation(frameDuration, sprites, playType);
	}

}

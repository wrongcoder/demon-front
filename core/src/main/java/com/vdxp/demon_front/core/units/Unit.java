package com.vdxp.demon_front.core.units;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.vdxp.demon_front.core.Drawable;
import com.vdxp.demon_front.core.Viewport;
import com.vdxp.demon_front.core.map.MapTile;

import java.util.Set;

import static com.vdxp.demon_front.core.Util.interpolate;

public abstract class Unit extends Drawable {

	// x/y: model position, map-relative
	// prevX/prevY: model position during previous physics tick, map-relative
	// drawX/drawY: visible position during previous frame, map-relative
	// dx/dy: current velocity, map-relative

	private Animation animation;
	private boolean animated = true;
	private float stateTime = 0;

    private Float angle = null;

	private float x = 0;
	private float y = 0;
	private float prevX = 0;
	private float prevY = 0;
	private float drawX = 0;
	private float drawY = 0;

	private float width = 32;
	private float height = 32;

	private final float maxHp;

	private float hp;
	private boolean alive = true;

	public Unit(final float maxHp) {
		this.animated = true;
		this.maxHp = maxHp;
		this.hp = maxHp;
	}

	@Override
	public void drawSprite(final SpriteBatch batch, final Viewport viewport, final float delta, final float alpha) {
		if (animated) {
			stateTime += delta;
		}

		drawX = interpolate(prevX, x, alpha);
		drawY = interpolate(prevY, y, alpha);

		final TextureRegion frame = animation.getKeyFrame(stateTime);
		batch.draw(frame, drawX - viewport.viewportX - drawOffsetX, drawY - viewport.viewportY - drawOffsetY);
	}

	@Override
	public void drawOverlay(final ShapeRenderer shape, final Viewport viewport, final float delta, final float alpha) {
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

	protected void setAnimation(final Animation animation) {
		setAnimation(animation, false);
	}

	protected void setAnimation(final Animation animation, final boolean resetTimer) {
		this.animation = animation;
		if (resetTimer) {
			stateTime = 0;
		}
	}

	protected void setAnimated(final boolean animated) {
		this.animated = animated;
	}

	protected boolean tryMove(final Float angle, final float delta, final Set<Unit> activeCollidables, final Set<MapTile> inactiveCollidables) {
		this.prevX = this.x;
		this.prevY = this.y;
		this.angle = null;

		if (angle == null) {
			return true;
		}

		final float targetDeltaX = (float) (getSpeed() * Math.cos(angle)) * delta;
		final float targetDeltaY = (float) (getSpeed() * Math.sin(angle)) * delta;
		final float actualX;
		final float actualY;

		// this is going to be slow with a lot of units
		if (!isCollision(activeCollidables, inactiveCollidables, targetDeltaX, targetDeltaY)) {
			actualX = this.x + targetDeltaX;
			actualY = this.y + targetDeltaY;
		} else if (!isCollision(activeCollidables, inactiveCollidables, targetDeltaX, 0)) {
			actualX = this.x + targetDeltaX;
			actualY = this.y;
		} else if (!isCollision(activeCollidables, inactiveCollidables, 0, targetDeltaY)) {
			actualX = this.x;
			actualY = this.y + targetDeltaY;
		} else if (!isCollision(activeCollidables, inactiveCollidables, targetDeltaX/2, targetDeltaY/2)) {
			actualX = this.x + targetDeltaX/2;
			actualY = this.y + targetDeltaY/2;
		} else if (!isCollision(activeCollidables, inactiveCollidables, targetDeltaX/2, 0)) {
			actualX = this.x + targetDeltaX/2;
			actualY = this.y;
		} else if (!isCollision(activeCollidables, inactiveCollidables, 0, targetDeltaY/2)) {
			actualX = this.x;
			actualY = this.y + targetDeltaY/2;
		} else {
			return false;
		}

		// N.B. Rectangle.tmp is not draw-offset
		// FIXME viewport should not contain the map size
		Rectangle.tmp.set(actualX + 2, actualY + 2, this.width - 4, this.height - 4);
		Rectangle.tmp2.set(width * 2, height, Viewport.mapWidth - width * 3, Viewport.mapHeight - height * 2);
		if (!Rectangle.tmp.overlaps(Rectangle.tmp2)) {
			return false;
		}

		this.x = actualX;
		this.y = actualY;
		this.angle = angle;
		return true;
	}

	private boolean isCollision(final Set<Unit> activeCollidables, final Set<MapTile> inactiveCollidables, final float targetDeltaX, final float targetDeltaY) {
		Rectangle.tmp.set(this.x + targetDeltaX + 2, this.y + targetDeltaY + 2, this.width - 4, this.height - 4);

		for (final Unit other : activeCollidables) {
			if (other == this) {
				continue;
			}
			Rectangle.tmp2.set(other.x, other.y, other.width, other.height);
			if (Rectangle.tmp.overlaps(Rectangle.tmp2)) {
				return true;
			}
		}
		for (final MapTile other : inactiveCollidables) {
			Rectangle.tmp2.set(other.getX(), other.getY(), other.getWidth(), other.getHeight());
			if (Rectangle.tmp.overlaps(Rectangle.tmp2)) {
				return true;
			}
		}
		return false;
	}

	/** @return pixels per second */
	public abstract float getSpeed();

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

	public Float getAngle() {
		return angle;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public boolean isAlive() {
		return alive;
	}

	public float getMaxHp() {
		return maxHp;
	}

	public float getHp() {
		return hp;
	}

	public void setHp(final float hp) {
		this.hp = hp;
	}

	public void changeHp(final float deltaHp) {
		this.hp += deltaHp;
		if (this.hp < 0) {
			die();
		}
	}

	public void die() {
		this.alive = false;
	}

	public abstract void physics(final float delta, final Set<Unit> activeCollidables, final Set<MapTile> inactiveCollidables);

	public abstract void combat(final float delta, final Set<Unit> activeCollidables);

	public void receiveHit(final int hp, final Unit source) {
		changeHp(-hp);
	}

	public float getHitPointsFraction() {
		return Math.max(0, hp / maxHp);
	}

	public static Animation buildAnimation(final float frameDuration, final TextureAtlas spritesAtlas, final int playType, final String... spriteNames) {
		return buildAnimation(frameDuration, spritesAtlas, playType, false, spriteNames);
	}

	public static Animation buildAnimation(final float frameDuration, final TextureAtlas spritesAtlas, final int playType, final boolean flipX, final String... spriteNames) {
		final Array<Sprite> sprites = new Array<Sprite>(spriteNames.length);
		for (final String spriteName : spriteNames) {
			final TextureAtlas.AtlasRegion region = spritesAtlas.findRegion(spriteName);
			final Sprite sprite = new Sprite(region);
			sprite.flip(flipX, false);
			sprites.add(sprite);
		}
		return new Animation(frameDuration, sprites, playType);
	}

}

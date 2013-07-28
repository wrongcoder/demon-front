package com.vdxp.demon_front.core.units;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.vdxp.demon_front.core.map.MapTile;

import java.util.Set;

public class HeroUnit extends Unit {

	private boolean movingUp;
	private boolean movingDown;
	private boolean movingLeft;
	private boolean movingRight;

	public HeroUnit(final TextureAtlas spritesAtlas) {
		final TextureAtlas.AtlasRegion frame1 = spritesAtlas.findRegion("invader1_1of2");
		final TextureAtlas.AtlasRegion frame2 = spritesAtlas.findRegion("invader1_2of2");
		animation = new Animation(0.25f, frame1, frame2);
		animation.setPlayMode(Animation.LOOP);

		setDimensions(1000, 1000, frame1.getRegionWidth(), frame1.getRegionHeight());
	}

	@Override
	public void physics(final float delta, final Set<Unit> activeCollidables, final Set<MapTile> inactiveCollidables) {
		final Float angle = computeMovementAngle();
		if (angle != null) {
			final float dx = getSpeed() * (float) Math.cos(angle) * delta;
			final float dy = getSpeed() * (float) Math.sin(angle) * delta;
			tryMove(getX() + dx, getY() + dy, activeCollidables, inactiveCollidables);
		} else {
			tryMove(getX(), getY(), activeCollidables, inactiveCollidables);
		}
	}

	@Override
	public float getSpeed() {
		return 80; // pixels per second
	}

	public float getDrawX() {
		return drawX;
	}

	public float getDrawY() {
		return drawY;
	}

	public Float computeMovementAngle() {
		final float pi = (float) Math.PI;

		if (movingUp && !movingDown) {
			if (movingLeft && !movingRight) {
				return 0.75f * pi;
			}
			if (movingRight && !movingLeft) {
				return 0.25f * pi;
			}
			return 0.5f * pi;
		}
		if (movingDown && !movingUp) {
			if (movingLeft && !movingRight) {
				return 1.25f * pi;
			}
			if (movingRight && !movingLeft) {
				return 1.75f * pi;
			}
			return 1.5f * pi;
		}
		if (movingLeft && !movingRight) {
			return pi;
		}
		if (movingRight && !movingLeft) {
			return 0f;
		}
		return null;
	}

	public boolean isMovingUp() {
		return movingUp;
	}

	public void setMovingUp(final boolean movingUp) {
		this.movingUp = movingUp;
	}

	public boolean isMovingDown() {
		return movingDown;
	}

	public void setMovingDown(final boolean movingDown) {
		this.movingDown = movingDown;
	}

	public boolean isMovingLeft() {
		return movingLeft;
	}

	public void setMovingLeft(final boolean movingLeft) {
		this.movingLeft = movingLeft;
	}

	public boolean isMovingRight() {
		return movingRight;
	}

	public void setMovingRight(final boolean movingRight) {
		this.movingRight = movingRight;
	}

}

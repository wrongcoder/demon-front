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

	private Animation stoppedAnimation;

	private final Animation downStoppedAnimation;
	private final Animation downMovingAnimation;
	private final Animation upStoppedAnimation;
	private final Animation upMovingAnimation;
	private final Animation rightStoppedAnimation;
	private final Animation rightMovingAnimation;
	private final Animation leftStoppedAnimation;
	private final Animation leftMovingAnimation;

	public HeroUnit(final TextureAtlas spritesAtlas) {
		final float animationSpeed = 0.25f;
		downStoppedAnimation = buildAnimation(animationSpeed, spritesAtlas, Animation.NORMAL, "yourside_richtaur_32x32_4_2of10");
		upStoppedAnimation = buildAnimation(animationSpeed, spritesAtlas, Animation.NORMAL, "yourside_richtaur_32x32_4_1of10");
		rightStoppedAnimation = buildAnimation(animationSpeed, spritesAtlas, Animation.NORMAL, "yourside_richtaur_32x32_4_5of10");
		leftStoppedAnimation = buildAnimation(animationSpeed, spritesAtlas, Animation.NORMAL, true, "yourside_richtaur_32x32_4_5aof10");
		downMovingAnimation = buildAnimation(animationSpeed, spritesAtlas, Animation.LOOP, "yourside_richtaur_32x32_4_3of10", "yourside_richtaur_32x32_4_4of10");
		upMovingAnimation = buildAnimation(animationSpeed, spritesAtlas, Animation.LOOP, "yourside_richtaur_32x32_4_9of10", "yourside_richtaur_32x32_4_10of10");
		rightMovingAnimation = buildAnimation(animationSpeed, spritesAtlas, Animation.LOOP, "yourside_richtaur_32x32_4_6of10", "yourside_richtaur_32x32_4_7of10", "yourside_richtaur_32x32_4_8of10", "yourside_richtaur_32x32_4_7of10");
		leftMovingAnimation = buildAnimation(animationSpeed, spritesAtlas, Animation.LOOP, true, "yourside_richtaur_32x32_4_6aof10", "yourside_richtaur_32x32_4_7aof10", "yourside_richtaur_32x32_4_8aof10", "yourside_richtaur_32x32_4_7bof10");

		setDimensions(1000, 1000, 32, 32);
		setAnimation(downStoppedAnimation, true);
		stoppedAnimation = downStoppedAnimation;
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

		setNextAnimation(angle);
	}

	public void setNextAnimation(final Float angle) {
		if (angle == null) {
			setAnimation(stoppedAnimation);
		} else if (angle < (3f / 8f) * Math.PI) {
			setAnimation(rightMovingAnimation);
			stoppedAnimation = rightStoppedAnimation;
		} else if (angle < (5f / 8f) * Math.PI) {
			setAnimation(upMovingAnimation);
			stoppedAnimation = upStoppedAnimation;
		} else if (angle < (11f / 8f) * Math.PI) {
			setAnimation(leftMovingAnimation);
			stoppedAnimation = leftStoppedAnimation;
		} else if (angle < (13f / 8f) * Math.PI) {
			setAnimation(downMovingAnimation);
			stoppedAnimation = downStoppedAnimation;
		} else {
			setAnimation(rightMovingAnimation);
			stoppedAnimation = rightStoppedAnimation;
		}
	}

	@Override
	public float getSpeed() {
		return 80; // pixels per second
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

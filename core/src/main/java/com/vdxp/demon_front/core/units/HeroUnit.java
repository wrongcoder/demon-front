package com.vdxp.demon_front.core.units;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.vdxp.demon_front.core.map.MapTile;

import java.util.Set;

public class HeroUnit extends YourSideUnit {

	private boolean movingUp;
	private boolean movingDown;
	private boolean movingLeft;
	private boolean movingRight;

	private static final float maxHp = 270;
	private float hp = 270;

	public HeroUnit(final TextureAtlas spritesAtlas) {
		super(spritesAtlas, 4, 0.25f, 16, 14);
	}

	@Override
	public void physics(final float delta, final Set<Unit> activeCollidables, final Set<MapTile> inactiveCollidables) {
		final Float angle = computeMovementAngle();
		tryMove(angle, delta, activeCollidables, inactiveCollidables);
		setNextAnimation(angle);
	}

	@Override
	public void combat(final float delta, final Set<Unit> activeCollidables) {
		// regeneration
		hp = Math.min(hp + 3 * delta, maxHp);

		if (Math.random() < delta) {
			Rectangle.tmp.set(getX() - 8, getY() - 8, getWidth() + 16, getHeight() + 16);
			for (final Unit unit : activeCollidables) {
				if (unit instanceof EnemyUnit) {
					Rectangle.tmp2.set(unit.getX(), unit.getY(), unit.getWidth(), unit.getHeight());
					if (Rectangle.tmp.overlaps(Rectangle.tmp2)) {
						unit.receiveHit(36, this);
					}
				}
			}
		}
	}

	@Override
	public void receiveHit(final int hp, final Unit source) {
		this.hp -= hp;
		if (this.hp < 0) {
			die();
		}
	}

	@Override
	public float getHitPointsFraction() {
		return Math.max(0, hp / maxHp);
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

	public void setMovingUp(final boolean movingUp) {
		this.movingUp = movingUp;
	}

	public void setMovingDown(final boolean movingDown) {
		this.movingDown = movingDown;
	}

	public void setMovingLeft(final boolean movingLeft) {
		this.movingLeft = movingLeft;
	}

	public void setMovingRight(final boolean movingRight) {
		this.movingRight = movingRight;
	}

}

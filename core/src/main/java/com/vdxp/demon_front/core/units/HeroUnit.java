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

	private boolean shoutingUp;
	private boolean shoutingDown;
	private boolean shoutingLeft;
	private boolean shoutingRight;

	public HeroUnit(final TextureAtlas spritesAtlas) {
		super(270, spritesAtlas, 4, 0.25f, 16, 14);
	}

	@Override
	public void physics(final float delta, final Set<Unit> activeCollidables, final Set<MapTile> inactiveCollidables) {
		final Float angle = computeMovementAngle();
		tryMove(angle, delta, activeCollidables, inactiveCollidables);
		setNextAnimation(angle);

		final ShoutCommand shoutCommand = computeShoutCommand();
		if (shoutCommand != null) {
			for (final Unit unit : activeCollidables) {
				if (unit != this && unit instanceof YourSideUnit) {
					final float unitDX = unit.getX() - getX();
					final float unitDY = unit.getY() - getY();
					final float distance = (float) Math.sqrt(unitDX*unitDX + unitDY*unitDY);
					if (distance < 192) {
						((YourSideUnit) unit).setShoutCommand(shoutCommand);
					}
				}
			}
		}
	}

	@Override
	public void combat(final float delta, final Set<Unit> activeCollidables) {
		// regeneration
		setHp(Math.min(getMaxHp(), getHp() + 3 * delta));

		if (Math.random() < delta) {
			Rectangle.tmp.set(getX() - 8, getY() - 8, getWidth() + 16, getHeight() + 16);
			for (final Unit unit : activeCollidables) {
				if (unit instanceof EnemyUnit || unit instanceof DemonGate) {
					Rectangle.tmp2.set(unit.getX(), unit.getY(), unit.getWidth(), unit.getHeight());
					if (Rectangle.tmp.overlaps(Rectangle.tmp2)) {
						unit.receiveHit(36, this);
					}
				}
			}
		}
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

	public ShoutCommand computeShoutCommand() {
		if (shoutingLeft) {
			return ShoutCommand.Left;
		}
		if (shoutingRight) {
			return ShoutCommand.Right;
		}
		if (shoutingUp) {
			return ShoutCommand.Up;
		}
		if (shoutingDown) {
			return ShoutCommand.Down;
		}
		return null;
	}

	public boolean isShouting() {
		return computeShoutCommand() != null;
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

	public void setShoutingUp(final boolean shoutingUp) {
		this.shoutingUp = shoutingUp;
	}

	public void setShoutingDown(final boolean shoutingDown) {
		this.shoutingDown = shoutingDown;
	}

	public void setShoutingLeft(final boolean shoutingLeft) {
		this.shoutingLeft = shoutingLeft;
	}

	public void setShoutingRight(final boolean shoutingRight) {
		this.shoutingRight = shoutingRight;
	}

	public enum ShoutCommand {
		Left(Math.PI), Right(0), Up(0.5f * Math.PI), Down(1.5f * Math.PI);

		public final float angle;

		ShoutCommand(final float angle) {
			this.angle = angle;
		}
		ShoutCommand(final double angle) {
			this.angle = (float) angle;
		}
	}

}

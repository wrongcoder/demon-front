package com.vdxp.demon_front.core.units;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.vdxp.demon_front.core.Game;
import com.vdxp.demon_front.core.Viewport;

import java.util.Set;

public abstract class FriendlyUnit extends Unit {

	private final Sprite clawSlash;
	private float clawSlashTimer = 0;

	public FriendlyUnit(final float maxHp, final TextureAtlas spritesAtlas) {
		super(maxHp);
		clawSlash = new Sprite(spritesAtlas.findRegion("claw-slash"));
	}

	@Override
	public void drawSprite(final SpriteBatch batch, final Viewport viewport, final float delta, final float alpha) {
		super.drawSprite(batch, viewport, delta, alpha);
		if (clawSlashTimer > delta) {
			clawSlashTimer -= delta;
			batch.draw(clawSlash, getDrawX() - viewport.viewportX - drawOffsetX, getDrawY() - viewport.viewportY - drawOffsetY);
            Game.instance().getSoundMan().playEnemyAttack();
		}
	}

	@Override
	public void drawOverlay(final ShapeRenderer shape, final Viewport viewport, final float delta, final float alpha) {
		final int barWidth = 32;
		final int barHeight = 6;

		final float barX = getDrawX() - viewport.viewportX - barWidth / 2;
		final float barY = getDrawY() + getHeight() - viewport.viewportY - barHeight;

		shape.begin(ShapeRenderer.ShapeType.Filled);

		shape.setColor(0, 0.65f, 0, 1);
		shape.rect(barX, barY, barWidth, barHeight);

		shape.setColor(0, 0.33f, 0, 1);
		shape.rect(barX + 1, barY + 1, barWidth - 2, barHeight - 2);

		shape.setColor(0, 1, 0, 1);
		shape.rect(barX + 1, barY + 1, (barWidth - 2) * getHitPointsFraction(), barHeight - 2);

		shape.end();
	}

	@Override
	public void combat(final float delta, final Set<Unit> activeCollidables) {
		if (Math.random() < delta) {
			Rectangle.tmp.set(getX() - 8, getY() - 8, getWidth() + 16, getHeight() + 16);
			for (final Unit unit : activeCollidables) {
				if (unit instanceof EnemyUnit || unit instanceof DemonGate) {
					Rectangle.tmp2.set(unit.getX(), unit.getY(), unit.getWidth(), unit.getHeight());
					if (Rectangle.tmp.overlaps(Rectangle.tmp2)) {
						unit.receiveHit(10, this);
					}
				}
			}
		}
	}

	@Override
	public float getSpeed() {
		return 40;
	}

	@Override
	public void receiveHit(final int hp, final Unit source) {
		super.receiveHit(hp, source);
		clawSlashTimer += 0.1f;
	}
}

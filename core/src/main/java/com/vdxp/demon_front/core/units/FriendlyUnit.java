package com.vdxp.demon_front.core.units;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.vdxp.demon_front.core.Viewport;

public abstract class FriendlyUnit extends Unit {

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
	public float getSpeed() {
		return 80;
	}

}

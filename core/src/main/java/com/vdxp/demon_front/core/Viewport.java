package com.vdxp.demon_front.core;

import com.vdxp.demon_front.core.units.HeroUnit;

public class Viewport {
	public float viewportX = 0;
	public float viewportY = 0;

	// placeholder values
	public static final float mapWidth = 2560;
	public static final float mapHeight = 1920;

	public static final float viewportWidth = Game.WIDTH;
	public static final float viewportHeight = Game.HEIGHT;

	public static final float viewportSlopX = viewportWidth / 6f;
	public static final float viewportSlopY = viewportHeight / 6f;

	public static final float maxViewportX = mapHeight - viewportHeight;
	public static final float maxViewportY = mapWidth - viewportWidth;

	public Viewport(final HeroUnit hero) {
		viewportX = hero.getX() - viewportHeight / 2f;
		viewportY = hero.getY() - viewportWidth / 2f;
	}

	public void setPosition(final HeroUnit hero) {
		final float viewportRelativeHeroX = hero.getDrawX() - (viewportX + viewportWidth / 2f);
		final float viewportRelativeHeroY = hero.getDrawY() - (viewportY + viewportHeight / 2f);

		if (Math.abs(viewportRelativeHeroX) < viewportSlopX && Math.abs(viewportRelativeHeroY) < viewportSlopY) {
			// Hero is still within the slop area
			return;
		}

		if (viewportRelativeHeroX > viewportSlopX) {
			viewportX += (viewportRelativeHeroX - viewportSlopX);
		}
		if (viewportRelativeHeroX < -viewportSlopX) {
			viewportX += (viewportRelativeHeroX + viewportSlopX);
		}

		if (viewportRelativeHeroY > viewportSlopY) {
			viewportY += (viewportRelativeHeroY - viewportSlopY);
		}
		if (viewportRelativeHeroY < -viewportSlopY) {
			viewportY += (viewportRelativeHeroY + viewportSlopY);
		}

		if (viewportX < 0) {
			viewportX = 0;
		}
		if (viewportX > maxViewportX) {
			viewportX = maxViewportX;
		}

		if (viewportY < 0) {
			viewportY = 0;
		}
		if (viewportY > maxViewportY) {
			viewportY = maxViewportY;
		}
	}
}

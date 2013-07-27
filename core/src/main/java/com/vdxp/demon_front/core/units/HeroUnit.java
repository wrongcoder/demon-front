package com.vdxp.demon_front.core.units;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.vdxp.demon_front.core.map.MapTile;

import java.util.Set;

public class HeroUnit extends Unit {

	protected float dx = 0;
	protected float dy = 0;

	public HeroUnit(final TextureAtlas spritesAtlas) {
		final TextureAtlas.AtlasRegion frame1 = spritesAtlas.findRegion("invader1_1of2");
		final TextureAtlas.AtlasRegion frame2 = spritesAtlas.findRegion("invader1_2of2");
		animation = new Animation(0.25f, frame1, frame2);
		animation.setPlayMode(Animation.LOOP);

		setDimensions(1000, 1000, frame1.getRegionWidth(), frame1.getRegionHeight());
	}

	@Override
	public void physics(final float delta, final Set<Unit> activeCollidables, final Set<MapTile> inactiveCollidables) {
		tryMove(x + dx * delta, y + dy * delta, activeCollidables, inactiveCollidables);
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

	public float getDx() {
		return dx;
	}

	public void setDx(final float dx) {
		this.dx = dx;
	}

	public float getDy() {
		return dy;
	}

	public void setDy(final float dy) {
		this.dy = dy;
	}

}

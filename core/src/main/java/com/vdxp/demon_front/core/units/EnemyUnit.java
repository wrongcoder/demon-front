package com.vdxp.demon_front.core.units;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.vdxp.demon_front.core.map.Map;
import com.vdxp.demon_front.core.map.MapTile;

import java.util.Set;

public class EnemyUnit extends Unit {

	private final Animation defaultAnimation;

	public EnemyUnit(final TextureAtlas spritesAtlas, final int xTile, final int yTile) {
		final float x = Map.getGameXinPixel(xTile);
		final float y = Map.getGameYinPixel(yTile);
		final TextureAtlas.AtlasRegion frame1 = spritesAtlas.findRegion("invader2_1of2");
		final TextureAtlas.AtlasRegion frame2 = spritesAtlas.findRegion("invader2_2of2");
		defaultAnimation = new Animation(0.8f, frame1, frame2);
		defaultAnimation.setPlayMode(Animation.LOOP);

		setDimensions(x, y, frame1.getRegionWidth(), frame1.getRegionHeight());
		setAnimation(defaultAnimation, true);
	}

	@Override
	public float getSpeed() {
		return 50;
	}

	@Override
	public void physics(final float delta, final Set<Unit> activeCollidables, final Set<MapTile> inactiveCollidables) {
		final float angle = (float) (Math.PI * 2 * Math.random());
		tryMove(angle, delta, activeCollidables, inactiveCollidables);
	}

}

package com.vdxp.demon_front.core.units;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.vdxp.demon_front.core.map.MapTile;

import java.util.Set;

public class Enemy extends Unit {

	public Enemy(final TextureAtlas spritesAtlas, final float x, final float y) {
		final TextureAtlas.AtlasRegion frame1 = spritesAtlas.findRegion("invader2_1of2");
		final TextureAtlas.AtlasRegion frame2 = spritesAtlas.findRegion("invader2_2of2");
		animation = new Animation(0.8f, frame1, frame2);
		animation.setPlayMode(Animation.LOOP);

		setDimensions(x, y, frame1.getRegionWidth(), frame1.getRegionHeight());
	}

    public void physics(final float delta, final Set<Unit> activeCollidables, final Set<MapTile> inactiveCollidables) {
        final float angle = (float) (Math.PI * 2 * Math.random());
        final float deltaX = (float) (getSpeed() * Math.sin(angle)) * delta;
        final float deltaY = (float) (getSpeed() * Math.cos(angle)) * delta;
        tryMove(x + deltaX, y + deltaY, activeCollidables, inactiveCollidables);
    }

}

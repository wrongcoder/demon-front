package com.vdxp.demon_front.core.units;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.vdxp.demon_front.core.Viewport;
import com.vdxp.demon_front.core.map.MapTile;

import java.util.Set;

public class DemonGate extends Unit {

    TextureAtlas.AtlasRegion demonGateSprite;

    public DemonGate(final TextureAtlas spritesAtlas,
                    int mapX,
                    int mapY) {
        demonGateSprite = spritesAtlas.findRegion("demongate_active1");

        setDimensions(mapX * getWidth(),
                mapY * getHeight(),
                demonGateSprite.getRegionWidth(),
                demonGateSprite.getRegionHeight());

    }

    @Override
    public void drawSprite(final SpriteBatch batch,
                     final Viewport viewport,
                     final float delta,
                     final float alpha) {

        batch.draw(demonGateSprite,
                this.getX()  - viewport.viewportX /*- drawOffsetX*/,
                this.getY() - viewport.viewportY /*- drawOffsetY*/);
    }

	@Override
    public void physics(final float delta, final Set<Unit> activeCollidables, final Set<MapTile> inactiveCollidables) {
        // no movement, no collision
    }

	@Override
	public float getHitPointsFraction() {
		return 0.75f;
	}

	@Override
	public float getSpeed() {
		return 0;
	}

}

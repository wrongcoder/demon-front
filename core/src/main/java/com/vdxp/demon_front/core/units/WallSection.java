package com.vdxp.demon_front.core.units;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.vdxp.demon_front.core.Viewport;
import com.vdxp.demon_front.core.map.MapTile;

import java.util.Set;

public class WallSection extends Unit {

    TextureAtlas.AtlasRegion wallSectionSprite;

    public WallSection(final TextureAtlas spritesAtlas,
                       int mapX,
                       int mapY) {
	    super(100);

        wallSectionSprite = spritesAtlas.findRegion("walltiles_30x16_fromInvader_1of5");


        setDimensions(mapX * getWidth(),
                mapY * getHeight(),
                wallSectionSprite.getRegionWidth(),
                wallSectionSprite.getRegionHeight());
    }

	@Override
    public void drawSprite(final SpriteBatch batch,
                     final Viewport viewport,
                     final float delta,
                     final float alpha) {

        batch.draw(wallSectionSprite,
                this.getX()  - viewport.viewportX - drawOffsetX,
                this.getY() - viewport.viewportY - drawOffsetY);
    }

	@Override
	public float getSpeed() {
		return 0;
	}

	@Override
	public void physics(final float delta, final Set<Unit> activeCollidables, final Set<MapTile> inactiveCollidables) {
        // other things interacts with the wall but the wall does not move
    }

	@Override
	public void combat(final float delta, final Set<Unit> activeCollidables) {
		// walls don't attack
	}

}

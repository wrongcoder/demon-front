package com.vdxp.demon_front.core.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.vdxp.demon_front.core.Viewport;

public class GrassTile extends MapTile {

    TextureAtlas.AtlasRegion grass;

    public GrassTile(final TextureAtlas spritesAtlas,
                      int mapX,
                      int mapY) {
        grass = spritesAtlas.findRegion("grass1");

        width = grass.getRegionWidth();
        height = grass.getRegionHeight();

        x = mapX * width;
        y = mapY * height;
    }

    public void drawSprite(final SpriteBatch batch,
                     final Viewport viewport,
                     final float delta,
                     final float alpha) {

        setSpriteToDraw(grass);
        super.drawSprite(batch, viewport, delta, alpha);
    }

}

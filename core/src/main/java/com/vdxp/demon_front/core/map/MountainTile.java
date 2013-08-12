package com.vdxp.demon_front.core.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.vdxp.demon_front.core.Viewport;

public class MountainTile extends MapTile {

    TextureAtlas.AtlasRegion mountain;

    public MountainTile(final TextureAtlas spritesAtlas,
                        int mapX,
                        int mapY) {
        mountain = spritesAtlas.findRegion("mountain1_32x32");

        width = mountain.getRegionWidth();
        height = mountain.getRegionHeight();

        x = mapX * width;
        y = mapY * height;
    }

    public void drawSprite(final SpriteBatch batch,
                     final Viewport viewport,
                     final float delta,
                     final float alpha) {

        setSpriteToDraw(mountain);
        super.drawSprite(batch, viewport, delta, alpha);
    }

}

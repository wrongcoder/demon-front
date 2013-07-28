package com.vdxp.demon_front.core.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.vdxp.demon_front.core.Viewport;

public class GrassWLeaveTile extends MapTile {

    TextureAtlas.AtlasRegion grass_w_leave;

    public GrassWLeaveTile(final TextureAtlas spritesAtlas,
                           int mapX,
                           int mapY) {
        grass_w_leave = spritesAtlas.findRegion("grass2");

        setWidth(grass_w_leave.getRegionWidth());
        setHeight(grass_w_leave.getRegionHeight());

        setX(mapX * getWidth());
        setY(mapY * getHeight());
    }

    public void drawSprite(final SpriteBatch batch,
                     final Viewport viewport,
                     final float delta,
                     final float alpha) {

        setSpriteToDraw(grass_w_leave);
        super.drawSprite(batch, viewport, delta, alpha);
    }

}

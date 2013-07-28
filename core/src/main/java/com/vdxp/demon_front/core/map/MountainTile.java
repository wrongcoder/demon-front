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

        setWidth(mountain.getRegionWidth());
        setHeight(mountain.getRegionHeight());

        setX(mapX * getWidth());
        setY(mapY * getHeight());
    }

    public void drawSprite(final SpriteBatch batch,
                     final Viewport viewport,
                     final float delta,
                     final float alpha) {

        batch.draw(mountain,
                this.getX()  - viewport.viewportX,
                this.getY() - viewport.viewportY);

        /*


         */
    }

}

package com.vdxp.demon_front.core.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.vdxp.demon_front.core.Viewport;

public class TreeTile extends MapTile {

    TextureAtlas.AtlasRegion treeSprite;

    public TreeTile(final TextureAtlas spritesAtlas,
                    int mapX,
                    int mapY) {
        treeSprite = spritesAtlas.findRegion("tree1");

        setWidth(treeSprite.getRegionWidth());
        setHeight(treeSprite.getRegionHeight());

        setX(mapX * getWidth());
        setY(mapY * getHeight());
    }

    public void draw(final SpriteBatch batch,
                     final Viewport viewport,
                     final float delta,
                     final float alpha) {

        setSpriteToDraw(treeSprite);
        super.draw(batch, viewport, delta, alpha);
    }

}

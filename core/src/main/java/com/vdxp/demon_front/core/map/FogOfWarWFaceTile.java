package com.vdxp.demon_front.core.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.vdxp.demon_front.core.Game;
import com.vdxp.demon_front.core.SpriteTestScreen;
import com.vdxp.demon_front.core.Viewport;
import com.vdxp.demon_front.core.units.HeroUnit;

public class FogOfWarWFaceTile extends MapTile {

    TextureAtlas.AtlasRegion fogOfWarWFace;


    public FogOfWarWFaceTile(final TextureAtlas spritesAtlas,
                             int mapX,
                             int mapY) {
        fogOfWarWFace = spritesAtlas.findRegion((Math.random() < 0.9f)
                    ? "fogOfWar2" : "fogOfWar2b");

        fogOfWarWFace = spritesAtlas.findRegion("fogOfWar2b");

        setWidth(fogOfWarWFace.getRegionWidth());
        setHeight(fogOfWarWFace.getRegionHeight());

        setX(mapX * getWidth());
        setY(mapY * getHeight());
    }

    public void drawSprite(final SpriteBatch batch,
                     final Viewport viewport,
                     final float delta,
                     final float alpha) {

        HeroUnit hero = ((SpriteTestScreen) Game.instance().getScreen()).getHero();

        int tileDist = this.getTileDistFrom(hero.getX(), hero.getY());

        if (tileDist > 8) {
            setSpriteToDraw(fogOfWarWFace);

            if (tileDist < 12) {
                batch.setColor(new Color(1f,1f,1f,0.5f));
            } else if (tileDist < 14) {
                batch.setColor(new Color(1f,1f,1f,0.75f));
            } else if (tileDist < 16) {
                batch.setColor(new Color(1f,1f,1f,0.95f));
            }

            super.drawSprite(batch, viewport, delta, alpha);

            batch.setColor(new Color(1f,1f,1f,1f));
        }
    }

}

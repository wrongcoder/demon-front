package com.vdxp.demon_front.core.map;

import com.badlogic.gdx.Screen;
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

        width = fogOfWarWFace.getRegionWidth();
        height = fogOfWarWFace.getRegionHeight();

        x = mapX * width;
        y = mapY * height;
    }

    public void drawSprite(final SpriteBatch batch,
                     final Viewport viewport,
                     final float delta,
                     final float alpha) {

	    final Screen screen = Game.instance().getScreen();
	    if (screen.getClass() != SpriteTestScreen.class) {
		    return;
	    }
	    HeroUnit hero = ((SpriteTestScreen) screen).getHero();

        int tileDist = this.getTileDistFrom(hero.getX(), hero.getY());

        if (tileDist > 4) {
            setSpriteToDraw(fogOfWarWFace);

            if (tileDist < 6) {
                batch.setColor(new Color(1f,1f,1f,0.25f));
            } else if (tileDist < 8) {
                batch.setColor(new Color(1f,1f,1f,0.5f));
            } else if (tileDist < 10) {
                batch.setColor(new Color(1f,1f,1f,0.75f));
            } else if (tileDist < 12) {
                batch.setColor(new Color(1f,1f,1f,0.95f));
            }

            super.drawSprite(batch, viewport, delta, alpha);

            batch.setColor(new Color(1f,1f,1f,1f));
        }
    }

}

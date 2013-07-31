package com.vdxp.demon_front.core.map;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.vdxp.demon_front.core.Game;
import com.vdxp.demon_front.core.SpriteTestScreen;
import com.vdxp.demon_front.core.Viewport;
import com.vdxp.demon_front.core.units.HeroUnit;

public class FogOfWarTile extends MapTile {

    TextureAtlas.AtlasRegion fogOfWar1;

    public FogOfWarTile(final TextureAtlas spritesAtlas,
                        int mapX,
                        int mapY) {
        fogOfWar1 = spritesAtlas.findRegion("fogOfWar1");

        setWidth(fogOfWar1.getRegionWidth());
        setHeight(fogOfWar1.getRegionHeight());

        setX(mapX * getWidth());
        setY(mapY * getHeight());
    }

    public void drawSprite(final SpriteBatch batch,
                     final Viewport viewport,
                     final float delta,
                     final float alpha) {

        setSpriteToDraw(fogOfWar1);

	    final Screen screen = Game.instance().getScreen();
	    if (screen.getClass() != SpriteTestScreen.class) {
		    return;
	    }
	    HeroUnit hero = ((SpriteTestScreen) screen).getHero();

        int tileDist = this.getTileDistFrom(hero.getX(), hero.getY());

        if (tileDist > 4) {

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

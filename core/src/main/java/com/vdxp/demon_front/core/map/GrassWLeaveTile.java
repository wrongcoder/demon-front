package com.vdxp.demon_front.core.map;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.vdxp.demon_front.core.Game;
import com.vdxp.demon_front.core.SpriteTestScreen;
import com.vdxp.demon_front.core.Viewport;
import com.vdxp.demon_front.core.units.HeroUnit;

public class GrassWLeaveTile extends MapTile {

    TextureAtlas.AtlasRegion grass_w_leave;

    private Screen screen = null;

    public GrassWLeaveTile(final TextureAtlas spritesAtlas,
                           int mapX,
                           int mapY) {
        grass_w_leave = spritesAtlas.findRegion("grass2");

        width = grass_w_leave.getRegionWidth();
        height = grass_w_leave.getRegionHeight();

        x = mapX * width;
        y = mapY * height;

        screen = Game.instance().getScreen();
    }

    public void drawSprite(final SpriteBatch batch,
                     final Viewport viewport,
                     final float delta,
                     final float alpha) {

        final HeroUnit hero = ((SpriteTestScreen) screen).hero;

        int tileDist = this.getTileDistFrom(hero.getX(), hero.getY());

        if (tileDist < 13) {
            setSpriteToDraw(grass_w_leave);
            super.drawSprite(batch, viewport, delta, alpha);
        }
    }

}

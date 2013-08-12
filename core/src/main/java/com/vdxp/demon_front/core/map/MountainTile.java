package com.vdxp.demon_front.core.map;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.vdxp.demon_front.core.Game;
import com.vdxp.demon_front.core.SpriteTestScreen;
import com.vdxp.demon_front.core.Viewport;
import com.vdxp.demon_front.core.units.HeroUnit;

public class MountainTile extends MapTile {

    TextureAtlas.AtlasRegion mountain;

    private Screen screen = null;

    public MountainTile(final TextureAtlas spritesAtlas,
                        int mapX,
                        int mapY) {
        mountain = spritesAtlas.findRegion("mountain1_32x32");

        width = mountain.getRegionWidth();
        height = mountain.getRegionHeight();

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
            setSpriteToDraw(mountain);
            super.drawSprite(batch, viewport, delta, alpha);
        }
    }

}

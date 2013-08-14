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

    protected TextureAtlas.AtlasRegion fogOfWar1;
    private SpriteTestScreen screen = null;

    public FogOfWarTile(final TextureAtlas spritesAtlas,
                        int mapX,
                        int mapY) {
        fogOfWar1 = spritesAtlas.findRegion("fogOfWar1");

        width = fogOfWar1.getRegionWidth();
        height = fogOfWar1.getRegionHeight();

        x = mapX * width;
        y = mapY * height;

        screen = (SpriteTestScreen) Game.instance().getScreen();
    }

    public void drawSprite(final SpriteBatch batch,
                     final Viewport viewport,
                     final float delta,
                     final float alpha) {

        spriteToDraw = fogOfWar1;

        final HeroUnit hero = screen.hero;

        final float tileDist = this.getTileDistFrom(hero.getX(), hero.getY());

        if (tileDist < 29) {
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

                if (tileDist < 12) {
                    batch.setColor(new Color(1f,1f,1f,1f));
                }
            }
        }
    }

}

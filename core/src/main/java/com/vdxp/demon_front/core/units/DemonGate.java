package com.vdxp.demon_front.core.units;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.vdxp.demon_front.core.Viewport;
import com.vdxp.demon_front.core.map.Map;
import com.vdxp.demon_front.core.map.MapTile;

import java.util.Set;

public class DemonGate extends Unit {

    TextureAtlas.AtlasRegion demonGateActive1;
    TextureAtlas.AtlasRegion demonGateActive2;
    private final Animation defaultAnimation;


    public DemonGate(final TextureAtlas spritesAtlas,
                     final int xTile,
                     final int yTile) {

        final float xPixel = Map.getGameXinPixel(xTile);
        final float yPixel = Map.getGameYinPixel(yTile);
        final TextureAtlas.AtlasRegion demonGateActive1 = spritesAtlas.findRegion("demongate_active1");
        final TextureAtlas.AtlasRegion demonGateActive2 = spritesAtlas.findRegion("demongate_active2");
        defaultAnimation = new Animation(0.1f, demonGateActive1, demonGateActive2);
        defaultAnimation.setPlayMode(Animation.LOOP);

        setDimensions(xPixel, yPixel, demonGateActive2.getRegionWidth(), demonGateActive2.getRegionHeight());
        setAnimation(defaultAnimation, true);
    }

    @Override
    public void physics(final float delta, final Set<Unit> activeCollidables, final Set<MapTile> inactiveCollidables) {
        // no movement, no collision
    }

    @Override
    public void combat(final float delta, final Set<Unit> activeCollidables) {
        // does not attack
    }

    @Override
    public float getHitPointsFraction() {
        return 0.75f;
    }

    @Override
    public float getSpeed() {
        return 0;
    }

}

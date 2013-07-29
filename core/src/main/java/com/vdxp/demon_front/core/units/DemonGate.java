package com.vdxp.demon_front.core.units;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.vdxp.demon_front.core.Viewport;
import com.vdxp.demon_front.core.map.Map;
import com.vdxp.demon_front.core.map.MapTile;

import java.util.Set;

public class DemonGate extends Unit {

    TextureAtlas.AtlasRegion demonGateActive1;
    TextureAtlas.AtlasRegion demonGateActive2;
    TextureAtlas.AtlasRegion demonGateSealing1;
    TextureAtlas.AtlasRegion demonGateSealing2;
    TextureAtlas.AtlasRegion demonGateSealing3;
    private final Animation defaultAnimation;

    private final Animation dyingAnimation;


    public DemonGate(final TextureAtlas spritesAtlas,
                     final int xTile,
                     final int yTile) {

	    super(1000);

        final float xPixel = Map.getGameXinPixel(xTile);
        final float yPixel = Map.getGameYinPixel(yTile);

        demonGateActive1 = spritesAtlas.findRegion("demongate_active1");
        demonGateActive2 = spritesAtlas.findRegion("demongate_active2");
        defaultAnimation = new Animation(0.1f, demonGateActive1, demonGateActive2);
        defaultAnimation.setPlayMode(Animation.LOOP);

        demonGateSealing1 = spritesAtlas.findRegion("demongate_sealing1");
        demonGateSealing2 = spritesAtlas.findRegion("demongate_sealing2");
        demonGateSealing3 = spritesAtlas.findRegion("demongate_sealing3");
        dyingAnimation = new Animation(0.1f, demonGateSealing1, demonGateSealing2, demonGateSealing3);
        dyingAnimation.setPlayMode(Animation.LOOP);

        setDimensions(xPixel, yPixel, demonGateActive2.getRegionWidth(), demonGateActive2.getRegionHeight());
        setAnimation(defaultAnimation, true);

        // hack
        drawOffsetX = 0; drawOffsetY = 0;
    }

	@Override
	public void drawOverlay(final ShapeRenderer shape, final Viewport viewport, final float delta, final float alpha) {
		final int barWidth = 64;
		final int barHeight = 9;

		final float barX = getDrawX() - viewport.viewportX - barWidth / 2;
		final float barY = getDrawY() + getHeight() - viewport.viewportY - barHeight;

		shape.begin(ShapeRenderer.ShapeType.Filled);

		shape.setColor(0.65f, 0, 0, 1);
		shape.rect(barX, barY, barWidth, barHeight);

		shape.setColor(0.33f, 0, 0, 1);
		shape.rect(barX + 1, barY + 1, barWidth - 2, barHeight - 2);

		shape.setColor(1, 0, 0, 1);
		shape.rect(barX + 1, barY + 1, (barWidth - 2) * getHitPointsFraction(), barHeight - 2);

		shape.end();
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
	public float getSpeed() {
		return 0;
	}

}

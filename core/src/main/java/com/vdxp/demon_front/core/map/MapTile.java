package com.vdxp.demon_front.core.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.vdxp.demon_front.core.Collidable;
import com.vdxp.demon_front.core.Drawable;
import com.vdxp.demon_front.core.Viewport;

public abstract class MapTile extends Drawable implements Collidable {
	public float x;
	public float y;
	public float width;
	public float height;

    TextureAtlas.AtlasRegion spriteToDraw;

	@Override
	public void drawOverlay(final ShapeRenderer shape, final Viewport viewport, final float delta, final float alpha) {
	}

    public void setSpriteToDraw(TextureAtlas.AtlasRegion spriteToDraw) {
        this.spriteToDraw = spriteToDraw;
    }

    @Override
    public void drawSprite(final SpriteBatch batch,
                           final Viewport viewport,
                           final float delta,
                           final float alpha) {

        if (spriteToDraw != null) {
            batch.draw(spriteToDraw,
                    this.x  - viewport.viewportX  - drawOffsetX,
                    this.y - viewport.viewportY  - drawOffsetY);
        }
    }

    public int getTileDistFrom(float targetX, float targetY) {
        return Map.getDistInTile(Math.abs(
                  Math.sqrt(
                      (double) (
                              (x - targetX) * (x - targetX) +
                              (y - targetY) * (y - targetY)
                      )
                  )
              )
        );
    }

	@Override
	public final float getX() {
		return x;
	}

	@Override
	public final float getY() {
		return y;
	}

}

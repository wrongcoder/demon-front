package com.vdxp.demon_front.core.map;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.vdxp.demon_front.core.Drawable;
import com.vdxp.demon_front.core.Viewport;

public abstract class MapTile extends Drawable {
	private float x;
	private float y;
	private float width;
	private float height;

    TextureAtlas.AtlasRegion spriteToDraw;

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

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
                    this.getX()  - viewport.viewportX  - drawOffsetX,
                    this.getY() - viewport.viewportY  - drawOffsetY);
        }
    }

}

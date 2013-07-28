package com.vdxp.demon_front.core;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Drawable {

    protected float drawOffsetX = 16;
    protected float drawOffsetY = 16;

    /**
	 * @param batch A SpriteBatch in begin() state
	 * @param viewport The X offset of the viewport from the map frame
	 * @param delta The time (in seconds) since the last time this method was called
	 * @param alpha The fraction of time that has elapsed from the previous physics tick to the next
	 */
	public abstract void draw(final SpriteBatch batch, final Viewport viewport, final float delta, final float alpha);

}

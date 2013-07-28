package com.vdxp.demon_front.core.units;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public abstract class FriendlyUnit extends YourSideUnit {
	public FriendlyUnit(final TextureAtlas spritesAtlas, final int spriteId, final float animationSpeed, final float x, final float y) {
		super(spritesAtlas, spriteId, animationSpeed, x, y);
	}
}

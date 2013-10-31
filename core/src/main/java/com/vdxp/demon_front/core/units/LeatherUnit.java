package com.vdxp.demon_front.core.units;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.vdxp.demon_front.core.Registry;

public class LeatherUnit extends YourSideUnit {

	public LeatherUnit(final Registry r, final TextureAtlas spritesAtlas, final int xTile, final int yTile) {
		super(r, 90, spritesAtlas, 1, 0.25f, xTile, yTile);
	}

}

package com.vdxp.demon_front.core.units;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class WaspUnit extends EnemyUnit {

	public WaspUnit(final TextureAtlas spritesAtlas, final int xTile, final int yTile) {
		super(spritesAtlas, 2, 40, 57, 38, xTile, yTile);
	}

}

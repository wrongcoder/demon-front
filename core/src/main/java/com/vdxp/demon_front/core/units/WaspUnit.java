package com.vdxp.demon_front.core.units;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.vdxp.demon_front.core.Registry;

public class WaspUnit extends EnemyUnit {

	public WaspUnit(final Registry r, final TextureAtlas spritesAtlas, final int xTile, final int yTile) {
		super(r, spritesAtlas, 2, 40, 57, 38, xTile, yTile);
	}

}

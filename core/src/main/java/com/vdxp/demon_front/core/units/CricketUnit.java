package com.vdxp.demon_front.core.units;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.vdxp.demon_front.core.Registry;

public class CricketUnit extends EnemyUnit {

	public CricketUnit(final Registry r, final TextureAtlas spritesAtlas, final int xTile, final int yTile) {
		super(r, spritesAtlas, 4, 60, 53, 33, xTile, yTile);
	}

}

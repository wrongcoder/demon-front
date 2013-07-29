package com.vdxp.demon_front.core.units;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class LobsterUnit extends EnemyUnit {

	public LobsterUnit(final TextureAtlas spritesAtlas, final int xTile, final int yTile) {
		super(spritesAtlas, 3, 60, 66, 41, xTile, yTile);
	}

}

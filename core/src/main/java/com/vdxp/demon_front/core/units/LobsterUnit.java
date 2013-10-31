package com.vdxp.demon_front.core.units;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.vdxp.demon_front.core.Registry;

public class LobsterUnit extends EnemyUnit {

	public LobsterUnit(final Registry r, final TextureAtlas spritesAtlas, final int xTile, final int yTile) {
		super(r, spritesAtlas, 3, 60, 66, 41, xTile, yTile);
	}

}

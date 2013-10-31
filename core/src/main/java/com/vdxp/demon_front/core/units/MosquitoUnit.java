package com.vdxp.demon_front.core.units;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.vdxp.demon_front.core.Registry;

public class MosquitoUnit extends EnemyUnit {

	public MosquitoUnit(final Registry r, final TextureAtlas spritesAtlas, final int xTile, final int yTile) {
		super(r, spritesAtlas, 1, 40, 49, 34, xTile, yTile);
	}

}

package com.vdxp.demon_front.core.units;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class MosquitoUnit extends EnemyUnit {

	public MosquitoUnit(final TextureAtlas spritesAtlas, final int xTile, final int yTile) {
		super(spritesAtlas, 1, 40, 49, 34, xTile, yTile);
	}

}

package com.vdxp.demon_front.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SpriteTestScreen extends Screen {

	private BitmapFont font;
	private SpriteBatch batch;

	public SpriteTestScreen(final Game game) {
		super(game);
	}

	@Override
	public void show() {
		font = assetManager().get(Asset.mono16Font);
		batch = new SpriteBatch();
	}

	@Override
	public void render(final float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		font.draw(batch, "FPS " + (int) (1 / delta), 2, 26);
		batch.end();
	}

}

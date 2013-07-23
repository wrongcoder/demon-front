package com.vdxp.demon_front.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SpriteTestScreen extends Screen {

	private float toonX = 400;
	private float toonY = 300;
	private float toonDX = 0;
	private float toonDY = 0;

	private BitmapFont font;
	private SpriteBatch batch;
	private Texture invader1;

	private static final float physicsTimerRate = 0.1f;
	private float physicsTimerBucket = 0;

	public SpriteTestScreen(final Game game) {
		super(game);
	}

	@Override
	public void show() {
		font = assetManager().get(Asset.mono16Font);
		invader1 = assetManager().get(Asset.invader1);
		batch = new SpriteBatch();

		Gdx.input.setInputProcessor(new SpriteTextInputHandler());
	}

	@Override
	public void render(final float delta) {
		physicsTimerBucket += delta;
		while (physicsTimerBucket > physicsTimerRate) {
			physics(physicsTimerRate);
			physicsTimerBucket -= physicsTimerRate;
		}

		Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(invader1, toonX, toonY, 0, 0, 60, 44);
		font.draw(batch, "FPS " + (int) (1 / delta), 2, 26);
		batch.end();
	}

	private void physics(final float delta) {
		toonX += toonDX * delta;
		toonY += toonDY * delta;
	}

	private class SpriteTextInputHandler extends InputAdapter {
		private static final float V = 200; // pixels per second

		@Override
		public boolean keyDown(final int keycode) {
			switch (keycode) {
				case Input.Keys.LEFT:
					toonDX -= V;
					return true;
				case Input.Keys.RIGHT:
					toonDX += V;
					return true;
				case Input.Keys.UP:
					toonDY += V;
					return true;
				case Input.Keys.DOWN:
					toonDY -= V;
					return true;
			}
			return false;
		}

		@Override
		public boolean keyUp(final int keycode) {
			switch (keycode) {
				case Input.Keys.LEFT:
					toonDX += V;
					return true;
				case Input.Keys.RIGHT:
					toonDX -= V;
					return true;
				case Input.Keys.UP:
					toonDY -= V;
					return true;
				case Input.Keys.DOWN:
					toonDY += V;
					return true;
			}
			return false;
		}

	}
}

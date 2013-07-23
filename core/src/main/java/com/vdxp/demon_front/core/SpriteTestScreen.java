package com.vdxp.demon_front.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SpriteTestScreen extends Screen {

	private Toon toon;

	private BitmapFont font;
	private SpriteBatch batch;
	private Texture invaders;

	private static final float physicsTimerRate = 0.1f;
	private float physicsTimerBucket = 0;

	public SpriteTestScreen(final Game game) {
		super(game);
		toon = new Toon();
	}

	@Override
	public void show() {
		font = assetManager().get(Asset.mono16Font);
		invaders = assetManager().get(Asset.invaders);
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
		batch.draw(invaders, interpolate(toon.getPrevX(), toon.getX(), physicsTimerBucket / physicsTimerRate), interpolate(toon.getPrevY(), toon.getY(), physicsTimerBucket / physicsTimerRate), 0, 0, 50, 44);
		font.draw(batch, "FPS " + (int) (1 / delta), 2, 26);
		batch.end();
	}

	private void physics(final float delta) {
		toon.setX(toon.getX() + toon.getDx() * delta);
		toon.setY(toon.getY() + toon.getDy() * delta);
	}

	private static float interpolate(final float prev, final float curr, final float alpha) {
		return (prev * (1 - alpha)) + (curr * alpha);
	}

	private class SpriteTextInputHandler extends InputAdapter {
		private static final float V = 200; // pixels per second

		@Override
		public boolean keyDown(final int keycode) {
			switch (keycode) {
				case Input.Keys.LEFT:
					toon.setDx(toon.getDx() - V);
					return true;
				case Input.Keys.RIGHT:
					toon.setDx(toon.getDx() + V);
					return true;
				case Input.Keys.UP:
					toon.setDy(toon.getDy() + V);
					return true;
				case Input.Keys.DOWN:
					toon.setDy(toon.getDy() - V);
					return true;
			}
			return false;
		}

		@Override
		public boolean keyUp(final int keycode) {
			switch (keycode) {
				case Input.Keys.LEFT:
					toon.setDx(toon.getDx() + V);
					return true;
				case Input.Keys.RIGHT:
					toon.setDx(toon.getDx() - V);
					return true;
				case Input.Keys.UP:
					toon.setDy(toon.getDy() - V);
					return true;
				case Input.Keys.DOWN:
					toon.setDy(toon.getDy() + V);
					return true;
			}
			return false;
		}

	}
}

package com.vdxp.demon_front.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class SpriteTestScreen extends Screen {

	private Toon toon;

	private BitmapFont font;
	private SpriteBatch batch;

	private Music music;

	private static final float physicsTimerRate = 0.1f;
	private float physicsTimerBucket = 0;

	public static final float toonSpeed = 80; // pixels per second

	public SpriteTestScreen(final Game game) {
		super(game);
	}

	@Override
	public void show() {
		font = assetManager().get(Asset.mono16Font);
		music = assetManager().get(Asset.exoticDrums0);
		toon = new Toon(assetManager().<TextureAtlas> get(Asset.spritesAtlas));
		batch = new SpriteBatch();
		music.play();
		Gdx.input.setInputProcessor(new SpriteTestInputHandler());
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
		toon.draw(delta, (physicsTimerBucket / physicsTimerRate), batch);
		font.draw(batch, "FPS " + (int) (1 / delta), 2, 26);
		font.draw(batch, "delta " + delta, 2, 52);
		batch.end();
	}

	private void physics(final float delta) {
		toon.setX(toon.getX() + toon.getDx() * delta);
		toon.setY(toon.getY() + toon.getDy() * delta);
	}

	private class SpriteTestInputHandler extends InputAdapter {

		@Override
		public boolean keyDown(final int keycode) {
			switch (keycode) {
				case Input.Keys.LEFT:
					toon.setDx(toon.getDx() - toonSpeed);
					return true;
				case Input.Keys.RIGHT:
					toon.setDx(toon.getDx() + toonSpeed);
					return true;
				case Input.Keys.UP:
					toon.setDy(toon.getDy() + toonSpeed);
					return true;
				case Input.Keys.DOWN:
					toon.setDy(toon.getDy() - toonSpeed);
					return true;
			}
			return false;
		}

		@Override
		public boolean keyUp(final int keycode) {
			switch (keycode) {
				case Input.Keys.LEFT:
					toon.setDx(toon.getDx() + toonSpeed);
					return true;
				case Input.Keys.RIGHT:
					toon.setDx(toon.getDx() - toonSpeed);
					return true;
				case Input.Keys.UP:
					toon.setDy(toon.getDy() - toonSpeed);
					return true;
				case Input.Keys.DOWN:
					toon.setDy(toon.getDy() + toonSpeed);
					return true;
			}
			return false;
		}

	}
}

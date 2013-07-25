package com.vdxp.demon_front.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.vdxp.demon_front.core.units.Enemy;
import com.vdxp.demon_front.core.units.Hero;

public class SpriteTestScreen extends Screen {

	private Hero hero;
	private Enemy enemy1;
	private Enemy enemy2;
	private Enemy enemy3;

	private BitmapFont font;

	private SpriteBatch batch;
	private Music music;

	private Viewport viewport;

	private static final float physicsTimerRate = 0.1f;
	private float physicsTimerBucket = 0;

	private static final float controlTimerRate = 1.0f;
	private float controlTimerBucket = 0;

	public static final float heroSpeed = 80; // pixels per second
	public static final float enemySpeed = 20; // TODO move these into the unit classes

	public SpriteTestScreen(final Game game) {
		super(game);
	}

	@Override
	public void show() {
		final TextureAtlas spritesAtlas = assetManager().<TextureAtlas>get(Asset.spritesAtlas);
		font = assetManager().get(Asset.mono16Font);
		music = assetManager().get(Asset.exoticDrums0);
		hero = new Hero(spritesAtlas);
		enemy1 = new Enemy(spritesAtlas, 1200, 800);
		enemy2 = new Enemy(spritesAtlas, 800, 800);
		enemy3 = new Enemy(spritesAtlas, 500, 1000);
		batch = new SpriteBatch();
		viewport = new Viewport(hero);
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

		controlTimerBucket += delta;
		while (controlTimerBucket > controlTimerRate) {
			control(controlTimerRate);
			controlTimerBucket -= controlTimerRate;
		}

		// This will be wrong if we had to call physics multiple times
		// This will also be wrong for any movement taking place in the control loop
		final float alpha = physicsTimerBucket / physicsTimerRate;

		graphics(delta, alpha);
		viewport.setPosition(hero);
	}

	private void graphics(final float delta, final float alpha) {
		Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		hero.draw(batch, viewport, delta, alpha);
		enemy1.draw(batch, viewport, delta, alpha);
		enemy2.draw(batch, viewport, delta, alpha);
		enemy3.draw(batch, viewport, delta, alpha);
		font.draw(batch, "FPS " + (int) (1 / delta), 2, 26);
		font.draw(batch, "hero: " + hero.getDrawX() + ", " + hero.getDrawY(), 2, 52);
		font.draw(batch, "enemy1: " + enemy1.getX() + ", " + enemy1.getY(), 2, 78);
		font.draw(batch, "viewport: " + viewport.viewportX + ", " + viewport.viewportY, 2, 104);
		batch.end();
	}

	private void physics(final float delta) {
		hero.setX(hero.getX() + hero.getDx() * delta);
		hero.setY(hero.getY() + hero.getDy() * delta);

		makeRandomStep(enemy1, delta);
		makeRandomStep(enemy2, delta);
		makeRandomStep(enemy3, delta);
	}

	private void control(final float delta) {
	}

	private static void makeRandomStep(final Enemy enemy, final float delta) {
		final float angle = (float) (Math.PI * 2 * Math.random());
		final float deltaX = (float) (enemySpeed * Math.sin(angle)) * delta;
		final float deltaY = (float) (enemySpeed * Math.cos(angle)) * delta;
		enemy.setX(enemy.getX() + deltaX);
		enemy.setY(enemy.getY() + deltaY);
	}

	private class SpriteTestInputHandler extends InputAdapter {

		@Override
		public boolean keyDown(final int keycode) {
			switch (keycode) {
				case Input.Keys.LEFT:
					hero.setDx(hero.getDx() - heroSpeed);
					return true;
				case Input.Keys.RIGHT:
					hero.setDx(hero.getDx() + heroSpeed);
					return true;
				case Input.Keys.UP:
					hero.setDy(hero.getDy() + heroSpeed);
					return true;
				case Input.Keys.DOWN:
					hero.setDy(hero.getDy() - heroSpeed);
					return true;
			}
			return false;
		}

		@Override
		public boolean keyUp(final int keycode) {
			switch (keycode) {
				case Input.Keys.LEFT:
					hero.setDx(hero.getDx() + heroSpeed);
					return true;
				case Input.Keys.RIGHT:
					hero.setDx(hero.getDx() - heroSpeed);
					return true;
				case Input.Keys.UP:
					hero.setDy(hero.getDy() - heroSpeed);
					return true;
				case Input.Keys.DOWN:
					hero.setDy(hero.getDy() + heroSpeed);
					return true;
			}
			return false;
		}

	}

}

package com.vdxp.demon_front.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.vdxp.demon_front.core.map.Map;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.vdxp.demon_front.core.map.MapTile;
import com.vdxp.demon_front.core.units.EnemyUnit;
import com.vdxp.demon_front.core.units.HeroUnit;
import com.vdxp.demon_front.core.units.LeatherUnit;
import com.vdxp.demon_front.core.units.Unit;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SpriteTestScreen extends Screen {

	private HeroUnit hero;

	private BitmapFont font;

	private SpriteBatch batch;
	private ShapeRenderer shape;

	private Viewport viewport;

	private static final float physicsTimerRate = 0.1f;
	private float physicsTimerBucket = 0;

	private static final float controlTimerRate = 0.3333f;
	private float controlTimerBucket = 0;

	// These things are painted in this order, one list after another
	private Map map1_layer1 = new Map();
    private Map map1_layer2 = new Map();
    private Map fogOfWar = new Map();
    private Set<MapTile> background = new HashSet<MapTile>();
    private Set<MapTile> inactiveCollidables = new HashSet<MapTile>();
    private Set<Unit> activeCollidables = new HashSet<Unit>();
    private Set<Drawable> inactiveNonCollidables_effects = new HashSet<Drawable>();

	public SpriteTestScreen(final Game game) {
		super(game);
	}

    public HeroUnit getHero() {
        return hero;
    }

    @Override
	public void show() {
		final TextureAtlas spritesAtlas = assetManager().<TextureAtlas>get(Asset.spritesAtlas);
		font = assetManager().get(Asset.mono16Font);

        map1_layer1.Init("map/map_1_layer1.txt");
        map1_layer2.Init("map/map_1_layer2.txt");
        fogOfWar.Init("map/fogOfWar.txt");

        background.addAll(map1_layer1.getNonCollidableMapTiles());
        inactiveCollidables.addAll(map1_layer1.getCollidableMapTiles());
        activeCollidables.addAll(map1_layer1.getUnits());

        background.addAll(map1_layer2.getNonCollidableMapTiles());
        inactiveCollidables.addAll(map1_layer2.getCollidableMapTiles());
        activeCollidables.addAll(map1_layer2.getUnits());

        inactiveNonCollidables_effects.addAll(fogOfWar.getNonCollidableMapTiles());

        hero = new HeroUnit(spritesAtlas);
		activeCollidables.add(hero);

		activeCollidables.add(new LeatherUnit(spritesAtlas, 18, 11));
		activeCollidables.add(new LeatherUnit(spritesAtlas, 18, 8));
		activeCollidables.add(new EnemyUnit(spritesAtlas, 25, 6));
		activeCollidables.add(new EnemyUnit(spritesAtlas, 25, 4));

		batch = new SpriteBatch();
		shape = new ShapeRenderer();
		viewport = new Viewport(hero);
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
		for (final Drawable drawable : background) {
			drawable.drawSprite(batch, viewport, delta, alpha);
		}
		for (final Drawable drawable : inactiveCollidables) {
			drawable.drawSprite(batch, viewport, delta, alpha);
		}
		for (final Drawable drawable : activeCollidables) {
			drawable.drawSprite(batch, viewport, delta, alpha);
		}
		for (final Drawable drawable : inactiveNonCollidables_effects) {
            drawable.drawSprite(batch, viewport, delta, alpha);
        }
		font.draw(batch, "FPS " + (int) (1 / delta), 2, 26);
		font.draw(batch, "hero: " + hero.getDrawX() + ", " + hero.getDrawY(), 2, 52);
		font.draw(batch, "moving: " + hero.computeMovementAngle(), 300, 52);
		font.draw(batch, "viewport: " + viewport.viewportX + ", " + viewport.viewportY, 2, 78);
		if (!hero.isAlive()) {
			font.draw(batch, "Game Over", 2, 104);
		}
		batch.end();

		for (final Drawable drawable : background) {
			drawable.drawOverlay(shape, viewport, delta, alpha);
		}
		for (final Drawable drawable : inactiveCollidables) {
			drawable.drawOverlay(shape, viewport, delta, alpha);
		}
		for (final Drawable drawable : activeCollidables) {
			drawable.drawOverlay(shape, viewport, delta, alpha);
		}
	}

	private void physics(final float delta) {
		for (final Unit unit : activeCollidables) {
			unit.physics(delta, activeCollidables, inactiveCollidables);
		}
	}

	private void control(final float delta) {
		for (final Unit unit : activeCollidables) {
			unit.combat(delta, activeCollidables);
		}

		final Iterator<Unit> iterator = activeCollidables.iterator();
		while (iterator.hasNext()) {
			final Unit unit = iterator.next();
			if (!unit.isAlive() && unit != hero) {
				iterator.remove();
			}
		}
	}

	private class SpriteTestInputHandler extends InputAdapter {

		@Override
		public boolean keyDown(final int keycode) {
			switch (keycode) {
				case Input.Keys.LEFT:
					hero.setMovingLeft(true);
					return true;
				case Input.Keys.RIGHT:
					hero.setMovingRight(true);
					return true;
				case Input.Keys.UP:
					hero.setMovingUp(true);
					return true;
				case Input.Keys.DOWN:
					hero.setMovingDown(true);
					return true;
			}
			return false;
		}

		@Override
		public boolean keyUp(final int keycode) {
			switch (keycode) {
				case Input.Keys.LEFT:
					hero.setMovingLeft(false);
					return true;
				case Input.Keys.RIGHT:
					hero.setMovingRight(false);
					return true;
				case Input.Keys.UP:
					hero.setMovingUp(false);
					return true;
				case Input.Keys.DOWN:
					hero.setMovingDown(false);
					return true;
			}
			return false;
		}

	}

}

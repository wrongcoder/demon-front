package com.vdxp.demon_front.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.vdxp.demon_front.core.map.Map;
import com.vdxp.demon_front.core.map.MapTile;
import com.vdxp.demon_front.core.units.ClothUnit;
import com.vdxp.demon_front.core.units.CricketUnit;
import com.vdxp.demon_front.core.units.DemonGate;
import com.vdxp.demon_front.core.units.HeroUnit;
import com.vdxp.demon_front.core.units.LeatherUnit;
import com.vdxp.demon_front.core.units.LobsterUnit;
import com.vdxp.demon_front.core.units.MailUnit;
import com.vdxp.demon_front.core.units.MosquitoUnit;
import com.vdxp.demon_front.core.units.Unit;
import com.vdxp.demon_front.core.units.WaspUnit;

public class SpriteTestScreen extends Screen {

	private HeroUnit hero;

	private BitmapFont debugFont;
	private BitmapFont shoutFont;

	private SpriteBatch batch;
	private ShapeRenderer shape;

	private Viewport viewport;

	private static final float physicsTimerRate = 0.1f;
	private float physicsTimerBucket = 0;

	private static final float controlTimerRate = 0.3333f;
	private float controlTimerBucket = 0;

    private float friendlySpawnTimerbucket = 0;
	private static final float friendlySpawnInterval = 9 * controlTimerRate;

    // These things are painted in this order, one list after another
	private Map map1_layer1 = new Map();
    private Map map1_layer2 = new Map();
    private Map fogOfWar = new Map();
    private Array<MapTile> background = new Array<MapTile>();
    private Array<MapTile> inactiveCollidables = new Array<MapTile>();
    private Array<Unit> activeCollidables = new Array<Unit>();
    private Array<Drawable> inactiveNonCollidables_effects = new Array<Drawable>();

    private Array<Unit> toSpawn = new Array<Unit>();

    public Array<Unit> getActiveCollidables() {
        return activeCollidables;
    }

    public SpriteTestScreen(final Game game) {
		super(game);
	}

    public HeroUnit getHero() {
        return hero;
    }

    @Override
	public void show() {
		final TextureAtlas spritesAtlas = assetManager().<TextureAtlas>get(Asset.spritesAtlas);
		debugFont = assetManager().get(Asset.mono16Font);
	    shoutFont = assetManager().get(Asset.sans24boldFont);

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

		batch = new SpriteBatch();
		shape = new ShapeRenderer();
		viewport = new Viewport(hero);
		Gdx.input.setInputProcessor(new SpriteTestInputHandler());
	}

	@Override
	public void render(final float delta) {
		physicsTimerBucket += delta;
		if (physicsTimerBucket > physicsTimerRate) {
			physics(physicsTimerRate);
			physicsTimerBucket -= physicsTimerRate;
		}
		if (physicsTimerBucket > physicsTimerRate) {
			physicsTimerBucket = 0;
		}

		controlTimerBucket += delta;
		if (controlTimerBucket > controlTimerRate) {
			control(controlTimerRate);
			controlTimerBucket -= controlTimerRate;
		}
		if (controlTimerBucket > controlTimerRate) {
			controlTimerBucket = 0;
		}

        friendlySpawnTimerbucket += delta;
		if (friendlySpawnTimerbucket > friendlySpawnInterval) {
            scheduleFriendlySpawn(1);
            friendlySpawnTimerbucket = 0f;
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
		for (int ix = 0; ix < background.size; ix++) {
			background.get(ix).drawSprite(batch, viewport, delta, alpha);
		}
		for (int ix = 0; ix < inactiveCollidables.size; ix++) {
			inactiveCollidables.get(ix).drawSprite(batch, viewport, delta, alpha);
		}
		for (int ix = 0; ix < activeCollidables.size; ix++) {
			activeCollidables.get(ix).drawSprite(batch, viewport, delta, alpha);
		}
		batch.end();

		for (int ix = 0; ix < background.size; ix++) {
			background.get(ix).drawOverlay(shape, viewport, delta, alpha);
		}
		for (int ix = 0; ix < inactiveCollidables.size; ix++) {
			inactiveCollidables.get(ix).drawOverlay(shape, viewport, delta, alpha);
		}
		for (int ix = 0; ix < activeCollidables.size; ix++) {
			activeCollidables.get(ix).drawOverlay(shape, viewport, delta, alpha);
		}

		batch.begin();
		for (int ix = 0; ix < inactiveNonCollidables_effects.size; ix++) {
			inactiveNonCollidables_effects.get(ix).drawSprite(batch, viewport, delta, alpha);
		}
		/*
		debugFont.draw(batch, "FPS " + (int) (1 / delta), 2, 26);
		debugFont.draw(batch, "hero: " + hero.getDrawX() + ", " + hero.getDrawY(), 2, 52);
		debugFont.draw(batch, "moving: " + hero.computeMovementAngle(), 300, 52);
		debugFont.draw(batch, "viewport: " + viewport.viewportX + ", " + viewport.viewportY, 2, 78);
		if (!hero.isAlive()) {
			debugFont.draw(batch, "Game Over", 2, 104);
		}
		*/
		if (hero.isShouting()) {
			shoutFont.setColor(0, 0, 0, 0.4f);
			Util.drawCentredOn("Orcs! Go " + hero.computeShoutCommand() + "!", shoutFont, batch, hero.getDrawX() - viewport.viewportX + 2, hero.getDrawY() - viewport.viewportY - 17);
			shoutFont.setColor(1, 1, 1, 1);
			Util.drawCentredOn("Orcs! Go " + hero.computeShoutCommand() + "!", shoutFont, batch, hero.getDrawX() - viewport.viewportX, hero.getDrawY() - viewport.viewportY - 15);
		}
		batch.end();
	}

	private void physics(final float delta) {
		for (int ix = 0; ix < activeCollidables.size; ix++) {
			activeCollidables.get(ix).physics(delta, activeCollidables, inactiveCollidables);
		}
	}

    public void scheduleEnemySpawn(int numberToSpawn, int tileX, int tileY) {
	    final TextureAtlas spritesAtlas = assetManager().get(Asset.spritesAtlas);

        for (int i=0;i<numberToSpawn;i++) {
	        final double enemyType = Math.random() * 4;
	        final int spawnX = tileX + (int)Math.floor(Math.random() * 2);
	        final int spawnY = tileY + (int)Math.floor(Math.random() * 2);

	        if (enemyType < 1) {
		        toSpawn.add(new MosquitoUnit(spritesAtlas, spawnX, spawnY));
	        } else if (enemyType < 2) {
		        toSpawn.add(new WaspUnit(spritesAtlas, spawnX, spawnY));
	        } else if (enemyType < 3) {
		        toSpawn.add(new LobsterUnit(spritesAtlas, spawnX, spawnY));
	        } else {
		        toSpawn.add(new CricketUnit(spritesAtlas, spawnX, spawnY));
	        }
        }
        Game.instance().getSoundMan().playGrowl();
    }

    public void scheduleFriendlySpawn(int numberToSpawn) {
	    final TextureAtlas spritesAtlas = assetManager().get(Asset.spritesAtlas);

	    for (int i=0;i<numberToSpawn;i++) {
		    final double friendlyType = Math.random() * 3;
		    final int spawnX = (int) Math.ceil(Math.random() * 52) + 8;

		    if (friendlyType < 1) {
			    toSpawn.add(new LeatherUnit(spritesAtlas, spawnX, 4));
		    } else if (friendlyType < 2) {
			    toSpawn.add(new MailUnit(spritesAtlas, spawnX, 4));
		    } else {
			    toSpawn.add(new ClothUnit(spritesAtlas, spawnX, 4));
		    }
        }
        Game.instance().getSoundMan().playOrkSpawn();
    }

	private void control(final float delta) {
		for (int ix = activeCollidables.size - 1; ix >= 0; ix--) {
			final Unit unit = activeCollidables.get(ix);
			unit.combat(delta, activeCollidables);
			if (!unit.isAlive() && unit != hero) {
				activeCollidables.removeIndex(ix);
			}
		}

        if (toSpawn.size > 0) {
		    activeCollidables.addAll(toSpawn);
		    toSpawn.clear();
        }

		final MusicMan musicMan = game().getMusicMan();
		final int units = activeCollidables.size;
		final int demonGatesLeft = countGates(activeCollidables);

		if (demonGatesLeft == 1) {
			musicMan.requestMusic(MusicMan.Mood.Cliffhanger, delta);
		} else if (units > 60) {
			musicMan.requestMusic(MusicMan.Mood.Impasse, delta);
		} else if (units > 40) {
			musicMan.requestMusic(MusicMan.Mood.Conflict, delta);
		} else if (units > 20) {
			musicMan.requestMusic(MusicMan.Mood.Agitation, delta);
		} else {
			musicMan.requestMusic(MusicMan.Mood.Calm, delta);
		}

		if (!hero.isAlive() /* || gates are down */) {
			game().setScreen(new LoseEndSplashScreen(game()));
		}
		if (demonGatesLeft == 0) {
			game().setScreen(new WinEndSplashScreen(game()));
		}
	}

	private static int countGates(final Array<Unit> activeCollidables) {
		int count = 0;
		for (int ix = 0; ix < activeCollidables.size; ix++) {
			if (activeCollidables.get(ix) instanceof DemonGate) {
				count++;
			}
		}
		return count;
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
				case Input.Keys.W:
					hero.setShoutingUp(true);
					return true;
				case Input.Keys.A:
					hero.setShoutingLeft(true);
					return true;
				case Input.Keys.S:
					hero.setShoutingDown(true);
					return true;
				case Input.Keys.D:
					hero.setShoutingRight(true);
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
				case Input.Keys.W:
					hero.setShoutingUp(false);
					return true;
				case Input.Keys.A:
					hero.setShoutingLeft(false);
					return true;
				case Input.Keys.S:
					hero.setShoutingDown(false);
					return true;
				case Input.Keys.D:
					hero.setShoutingRight(false);
					return true;
			}
			return false;
		}

	}

}

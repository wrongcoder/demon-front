package com.vdxp.demon_front.core.units;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.vdxp.demon_front.core.Game;
import com.vdxp.demon_front.core.SpriteTestScreen;
import com.vdxp.demon_front.core.Viewport;
import com.vdxp.demon_front.core.map.Map;
import com.vdxp.demon_front.core.map.MapTile;

public class DemonGate extends Unit {

    TextureAtlas.AtlasRegion demonGateActive1;
    TextureAtlas.AtlasRegion demonGateActive2;
    TextureAtlas.AtlasRegion demonGateSealing1;
    TextureAtlas.AtlasRegion demonGateSealing2;
    TextureAtlas.AtlasRegion demonGateSealing3;
    private final Animation defaultAnimation;

    private final Sprite swordSlash;
    private float swordSlashTimer = 0;

    private final Animation dyingAnimation;

    private final float demonSpawnInterval1 = 80f * 0.3333f;
    private float demonSpawnBucket1 = 0f;
    private final int spawnNumberPerInterval1 = 4;

    public DemonGate(final TextureAtlas spritesAtlas,
                     final int xTile,
                     final int yTile) {

	    super(1000);

        final float xPixel = Map.getGameXinPixel(xTile);
        final float yPixel = Map.getGameYinPixel(yTile);

        demonGateActive1 = spritesAtlas.findRegion("demongate_active1");
        demonGateActive2 = spritesAtlas.findRegion("demongate_active2");
        defaultAnimation = new Animation(0.1f, demonGateActive1, demonGateActive2);
        defaultAnimation.setPlayMode(Animation.LOOP);

        demonGateSealing1 = spritesAtlas.findRegion("demongate_sealing1");
        demonGateSealing2 = spritesAtlas.findRegion("demongate_sealing2");
        demonGateSealing3 = spritesAtlas.findRegion("demongate_sealing3");
        dyingAnimation = new Animation(0.1f, demonGateSealing1, demonGateSealing2, demonGateSealing3);
        dyingAnimation.setPlayMode(Animation.NORMAL);

        swordSlash = new Sprite(spritesAtlas.findRegion("sword-slash"));

        setDimensions(xPixel, yPixel, 64, 64);
	    drawOffsetX += 8; // hacks
        setAnimation(defaultAnimation, true);
    }

    @Override
    public void drawSprite(final SpriteBatch batch, final Viewport viewport, final float delta, final float alpha) {

        if (this.getHp() <= 0) {
            if (this.getAnimation().isAnimationFinished(this.stateTime)) {
                die();
                Game.instance().getSoundMan().playSealing();
            }
        }

        super.drawSprite(batch, viewport, delta, alpha);
        if (swordSlashTimer > delta) {
            swordSlashTimer -= delta;
            batch.draw(swordSlash, getDrawX() - viewport.viewportX + 8, getDrawY() - viewport.viewportY);
            Game.instance().getSoundMan().playHumanAttack();
        }
    }

	@Override
	public void drawOverlay(final ShapeRenderer shape, final Viewport viewport, final float delta, final float alpha) {

        final HeroUnit hero = screen.hero;

        float targetX = hero.getX();
        float targetY = hero.getY();
        double tileDist = ((Math.abs(
                Math.sqrt(
                        (double) (
                                (x - targetX) * (x - targetX) +
                                        (y - targetY) * (y - targetY)
                        )
                )
        )) / 32);

        if (tileDist < 12) {
            final int barWidth = 64;
            final int barHeight = 9;

            final float barX = getDrawX() - viewport.viewportX - (barWidth / 8);
            final float barY = getDrawY() + getHeight() - viewport.viewportY - barHeight;

            shape.begin(ShapeRenderer.ShapeType.Filled);

            shape.setColor(0.65f, 0, 0, 1f);
            shape.rect(barX, barY, barWidth, barHeight);

            shape.setColor(0.33f, 0, 0, 1f);
            shape.rect(barX + 1, barY + 1, barWidth - 2, barHeight - 2);

            shape.setColor(1, 0, 0, 1f);
            shape.rect(barX + 1, barY + 1, (barWidth - 2) * getHitPointsFraction(), barHeight - 2);

            shape.end();
        }
	}

	@Override
    public void physics(final float delta, final Array<Unit> activeCollidables, final Array<MapTile> inactiveCollidables) {
        // no movement, no collision
    }

    @Override
    public void combat(final float delta, final Array<Unit> activeCollidables) {
        // does not attack

        // does spawning logic, delta is 0.3333f
        demonSpawnBucket1 += delta;
        if (demonSpawnBucket1 > demonSpawnInterval1) {
	        final Screen screen = Game.instance().getScreen();
	        if (screen.getClass() != SpriteTestScreen.class) {
		        return;
	        }
	        ((SpriteTestScreen) screen).
                    scheduleEnemySpawn(4, Map.getDistInTile(this.getX()), Map.getDistInTile(this.getY()));
            demonSpawnBucket1 = 0f;
        }
    }

	@Override
	public float getSpeed() {
		return 0;
	}

    @Override
    public void receiveHit(final int hp, final Unit source) {
        super.receiveHit(hp, source);
        swordSlashTimer += 0.1f;
    }

    @Override
    public void changeHp(final float deltaHp) {
        this.setHp(this.getHp() + deltaHp);

        if (this.getHp() < 0) {
            this.setAnimation(dyingAnimation);
        }
    }

	@Override
	public boolean isFriendly() {
		return false;
	}

}

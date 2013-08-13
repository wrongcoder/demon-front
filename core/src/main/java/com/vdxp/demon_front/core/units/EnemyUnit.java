package com.vdxp.demon_front.core.units;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.vdxp.demon_front.core.Game;
import com.vdxp.demon_front.core.LoseEndSplashScreen;
import com.vdxp.demon_front.core.SpriteTestScreen;
import com.vdxp.demon_front.core.Viewport;
import com.vdxp.demon_front.core.map.Map;
import com.vdxp.demon_front.core.map.MapTile;

public abstract class EnemyUnit extends Unit {

	private final Animation defaultAnimation;
	private final Sprite swordSlash;
	private float swordSlashTimer = 0;
    private float aggressivenessTimer = 0;
    private float currDirection = 0;
    private float directionChangeTimer = 4;

    public EnemyUnit(final TextureAtlas spritesAtlas, final int spriteId, final float maxHp, final float width, final float height, final int xTile, final int yTile) {
		super(maxHp);
		final float x = Map.getGameXinPixel(xTile);
		final float y = Map.getGameYinPixel(yTile);
		final TextureAtlas.AtlasRegion frame1 = spritesAtlas.findRegion("invader" + spriteId + "_1of2");
		final TextureAtlas.AtlasRegion frame2 = spritesAtlas.findRegion("invader" + spriteId + "_2of2");
		defaultAnimation = new Animation(0.6f, frame1, frame2);
		defaultAnimation.setPlayMode(Animation.LOOP);

		swordSlash = new Sprite(spritesAtlas.findRegion("sword-slash"));

		setDimensions(x, y, width, height);
		setAnimation(defaultAnimation, true);
	}

	@Override
	public float getSpeed() {
		return 50;
	}

	@Override
	public void physics(final float delta, final Array<Unit> activeCollidables, final Array<MapTile> inactiveCollidables) {

        // lose condition, enemy reaches end of screen
        if (this.getY() <= 10) {
            Game.instance().setScreen(new LoseEndSplashScreen(Game.instance()));
        }

        final int tileX = Map.getDistInTile(this.getX());
        final int tileY = Map.getDistInTile(this.getY());

        aggressivenessTimer += delta;
        final float aggressivenessLevel = aggressivenessTimer / 180f;

        if ((tileX > 27 || tileX < 53) && aggressivenessTimer > 30f) {
            aggressivenessTimer = 0;
        }

        directionChangeTimer += delta;
        if (Math.random() < (aggressivenessLevel)) {

            if (Math.random() > 0.6f) {
                if (tileX < 18) {
                    currDirection = 0;
                } else if (tileX > 61) {
                    currDirection = (float)Math.PI;
                } else if (tileY > 4) {
                    currDirection = 1.5f * (float)Math.PI;
                }
                tryMove(currDirection, delta, activeCollidables, inactiveCollidables);
                return;
            }

            if (directionChangeTimer > 1f) {
                currDirection = (float) ((Math.PI * 2) * Math.random());
                directionChangeTimer = 0;
            }

            tryMove(currDirection, delta, activeCollidables, inactiveCollidables);
            return;
        }

        if (directionChangeTimer > 3) {
            currDirection = (float) ((Math.PI * 2) * Math.random());
            directionChangeTimer = 0;
        }

		tryMove(currDirection, delta, activeCollidables, inactiveCollidables);
	}

	@Override
	public void drawSprite(final SpriteBatch batch, final Viewport viewport, final float delta, final float alpha) {
        super.drawSprite(batch, viewport, delta, alpha);
        if (swordSlashTimer > delta) {
            swordSlashTimer -= delta;
            batch.draw(swordSlash, getDrawX() - viewport.viewportX - drawOffsetX, getDrawY() - viewport.viewportY - drawOffsetY);
            Game.instance().getSoundMan().playHumanAttack();
        }
	}

	@Override
	public void drawOverlay(final ShapeRenderer shape, final Viewport viewport, final float delta, final float alpha) {

        HeroUnit hero = screen.hero;

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

            final int barWidth = 32;
            final int barHeight = 6;

            final float barX = getDrawX() - viewport.viewportX - barWidth / 2;
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
	public void combat(final float delta, final Array<Unit> activeCollidables) {
		if (Math.random() < delta / 1.5) {
			Rectangle.tmp.set(getX() - 8, getY() - 8, getWidth() + 16, getHeight() + 16);
			for (int ix = 0; ix < activeCollidables.size; ix++) {
				final Unit unit = activeCollidables.get(ix);
				if (!isOnMySide(unit)) {
					Rectangle.tmp2.set(unit.getX(), unit.getY(), unit.getWidth(), unit.getHeight());
					if (Rectangle.tmp.overlaps(Rectangle.tmp2)) {
						unit.receiveHit(20, this);
						return;
					}
				}
			}
		}
	}

	@Override
	public void receiveHit(final int hp, final Unit source) {
		super.receiveHit(hp, source);
		swordSlashTimer += 0.1f;
	}

	@Override
	public boolean isFriendly() {
		return false;
	}
}

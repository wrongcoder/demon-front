package com.vdxp.demon_front.core.units;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.vdxp.demon_front.core.Game;
import com.vdxp.demon_front.core.Viewport;
import com.vdxp.demon_front.core.map.Map;
import com.vdxp.demon_front.core.map.MapTile;

import java.util.Set;

public class EnemyUnit extends Unit {

	private final Animation defaultAnimation;
	private final Sprite swordSlash;
	private float swordSlashTimer = 0;

	private float aggressivenessTimer = 0;

	public EnemyUnit(final TextureAtlas spritesAtlas, final int xTile, final int yTile) {
		super(40);
		final float x = Map.getGameXinPixel(xTile);
		final float y = Map.getGameYinPixel(yTile);
		final TextureAtlas.AtlasRegion frame1 = spritesAtlas.findRegion("invader2_1of2");
		final TextureAtlas.AtlasRegion frame2 = spritesAtlas.findRegion("invader2_2of2");
		defaultAnimation = new Animation(0.8f, frame1, frame2);
		defaultAnimation.setPlayMode(Animation.LOOP);

		swordSlash = new Sprite(spritesAtlas.findRegion("sword-slash"));

		setDimensions(x, y, frame1.getRegionWidth(), frame1.getRegionHeight());
		setAnimation(defaultAnimation, true);
	}

	@Override
	public float getSpeed() {
		return 50;
	}

	@Override
	public void physics(final float delta, final Set<Unit> activeCollidables, final Set<MapTile> inactiveCollidables) {
        aggressivenessTimer += delta;

        final float aggressivenessLevel = aggressivenessTimer / (float) 2;

        final float angle = (float) (
                                (((Math.PI * 2) - (aggressivenessLevel * Math.PI))
                                * Math.random())
                                - (Math.PI / 4)
                                );

		tryMove(angle, delta, activeCollidables, inactiveCollidables);
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
		final int barWidth = 32;
		final int barHeight = 6;

		final float barX = getDrawX() - viewport.viewportX - barWidth / 2;
		final float barY = getDrawY() + getHeight() - viewport.viewportY - barHeight;

		shape.begin(ShapeRenderer.ShapeType.Filled);

		shape.setColor(0.65f, 0, 0, 1);
		shape.rect(barX, barY, barWidth, barHeight);

		shape.setColor(0.33f, 0, 0, 1);
		shape.rect(barX + 1, barY + 1, barWidth - 2, barHeight - 2);

		shape.setColor(1, 0, 0, 1);
		shape.rect(barX + 1, barY + 1, (barWidth - 2) * getHitPointsFraction(), barHeight - 2);

		shape.end();
	}

	@Override
	public void combat(final float delta, final Set<Unit> activeCollidables) {
		if (Math.random() < delta / 1.5) {
			Rectangle.tmp.set(getX() - 8, getY() - 8, getWidth() + 16, getHeight() + 16);
			for (final Unit unit : activeCollidables) {
				if (!(unit instanceof EnemyUnit) &&
                    !(unit instanceof DemonGate)
				        ) {
					Rectangle.tmp2.set(unit.getX(), unit.getY(), unit.getWidth(), unit.getHeight());
					if (Rectangle.tmp.overlaps(Rectangle.tmp2)) {
						unit.receiveHit(20, this);
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
}

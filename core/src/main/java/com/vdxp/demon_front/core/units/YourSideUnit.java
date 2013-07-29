package com.vdxp.demon_front.core.units;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.vdxp.demon_front.core.map.Map;
import com.vdxp.demon_front.core.map.MapTile;

import java.util.Set;

/** This is any FriendlyUnit that uses a Your Side sprite */
public abstract class YourSideUnit extends FriendlyUnit {

	private Animation stoppedAnimation;

	private final Animation downStoppedAnimation;
	private final Animation downMovingAnimation;
	private final Animation upStoppedAnimation;
	private final Animation upMovingAnimation;
	private final Animation rightStoppedAnimation;
	private final Animation rightMovingAnimation;
	private final Animation leftStoppedAnimation;
	private final Animation leftMovingAnimation;

	private HeroUnit.ShoutCommand shoutCommand = null;

	public YourSideUnit(final float maxHp, final TextureAtlas spritesAtlas, final int spriteId, final float animationSpeed, final int xTile, final int yTile) {
		super(maxHp, spritesAtlas);

		final float x = Map.getGameXinPixel(xTile);
		final float y = Map.getGameYinPixel(yTile);

		downStoppedAnimation = buildAnimation(animationSpeed, spritesAtlas, Animation.NORMAL, "yourside_richtaur_32x32_" + spriteId + "_2of10");
		upStoppedAnimation = buildAnimation(animationSpeed, spritesAtlas, Animation.NORMAL, "yourside_richtaur_32x32_" + spriteId + "_1of10");
		rightStoppedAnimation = buildAnimation(animationSpeed, spritesAtlas, Animation.NORMAL, "yourside_richtaur_32x32_" + spriteId + "_5of10");
		leftStoppedAnimation = buildAnimation(animationSpeed, spritesAtlas, Animation.NORMAL, true, "yourside_richtaur_32x32_" + spriteId + "_5of10");
		downMovingAnimation = buildAnimation(animationSpeed, spritesAtlas, Animation.LOOP, "yourside_richtaur_32x32_" + spriteId + "_3of10", "yourside_richtaur_32x32_" + spriteId + "_4of10");
		upMovingAnimation = buildAnimation(animationSpeed, spritesAtlas, Animation.LOOP, "yourside_richtaur_32x32_" + spriteId + "_9of10", "yourside_richtaur_32x32_" + spriteId + "_10of10");
		rightMovingAnimation = buildAnimation(animationSpeed, spritesAtlas, Animation.LOOP, "yourside_richtaur_32x32_" + spriteId + "_6of10", "yourside_richtaur_32x32_" + spriteId + "_7of10", "yourside_richtaur_32x32_" + spriteId + "_8of10", "yourside_richtaur_32x32_" + spriteId + "_7of10");
		leftMovingAnimation = buildAnimation(animationSpeed, spritesAtlas, Animation.LOOP, true, "yourside_richtaur_32x32_" + spriteId + "_6of10", "yourside_richtaur_32x32_" + spriteId + "_7of10", "yourside_richtaur_32x32_" + spriteId + "_8of10", "yourside_richtaur_32x32_" + spriteId + "_7of10");

		stoppedAnimation = downStoppedAnimation;
		setAnimation(downStoppedAnimation, true);
		setDimensions(x, y, 32, 32);
	}

	protected void setNextAnimation(final Float angle) {
		if (angle == null) {
			setAnimation(stoppedAnimation);
		} else if (angle < (3f / 8f) * Math.PI) {
			setAnimation(rightMovingAnimation);
			stoppedAnimation = rightStoppedAnimation;
		} else if (angle < (5f / 8f) * Math.PI) {
			setAnimation(upMovingAnimation);
			stoppedAnimation = upStoppedAnimation;
		} else if (angle < (11f / 8f) * Math.PI) {
			setAnimation(leftMovingAnimation);
			stoppedAnimation = leftStoppedAnimation;
		} else if (angle < (13f / 8f) * Math.PI) {
			setAnimation(downMovingAnimation);
			stoppedAnimation = downStoppedAnimation;
		} else {
			setAnimation(rightMovingAnimation);
			stoppedAnimation = rightStoppedAnimation;
		}
	}

	@Override
	public void physics(final float delta, final Set<Unit> activeCollidables, final Set<MapTile> inactiveCollidables) {
		final float angle;

		if (shoutCommand != null) {
			angle = shoutCommand.angle;
		} else {
			angle = (float) (Math.PI * 2 * Math.random());
		}

		final boolean moved = tryMove(angle, delta, activeCollidables, inactiveCollidables);
		if (!moved && shoutCommand != null) {
			shoutCommand = null;
		}

		setNextAnimation(getAngle());
	}

	public void setShoutCommand(final HeroUnit.ShoutCommand shoutCommand) {
		this.shoutCommand = shoutCommand;
	}
}

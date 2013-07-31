package com.vdxp.demon_front.core.units;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.vdxp.demon_front.core.Game;
import com.vdxp.demon_front.core.Viewport;
import com.vdxp.demon_front.core.map.MapTile;

public class WallSection extends Unit {

    private final Sprite clawSlash;

    TextureAtlas.AtlasRegion wallSectionSprite1;
    TextureAtlas.AtlasRegion wallSectionSprite2;
    TextureAtlas.AtlasRegion wallSectionSprite3;
    TextureAtlas.AtlasRegion wallSectionSprite4;
    TextureAtlas.AtlasRegion wallSectionSprite5;
    private float clawSlashTimer = 0;

    public WallSection(final TextureAtlas spritesAtlas,
                       int mapX,
                       int mapY) {
	    super(100);

        wallSectionSprite1 = spritesAtlas.findRegion("walltiles_30x16_fromInvader_1of5");
        wallSectionSprite2 = spritesAtlas.findRegion("walltiles_30x16_fromInvader_2of5");
        wallSectionSprite3 = spritesAtlas.findRegion("walltiles_30x16_fromInvader_3of5");
        wallSectionSprite4 = spritesAtlas.findRegion("walltiles_30x16_fromInvader_4of5");
        wallSectionSprite5 = spritesAtlas.findRegion("walltiles_30x16_fromInvader_5of5");

        clawSlash = new Sprite(spritesAtlas.findRegion("claw-slash"));

        setDimensions(mapX * getWidth(),
                mapY * getHeight(),
                wallSectionSprite1.getRegionWidth(),
                wallSectionSprite1.getRegionHeight());
    }

	@Override
    public void drawSprite(final SpriteBatch batch,
                     final Viewport viewport,
                     final float delta,
                     final float alpha) {

        if (this.getHp() <= 0) {
            die();
            Game.instance().getSoundMan().playSealing();
            return;
        }

        TextureAtlas.AtlasRegion toDraw = null;

        switch ((int)(this.getHp() / 20)) {
            case 5:
                toDraw = wallSectionSprite1;
                break;
            case 4:
                toDraw = wallSectionSprite2;
                break;
            case 3:
                toDraw = wallSectionSprite3;
                break;
            case 2:
                toDraw = wallSectionSprite4;
                break;
            case 1:
                toDraw = wallSectionSprite5;
                break;
            default:
                break;
        }

        if (toDraw != null) {
            batch.draw(toDraw,
                    this.getX()  - viewport.viewportX - drawOffsetX,
                    this.getY() - viewport.viewportY - drawOffsetY);
        }

        if (clawSlashTimer > delta) {
            clawSlashTimer -= delta;
            batch.draw(clawSlash, getDrawX() - viewport.viewportX - drawOffsetX, getDrawY() - viewport.viewportY - drawOffsetY);
            Game.instance().getSoundMan().playEnemyAttack();
        }
    }

	@Override
	public float getSpeed() {
		return 0;
	}

	@Override
	public void physics(final float delta, final Array<Unit> activeCollidables, final Array<MapTile> inactiveCollidables) {
        // other things interacts with the wall but the wall does not move
    }

	@Override
	public void combat(final float delta, final Array<Unit> activeCollidables) {
		// walls don't attack
	}

    @Override
    public void receiveHit(final int hp, final Unit source) {
        super.receiveHit(hp, source);
        clawSlashTimer += 0.1f;
    }

}

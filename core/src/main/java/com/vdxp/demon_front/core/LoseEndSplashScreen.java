package com.vdxp.demon_front.core;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LoseEndSplashScreen extends GameEndSplashScreen {

	public LoseEndSplashScreen(final Registry r) {
		super(r);
	}

	@Override
	public void show() {
		super.show();
		r.game.getMusicMan().playBattleEndBad();
        r.game.getSoundMan().playGrowl();
    }

	@Override
	public void showMessage(final SpriteBatch batch, final BitmapFont bigFont, final BitmapFont normalFont, final BitmapFont smallFont) {

		drawCentred(normalFont, batch, "The demons swarm the city.", Registry.WIDTH / 2, 525);
		drawCentred(normalFont, batch, "Many innocent orcs perished...", Registry.WIDTH / 2, 475);

        drawCentred(normalFont, batch, "Here lies the big humies boss, commander of orcs.", Registry.WIDTH / 2, 375);
        drawCentred(normalFont, batch, "He was quite strong, but was still crumped in the end.", Registry.WIDTH / 2, 325);

        drawCentred(bigFont, batch, "You lose.", Registry.WIDTH / 2, 245);
	}

}

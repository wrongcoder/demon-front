package com.vdxp.demon_front.core;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LoseEndSplashScreen extends GameEndSplashScreen {

	public LoseEndSplashScreen(final Game game) {
		super(game);
	}

	@Override
	public void show() {
		super.show();
		game().getMusicMan().playBattleEndBad();
        Game.instance().getSoundMan().playGrowl();
    }

	@Override
	public void showMessage(final SpriteBatch batch, final BitmapFont bigFont, final BitmapFont normalFont, final BitmapFont smallFont) {

		Util.drawCentred("The demons swarm the city.", normalFont, batch, 525);
		Util.drawCentred("Many innocent orcs perished...", normalFont, batch, 475);

        Util.drawCentred("Here lies the big humies boss, commander of orcs.", normalFont, batch, 375);
        Util.drawCentred("He was quite strong, but was still crumped in the end.", normalFont, batch, 325);

        Util.drawCentred("You lose.", bigFont, batch, 245);
	}

}

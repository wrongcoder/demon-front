package com.vdxp.demon_front.core;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WinEndSplashScreen extends GameEndSplashScreen {

	public WinEndSplashScreen(final Game game) {
		super(game);
	}

	@Override
	public void show() {
		super.show();
		game().getMusicMan().playBattleEndGood();
        Game.instance().getSoundMan().playOrkSpawn();

    }

	@Override
	public void showMessage(final SpriteBatch batch, final BitmapFont bigFont, final BitmapFont normalFont, final BitmapFont smallFont) {

		Util.drawCentred("The demons have been pushed back.", normalFont, batch, 450);
		Util.drawCentred("The Orcs have been saved... for now.", normalFont, batch, 400);
		Util.drawCentred("Oi Oi Baus! You win!", bigFont, batch, 300);
	}

}

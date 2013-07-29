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
		game().getMusicMan().requestMusic(MusicMan.Mood.BattleEndBad, 1000);
	}

	@Override
	public void showMessage(final SpriteBatch batch, final BitmapFont bigFont, final BitmapFont normalFont, final BitmapFont smallFont) {
		Util.drawCentred("The demons swarm the city.", normalFont, batch, 450);
		Util.drawCentred("The once great city lies in ruins.", normalFont, batch, 400);
		Util.drawCentred("You lose.", bigFont, batch, 300);
	}

}

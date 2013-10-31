package com.vdxp.demon_front.core;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WinEndSplashScreen extends GameEndSplashScreen {

	public WinEndSplashScreen(final Registry r) {
		super(r);
	}

	@Override
	public void show() {
		super.show();
		r.game.getMusicMan().playBattleEndGood();
        r.game.getSoundMan().playOrkSpawn();

    }

	@Override
	public void showMessage(final SpriteBatch batch, final BitmapFont bigFont, final BitmapFont normalFont, final BitmapFont smallFont) {

		drawCentred(normalFont, batch, "The demons have been pushed back.", Registry.WIDTH / 2, 525);
		drawCentred(normalFont, batch, "The Orcs have been saved... for now.", Registry.WIDTH / 2, 475);
		drawCentred(bigFont, batch, "Oi oi boss! We won boss!", Registry.WIDTH / 2, 400);
        drawCentred(bigFont, batch, "We iz great right boss?", Registry.WIDTH / 2, 300);
	}

}

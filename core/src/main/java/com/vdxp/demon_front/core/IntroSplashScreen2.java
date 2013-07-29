package com.vdxp.demon_front.core;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class IntroSplashScreen2 extends IntroSplashScreen {

	public IntroSplashScreen2(final Game game) {
		super(game, new SpriteTestScreen(game));
	}

	@Override
	public void render(final float delta) {
		super.render(delta);

		final SpriteBatch batch = getBatch();
		final BitmapFont bigFont = getBigFont();
		final BitmapFont normalFont = getNormalFont();
		final BitmapFont smallFont = getSmallFont();

		batch.begin();
		Util.drawCentred("You have been hired", bigFont, batch, 450);
		Util.drawCentred("to lead the Orcs", bigFont, batch, 390);
		Util.drawCentred("to Victory!", bigFont, batch, 330);
        Util.drawCentred("Orcs will shuffle aimlessly unless commanded", smallFont, batch, 220);
        Util.drawCentred("Commands affect all orcs that the hero can clearly see", smallFont, batch, 200);
        Util.drawCentred("Destroy all the demon gates, but stay alive and don't let the demons enter your city", smallFont, batch, 180);
        batch.end();
	}

}

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
		Util.drawCentred("You have been hired", bigFont, batch, 480);
		Util.drawCentred("to lead the Orcs", bigFont, batch, 420);
		Util.drawCentred("to Victory!", bigFont, batch, 360);
        batch.end();
	}

}

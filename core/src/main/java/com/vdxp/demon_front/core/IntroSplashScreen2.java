package com.vdxp.demon_front.core;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class IntroSplashScreen2 extends IntroSplashScreen {

	public IntroSplashScreen2(final Registry r) {
		super(r, new SpriteTestScreen(r));
	}

	@Override
	public void render(final float delta) {
		super.render(delta);

		final SpriteBatch batch = getBatch();
		final BitmapFont bigFont = getBigFont();
		final BitmapFont normalFont = getNormalFont();
		final BitmapFont smallFont = getSmallFont();

		batch.begin();
		drawCentred(bigFont, batch, "You have been hired", Registry.WIDTH / 2, 480);
		drawCentred(bigFont, batch, "to lead the Orcs", Registry.WIDTH / 2, 420);
		drawCentred(bigFont, batch, "to Victory!", Registry.WIDTH / 2, 360);
        batch.end();
	}

}

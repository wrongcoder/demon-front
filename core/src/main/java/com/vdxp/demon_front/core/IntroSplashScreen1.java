package com.vdxp.demon_front.core;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class IntroSplashScreen1 extends IntroSplashScreen {

	public IntroSplashScreen1(final Registry r) {
		super(r, new IntroSplashScreen2(r));
	}

    @Override
    public void show() {
        r.game.getSoundMan().playIntro();
        super.show();
    }

	@Override
	public void render(final float delta) {
		super.render(delta);

		final SpriteBatch batch = getBatch();
		final BitmapFont bigFont = getBigFont();
		final BitmapFont normalFont = getNormalFont();
		final BitmapFont smallFont = getSmallFont();

		batch.begin();
		drawCentred(bigFont, batch, "The demon gates", Registry.WIDTH / 2, 450);
		drawCentred(bigFont, batch, "have opened!", Registry.WIDTH / 2, 370);
        batch.end();
	}
}

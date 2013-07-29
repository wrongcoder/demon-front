package com.vdxp.demon_front.core;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class IntroSplashScreen1 extends IntroSplashScreen {

	public IntroSplashScreen1(final Game game) {
		super(game, new IntroSplashScreen2(game));
	}

    @Override
    public void show(){

        Game.instance().getSoundMan().playIntro();
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
		Util.drawCentred("The demon gates", bigFont, batch, 420);
		Util.drawCentred("have opened!", bigFont, batch, 340);
        Util.drawCentred("Orcs will shuffle aimlessly unless commanded", smallFont, batch, 220);
        Util.drawCentred("Commands affect all orcs that the hero can clearly see", smallFont, batch, 200);
        Util.drawCentred("Destroy all the demon gates, but stay alive and don't let the demons enter your city", smallFont, batch, 180);


        batch.end();
	}
}

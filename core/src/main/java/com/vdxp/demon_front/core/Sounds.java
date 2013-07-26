package com.vdxp.demon_front.core;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

public class Sounds {

    private static String[] soundFiles = {
            "sfx/growl1.wav", "sfx/growl2.wav", "sfx/growl3.wav",
            "sfx/growl4.wav", "sfx/growl5.wav", "sfx/growl6.wav"
    };

    public static void Init (final AssetManager assetManager) {
        for (int i=0; i<soundFiles.length; i++) {
            assetManager.load(soundFiles[i],Sound.class);
        }
    }

    public static void playGrowl() {
        int whatToPlay = floor(Math.random() * soundFiles.length);


    }
}

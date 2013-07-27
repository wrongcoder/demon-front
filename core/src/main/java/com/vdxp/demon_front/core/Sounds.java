package com.vdxp.demon_front.core;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

public class Sounds {

    private Sound playing;

    private String[] growlFiles = {
            "sfx/growl1.wav", "sfx/growl2.wav", "sfx/growl3.wav",
            "sfx/growl4.wav", "sfx/growl5.wav", "sfx/growl6.wav"
    };

    private String[] enemiesAttackFiles = {
            "sfx/animal_melee_sound.wav"
    };

    private String[] humanAttackFiles = {
            "sfx/sword_sound.wav"
    };

    private String sealingSound = "sfx/explode1.ogg";

    public Sounds() {
        this.Init(Game.instance().assetManager());
    }

    public void Init (final AssetManager assetManager) {
        for (int i=0; i< growlFiles.length; i++) {
            assetManager.load(growlFiles[i],Sound.class);
        }

        for (int i=0; i< enemiesAttackFiles.length; i++) {
            assetManager.load(enemiesAttackFiles[i],Sound.class);
        }

        for (int i=0; i< humanAttackFiles.length; i++) {
            assetManager.load(humanAttackFiles[i],Sound.class);
        }

        assetManager.load(sealingSound,Sound.class);
    }

    private void playRandomInList(String[] list) {
        int whatToPlay = (int)Math.floor(Math.random() * list.length);

        playing = Game.instance().assetManager().get(list[whatToPlay]);
        playing.play();
    }

    public void playGrowl() {
        playRandomInList(growlFiles);
    }

    public void playHumanAttack() {
        playRandomInList(humanAttackFiles);
    }

    public void playEnemyAttack() {
        playRandomInList(enemiesAttackFiles);
    }

    public void playSealing() {
        playing = Game.instance().assetManager().get(sealingSound);
        playing.play();
    }
}

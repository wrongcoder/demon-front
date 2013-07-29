package com.vdxp.demon_front.core;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class Sounds {

    private Sound playing;
    private Music mp3Player;

    private String[] growlFiles = {
            "sfx/growl1.wav", "sfx/growl2.wav", "sfx/growl3.wav",
            "sfx/growl4.wav", "sfx/growl5.wav", "sfx/growl6.wav"
    };

    private String[] orkSpawnFiles = {
            "sfx/demonfront_orkspawn1.mp3", "sfx/demonfront_orkspawn2.mp3"
    };

    private String[] enemiesAttackFiles = {
            "sfx/animal_melee_sound.wav"
    };

    private String[] humanAttackFiles = {
            "sfx/sword_sound.wav"
    };

    private String sealingSound = "sfx/explode1.wav";
    private String intro = "sfx/demonfront_introVoiceOver.mp3";
    private String goNorth = "sfx/demonfront_goNorth.mp3";
    private String goSouth = "sfx/demonfront_goSouth.mp3";
    private String goWest = "sfx/demonfront_goWest.mp3";
    private String goEast = "sfx/demonfront_goEast.mp3";

    public Sounds() {
        this.Init(Game.instance().assetManager());
    }

    public void Init (final AssetManager assetManager) {
        for (int i=0; i< growlFiles.length; i++) {
            assetManager.load(growlFiles[i],Sound.class);
        }

        for (int i=0; i< orkSpawnFiles.length; i++) {
            assetManager.load(orkSpawnFiles[i],Sound.class);
        }

        for (int i=0; i< enemiesAttackFiles.length; i++) {
            assetManager.load(enemiesAttackFiles[i],Sound.class);
        }

        for (int i=0; i< humanAttackFiles.length; i++) {
            assetManager.load(humanAttackFiles[i],Sound.class);
        }

        assetManager.load(sealingSound,Sound.class);

        assetManager.load(goNorth,Music.class);
        assetManager.load(goSouth,Music.class);
        assetManager.load(goWest,Music.class);
        assetManager.load(goEast,Music.class);
        assetManager.load(intro,Music.class);
    }

    public void playIntro() {
        mp3Player = Game.instance().assetManager().get(intro);
        mp3Player.play();
    }

    public void playGoNorth() {
        mp3Player = Game.instance().assetManager().get(goNorth);
        mp3Player.setVolume(0.7f);
        mp3Player.play();
    }

    public void playGoSouth() {
        mp3Player = Game.instance().assetManager().get(goSouth);
        mp3Player.play();
    }

    public void playGoWest() {
        mp3Player = Game.instance().assetManager().get(goWest);
        mp3Player.play();
    }

    public void playGoEast() {
        mp3Player = Game.instance().assetManager().get(goEast);
        mp3Player.play();
    }

    private void playRandomInList(String[] list) {
        int whatToPlay = (int)Math.floor(Math.random() * list.length);

        playing = Game.instance().assetManager().get(list[whatToPlay]);
        playing.play();
    }

    public void playGrowl() {
        playRandomInList(growlFiles);
    }

    public void playOrkSpawn() {
        playRandomInList(orkSpawnFiles);
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

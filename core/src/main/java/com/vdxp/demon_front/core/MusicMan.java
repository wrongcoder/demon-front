package com.vdxp.demon_front.core;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;

import java.util.HashMap;
import java.util.Map;


public class MusicMan {

    private Music nowPlaying;
	private Music prevPlaying;
	private float faderTime;
	private Map<Mood, Float> moodChangeTendency;
	private float nowPlayingTime = 30;
	private Mood currentMood = null;

	private final AssetManager assetManager;

    private String UI_TitleScreen = "music/00_UI/06-No Silence Please-Ehren Starks.mp3";
    private String UI_StageIntro = "music/00_UI/10-Tunnel Systems-Ehren Starks.mp3";

    private String[] Calm = {
            "music/01_Calm/00_old_city_theme_3_96.mp3",
            "music/01_Calm/10-Lamento Di Tristano_Rotta-Daughters of Elvin.mp3",
            "music/01_Calm/02_swarm_96.mp3"
    };

    private String[] Agitation = {
            "music/02_Agitation/exotic_drums_0_96.mp3",
            "music/02_Agitation/radakan_moldan_chase_96.mp3"
    };

    private String[] Conflict = {
            "music/03_Conflict/Battle_96.mp3",
            "music/03_Conflict/12-Galdrbok-Daughters of Elvin.mp3",
            "music/03_Conflict/osn2u_96.mp3",
            "music/03_Conflict/regular_battle_96.mp3"
    };

    private String[] Impasse = {
            "music/04_Impasse/boss_theme_96.mp3",
            "music/04_Impasse/Brave_Solders_96.mp3",
            "music/04_Impasse/durante_vergine_tutto_amore_96.mp3",
            "music/04_Impasse/northindianbeat_96.mp3",
            "music/04_Impasse/radakan_inferno_0_96.mp3"
    };

    private String[] CliffHanger = {
            "music/05_CliffHanger/evil_temple_96.mp3"
    };

    private String[] Climax = {
            "music/06_Climax/Theme_of_Agrual_0_96.mp3"
    };

    private String BattleEnd_Bad = "music/07_BattleEnd/BadEnd_bleeding_out2_2_96.mp3";
    private String BattleEnd_Good = "music/07_BattleEnd/NeutralEnd_POL-battle-march-short_96.mp3";

    public MusicMan(final Registry r) {
	    faderTime = 0;
	    moodChangeTendency = new HashMap<Mood, Float>();
	    resetMoodChangeTendency(5);
	    this.assetManager = r.assetManager;

	    this.Init();
    }

    protected void loadMusicList(String[] list) {
        for (int i=0; i< list.length; i++) {
            assetManager.load(list[i],Music.class);
        }
    }

    private void Init() {

        loadMusicList(Calm);

        loadMusicList(Agitation);

        loadMusicList(Conflict);

        loadMusicList(Impasse);

        loadMusicList(CliffHanger);

        loadMusicList(Climax);

        assetManager.load(UI_TitleScreen,Music.class);
        assetManager.load(UI_StageIntro,Music.class);
        assetManager.load(BattleEnd_Bad,Music.class);
        assetManager.load(BattleEnd_Good,Music.class);
    }

    private void playRandomInList(String[] list) {
	    nowPlaying = getRandomInList(list);
        nowPlaying.play();
    }

	private Music getRandomInList(final String[] list) {
		final int whatToPlay = (int)Math.floor(Math.random() * list.length);
		return assetManager.get(list[whatToPlay]);
	}

	public void playCalm (){
        playRandomInList(Calm);
    }

    public void playAgitation() {
        playRandomInList(Agitation);
    }

    public void playConflict() {
        playRandomInList(Conflict);
    }

    public void playImpasse() {
        playRandomInList(Impasse);
    }

    public void playCliffHanger() {
        playRandomInList(CliffHanger);
    }

    public void playUITitleScreen() {
        nowPlaying = assetManager.get(UI_TitleScreen);
        nowPlaying.setVolume(0.3f);
        nowPlaying.play();
    }

    public void playUIStageIntro() {
        nowPlaying = assetManager.get(UI_StageIntro);
        nowPlaying.setVolume(0.3f);
        nowPlaying.play();
    }

    public void playBattleEndGood() {
        if (nowPlaying != null) {
            nowPlaying.stop();
        }
        nowPlaying = assetManager.get(BattleEnd_Good);
        nowPlaying.play();
	    nowPlayingTime = 1000;
	    resetMoodChangeTendency(60);
    }

    public void playBattleEndBad () {
        if (nowPlaying != null) {
            nowPlaying.stop();
        }
        nowPlaying = assetManager.get(BattleEnd_Bad);
        nowPlaying.play();
	    nowPlayingTime = 1000;
	    resetMoodChangeTendency(60);
    }

	public void stopPlaying() {
		if (prevPlaying != null) {
			prevPlaying.stop();
			prevPlaying = null;
		}
		if (nowPlaying != null) {
			nowPlaying.stop();
			nowPlaying = null;
		}
	}

	public void requestMusic(final Mood mood, final float delta) {
		if (prevPlaying != null) {
			final float totalFaderTime = 2.5f;
			faderTime += delta;
			final float fader = Math.min(faderTime / totalFaderTime, 1);
			prevPlaying.setVolume(1 - fader);
			if (nowPlaying != null) {
				nowPlaying.setVolume(fader);
			}
			if (faderTime > totalFaderTime) {
				faderTime = 0;
				prevPlaying.stop();
				prevPlaying = null;
			}
		}

		if (nowPlaying == null || !nowPlaying.isPlaying()) {
			currentMood = mood;
			nowPlaying = getMusicForMood(mood);
			nowPlaying.setVolume(1);
			nowPlaying.play();
			nowPlayingTime = mood.shortPlayTime ? 60 : 0;
			resetMoodChangeTendency(mood.shortPlayTime ? 5 : 0);
			return;
		}

		nowPlayingTime += delta;
		updateMoodChangeTendency(mood, delta);

		if (mood != currentMood && nowPlayingTime > 30 && moodChangeTendency.get(mood) > 5) {
			prevPlaying = nowPlaying;
			nowPlaying = getMusicForMood(mood);
			nowPlaying.play();
			nowPlayingTime = mood.shortPlayTime ? 60 : 0;
			resetMoodChangeTendency(mood.shortPlayTime ? 5 : 0);
			currentMood = mood;
		}
	}

	private void updateMoodChangeTendency(final Mood requestedMood, final float delta) {
		for (final Mood mood : Mood.values()) {
			if (mood == requestedMood) {
				moodChangeTendency.put(mood, moodChangeTendency.get(mood) + delta);
			} else {
				moodChangeTendency.put(mood, 0f);
			}
		}
	}

	private void resetMoodChangeTendency(final float init) {
		for (final Mood mood : Mood.values()) {
			moodChangeTendency.put(mood, init);
		}
	}

	private Music getMusicForMood(final Mood mood) {
		switch (mood) {
			case Calm:
				return getRandomInList(Calm);
			case Agitation:
				return getRandomInList(Agitation);
			case Conflict:
				return getRandomInList(Conflict);
			case Impasse:
				return getRandomInList(Impasse);
			case Cliffhanger:
				return getRandomInList(CliffHanger);
			case BattleEndGood:
				return assetManager.get(BattleEnd_Good);
			case BattleEndBad:
				return assetManager.get(BattleEnd_Bad);
		}
		return null;
	}

	public enum Mood {
		Calm(), Agitation(), Conflict(), Impasse(), Cliffhanger(), BattleEndGood(true), BattleEndBad(true);

		public final boolean shortPlayTime;

		Mood() {
			shortPlayTime = false;
		}

		Mood(final boolean shortPlayTime) {
			this.shortPlayTime = shortPlayTime;
		}
	}
}

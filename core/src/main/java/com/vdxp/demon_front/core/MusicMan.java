package com.vdxp.demon_front.core;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;


public class MusicMan {

    private com.badlogic.gdx.audio.Music playing;
    private AssetManager mAssetManager = null;

    private String UI_TitleScreen = "music/00_UI/00_TitleScreen_ehren-stark_noSilencePlease_magnatune.mp3";
    private String UI_StageIntro = "music/00_UI/01_StageIntro(whichisalsothetutorial)_ehren-stark_TunnelSystems_magnatune.mp3";

    private String[] Calm = {
            "music/01_Calm/00_old_city_theme_3.mp3",
            "music/01_Calm/01_daughters_LamentoDiTristano_magnatune.mp3",
            "music/01_Calm/02_swarm.mp3"
    };

    private String[] Agitation = {
            "music/02_Agitation/battle theme.mp3",
            "music/02_Agitation/exotic drums_0.mp3",
            "music/02_Agitation/radakan - moldan chase.mp3"
    };

    private String[] Conflict = {
            "music/03_Conflict/Battle.mp3",
            "music/03_Conflict/daughters_galdrbok_magnatune.mp3",
            "music/03_Conflict/osn2u.mp3",
            "music/03_Conflict/regular battle.mp3"
    };

    private String[] Impasse = {
            "music/04_Impasse/boss theme.mp3",
            "music/04_Impasse/Brave Solders.mp3",
            "music/04_Impasse/durante - vergine tutto amore.mp3",
            "music/04_Impasse/northindianbeat.mp3",
            "music/04_Impasse/radakan - inferno_0.mp3"
    };

    private String[] CliffHanger = {
            "music/05_CliffHanger/evil_temple.mp3"
    };

    private String[] Climax = {
            "music/06_Climax/Theme of Agrual_0.mp3"
    };

    private String BattleEnd_Bad = "music/07_BattleEnd/NeutralEnd_POL-battle-march-short.wav";
    private String BattleEnd_Good = "music/07_BattleEnd/NeutralEnd_POL-battle-march-short.wav";

    public MusicMan(){
       this.Init(Game.instance().assetManager());
    }

    protected void loadMusicList(String[] list) {
        for (int i=0; i< list.length; i++) {
            mAssetManager.load(list[i],Music.class);
        }
    }

    public void Init (final AssetManager assetManager) {

        mAssetManager = assetManager;

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
        int whatToPlay = (int)Math.floor(Math.random() * list.length);

        playing = Game.instance().assetManager().get(list[whatToPlay]);
        playing.play();
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
        playing = Game.instance().assetManager().get(UI_TitleScreen);
        playing.play();
    }

    public void playUIStageIntro() {
        playing = Game.instance().assetManager().get(UI_StageIntro);
        playing.play();
    }

    public void playBattleEndGood() {
        playing = Game.instance().assetManager().get(BattleEnd_Good);
        playing.play();
    }

    public void playBattleEndBad () {
        playing = Game.instance().assetManager().get(BattleEnd_Bad);
        playing.play();
    }
}
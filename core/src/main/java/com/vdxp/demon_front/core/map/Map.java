package com.vdxp.demon_front.core.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.vdxp.demon_front.core.Asset;
import com.vdxp.demon_front.core.Drawable;
import com.vdxp.demon_front.core.Game;
import com.vdxp.demon_front.core.units.DemonGate;
import com.vdxp.demon_front.core.units.Unit;
import com.vdxp.demon_front.core.units.WallSection;

import java.util.HashSet;
import java.util.Set;

public class Map {

    private String asciiSourcePath = null;
    private String asciiSource = null;
    private Set<MapTile> nonCollidableMapTiles = new HashSet<MapTile>();
    private Set<MapTile> collidableMapTiles = new HashSet<MapTile>();
    private Set<Unit> units = new HashSet<Unit>();

    private int tileMapSizeX = 0;
    private int tileMapSizeY = 0;

    // hack: should determine the map size by reading the
    // number of lines, but too tired now for that
    private static final int TILE_MAP_TOP = 59;
    private static final int TILE_MAP_LEFT = 0;

    public Map() {

    }

    public Map(String asciiSourcePath) {
        this.Init(asciiSourcePath);
    }

    public void Init(String asciiSourcePath) {
        this.asciiSourcePath = asciiSourcePath;

        asciiSource =
                Gdx.files.internal(asciiSourcePath).readString();

        // Now create all the maptiles including sprite references
        final TextureAtlas spritesAtlas = Game.instance().assetManager().<TextureAtlas>get(Asset.spritesAtlas);


        int currX = TILE_MAP_LEFT;
        int currY = TILE_MAP_TOP;
        char currChar = '*';

        for (int i=0;i<asciiSource.length();i++) {

            currChar = asciiSource.charAt(i);

            switch (currChar) {
                case '*':
                    // add a "blank tile"
                    break;
                case '\n':
                    currY--;
                    currX = TILE_MAP_LEFT;
                    break;
                case 'L':
                    if (Math.random() > 0.9f) {
                        nonCollidableMapTiles.add(new GrassWLeaveTile(spritesAtlas, currX, currY));
                    } else {
                        nonCollidableMapTiles.add(new GrassTile(spritesAtlas, currX, currY));
                    }
                    break;
                case 't':
                    collidableMapTiles.add(new TreeTile(spritesAtlas, currX, currY));
                    break;
                case 'T':
                    collidableMapTiles.add(new TreeTile(spritesAtlas, currX, currY));
                    break;
                case 'M':
                    collidableMapTiles.add(new MountainTile(spritesAtlas, currX, currY));
                    break;
                case 'd':
                    units.add(new DemonGate(spritesAtlas, currX, currY));
                    break;
                case 'w':
                    units.add(new WallSection(spritesAtlas, currX, currY));
                    break;
                case 'F':
                    if ((currX*currY) % 2 == 0) {
                        nonCollidableMapTiles.add(new FogOfWarTile(spritesAtlas, currX, currY));
                    } else {
                        nonCollidableMapTiles.add(new FogOfWarWFaceTile(spritesAtlas, currX, currY));
                    }
                    break;
            }

            currX++;
        }

    }

    public Set<MapTile> getNonCollidableMapTiles() {
        return nonCollidableMapTiles;
    }

    public Set<MapTile> getCollidableMapTiles() {
        return collidableMapTiles;
    }

    public Set<Unit> getUnits() {
        return units;
    }

    public static float getGameXinPixel(int gameXinMapTile) {
        return gameXinMapTile * 32;
    }

    public static float getGameYinPixel(int gameYinMapTile) {
        return gameYinMapTile * 32;
    }

    public static int getDistInTile(double distInPixel) {
        return (int) (distInPixel / 32);
    }
}

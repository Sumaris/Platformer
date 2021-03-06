package com.alfredradu.platformer.levels;

import android.content.res.AssetManager;
import android.util.SparseArray;

import com.alfredradu.platformer.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Level2 extends LevelData{
    private final SparseArray<String> _tileIdToSpriteName = new SparseArray<>();
    public Level2() throws IOException {
        _mapName = "level2";
        _tileIdToSpriteName.put(0, NULLSPRITE);
        _tileIdToSpriteName.put(1, PLAYER);
        _tileIdToSpriteName.put(2, ENEMY);
        _tileIdToSpriteName.put(3, SPEARS);
        _tileIdToSpriteName.put(4, "wavesnow_roundleft");
        _tileIdToSpriteName.put(5, "wavesnow_roundright");
        _tileIdToSpriteName.put(6, "wavesnow_square");
        _tileIdToSpriteName.put(7, "wavesnow_uproundleft");
        _tileIdToSpriteName.put(8, "wavesnow_uproundright");
        _tileIdToSpriteName.put(9, "wavesnow_icesquare");
        _tileIdToSpriteName.put(10, HEART);
        _tileIdToSpriteName.put(11, COIN);

        AssetManager am = Game.getCont().getAssets();
        InputStream is = am.open("maps/"+_mapName+".txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        int row = 0;
        int column = 0;
        String line;
        ArrayList<String[]> temp = new ArrayList<>();
        try {
            while ((line = br.readLine()) != null) {
                String[] str = line.split(",");
                row++;
                if (str.length > column){
                    column = str.length;
                }
                temp.add(str);
            }
        } catch(Exception e) {
            System.out.println(e);
        }
        _tiles = new int[row][column];
        for (int i = 0; i < row; i++){
            for (int j = 0; j < column; j++){
                _tiles[i][j] = Integer.parseInt(temp.get(i)[j]);
            }
        }
        updateLevelDimensions();
    }
    @Override
    public String getSpriteName(int tileType) {
        final String fileName = _tileIdToSpriteName.get(tileType);
        if (fileName != null){
            return fileName;
        }
        return NULLSPRITE;
    }
}

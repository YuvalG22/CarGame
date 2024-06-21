package com.example.cargame.Logic;
import java.util.Arrays;
import java.util.Random;

public class GameManager {
    private int lifeCount;
    private int collisions = 0;
    private int [][] stoneMat = new int[][]{{0,0,0},
                                            {0,0,0},
                                            {0,0,0},
                                            {0,0,0},
                                            {0,0,0}};
private Random random;
    public GameManager(int lifeCount) {
        this.lifeCount = lifeCount;
    }

    public int getLifeCount(){
        return lifeCount;
    }

    public int getCollisions() {
        return collisions;
    }

    public boolean isGameLost(){
        return getLifeCount() == getCollisions();
    }

    public void updateStones(){
        for (int i = stoneMat.length - 1; i > 0; i--) {
            for (int j = 0; j < stoneMat[i].length; j++) {
                stoneMat[i][j] = stoneMat[i - 1][j];
            }
        }
        for (int j = 0; j < stoneMat[0].length; j++) {
            stoneMat[0][j] = 0;
        }
        random = new Random();
        int randomStone = random.nextInt(3);
        stoneMat[0][randomStone] = 1;
    }

    public void checkCollision(boolean collision){
        if(collision){
            collisions++;
        }
    }

    public int[][] getStoneMat() {
        return stoneMat;
    }
}


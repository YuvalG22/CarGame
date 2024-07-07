package com.example.cargame.Logic;
import java.util.Arrays;
import java.util.Random;

public class GameManager {
    private int lifeCount;
    private int collisions = 0;
    private int coinsCount = 0;
    private int [][] stoneMat = new int[][]{{0,0,0,0,0},
                                            {0,0,0,0,0},
                                            {0,0,0,0,0},
                                            {0,0,0,0,0},
                                            {0,0,0,0,0},
                                            {0,0,0,0,0}};

    private int [][] coinsMat = new int[][]{{0,0,0,0,0},
                                            {0,0,0,0,0},
                                            {0,0,0,0,0},
                                            {0,0,0,0,0},
                                            {0,0,0,0,0},
                                            {0,0,0,0,0}};
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
        int randomStone = random.nextInt(5);
        stoneMat[0][randomStone] = 1;

        for (int i = coinsMat.length - 1; i > 0; i--) {
            for (int j = 0; j < coinsMat[i].length; j++) {
                coinsMat[i][j] = coinsMat[i - 1][j];
            }
        }
        for (int j = 0; j < coinsMat[0].length; j++) {
            coinsMat[0][j] = 0;
        }
        random = new Random();
        if(Math.random() < 0.5) {
            int randomCoin = random.nextInt(5);
            if(stoneMat[0][randomCoin] != 1) {
                coinsMat[0][randomCoin] = 1;
            }
        }
    }

    public void checkCollision(boolean collision){
        if(collision){
            collisions++;
        }
    }

    public void addCoin(boolean coinCollected){
        if(coinCollected){
            coinsCount++;
        }
    }

    public int[][] getStoneMat() {
        return stoneMat;
    }

    public int[][] getCoinsMat(){
        return coinsMat;
    }

    public int getCoinsCount() {
        return coinsCount;
    }
}


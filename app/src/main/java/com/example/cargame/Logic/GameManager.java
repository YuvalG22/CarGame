package com.example.cargame.Logic;
import java.util.Arrays;
import java.util.Random;

public class GameManager {
    private int lifeCount;
    private int collisions = 0;
    private int [][] stoneMat = new int[4][3];

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

    public void initMat(){
        for (int[] ints : stoneMat) {
            Arrays.fill(ints, 0);
        }
    }

    public void updateStones(){
        Random rand = new Random();
        int randomStone = rand.nextInt(2) + 1;
        stoneMat[0][randomStone] = 1;
        for (int i = stoneMat.length - 1; i > 0; i--) {
            for (int j = 0; j < stoneMat[i].length; j++) {
                stoneMat[i][j] = stoneMat[i - 1][j];
            }
        }
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


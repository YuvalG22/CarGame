package com.example.cargame.Logic;

public class GameManager {
    private int lifeCount;
    private int collisions = 0;

    public GameManager(int lifeCount) {
        this.lifeCount = lifeCount;
    }

    public int getLifeCount(){
        return lifeCount;
    }

    public int getCollisions() {
        return collisions;
    }

    public boolean isLifeZero(){
        return getLifeCount() == getCollisions();
    }

    public void checkCollision(boolean collision){
        if(collision){
            collisions++;
        }
    }
}


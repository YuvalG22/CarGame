package com.example.cargame;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cargame.Logic.GameManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.Random;
public class MainActivity extends AppCompatActivity {
    private static final long DELAY = 1000L;
    private FloatingActionButton main_BTN_arrow_left;
    private FloatingActionButton main_BTN_arrow_right;
    private AppCompatImageView main_IMG_car_left;
    private AppCompatImageView main_IMG_car_center;
    private AppCompatImageView main_IMG_car_right;
    private AppCompatImageView[][] stonesMat;
    private AppCompatImageView main_IMG_stone_left1;
    private AppCompatImageView main_IMG_stone_left2;
    private AppCompatImageView main_IMG_stone_left3;
    private AppCompatImageView main_IMG_stone_left4;
    private AppCompatImageView main_IMG_stone_left5;
    private AppCompatImageView main_IMG_stone_right1;
    private AppCompatImageView main_IMG_stone_right2;
    private AppCompatImageView main_IMG_stone_right3;
    private AppCompatImageView main_IMG_stone_right4;
    private AppCompatImageView main_IMG_stone_right5;
    private AppCompatImageView main_IMG_stone_center1;
    private AppCompatImageView main_IMG_stone_center2;
    private AppCompatImageView main_IMG_stone_center3;
    private AppCompatImageView main_IMG_stone_center4;
    private AppCompatImageView main_IMG_stone_center5;
    private AppCompatImageView[] main_IMG_hearts;
    private GameManager gameManager;
    private ToastVibrate toastVibrate;
    final Handler handler = new Handler();
    private boolean isRunning = true;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(isRunning) {
                handler.postDelayed(this, DELAY);
                gameManager.updateStones();
                checkCollisions();
                refreshUI();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        toastVibrate = new ToastVibrate(this);
        gameManager = new GameManager(main_IMG_hearts.length);
        initViews();
        handler.postDelayed(runnable, 0);
    }

    private void initViews(){
        main_IMG_car_left.setVisibility(View.INVISIBLE);
        main_IMG_car_center.setVisibility(View.VISIBLE);
        main_IMG_car_right.setVisibility(View.INVISIBLE);
        for(int i = 0; i < stonesMat.length; i++){
            for(int j = 0; j < stonesMat[i].length; j++) {
                stonesMat[i][j].setVisibility(View.INVISIBLE);
            }
        }
        main_BTN_arrow_right.setOnClickListener(view -> moveCarRight());
        main_BTN_arrow_left.setOnClickListener(view -> moveCarLeft());
    }

    private void refreshUI(){
        if(gameManager.isGameLost()){
            changeActivity("😭 GAME OVER");
        }
        else {
            for (int i = 0; i < stonesMat.length; i++) {
                for (int j = 0; j < stonesMat[i].length; j++) {
                    if (gameManager.getStoneMat()[i][j] == 1) {
                        stonesMat[i][j].setVisibility(View.VISIBLE);
                    } else {
                        stonesMat[i][j].setVisibility(View.INVISIBLE);
                    }
                }
            }
            if(gameManager.getCollisions() != 0){
                main_IMG_hearts[main_IMG_hearts.length - gameManager.getCollisions()].setVisibility(View.INVISIBLE);
            }
        }
    }

    private void changeActivity(String status) {
        Intent scoreIntent = new Intent(this, ScoreActivity.class);
        scoreIntent.putExtra(ScoreActivity.KEY_STATUS, status);
        startActivity(scoreIntent);
        finish();
    }

    private void moveCarLeft() {
        if(main_IMG_car_center.getVisibility() == View.VISIBLE){
            main_IMG_car_center.setVisibility(View.INVISIBLE);
            main_IMG_car_left.setVisibility(View.VISIBLE);
        }
        else if(main_IMG_car_right.getVisibility() == View.VISIBLE) {
            main_IMG_car_center.setVisibility(View.VISIBLE);
            main_IMG_car_right.setVisibility(View.INVISIBLE);
        }
    }

    private void moveCarRight(){
        if(main_IMG_car_center.getVisibility() == View.VISIBLE){
            main_IMG_car_center.setVisibility(View.INVISIBLE);
            main_IMG_car_right.setVisibility(View.VISIBLE);
        }
        else if(main_IMG_car_left.getVisibility() == View.VISIBLE){
            main_IMG_car_center.setVisibility(View.VISIBLE);
            main_IMG_car_left.setVisibility(View.INVISIBLE);
        }
    }

    private void checkCollisions(){
        boolean collisionDetected = false;
        if(main_IMG_car_center.getVisibility() == View.VISIBLE && stonesMat[3][1].getVisibility() == View.VISIBLE){
            collisionDetected = true;
            toastVibrate.toastAndVibrate("Collision!");
        }
        else if(main_IMG_car_left.getVisibility() == View.VISIBLE && stonesMat[3][0].getVisibility() == View.VISIBLE){
            collisionDetected = true;
            toastVibrate.toastAndVibrate("Collision!");
        }
        else if(main_IMG_car_right.getVisibility() == View.VISIBLE && stonesMat[3][2].getVisibility() == View.VISIBLE){
            collisionDetected = true;
            toastVibrate.toastAndVibrate("Collision!");
        }
        if(collisionDetected){
            gameManager.checkCollision(collisionDetected);
        }
        if(gameManager.getCollisions() != 0){
            main_IMG_hearts[main_IMG_hearts.length - gameManager.getCollisions()].setVisibility(View.INVISIBLE);
        }
        if (gameManager.getCollisions() == main_IMG_hearts.length) {
            gameOver();
        }
    }

    private void gameOver(){
        isRunning = false;
        handler.removeCallbacks(runnable);
    }

    private void findViews(){
        main_IMG_hearts = new AppCompatImageView[]{findViewById(R.id.main_IMG_heart1), findViewById(R.id.main_IMG_heart2), findViewById(R.id.main_IMG_heart3)};
        stonesMat = new AppCompatImageView[][]{{findViewById(R.id.main_IMG_stone_left1), findViewById(R.id.main_IMG_stone_center1), findViewById(R.id.main_IMG_stone_right1)},
                                               {findViewById(R.id.main_IMG_stone_left2), findViewById(R.id.main_IMG_stone_center2), findViewById(R.id.main_IMG_stone_right2)},
                                               {findViewById(R.id.main_IMG_stone_left3), findViewById(R.id.main_IMG_stone_center3), findViewById(R.id.main_IMG_stone_right3)},
                                               {findViewById(R.id.main_IMG_stone_left4), findViewById(R.id.main_IMG_stone_center4), findViewById(R.id.main_IMG_stone_right4)},
                                               {findViewById(R.id.main_IMG_stone_left5), findViewById(R.id.main_IMG_stone_center5), findViewById(R.id.main_IMG_stone_right5)}};
        main_BTN_arrow_right = findViewById(R.id.main_BTN_arrow_right);
        main_BTN_arrow_left = findViewById(R.id.main_BTN_arrow_left);
        main_IMG_car_center = findViewById(R.id.main_IMG_car_center);
        main_IMG_car_right = findViewById(R.id.main_IMG_car_right);
        main_IMG_car_left = findViewById(R.id.main_IMG_car_left);
    }
}
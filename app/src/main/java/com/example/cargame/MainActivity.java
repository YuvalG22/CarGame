package com.example.cargame;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;

import com.example.cargame.Interfaces.LocationCallack;
import com.example.cargame.Interfaces.MoveCallback;
import com.example.cargame.Logic.GameManager;
import com.example.cargame.Logic.Record;
import com.example.cargame.Logic.RecordsList;
import com.example.cargame.Utilities.MoveSensors;
import com.example.cargame.Utilities.SharedPreferencesManager;
import com.example.cargame.Utilities.SoundPlayer2;
import com.example.cargame.Utilities.ToastVibrate;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    private long delay = 1000L;
    private static final long delay_fast = 500L;
    private MaterialTextView main_LBL_coins;
    private AppCompatImageView main_BTN_arrow_left;
    private AppCompatImageView main_BTN_arrow_right;
    private AppCompatImageView[] main_IMG_cars;
    private AppCompatImageView[][] stonesMat;
    private AppCompatImageView[][] coinsMat;
    private AppCompatImageView[] main_IMG_hearts;
    private FusedLocationProviderClient fusedLocationProviderClient;
    MoveSensors moveSensors;
    private Gson gson;
    private GameManager gameManager;
    private ToastVibrate toastVibrate;
    private SoundPlayer2 soundPlayer;
    final Handler handler = new Handler();
    private boolean isSensors;
    private boolean isRunning = true;
    private boolean isRunnablePosted;
    Runnable runnable;
    private void startGame(long delay) {
        runnable = new Runnable() {
            @Override
            public void run() {
                if (isRunning) {
                    handler.postDelayed(this, delay);
                    gameManager.updateStones();
                    checkCollisions();
                    refreshUI();
                }
            }
        };
        handler.postDelayed(runnable, 0);
        isRunnablePosted = true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        gson = new Gson();
        Intent previousActivity = getIntent();
        Bundle extras = previousActivity.getExtras();
        boolean isFast = extras.getBoolean("SPEED", false);
        isSensors = extras.getBoolean("MODE", false);
        if(isFast) delay = delay_fast;
        if(isSensors){
            initMoveDetector();
        }
        findViews();
        soundPlayer = new SoundPlayer2(this);
        toastVibrate = new ToastVibrate(this);
        gameManager = new GameManager(main_IMG_hearts.length);
        initViews();
        startGame(delay);
    }

    private void initViews(){
        main_IMG_cars[0].setVisibility(View.INVISIBLE);
        main_IMG_cars[1].setVisibility(View.INVISIBLE);
        main_IMG_cars[2].setVisibility(View.VISIBLE);
        main_IMG_cars[3].setVisibility(View.INVISIBLE);
        main_IMG_cars[4].setVisibility(View.INVISIBLE);
        for(int i = 0; i < stonesMat.length; i++){
            for(int j = 0; j < stonesMat[i].length; j++) {
                stonesMat[i][j].setVisibility(View.INVISIBLE);
            }
        }
        for(int i = 0; i < coinsMat.length; i++){
            for(int j = 0; j < coinsMat[i].length; j++) {
                coinsMat[i][j].setVisibility(View.INVISIBLE);
            }
        }
        if(isSensors){
            main_BTN_arrow_right.setVisibility(View.INVISIBLE);
            main_BTN_arrow_left.setVisibility(View.INVISIBLE);
            moveSensors.start();
        }
        else {
            main_BTN_arrow_right.setOnClickListener(view -> moveCarRight());
            main_BTN_arrow_left.setOnClickListener(view -> moveCarLeft());
        }
    }

    private void initMoveDetector() {
        moveSensors = new MoveSensors(this,
                new MoveCallback() {
                    @Override
                    public void moveXRight() {
                        moveCarRight();
                    }

                    @Override
                    public void moveXLeft() {
                        moveCarLeft();
                    }
                }
        );
    }

    private void refreshUI(){
        if(gameManager.isGameLost()){
            changeActivity("😭 GAME OVER", gameManager.getCoinsCount());
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
            for (int i = 0; i < coinsMat.length; i++) {
                for (int j = 0; j < coinsMat[i].length; j++) {
                    if (gameManager.getCoinsMat()[i][j] == 1) {
                        coinsMat[i][j].setVisibility(View.VISIBLE);
                    } else {
                        coinsMat[i][j].setVisibility(View.INVISIBLE);
                    }
                }
            }
            if(gameManager.getCollisions() != 0){
                main_IMG_hearts[main_IMG_hearts.length - gameManager.getCollisions()].setVisibility(View.INVISIBLE);
            }
        }
    }

    private void changeActivity(String status, int coins) {
        Intent scoreIntent = new Intent(this, ScoreActivity.class);
        Bundle extras = new Bundle();
        extras.putString(ScoreActivity.KEY_STATUS, status);
        extras.putInt("COINS", coins);
        scoreIntent.putExtras(extras);
        startActivity(scoreIntent);
        finish();
    }

    private void moveCarLeft() {
        if(main_IMG_cars[2].getVisibility() == View.VISIBLE){
            main_IMG_cars[2].setVisibility(View.INVISIBLE);
            main_IMG_cars[1].setVisibility(View.VISIBLE);
        }
        else if(main_IMG_cars[1].getVisibility() == View.VISIBLE){
            main_IMG_cars[1].setVisibility(View.INVISIBLE);
            main_IMG_cars[0].setVisibility(View.VISIBLE);
        }
        else if(main_IMG_cars[3].getVisibility() == View.VISIBLE) {
            main_IMG_cars[3].setVisibility(View.INVISIBLE);
            main_IMG_cars[2].setVisibility(View.VISIBLE);
        }
        else if(main_IMG_cars[4].getVisibility() == View.VISIBLE) {
            main_IMG_cars[4].setVisibility(View.INVISIBLE);
            main_IMG_cars[3].setVisibility(View.VISIBLE);
        }
    }

    private void moveCarRight(){
        if(main_IMG_cars[2].getVisibility() == View.VISIBLE){
            main_IMG_cars[2].setVisibility(View.INVISIBLE);
            main_IMG_cars[3].setVisibility(View.VISIBLE);
        }
        else if(main_IMG_cars[1].getVisibility() == View.VISIBLE){
            main_IMG_cars[1].setVisibility(View.INVISIBLE);
            main_IMG_cars[2].setVisibility(View.VISIBLE);
        }
        else if(main_IMG_cars[3].getVisibility() == View.VISIBLE) {
            main_IMG_cars[3].setVisibility(View.INVISIBLE);
            main_IMG_cars[4].setVisibility(View.VISIBLE);
        }
        else if(main_IMG_cars[0].getVisibility() == View.VISIBLE) {
            main_IMG_cars[0].setVisibility(View.INVISIBLE);
            main_IMG_cars[1].setVisibility(View.VISIBLE);
        }
    }

    private void checkCollisions(){
        boolean collisionDetected = false;
        boolean coinCollected = false;
        for (int i = 0; i < main_IMG_cars.length; i++) {
            if (main_IMG_cars[i].getVisibility() == View.VISIBLE && stonesMat[4][i].getVisibility() == View.VISIBLE) {
                collisionDetected = true;
                toastVibrate.toastAndVibrate("Collision!");
                soundPlayer.playCrashSound();
                break;
            }
        }
        for (int i = 0; i < main_IMG_cars.length; i++) {
            if (main_IMG_cars[i].getVisibility() == View.VISIBLE && coinsMat[4][i].getVisibility() == View.VISIBLE) {
                coinCollected = true;
                break;
            }
        }
        if(collisionDetected){
            gameManager.checkCollision(collisionDetected);
        }
        if(coinCollected){
            gameManager.addCoin(coinCollected);
            main_LBL_coins.setText(String.valueOf(gameManager.getCoinsCount()));
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
        getCurrentLocation(new LocationCallack() {
            @Override
            public void onLocationResult(double latitude, double longitude) {
                String name = "Yuval";
                int score = gameManager.getCoinsCount();
                Record record = new Record(name, score, latitude, longitude);
                String recordAsJson = SharedPreferencesManager.getInstance().getString("records", "");
                RecordsList recordsList;
                if(!recordAsJson.isEmpty()) {
                    recordsList = gson.fromJson(recordAsJson, RecordsList.class);
                }
                else{
                    recordsList = new RecordsList();
                }
                recordsList.addRecord(record);
                String toJson = gson.toJson(recordsList);
                SharedPreferencesManager.getInstance().putString("records", toJson);
            }
        });
        /*String name = "Yuval";
        int score = gameManager.getCoinsCount();
        Record record = new Record(name, score);
        String recordAsJson = SharedPreferencesManager.getInstance().getString("records", "");
        RecordsList recordsList;
        if(!recordAsJson.isEmpty()) {
            recordsList = gson.fromJson(recordAsJson, RecordsList.class);
        }
        else{
            recordsList = new RecordsList();
        }
        recordsList.addRecord(record);
        String toJson = gson.toJson(recordsList);
        SharedPreferencesManager.getInstance().putString("records", toJson);*/
    }

    private void findViews(){
        main_IMG_hearts = new AppCompatImageView[]{findViewById(R.id.main_IMG_heart1), findViewById(R.id.main_IMG_heart2), findViewById(R.id.main_IMG_heart3)};

        stonesMat = new AppCompatImageView[][]{{findViewById(R.id.main_IMG_stone_left1), findViewById(R.id.main_IMG_stone_leftCenter1), findViewById(R.id.main_IMG_stone_center1), findViewById(R.id.main_IMG_stone_rightCenter1), findViewById(R.id.main_IMG_stone_right1)},
                                               {findViewById(R.id.main_IMG_stone_left2), findViewById(R.id.main_IMG_stone_leftCenter2), findViewById(R.id.main_IMG_stone_center2), findViewById(R.id.main_IMG_stone_rightCenter2), findViewById(R.id.main_IMG_stone_right2)},
                                               {findViewById(R.id.main_IMG_stone_left3), findViewById(R.id.main_IMG_stone_leftCenter3), findViewById(R.id.main_IMG_stone_center3), findViewById(R.id.main_IMG_stone_rightCenter3), findViewById(R.id.main_IMG_stone_right3)},
                                               {findViewById(R.id.main_IMG_stone_left4), findViewById(R.id.main_IMG_stone_leftCenter4), findViewById(R.id.main_IMG_stone_center4), findViewById(R.id.main_IMG_stone_rightCenter4), findViewById(R.id.main_IMG_stone_right4)},
                                               {findViewById(R.id.main_IMG_stone_left5), findViewById(R.id.main_IMG_stone_leftCenter5), findViewById(R.id.main_IMG_stone_center5), findViewById(R.id.main_IMG_stone_rightCenter5), findViewById(R.id.main_IMG_stone_right5)},
                                               {findViewById(R.id.main_IMG_stone_left6), findViewById(R.id.main_IMG_stone_leftCenter6), findViewById(R.id.main_IMG_stone_center6), findViewById(R.id.main_IMG_stone_rightCenter6), findViewById(R.id.main_IMG_stone_right6)}};


        coinsMat = new AppCompatImageView[][]{{findViewById(R.id.main_IMG_coin_left1), findViewById(R.id.main_IMG_coin_leftCenter1), findViewById(R.id.main_IMG_coin_center1), findViewById(R.id.main_IMG_coin_rightCenter1), findViewById(R.id.main_IMG_coin_right1)},
                                              {findViewById(R.id.main_IMG_coin_left2), findViewById(R.id.main_IMG_coin_leftCenter2), findViewById(R.id.main_IMG_coin_center2), findViewById(R.id.main_IMG_coin_rightCenter2), findViewById(R.id.main_IMG_coin_right2)},
                                              {findViewById(R.id.main_IMG_coin_left3), findViewById(R.id.main_IMG_coin_leftCenter3), findViewById(R.id.main_IMG_coin_center3), findViewById(R.id.main_IMG_coin_rightCenter3), findViewById(R.id.main_IMG_coin_right3)},
                                              {findViewById(R.id.main_IMG_coin_left4), findViewById(R.id.main_IMG_coin_leftCenter4), findViewById(R.id.main_IMG_coin_center4), findViewById(R.id.main_IMG_coin_rightCenter4), findViewById(R.id.main_IMG_coin_right4)},
                                              {findViewById(R.id.main_IMG_coin_left5), findViewById(R.id.main_IMG_coin_leftCenter5), findViewById(R.id.main_IMG_coin_center5), findViewById(R.id.main_IMG_coin_rightCenter5), findViewById(R.id.main_IMG_coin_right5)},
                                              {findViewById(R.id.main_IMG_coin_left6), findViewById(R.id.main_IMG_coin_leftCenter6), findViewById(R.id.main_IMG_coin_center6), findViewById(R.id.main_IMG_coin_rightCenter6), findViewById(R.id.main_IMG_coin_right6)}};


        main_IMG_cars = new AppCompatImageView[]{findViewById(R.id.main_IMG_car_left), findViewById(R.id.main_IMG_car_leftCenter), findViewById(R.id.main_IMG_car_center), findViewById(R.id.main_IMG_car_rightCenter), findViewById(R.id.main_IMG_car_right)};
        main_BTN_arrow_right = findViewById(R.id.main_BTN_arrow_right);
        main_BTN_arrow_left = findViewById(R.id.main_BTN_arrow_left);
        main_LBL_coins = findViewById(R.id.main_LBL_coins);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isRunning = false;
        handler.removeCallbacks(runnable);
        isRunnablePosted = false;
        if(moveSensors != null) {
            moveSensors.stop();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isRunning = true;
        if (!isRunnablePosted) {
            handler.postDelayed(runnable, 0);
            isRunnablePosted = true;
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        if (soundPlayer != null) {
            soundPlayer.release();
        }
    }

    public void getCurrentLocation(LocationCallack callack) {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    if(callack != null){
                        callack.onLocationResult(currentLocation.latitude, currentLocation.longitude);
                    }
                }
            }
        });
    }
}
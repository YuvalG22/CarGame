package com.example.cargame;

import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton main_BTN_arrow_left;
    private FloatingActionButton main_BTN_arrow_right;
    private AppCompatImageView main_IMG_car_left;
    private AppCompatImageView main_IMG_car_center;
    private AppCompatImageView main_IMG_car_right;
    private AppCompatImageView main_IMG_stone_left1;
    private AppCompatImageView main_IMG_stone_left2;
    private AppCompatImageView main_IMG_stone_left3;
    private AppCompatImageView main_IMG_stone_left4;
    private AppCompatImageView main_IMG_stone_right1;
    private AppCompatImageView main_IMG_stone_right2;
    private AppCompatImageView main_IMG_stone_right3;
    private AppCompatImageView main_IMG_stone_right4;
    private AppCompatImageView main_IMG_stone_center1;
    private AppCompatImageView main_IMG_stone_center2;
    private AppCompatImageView main_IMG_stone_center3;
    private AppCompatImageView main_IMG_stone_center4;
    private AppCompatImageView[] main_IMG_hearts;
    private GameManager gameManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
      //  ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
          //  Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
           // v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
          //  return insets;
        //});
        findViews();
        start();
        gameManager = new GameManager(main_IMG_hearts.length);
        initViews();
    }

    private void initViews(){
        main_BTN_arrow_right.setOnClickListener(view -> moveCarRight());
        main_BTN_arrow_left.setOnClickListener(view -> moveCarLeft());
        collision();
    }

    private void moveCarLeft() {
        if(main_IMG_car_center.getVisibility() == View.VISIBLE){
            main_IMG_car_center.setVisibility(View.INVISIBLE);
            main_IMG_car_left.setVisibility(View.VISIBLE);
        }
        else if(main_IMG_car_right.getVisibility() == View.VISIBLE){
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

    private void collision(){
        if(main_IMG_car_center.getVisibility() == View.VISIBLE && main_IMG_stone_center4.getVisibility() == View.VISIBLE){
            gameManager.checkCollision(true);
        }
    }

    private void findViews(){
        main_IMG_hearts = new AppCompatImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)
        };
        main_IMG_car_left = findViewById(R.id.main_IMG_car_left);
        main_IMG_car_right = findViewById(R.id.main_IMG_car_right);
        main_IMG_car_center = findViewById(R.id.main_IMG_car_center);
        main_BTN_arrow_left = findViewById(R.id.main_BTN_arrow_left);
        main_BTN_arrow_right = findViewById(R.id.main_BTN_arrow_right);
        main_IMG_stone_left1 = findViewById(R.id.main_IMG_stone_left1);
        main_IMG_stone_left2 = findViewById(R.id.main_IMG_stone_left2);
        main_IMG_stone_left3 = findViewById(R.id.main_IMG_stone_left3);
        main_IMG_stone_left4 = findViewById(R.id.main_IMG_stone_left4);
        main_IMG_stone_right1 = findViewById(R.id.main_IMG_stone_right1);
        main_IMG_stone_right2 = findViewById(R.id.main_IMG_stone_right2);
        main_IMG_stone_right3 = findViewById(R.id.main_IMG_stone_right3);
        main_IMG_stone_right4 = findViewById(R.id.main_IMG_stone_right4);
        main_IMG_stone_center1 = findViewById(R.id.main_IMG_stone_center1);
        main_IMG_stone_center2 = findViewById(R.id.main_IMG_stone_center2);
        main_IMG_stone_center3 = findViewById(R.id.main_IMG_stone_center3);
        main_IMG_stone_center4 = findViewById(R.id.main_IMG_stone_center4);
    }

    private void start(){
        main_IMG_car_left.setVisibility(View.INVISIBLE);
        main_IMG_car_right.setVisibility(View.INVISIBLE);
        main_IMG_stone_left1.setVisibility(View.INVISIBLE);
        main_IMG_stone_left2.setVisibility(View.INVISIBLE);
        main_IMG_stone_left3.setVisibility(View.INVISIBLE);
        main_IMG_stone_left4.setVisibility(View.INVISIBLE);
        main_IMG_stone_right1.setVisibility(View.INVISIBLE);
        main_IMG_stone_right2.setVisibility(View.INVISIBLE);
        main_IMG_stone_right3.setVisibility(View.INVISIBLE);
        main_IMG_stone_right4.setVisibility(View.INVISIBLE);
        main_IMG_stone_center1.setVisibility(View.INVISIBLE);
        main_IMG_stone_center2.setVisibility(View.INVISIBLE);
        main_IMG_stone_center3.setVisibility(View.INVISIBLE);
        main_IMG_stone_center4.setVisibility(View.INVISIBLE);
    }

}
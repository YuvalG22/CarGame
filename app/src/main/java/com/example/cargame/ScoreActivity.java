package com.example.cargame;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class ScoreActivity extends AppCompatActivity {
    private MaterialTextView score_LBL_status;
    private MaterialTextView score_LBL_coins;
    private MaterialButton main_BTN_back;
    public static final String KEY_STATUS = "KEY_STATUS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_score);
        findViews();
        initViews();
    }
    private void initViews() {
        main_BTN_back.setOnClickListener(v -> onBack());
        Intent previousActivity = getIntent();
        Bundle extras = previousActivity.getExtras();
        String status = extras.getString(KEY_STATUS);
        int coins = extras.getInt("COINS");
        score_LBL_status.setText(status);
        score_LBL_coins.setText("Coins: " + String.valueOf(coins));
    }

    private void onBack() {
        Intent menu = new Intent(this, MenuActivity.class);
        startActivity(menu);
        finish();
    }

    private void findViews(){
        score_LBL_status = findViewById(R.id.score_LBL_status);
        score_LBL_coins = findViewById(R.id.score_LBL_coins);
        main_BTN_back = findViewById(R.id.main_BTN_back);
    }
}

package com.example.cargame;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textview.MaterialTextView;

public class ScoreActivity extends AppCompatActivity {
    private MaterialTextView score_LBL_status;
    public static final String KEY_STATUS = "KEY_STATUS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_score);
        //ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
        //Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
        //  v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
        //return insets;
        //});
        findViews();
        initViews();
    }
    private void initViews() {
        Intent previousActivity = getIntent();
        String status = previousActivity.getStringExtra(KEY_STATUS);
        score_LBL_status.setText(status);
    }

    private void findViews(){
        score_LBL_status = findViewById(R.id.score_LBL_status);
    }
}

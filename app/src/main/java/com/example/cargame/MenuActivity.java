package com.example.cargame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textview.MaterialTextView;

public class MenuActivity extends AppCompatActivity {
    private MaterialButton main_BTN_button;
    private MaterialButton main_BTN_sensor;
    private MaterialSwitch main_SWI_speed;
    private MaterialButton main_BTN_records;
    private MaterialTextView main_LBL_switch;
    private boolean isFast;
    private boolean isSensors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        findViews();
        initViews();
    }

    private void initViews() {
        main_BTN_button.setOnClickListener(view -> setButtonMode());
        main_BTN_sensor.setOnClickListener(view -> setSensorMode());
        main_BTN_records.setOnClickListener(view -> changeActivityRecords());
        main_SWI_speed.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                main_LBL_switch.setText("Fast Mode : ON");
            }
            else{
                main_LBL_switch.setText("Fast Mode : OFF");
            }
            isFast = isChecked;
        });
    }

    private void setSensorMode() {
        isSensors = true;
        changeActivityGame();
    }

    private void setButtonMode() {
        isSensors = false;
        changeActivityGame();
    }

    private void changeActivityGame() {
        Intent gameIntent = new Intent(this, MainActivity.class);
        Bundle extras = new Bundle();
        extras.putBoolean("SPEED", isFast);
        extras.putBoolean("MODE", isSensors);
        gameIntent.putExtras(extras);
        startActivity(gameIntent);
        finish();
    }

    private void changeActivity2() {
        Intent gameIntent = new Intent(this, MainActivity.class);
        Bundle extras = new Bundle();
        extras.putBoolean("SPEED", isFast);
        extras.putBoolean("MODE", isSensors);
        gameIntent.putExtras(extras);
        startActivity(gameIntent);
        finish();
    }

    private void changeActivityRecords(){
        Intent recordsIntent = new Intent(this, RecordsActivity.class);
        startActivity(recordsIntent);
        finish();
    }

    private void findViews() {
        main_BTN_button = findViewById(R.id.main_BTN_button);
        main_BTN_sensor = findViewById(R.id.main_BTN_sensor);
        main_SWI_speed = findViewById(R.id.main_SWI_speed);
        main_LBL_switch = findViewById(R.id.main_LBL_switch);
        main_BTN_records = findViewById(R.id.main_BTN_records);
    }
}
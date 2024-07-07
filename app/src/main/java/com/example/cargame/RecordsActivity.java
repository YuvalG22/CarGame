package com.example.cargame;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cargame.Interfaces.ListItemClicked;
import com.example.cargame.Logic.RecordsList;
import com.example.cargame.Utilities.SharedPreferencesManager;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;

public class RecordsActivity extends AppCompatActivity {
    private FrameLayout main_FRAME_list;
    private FrameLayout main_FRAME_map;
    private MapFragment mapFragment;
    private FragmentList fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        findViews();
        initViews();
    }

    private void initViews() {
        fragmentList = new FragmentList();
        fragmentList.setListItemClicked(new ListItemClicked() {
            @Override
            public void listItemClicked(double lat, double lon) {
                mapFragment.zoom(lat, lon);
            }
        });
        getSupportFragmentManager().beginTransaction().add(R.id.main_FRAME_list, fragmentList).commit();
        mapFragment = new MapFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.main_FRAME_map, mapFragment).commit();
    }

    private void findViews() {
        main_FRAME_list = findViewById(R.id.main_FRAME_list);
        main_FRAME_map = findViewById(R.id.main_FRAME_map);
    }
}
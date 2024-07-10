package com.example.cargame;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cargame.Interfaces.LocationCallack;
import com.example.cargame.Interfaces.LocationCallack;
import com.example.cargame.Logic.RecordsList;
import com.example.cargame.Utilities.SharedPreferencesManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;

public class FragmentList extends Fragment {
    private MaterialTextView[] main_LBL_records;
    private MaterialButton main_BTN_back;
    private LocationCallack listItemClicked;
    private RecordsList recordsList;
    private Gson gson;


    public FragmentList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_list, container, false);
        gson = new Gson();


        findViews(v);
        initViews();

        return v;
    }

    private void initViews() {
        main_BTN_back.setOnClickListener(v -> onBack());

        String recordAsJson = SharedPreferencesManager.getInstance().getString("records", "");
        if(!recordAsJson.isEmpty()){
            recordsList = gson.fromJson(recordAsJson, RecordsList.class);
            for(int i = 0; i < recordsList.getRecordsList().size(); i++){
                main_LBL_records[i].setText(i + 1 + ". " + recordsList.getRecordsList().get(i).toString());
            }
        }

        for (int i = 0; i < main_LBL_records.length; i++) {
            int index = i;
            main_LBL_records[i].setOnClickListener(v -> itemClicked(recordsList.getRecordsList().get(index).getLat(), recordsList.getRecordsList().get(index).getLon()));
        }
    }

    private void onBack() {
        Intent menu = new Intent(requireActivity(), MenuActivity.class);
        startActivity(menu);
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }

    private void itemClicked(double lat, double lon) {
        if(listItemClicked != null) {
            listItemClicked.onLocationResult(lat, lon);
        }
    }

    private void findViews(View v) {
        main_LBL_records = new MaterialTextView[]{v.findViewById(R.id.main_LBL_record1), v.findViewById(R.id.main_LBL_record2), v.findViewById(R.id.main_LBL_record3),
                                                  v.findViewById(R.id.main_LBL_record4), v.findViewById(R.id.main_LBL_record5), v.findViewById(R.id.main_LBL_record6),
                                                  v.findViewById(R.id.main_LBL_record7), v.findViewById(R.id.main_LBL_record8), v.findViewById(R.id.main_LBL_record9), v.findViewById(R.id.main_LBL_record10)};

        main_BTN_back = v.findViewById(R.id.main_BTN_back);
    }

    public void setListItemClicked(LocationCallack listItemClicked) {
        this.listItemClicked = listItemClicked;
    }
}
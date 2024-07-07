package com.example.cargame.Logic;

import com.example.cargame.Utilities.SharedPreferencesManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RecordsList {
    ArrayList<Record> recordsList = new ArrayList<>();

    public RecordsList(){

    }

    public ArrayList<Record> getRecordsList() {
        return recordsList;
    }

    public void addRecord(Record record){
        recordsList.add(record);
        recordsList.sort(new Comparator<Record>() {
            @Override
            public int compare(Record r1, Record r2) {
                return r1.getScore() - r2.getScore();
            }
        });
        Collections.reverse(recordsList);
        if(recordsList.size() > 10){
            recordsList = (ArrayList<Record>) recordsList.subList(0, 10);
        }
    }
}

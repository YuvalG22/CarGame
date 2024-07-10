package com.example.cargame.Utilities;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.cargame.Interfaces.MoveCallback;

public class MoveSensors {

    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener sensorEventListener;
    private long timestamp = 0l;
    private MoveCallback moveCallback;

    public MoveSensors(Context context, MoveCallback moveCallback) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.moveCallback = moveCallback;
        initEventListener();
    }

    private void initEventListener() {
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                calculateMove(x);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                //pass
            }
        };
    }

    private void calculateMove(float x) {
        if (System.currentTimeMillis() - timestamp > 200) {
            timestamp = System.currentTimeMillis();
            if (x > 1.0) {
                if (moveCallback != null) {
                    moveCallback.moveXLeft();
                }
            }
            if (x < -1.0) {
                if (moveCallback != null) {
                    moveCallback.moveXRight();
                }
            }
        }
    }

    public void start() {
        sensorManager.registerListener(
                sensorEventListener,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stop() {
        sensorManager.unregisterListener(
                sensorEventListener,
                sensor);
    }
}
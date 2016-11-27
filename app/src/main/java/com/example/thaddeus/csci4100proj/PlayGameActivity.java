package com.example.thaddeus.csci4100proj;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


public class PlayGameActivity extends AppCompatActivity
        implements SensorEventListener, View.OnTouchListener {
    public static final float TILT_THRESHOLD = 1.5f;
    public String appName;
    private GameModel model;
    private CanvasView view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        appName = getResources().getString(R.string.app_name);
        float worldWidth = 800;
        float worldHeight = 1280;

        model = new GameModel(worldWidth, worldHeight);

        view = (CanvasView)findViewById(R.id.gameView);
        view.setOnTouchListener(this);


        SensorManager manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if(manager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() == 0){
            Log.d(appName, "No accelerometer");
        } else {
            Sensor accelerometer = manager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
            if(!manager.registerListener(this,accelerometer, SensorManager.SENSOR_DELAY_GAME)){
                Log.d(appName, "Unable to register sensor listener");
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        Log.d(appName, "Tilt: " + x);
        if(x >= TILT_THRESHOLD) {

        } else if (x <= -TILT_THRESHOLD){

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d(appName, "TOUCH!");
        return false;
    }
}

package com.example.thaddeus.csci4100proj;

import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
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
    public static final int JUMP_TIME = 15;
    public static final int CRASH_TIME = 20;
    public static final int JUMP_COOLDOWN_TIME = 50;
    public String appName;
    private GameModel model;
    private CanvasView view;
    private boolean paused = false;
    private int jumpTime = 0;
    private int jumpCooldown = 0;
    private int crash = 0;
    private AudioHandler audioHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        appName = getResources().getString(R.string.app_name);
        float worldWidth = CanvasView.WORLD_WIDTH;
        float worldHeight = CanvasView.WORLD_HEIGHT;

        model = new GameModel(worldWidth, worldHeight);

        view = (CanvasView)findViewById(R.id.gameView);

        view.setPlayerPosition(model.getPosition());

        view.setOnTouchListener(this);

        update();

        audioHandler = new AudioHandler(this);

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
    public void onPause(){
        super.onPause();
        view.setOnTouchListener(null);
        audioHandler.pause(isFinishing());
        paused = true;
    }

    @Override
    public void onResume(){
        super.onResume();
        view.setOnTouchListener(this);
        audioHandler.resume();
        paused = false;
    }

    private void update(){
        view.setPlayerDimensions(model.getSpriteWidth(), model.getSpriteHeight());
        view.setObstacleDimensions(model.getSpriteWidth(), model.getSpriteHeight());
        view.setPlayerPosition(model.getPosition());


        view.clearObstacles();
        for(PointF pos : model.getObstaclePositions()){
            view.addObstacle(pos);
        }


        if(jumpTime == 0) {
            view.setPlayerState(CanvasView.PlayerState.STRAIGHT);
            if (model.checkCollision()) {
                paused = true;
                crash = CRASH_TIME;
                model.decrLives();
                if(model.getLives() == 0){
                    model.setGameOver(true);
                    view.setGameOver(true);
                }
                view.setLives(model.getLives());
                audioHandler.playCrashSound();
            }
        } else {
            if(model.checkCollision()) {
                model.incrScore(10);
                view.setScore(model.getScore());
            }
            jumpTime--;
        }
        view.redraw();
        model.moveScene();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(!model.isGameOver() && !paused) {
            float x = event.values[0];
            if (x >= TILT_THRESHOLD) {
                model.moveLeft();
            } else if (x <= -TILT_THRESHOLD) {
                model.moveRight();
            }
            update();
        } else if (crash != 0){
            crash--;
            if(crash == 0 && !model.isGameOver()){
                view.setPromptTap(true);
                view.redraw();
            }
        }
        if(jumpCooldown != 0){
            Log.d("Jump Cooldown", ""+jumpCooldown);
            jumpCooldown--;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d(appName, "TOUCH!");
        if(!model.isGameOver()) {
            if (crash == 0) {
                view.setPromptTap(false);
                paused = false;
                if (jumpCooldown == 0) {
                    jumpTime = JUMP_TIME;
                    jumpCooldown = JUMP_COOLDOWN_TIME;
                    view.setPlayerState(CanvasView.PlayerState.JUMP);
                }
            }
        } else {
            Intent intent = this.getIntent();
            intent.putExtra("result", model.getScore());
            this.setResult(RESULT_OK, intent);
            finish();
        }
        return true;
    }
}

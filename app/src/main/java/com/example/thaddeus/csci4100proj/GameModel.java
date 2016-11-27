package com.example.thaddeus.csci4100proj;

import android.graphics.PointF;

public class GameModel {
    public static final float DRIFT_SPEED = 10;
    public static final float PLAYER_Y = 200;

    private float boardWidth;
    private float boardHeight;

    private Player player;

    public GameModel(float boardWidth, float boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;

        player = new Player();
        player.setLocation(new PointF(boardWidth/2, PLAYER_Y));
    }

    public void moveLeft() {
        PointF location = player.getLocation();
        location.x -= DRIFT_SPEED;
        int leftBorder = CanvasView.SPRITE_WIDTH/2;
        if(location.x < leftBorder){
            location.x = leftBorder;
        }
        player.setLocation(location);
    }

    public void moveRight() {
        PointF location = player.getLocation();
        location.x += DRIFT_SPEED;
        int rightBorder = (int)boardWidth - CanvasView.SPRITE_WIDTH/2 - 1;
        if(location.x > rightBorder){
            location.x = rightBorder;
        }
        player.setLocation(location);
    }

    public PointF getPosition(){
        return player.getLocation();
    }
}

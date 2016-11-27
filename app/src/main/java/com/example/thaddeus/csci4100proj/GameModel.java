package com.example.thaddeus.csci4100proj;

import android.graphics.PointF;
import android.util.Log;

public class GameModel {
    public static final float SPEED = 10;
    public static final float DRIFT_SPEED = 10;
    public static final float PLAYER_Y = 200;
    public static final int STARTING_LIVES = 5;

    private float boardWidth;
    private float boardHeight;

    private Sprite player;
    private Sprite obstacles[][] = new Sprite[2][4];
    private int screenSwap;
    private float scroll;
    private float spriteWidth;
    private float spriteHeight;

    private int lives = STARTING_LIVES;
    private boolean gameOver = false;
    private int score = 0;

    public GameModel(float boardWidth, float boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;

        setSpriteWidth(boardWidth / 5);
        setSpriteHeight(getSpriteWidth());
        player = new Sprite(new PointF(boardWidth/2, PLAYER_Y), getSpriteWidth(), getSpriteWidth());

        screenSwap = 0;
        scroll = boardHeight;
        generateObstacles();
        //swapScreens();
        //generateObstacles();
    }

    public void moveLeft() {
        PointF location = player.getLocation();
        location.x -= DRIFT_SPEED;
        int leftBorder = (int)player.getWidth()/2;
        if(location.x < leftBorder){
            location.x = leftBorder;
        }
        player.setLocation(location);
    }

    public void moveRight() {
        PointF location = player.getLocation();
        location.x += DRIFT_SPEED;
        int rightBorder = (int)(boardWidth - player.getWidth()/2 - 1);
        if(location.x > rightBorder){
            location.x = rightBorder;
        }
        player.setLocation(location);
    }

    public PointF getPosition(){
        return player.getLocation();
    }

    public PointF[] getObstaclePositions() {
        PointF[] positions = new PointF[obstacles[0].length*2];
        int index = 0;
        for(Sprite[] screen : obstacles) {
            for (Sprite obstacle : screen) {
                if(obstacle == null){
                    positions[index] = new PointF(0, -boardHeight);
                } else {
                    positions[index] = obstacle.getLocation();
                }
                index++;
            }
        }
        return positions;
    }

    private void generateObstacles(){
        for(int i=0; i<obstacles[screenSwap].length; i++){
            obstacles[screenSwap][i] = new Sprite(randomLocation(), getSpriteWidth(), getSpriteWidth());
        }
    }

    private void swapScreens() {
        screenSwap = (screenSwap == 1) ? 0 : 1;
    }

    public void moveObstacles(){
        for(Sprite[] screen : obstacles) {
            for (Sprite obstacle : screen) {
                if(obstacle != null) {
                    PointF location = obstacle.getLocation();
                    location.y -= SPEED;
                }
            }
        }
        scroll -= SPEED;
        if(scroll <= 0){
            scroll = boardHeight;
            swapScreens();
            generateObstacles();
        }
    }

    public boolean checkCollision(){
        for(Sprite[] screen : obstacles){
            for(Sprite obstacle : screen){
                if (obstacle != null && player.intersects(obstacle)){
                    return true;
                }
            }
        }
        return false;
    }

    private PointF randomLocation() {
        float y = (int)(Math.random()*boardHeight) + boardHeight;
        float x = (int)(Math.random()*boardWidth);
        return new PointF(x, y);
    }

    public float getSpriteWidth() {
        return spriteWidth;
    }

    public void setSpriteWidth(float spriteWidth) {
        this.spriteWidth = spriteWidth;
    }

    public float getSpriteHeight() {
        return spriteHeight;
    }

    public void setSpriteHeight(float spriteHeight) {
        this.spriteHeight = spriteHeight;
    }

    public int getLives(){
        return lives;
    }

    public void decrLives() {
        lives--;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getScore() {
        return score;
    }

    public void incrScore(int amount) {
        score += amount;
        Log.d("Score", ""+score);
    }
}

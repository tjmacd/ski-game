package com.example.thaddeus.csci4100proj;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

public class CanvasView extends View {


    public enum PlayerState {STRAIGHT, JUMP}
    public static final int WORLD_WIDTH = 800;
    public static final int WORLD_HEIGHT = 1280;
    private RectF playerPos;
    private ArrayList<RectF> obstaclePositions;
    private Bitmap playerSprite;
    private Bitmap playerJumpSprite;
    private Bitmap obstacleSprite;
    private int playerWidth;
    private int playerHeight;
    private int obstacleWidth;
    private int obstacleHeight;
    private PlayerState playerState = PlayerState.STRAIGHT;

    public CanvasView(Context ctx){
        super(ctx);
        init();
    }

    public CanvasView(Context ctx, AttributeSet attr) {
        super(ctx, attr);
        init();
    }

    private void init() {
        playerSprite = BitmapFactory.decodeResource(getResources(), R.drawable.player);
        playerJumpSprite = BitmapFactory.decodeResource(getResources(), R.drawable.jump);
        obstacleSprite = BitmapFactory.decodeResource(getResources(), R.drawable.tree2);
        obstaclePositions = new ArrayList<>();
    }

    private PointF convertToScreenCoords(PointF point){
        PointF screenPoint = new PointF();
        screenPoint.x = point.x * getWidth() / WORLD_WIDTH;
        screenPoint.y = point.y * getHeight() / WORLD_HEIGHT;
        return screenPoint;
    }

    public void setPlayerDimensions(float width, float height){
        playerWidth = (int)(width * getWidth() / WORLD_WIDTH);
        playerHeight = (int)(height*getHeight()/WORLD_HEIGHT);
    }

    public void setObstacleDimensions(float width, float height){
        obstacleWidth = (int)(width * getWidth() / WORLD_WIDTH);
        obstacleHeight = (int)(height*getHeight()/WORLD_HEIGHT);
    }

    public void setPlayerPosition(PointF position) {
        PointF screenPos = convertToScreenCoords(position);
        int halfWidth = playerWidth / 2;
        int halfHeight = playerHeight / 2;
        playerPos = new RectF(screenPos.x-halfWidth, screenPos.y-halfHeight,
                screenPos.x+halfWidth, screenPos.y+halfHeight);
    }

    public void clearObstacles() {
        obstaclePositions.clear();
    }

    public void addObstacle(PointF position) {
        PointF screenPos = convertToScreenCoords(position);
        int halfWidth = obstacleWidth / 2;
        //int halfHeight = obstacleHeight / 2;
        //int halfWidth = obstacleSprite.getWidth()/2;
        int halfHeight = obstacleWidth*obstacleSprite.getHeight()/
                (obstacleSprite.getWidth()*2);
        RectF newPosition = new RectF(screenPos.x-halfWidth, screenPos.y-halfHeight,
                screenPos.x+halfWidth, screenPos.y+halfHeight);
        obstaclePositions.add(newPosition);
    }

    public RectF getPlayerPosition() {
        return playerPos;
    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    public void redraw() {
        invalidate();
    }

    protected void drawObstacles(Canvas canvas){
        for(RectF obstacle : obstaclePositions){
            canvas.drawBitmap(obstacleSprite, null, obstacle, null);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRGB(255,255,255);

        switch (playerState){
            case STRAIGHT:
                canvas.drawBitmap(playerSprite, null, playerPos, null);
                drawObstacles(canvas);
                break;
            case JUMP:
                drawObstacles(canvas);
                canvas.drawBitmap(playerJumpSprite, null, playerPos, null);
                break;
        }


    }


}

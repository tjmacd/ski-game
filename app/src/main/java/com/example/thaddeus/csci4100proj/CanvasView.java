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
    public static final int WORLD_WIDTH = 800;
    public static final int WORLD_HEIGHT = 1280;
    private RectF playerPos;
    private ArrayList<RectF> obstaclePositions;
    private Bitmap playerSprite;
    private Bitmap obstacleSprite;
    int playerWidth;
    int playerHeight;
    int obstacleWidth;
    int obstacleHeight;

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
        obstacleSprite = BitmapFactory.decodeResource(getResources(), R.drawable.hazard);
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
        int halfHeight = obstacleHeight / 2;
        RectF newPosition = new RectF(screenPos.x-halfWidth, screenPos.y-halfHeight,
                screenPos.x+halfWidth, screenPos.y+halfHeight);
        obstaclePositions.add(newPosition);
    }

    public RectF getPlayerPosition() {
        return playerPos;
    }

    public void redraw() {
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRGB(255,255,255);

        canvas.drawBitmap(playerSprite, null, playerPos, null);

        for(RectF obstacle : obstaclePositions){
            canvas.drawBitmap(obstacleSprite, null, obstacle, null);
        }
    }
}

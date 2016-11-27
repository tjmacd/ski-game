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

public class CanvasView extends View {
    public static final int SPRITE_WIDTH = 275;
    public static final int SPRITE_HEIGHT = 275;
    public static final int WORLD_WIDTH = 800;
    public static final int WORLD_HEIGHT = 1280;
    private RectF playerPos;
    private Bitmap playerSprite;

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
    }

    private PointF convertToScreenCoords(PointF point){
        PointF screenPoint = new PointF();
        screenPoint.x = point.x * getWidth() / WORLD_WIDTH;
        screenPoint.y = point.y * getHeight() / WORLD_HEIGHT;
        return screenPoint;
    }

    public void setPlayerPosition(PointF position) {
        PointF screenPos = convertToScreenCoords(position);
        int halfWidth = SPRITE_WIDTH / 2;
        int halfHeight = SPRITE_HEIGHT / 2;
        playerPos = new RectF(screenPos.x-halfWidth, screenPos.y-halfHeight,
                screenPos.x+halfWidth, screenPos.y+halfHeight);
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
    }
}

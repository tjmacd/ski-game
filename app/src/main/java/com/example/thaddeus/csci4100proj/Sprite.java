package com.example.thaddeus.csci4100proj;

import android.graphics.PointF;

public class Sprite {
    private PointF location;
    private float width;
    private float height;

    public Sprite(PointF location, float width, float height){
        setLocation(location);
        setWidth(width);
        setHeight(height);
    }

    public PointF getLocation() {
        return location;
    }

    public void setLocation(PointF location) {
        this.location = location;
    }

    private float distanceTo(PointF otherLocation){
        float deltaX = Math.abs(location.x - otherLocation.x);
        float deltaY = Math.abs(location.y - otherLocation.y);
        return (float)Math.sqrt(deltaX*deltaX + deltaY*deltaY);
    }

    public boolean intersects(Sprite object2){
        PointF location2 = object2.getLocation();

        float distance = distanceTo(location2);

        return (distance < (width * 0.5));
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}

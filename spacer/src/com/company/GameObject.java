package com.company;

import java.awt.*;

public abstract class GameObject {


    /* abstraktna klasa koja sluzi kao tvornica objekata */

    protected float x, y;

    protected ID id;

    protected float velX, velY;

    public abstract  Rectangle getBounds();

    public GameObject(float x, float y, ID id){
        this.x = x;
        this.y = y;
        this.id = id;

    }

    public abstract void tick();
    public abstract void render(Graphics g);


    public void setX(float x){
        this.x = x;
    }
    public void setY(float y){
        this.y = y;
    }
    public void setId(ID id){
        this.id = id;
    }
    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }

    public float getVelX() {
        return velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }

    public ID getId(){
        return id;
    }
    public void setVelX(int velX){
        this.velX = velX;
    }
}

package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class Health extends GameObject implements ImageObserver {
    public Health(float x, float y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
        setVelY(1);

    }

    public ImageIcon healthIcon = new ImageIcon("resources/Health.png");

    public  Image health = healthIcon.getImage();

    Handler handler;
    Boolean contact = false;

    public Rectangle getBounds() {

        if(!contact){
            return new Rectangle((int)getX(),(int)getY()-10, health.getWidth(this),
                    health.getHeight(this));
        } else
            return new Rectangle((int)getX(),(int)getY(),0,0);
    }

    private AudioHandler audioHandler;
    private void collision(){
        for(int i = 0; i < handler.object.size(); i++){
            GameObject tempObject = handler.object.get(i);

            if(tempObject.id == ID.Player){
                if(this.getBounds().intersects(tempObject.getBounds())){
                    contact = true;
                    HUD.PLAYERHEALTH += 15;
                    audioHandler.playSound("resources/HealthPickup.wav", 0.5f);
                    handler.removeObject(this);
                }
            }
        }
    }


    public void tick() {

        collision();


        x += velX;
        y += velY;
    }
    public  void outOfBounds(){

        if(this.getX() > Game.WIDTH + 15 || this.getX() < -15){
            handler.removeObject(this);

        }else if(this.getY() < Game.HEIGHT){
            handler.removeObject(this);
        }
    }


    public void render(Graphics g) {



            g.drawImage(health,(int)getX(),(int)getY(),this);

    }

    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        return true;
    }
}

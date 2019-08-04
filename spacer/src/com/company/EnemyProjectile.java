package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class EnemyProjectile extends GameObject implements ImageObserver {

    public EnemyProjectile(float x, float y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
        setVelY(8);
        audioHandler.playSound("resources/EnemyLaser1.wav", 0.2f);
    }

    private AudioHandler audioHandler;

    public ImageIcon projectile = new ImageIcon("resources/EnemyProjectile.gif");
    public ImageIcon projectileExplosion = new ImageIcon("resources/ProjectileExplosion.gif");
    public  Image projectileImage = projectile.getImage();

    Handler handler;
    Boolean contact = false;

    public Rectangle getBounds() {

        if(!contact){
            return new Rectangle((int)getX(),(int)getY()-10, projectileImage.getWidth(this),
                    projectileImage.getHeight(this));
        } else
            return new Rectangle((int)getX(),(int)getY(),0,0);
    }

    private void collision(){
        for(int i = 0; i < handler.object.size(); i++){
            GameObject tempObject = handler.object.get(i);

            if(tempObject.id == ID.Player){
                if(this.getBounds().intersects(tempObject.getBounds()) && !HUD.dash){
                    audioHandler.playSound("resources/PlayerHit.wav", 0.4f);
                    contact = true;
                    HUD.PLAYERHEALTH -= 5;
                }
            }
        }
    }


    public void tick() {

        collision();
        if(contact){
            setVelY(15);
        }
        x += velX;
        y += velY;
        outOfBounds();
    }
    public  void outOfBounds(){

        if(this.getX() > Game.WIDTH + 15 || this.getX() < -15){
            handler.removeObject(this);

        }else if(this.getY() > Game.HEIGHT){
            handler.removeObject(this);
        }
    }


    public void render(Graphics g) {

        if(!contact){
            g.drawImage(projectileImage,(int)getX(),(int)getY(),this);
        }else{ projectileImage = projectileExplosion.getImage();
            g.drawImage(projectileImage,(int)getX()+8,(int)getY()-5,this);
        }
    }

    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        return true;
    }
}

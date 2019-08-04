package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class PlayerProjectile extends GameObject implements ImageObserver {

    Handler handler;
    Boolean contact = false;


     public ImageIcon projectile = new ImageIcon("resources/Projectile.gif");
    public ImageIcon projectileExplosion = new ImageIcon("resources/ProjectileExplosion.gif");
      public  Image projectileImage = projectile.getImage();

    public PlayerProjectile(float x, float y, ID id, Handler handler) {
        super(x, y, id);

        this.handler = handler;

        setVelY(-15);

        audioHandler.playSound("resources/Laser.wav", 0.2f);
    }


    private void collision(){
        for(int i = 0; i < handler.object.size(); i++){
            GameObject tempObject = handler.object.get(i);

            if(tempObject.id == ID.EnemyLVL1){
                if(this.getBounds().intersects(tempObject.getBounds())){
                    audioHandler.playSound("resources/OnHit.wav", 0.4f);
                    contact = true;
                    HUD.scoreCount += 50;
                    HUD.goldCount += 5;
                    if(HUD.dashRecharge){
                        HUD.dashCD+=4;
                    }
                }
            }else if(tempObject.id == ID.SuicideEnemy){
                if(this.getBounds().intersects(tempObject.getBounds())){
                    audioHandler.playSound("resources/OnHit.wav", 0.4f);
                    contact = true;
                    HUD.scoreCount += 60;
                    HUD.goldCount += 10;
                    if(HUD.dashRecharge){
                        HUD.dashCD+=4;
                    }
                }
            }else if(tempObject.id == ID.EnemyLVL2){
                if(this.getBounds().intersects(tempObject.getBounds())){
                    audioHandler.playSound("resources/OnHit.wav", 0.4f);
                    contact = true;
                    HUD.scoreCount += 50;
                    HUD.goldCount += 20;
                    if(HUD.dashRecharge){
                        HUD.dashCD+=4;
                    }
                }
            }else if(tempObject.id == ID.Boss){
                if(this.getBounds().intersects(tempObject.getBounds())){
                    audioHandler.playSound("resources/OnHit.wav", 0.4f);
                    contact = true;
                    HUD.scoreCount += 80;
                    HUD.goldCount += 35;
                    if(HUD.dashRecharge){
                        HUD.dashCD +=4;
                    }
                }
            }
        }
    }


    private AudioHandler audioHandler;


    public Rectangle getBounds() {

        if(!contact){
        return new Rectangle((int)getX(),(int)getY()-10, projectileImage.getWidth(this),
                projectileImage.getHeight(this));
        } else
            return new Rectangle((int)getX(),(int)getY(),0,0);
    }



    public void tick() {
        collision();
        if(contact){
            setVelY(-20);
        }
        x += velX;
        y += velY;

        outOfBounds();
    }


    public void render(Graphics g) {

        if(!contact){
            g.drawImage(projectileImage,(int)getX(),(int)getY(),this);
        }else{ projectileImage = projectileExplosion.getImage();
        g.drawImage(projectileImage,(int)getX(),(int)getY()-5,this);
        }

    }

    public  void outOfBounds(){

        if(this.getX() > Game.WIDTH + 15 || this.getX() < -15){
            handler.removeObject(this);

        }else if(this.getY() < 0){
            handler.removeObject(this);

        }

    }


    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {

        this.projectileImage = img;
        return true;
    }
}

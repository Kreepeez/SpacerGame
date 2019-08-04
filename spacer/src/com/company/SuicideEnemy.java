package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.Random;

public class SuicideEnemy extends GameObject implements ImageObserver {

    private boolean dropped;

    public SuicideEnemy(float x, float y, ID id, Handler handler) {
        super(x, y, id);
        suicideEnemyHealth = 10;

        this.alive = true;

        for(int i = 0; i<handler.object.size(); i++){
            if(handler.object.get(i).id == ID.Player){
                player = handler.object.get(i);
            }
        }


        this.handler = handler;
    }

    Handler handler;
    ImageIcon explosion = new ImageIcon("resources/Explosion.gif");
    ImageIcon enemyShip = new ImageIcon("resources/SuicideEnemy.png");
    private Image enemyImage = enemyShip.getImage();

    private  float suicideEnemyHealth;
    private boolean alive;
    boolean hit = false;


    private GameObject player;

    public  void onHit(){
        hit = true;

    }

    private void collision(){
        for(int i = 0; i < handler.object.size(); i++){
            GameObject tempObject = handler.object.get(i);


            if(tempObject.id == ID.Player){
                if(getBounds().intersects(tempObject.getBounds()) && !HUD.dash){
                    this.suicideEnemyHealth = 0;
                    HUD.PLAYERHEALTH -=10;

                }
            }else if(tempObject.id == ID.PlayerProjectile){
                if(getBounds().intersects(tempObject.getBounds())){
                    onHit();



                }
            }
    }

    }


    public Rectangle getBounds() {

        if(alive){
            return new Rectangle((int)x,(int)y,enemyImage.getWidth(this), enemyImage.getHeight(this));
        }else
            return new Rectangle((int)x, (int)y,0,0);


    }

    private AudioHandler audioHandler;

    public void onDeath(){


        if(this.suicideEnemyHealth <= 0) {
            if(alive)audioHandler.playSound("resources/Explosion.wav", 0.3f);
            alive = false;
            setVelY((int)getVelY()+5);


        }

    }


     private Random rand = new Random();

    private int drop = rand.nextInt(20);

    /* ovaj tick metod prolazi kroz promjene kao i ostali, ali i implentira kretanja ovog objekta ka
    * objektu klase Player */

    public void tick() {

        onDeath();


        if(!alive && !dropped && drop == 0){
            handler.addObject(new Health(this.getX(),this.getY(), ID.Health,handler));
            dropped = true;
        }

        if(hit){
            this.suicideEnemyHealth -= HUD.dmg;
            hit = false;
        }

        y += velY + 4;
        x += velX +velX;

        float diffX = x - player.getX() - 8;
        float diffY = y - player.getY() - 8;

        float distance = (float) Math.sqrt((x-player.getX())*(x-player.getX()) + (y-player.getY())*(y-player.getY()));

        velX = (float) ((-1.0/distance)*diffX);
        velY = (float) ((-1.0/distance)*diffY);



        outOfBounds();

        collision();

        this.suicideEnemyHealth = Game.clamp((int)suicideEnemyHealth, 0,30);

    }

    public  void outOfBounds(){

       if(this.getX() > Game.WIDTH + 15 || this.getX() < -15){
           handler.removeObject(this);

       }else if(this.getY() > Game.HEIGHT + 400){
           handler.removeObject(this);
           WaveSpawner.enemyCount -= 1;


       }

    }

    Image enemyDeath = explosion.getImage();

    public void render(Graphics g) {

        if(alive){
            g.drawImage(enemyImage,(int)getX(),(int)getY(),this);

        }else{
            g.drawImage(enemyDeath,(int)getX() -150,(int)getY()-150,this);
            setVelY(8);




        }
        if(alive){
            g.setColor(Color.red);
            g.fillRect((int)getX() +5 ,(int)getY()-14, (int)this.suicideEnemyHealth*2, 5);

        }


    }

@Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        return true;
    }

}

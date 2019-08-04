package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.Random;

public class EnemyLVL1 extends GameObject implements ImageObserver {

    Handler handler;
    ImageIcon explosion = new ImageIcon("resources/Explosion.gif");
    ImageIcon enemyShip = new ImageIcon("resources/Enemy1.png");
    public Image enemyImage = enemyShip.getImage();

    private  float ENEMY_LVL1_HEALTH;
    private boolean alive;
    boolean hit = false;
    HUD hud;



  //  Random bulletGenerator = new Random();
    int enemyFire;
    public  void onHit(){
        hit = true;

    }

    private void collision(){
        for(int i = 0; i < handler.object.size(); i++){
            GameObject tempObject = handler.object.get(i);


            if(tempObject.id == ID.Player){
                if(getBounds().intersects(tempObject.getBounds())){
                    this.ENEMY_LVL1_HEALTH = 0;
                    HUD.PLAYERHEALTH -= 5;

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

    public EnemyLVL1(float x, float y, ID id, Handler handler) {
        super(x, y, id);

        ENEMY_LVL1_HEALTH = 20;

        velY = 3;
        velX = 0;
        this.alive = true;


        this.handler = handler;
    }


    private Random rand = new Random();
    boolean dropped = false;

    private AudioHandler audioHandler;

    public void onDeath(){



        if(this.ENEMY_LVL1_HEALTH <= 0) {
            if(alive)audioHandler.playSound("resources/Explosion.wav", 0.3f);
            alive = false;
            setVelY((int)getVelY()+5);

        }

    }



    private int drop = rand.nextInt(10);

    public void tick() {

        onDeath();



        if(!alive && !dropped && drop == 0){
            handler.addObject(new Health(this.getX(),this.getY(), ID.Health,handler));
            dropped = true;
        }


        if(hit){
            this.ENEMY_LVL1_HEALTH -= HUD.dmg;
            hit = false;
        }

        y += velY;
        x += velX;



        outOfBounds();

        collision();
        this.ENEMY_LVL1_HEALTH = Game.clamp((int)ENEMY_LVL1_HEALTH, 0,30);
        if(getY()>-250) {
      //  enemyFire = bulletGenerator.nextInt(200);
            enemyFire++;

        if(ENEMY_LVL1_HEALTH >0)
            if (enemyFire >= 90) {
                enemyFire = 0;
               // audioHandler.playSound("src/resources/EnemyLaser1.wav", 0.1f);
                handler.addObject(new EnemyProjectile(this.getX(), this.getY(), ID.EnemyProjectile, handler));
            }

        }



    }

    public  void outOfBounds(){

       if(this.getX() > Game.WIDTH + 15 || this.getX() < -15){
           handler.removeObject(this);

       }else if(this.getY() > Game.HEIGHT+400){
           handler.removeObject(this);
           WaveSpawner.enemyCount -= 1;


       }

    }

    Image enemyDeath = explosion.getImage();

    public void render(Graphics g) {

        if(alive){
            g.drawImage(enemyImage,(int)getX(),(int)getY(),this);

        }else{
            g.drawImage(enemyDeath,(int)getX() -100,(int)getY()-150,this);
            setVelY(8);




        }
        if(alive){
            g.setColor(Color.red);
            g.fillRect((int)getX()  ,(int)getY()-14, (int)this.ENEMY_LVL1_HEALTH*2, 5);

        }


    }

@Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        return true;
    }

}

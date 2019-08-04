package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.Random;

public class BossLVL1 extends GameObject implements ImageObserver {

    Handler handler;
    ImageIcon explosion = new ImageIcon("resources/Explosion.gif");
    ImageIcon enemyShip = new ImageIcon("resources/Boss1.png");
    public Image enemyImage = enemyShip.getImage();

    private  float BOSS_LVL1_HEALTH;
    private boolean alive;
    boolean hit = false;
    HUD hud;



    Random bulletGenerator = new Random();
    int enemyFire;
    int enemyFire2;
    int bossSpawn;
    int fireSpawn;
    private boolean dropped;


    /* metod deklarise hitac */
    public  void onHit(){
        hit = true;

    }

    /* metod koji provjerava da li se hitboxes preklapaju sa .intersects() */

    private void collision(){
        for(int i = 0; i < handler.object.size(); i++){
            GameObject tempObject = handler.object.get(i);


            if(tempObject.id == ID.Player){
                if(getBounds().intersects(tempObject.getBounds())){
                    HUD.PLAYERHEALTH -= 50;

                }
            }else if(tempObject.id == ID.PlayerProjectile){
                if(getBounds().intersects(tempObject.getBounds())){
                    onHit();

                }
            }
    }

    }


    /* metod za generisanje i uklanjanje hitboxa*/
    public Rectangle getBounds() {

        if(alive){
            return new Rectangle((int)x,(int)y,enemyImage.getWidth(this), enemyImage.getHeight(this));
        }else
            return new Rectangle((int)x, (int)y,0,0);


    }

    /* konstruktor klase sa superom na GameObject klasu u kojem se odredjuju hitpoints i kretanje*/
    public BossLVL1(float x, float y, ID id, Handler handler) {
        super(x, y, id);
        BOSS_LVL1_HEALTH= 5000;

        velY = 2;
        velX = 5;
        this.alive = true;


        this.handler = handler;
    }

private AudioHandler audioHandler;

   /* metod koji provjerava smrt */
    public void onDeath(){



        if(this.BOSS_LVL1_HEALTH <= 0) {
            if(alive)audioHandler.playSound("src/resources/Explosion.wav", 0.3f);
            alive = false;

            setVelY((int)getVelY()+10);

        }

    }


     private Random rand = new Random();
    private int drop = rand.nextInt(5);


    /* tick metod provjerava stanje svaki cycle */
    public void tick() {

        onDeath();



        if(!alive && !dropped){
            handler.addObject(new Health(this.getX(),this.getY(), ID.Health,handler));
            handler.addObject(new Health(this.getX(),this.getY(), ID.Health,handler));
            dropped = true;
        }

        if(hit){
            this.BOSS_LVL1_HEALTH -= HUD.dmg;
            hit = false;
        }

        y += velY;
        x += velX;



        outOfBounds();

        collision();

        this.BOSS_LVL1_HEALTH = Game.clamp((int)BOSS_LVL1_HEALTH, 0,5000);
        if(getY()> 30) {

            setVelY(0);

      //  enemyFire = bulletGenerator.nextInt(200);

            /* cast timeri */
            enemyFire++;
            enemyFire2++;
            bossSpawn++;
            fireSpawn++;

            /* generisanje child objekata bossa */
        if(BOSS_LVL1_HEALTH >0)
            if (enemyFire >= 30) {
                enemyFire = 0;
                handler.addObject(new EnemyProjectile2(this.getX(),
                        this.getY()+enemyImage.getHeight(this) -70,
                        ID.EnemyProjectile, handler, -1));

                handler.addObject(new EnemyProjectile2(this.getX() +300,
                        this.getY()+enemyImage.getHeight(this)-70,
                        ID.EnemyProjectile, handler,1));

            }if (enemyFire2 >= 50){
                enemyFire2 = 0;
                handler.addObject(new EnemyProjectile(this.getX()+ 150, this.getY()+enemyImage.getHeight(this)-70,
                        ID.EnemyProjectile, handler));
                handler.addObject(new EnemyProjectile(this.getX() +100, this.getY()+enemyImage.getHeight(this)-70,
                        ID.EnemyProjectile, handler));
            }if(bossSpawn >= 150){
                bossSpawn = 0;
                handler.addObject(new SuicideEnemy(0, 0,
                        ID.EnemyProjectile, handler));
                WaveSpawner.enemyCount++;
                handler.addObject(new SuicideEnemy(Game.HEIGHT-10, 0,
                        ID.EnemyProjectile, handler));
                WaveSpawner.enemyCount++;
            }if(fireSpawn >= 500){
                fireSpawn = 0;
                handler.addObject(new FireProjectile(this.getX() ,
                        this.getY() +enemyImage.getHeight(this)- 200,
                        ID.EnemyProjectile, handler));

            }

        }



    }

    /* metod koji provjerava da li je objekat unutar prozora i mijenja kretanje */
    public  void outOfBounds(){

       if(this.getX() > Game.WIDTH  - 330){
           setVelX(-5);

       }else if(this.getY() > Game.HEIGHT+400){

           /* pr izlasku iz prozora, objekat se uklanja iz objekt liste */
           handler.removeObject(this);
           WaveSpawner.enemyCount -= 1;
       }else if(this.getY() > Game.HEIGHT+400) {

       }else if(this.getX() <= 0 ){
            setVelX(5);

        }
    }

    Image enemyDeath = explosion.getImage();


    /* metod koji iscrtava objekat */
    public void render(Graphics g) {

        if(alive){
            g.drawImage(enemyImage,(int)getX(),(int)getY(),this);

        }else{
            g.drawImage(enemyDeath,(int)getX() -100,(int)getY()-150,this);
            setVelY(8);




        }
        if(alive){
            g.setColor(Color.red);
            g.fillRect((int)getX()-5  ,(int)getY()-14, (int)this.BOSS_LVL1_HEALTH/15, 5);

        }


    }

@Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        return true;
    }

}

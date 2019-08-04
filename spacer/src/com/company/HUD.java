package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.sql.*;

public class HUD implements ImageObserver {

    /* HEAD UP DISPLAY klasa
    * sluzi i kao wrapper klasa za staticne proste tipove */


    public HUD(Handler handler){
        this.handler = handler;
    }

    private Handler handler;


    public static long scoreCount;

    public static long goldCount ;

    private WaveSpawner waveSpawner;

    public static int waveCount = 0;


    public static ImageIcon coinIcon = new ImageIcon("resources/Coin.png");
   public static Image coinImage = coinIcon.getImage();

    public static int maxHP = 100;
    public static int maxHPcap = 300;
    public static int dmg = 10;
    public static int dmgCap = 30;
    public static int speed = 0;
    public static int speedCap = 5;
    public static float PLAYERHEALTH = maxHP;
    public static boolean dash = false;
    public static float dashCD = 0;
    public static int dashDuration;
    public static int dashSpeed;

    public static boolean dashRecharge;

    public static boolean extraCannon;

    public static boolean skillDash = false;

    public static boolean musicPlaying = false;


 public String score = String.valueOf(scoreCount);

 public String gold = String.valueOf(goldCount);


 /* HUD tick metod koji provjerava promjene i dodjeljuje varijable
  */

    public void tick(){


        if(dashCD < 100){
            dashCD+=0.75f;
        } if(dashCD >= 25){
            dash = false;
        }
        if(dashDuration >0 ){
            dashDuration--;
            if(dashDuration == 0){
                for (int i = 0; i < handler.object.size(); i++) {
                    GameObject tempObject = handler.object.get(i);

                    if (tempObject.id == ID.Player) {

                        if(tempObject.getVelY() > 5 +HUD.speed){
                            tempObject.setVelY((int)tempObject.getVelY()-dashSpeed);
                        }else if(tempObject.getVelY() < -5 - HUD.speed){
                            tempObject.setVelY((int)tempObject.getVelY()+dashSpeed);
                        }else if(tempObject.getVelX() > 8 + HUD.speed){
                            tempObject.setVelX((int)tempObject.getVelX()-dashSpeed);
                        }else if(tempObject.getVelX() < -8 -HUD.speed){
                            tempObject.setVelX((int)tempObject.getVelX()+dashSpeed);
                        }


                    }
                }
            }
        }



        gold = String.valueOf(goldCount);

        score = String.valueOf(scoreCount);
        PLAYERHEALTH = Game.clamp(PLAYERHEALTH, 0,maxHP);
        dashCD = Game.clamp(dashCD, 0,100);


    }

    /* metod koji iscrtava elemente HEADS UP DISPLAYa */

    public void render(Graphics g){

        Font font = new Font("arial", 1, 20);
        g.setFont(font);
        g.setColor(Color.red);
        g.fillRect(15,15,maxHP*2,20);
        g.setColor(Color.green);

        g.fillRect(15,15, (int)PLAYERHEALTH*2, 20);
        g.setColor(Color.cyan);
        if(skillDash) {
            g.fillRect(15, 45, (int)dashCD * 2, 20);
            g.setColor(Color.white);
            g.drawRect(15,45,200,20);
        }
            g.setColor(Color.white);
            g.drawRect(14, 14, maxHP*2, 21);

        g.drawImage(coinImage,15, Game.HEIGHT - 100, this);

        g.drawString(gold, 60, Game.HEIGHT - 75);

        g.drawString("WAVE " + waveCount, Game.WIDTH -200, Game.HEIGHT -100);


        g.drawString("Score: " + scoreCount, Game.WIDTH -200, 32);



    }

    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        this.coinImage = img;
        return true;
    }

    public void waveCount( int waveCount){
        this.waveCount = waveCount;
    }
    public int getWaveCount(){
        return waveCount;
    }

}

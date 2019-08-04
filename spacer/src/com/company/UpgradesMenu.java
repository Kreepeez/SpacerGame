package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;

public class UpgradesMenu extends MouseAdapter implements ImageObserver {

    /* klasa koja upravlja nadogradnjama i iscrtava upgrade meni */

    private Game game;
    private Handler handler;
    private MouseInput mouseInput;

    public UpgradesMenu(Game game, Handler handler){
        this.game = game;

        this.handler = handler;
       // panel.add(hpLabel);

    }

    //Label hpLabel = new Label();
   // JPanel panel = new JPanel();



    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        return true;
    }


    public ImageIcon menuIcon = new ImageIcon("resources/MenuCanvas.png");
    public Image menuImg;


    private AudioHandler audioHandler;
    private int clickCounter = 0;
    private boolean clicked = false;
     // Button infoBtn = new Button();

   // Button upgradesBtn = new Button("UPGRADES");

  //  private String info = "INFO INFO INFO INFO INFO INFO INFO INFO INFO INFO INFO INFO INFO ";



    public void mouseClicked(MouseEvent e){
        int mx = e.getX();
        int my = e.getY();

        if(clickCounter == 0 && !clicked){

        if(Game.gameState == Game.STATE.Upgrade) {
            if (mouseOver(mx, my, 100, 100, 200, 50)) {
                if (HUD.maxHP < HUD.maxHPcap && HUD.goldCount >= 5000) {
                    clickCounter = 5;
                    clicked = true;
                    HUD.maxHP += 20;
                    HUD.PLAYERHEALTH += 20;
                    HUD.goldCount -= 5000;
                    audioHandler.playSound("resources/Click.wav", 0.5f);
                }
            } else if (mouseOver(mx, my, 100, 200, 200, 50)) {

                if (HUD.dmg < HUD.dmgCap && HUD.goldCount >= 3000) {
                    clickCounter = 5;
                    clicked = true;
                    HUD.dmg += 2;
                    HUD.goldCount -= 3000;
                    audioHandler.playSound("resources/Click.wav", 0.5f);
                }
            } else if (mouseOver(mx, my, 100, 300, 200, 50)) {

                if (HUD.speed < HUD.speedCap && HUD.goldCount >= 1700) {
                    clickCounter = 5;
                    clicked = true;
                    HUD.speed += 1;
                    HUD.goldCount -= 1700;
                    audioHandler.playSound("resources/Click.wav", 0.5f);
                }
            } else if (mouseOver(mx, my, 500, 750, 200, 50)) {

                Game.gameState = Game.STATE.Menu;

            } else if (mouseOver(mx, my, 350, 100, 200, 50)) {

                if (!HUD.skillDash && HUD.goldCount >= 300) {
                    clickCounter = 5;
                    clicked = true;
                    HUD.skillDash = true;
                    HUD.goldCount -= 300;
                    audioHandler.playSound("resources/Click.wav", 0.5f);
                }
            } else if (mouseOver(mx, my, 350, 200, 200, 50)) {

                if (!HUD.extraCannon && HUD.goldCount >= 10000) {
                    clickCounter = 5;
                    clicked = true;
                    HUD.extraCannon = true;
                    HUD.goldCount -= 10000;
                    audioHandler.playSound("resources/Click.wav", 0.5f);
                }

            }else if(mouseOver(mx,my,100,400,200,50)) {
                if (HUD.skillDash && HUD.goldCount >= 15000) {
                    clickCounter = 5;
                    clicked = true;
                    HUD.dashRecharge = true;
                    HUD.goldCount -= 15000;
                    audioHandler.playSound("resources/Click.wav", 0.5f);
                }
            }
        }

        }
    }


    /* metod koji nije koristen */
     public void mouseMoved(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if(mouseOver(mx,my,100,100,200,50)){
            System.out.println("MOUSE MOVED");

        }

    }
    // String infoText;

    public void mouseReleased(MouseEvent e){

    }
   /* public void mouseEntered(MouseEvent e){
       int mx = e.getX();
       int my = e.getY();

       if(mouseOver(mx, my,350,100,200,50)){
           infoText = "Press SPACEBAR to Dash.";
       }

   } */
    private boolean mouseOver(int mx, int my, int x, int y, int width, int height){
        if(mx>x && mx<x+width){
            if(my>y && my<y+height){
                return true;
            }else return false;
        }else return false;

    }


    public void tick(){

      /* ovaj blok koda sprjecava spam klikova i stvara delay izmedju klikova */

        if(clickCounter != 0 && clicked){
            clickCounter--;
            if(clickCounter == 0){
                clicked = false;
            }
        }

    }

    private Window window;


    public void render(Graphics g){

       int mx = MouseInfo.getPointerInfo().getLocation().x - Window.locationX ;
        int my = MouseInfo.getPointerInfo().getLocation().y  - Window.locationY;



        if(Game.gameState == Game.STATE.Upgrade) {
            Font font = new Font("arial", 1, 25);

            g.setFont(font);
            g.setColor(Color.cyan);

            if(HUD.maxHP < HUD.maxHPcap){
                g.setColor(Color.cyan);
            }else g.setColor(Color.gray);


            g.drawRect(100,100,200,50);
            g.drawString("MaxHP +20",130,135);
        }
        if(HUD.dmg < HUD.dmgCap){
            g.setColor(Color.cyan);
        }else g.setColor(Color.gray);


        g.drawRect(100,200,200,50);
        g.drawString("DMG +2",150,235);

        if(HUD.speed < HUD.speedCap){
            g.setColor(Color.cyan);
        }else g.setColor(Color.gray);

        g.drawRect(100,300,200,50);
        g.drawString("SPEED +1",140,335);

        if(!HUD.skillDash){
            g.setColor(Color.gray);
        }else g.setColor(Color.cyan);

        if(HUD.dashRecharge)g.setColor(Color.gray);

        g.drawRect(100,400,200,50);
        g.drawString("Dash Recharge",110,435);


        if(!HUD.skillDash){
            g.setColor(Color.cyan);
        }else
        g.setColor(Color.gray);



        g.drawRect(350,100,200,50);
        g.drawString("DASH",410,135);

        if(!HUD.extraCannon){
            g.setColor(Color.cyan);
        }else
            g.setColor(Color.gray);



        g.drawRect(350,200,200,50);
        g.drawString("Extra Cannon",365,235);


        g.setColor(Color.cyan);
        g.drawRect(800,80,300,450);
        g.drawString("MaxHP: " + HUD.maxHP ,820, 135);
        g.drawString("DMG: " + HUD.dmg ,820, 235);
        g.drawString("SPEED BONUS: " + HUD.speed ,820, 335);


        g.drawRect(500, 750,200,50);
        g.drawString("BACK",560,785);

        g.drawImage(HUD.coinImage,15, Game.HEIGHT - 100, this);



        g.setColor(Color.white);
        g.drawString("" +HUD.goldCount, 60, Game.HEIGHT - 75);
        g.setColor(Color.red);
        g.fillRect(15,15,HUD.maxHP*2,20);
        g.setColor(Color.green);
        g.fillRect(15,15, (int)HUD.PLAYERHEALTH*2, 20);

        g.setColor(Color.white);
        g.drawRect(14,14,HUD.maxHP*2,21);
// 5000, 3000, 1700, 300
        Font font2 = new Font("arial", 1, 25);
        g.setFont(font2);
        g.setColor(Color.white);
        if(HUD.maxHP<HUD.maxHPcap)
        g.drawString("5000 G",160,180 );
        if(HUD.dmg < HUD.dmgCap)
        g.drawString("3000 G",160,280 );
        if(HUD.speed < HUD.speedCap)
        g.drawString("1700 G",160,380 );
        if(!HUD.dashRecharge)
        g.drawString("15000 G", 155, 480);

        if(!HUD.skillDash) {
            g.drawString("300 G", 420, 180);
        }
        if(!HUD.extraCannon) {
            g.drawString("10000 G", 410, 280);
        }

        Font font3 = new Font("arial", 1, 18);

        g.setFont(font3);
        g.setColor(Color.YELLOW);



       if(mouseOver(mx,my,100,100,200,50)){
           g.drawString("Increases your maximum health by 20.", 800,600);
           g.drawString("Health cap is 300.", 800,650);
        }else if(mouseOver(mx,my,100,200,200,50)){
           g.drawString("Increases your maximum damage by 2.", 800,600);
           g.drawString("Damage cap is 30.", 800,650);
       }else if(mouseOver(mx,my,100,300,200,50)){
           g.drawString("Increases your maximum speed by 20.", 800,600);
           g.drawString("Speed cap is 5.", 800,650);
       }else if(mouseOver(mx,my,100,400,200,50)){
           g.drawString("Recharges your dash meter on hit.", 800,600);
       }else if(mouseOver(mx,my,350,100,200,50)) {
           g.drawString("Press SPACE to dash.", 800, 600);
           g.drawString("Become temporary invulnerable ", 800, 650);
           g.drawString("to projectiles and some enemies.", 800, 670);
       }else if(mouseOver(mx,my,350,200,200,50)) {
           g.drawString("Get an extra canon for 2x DMG.", 800, 600);

       }
    }



}

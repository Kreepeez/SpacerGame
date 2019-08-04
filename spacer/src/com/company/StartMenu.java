package com.company;



import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;
import java.sql.*;


public class StartMenu extends MouseAdapter implements ImageObserver {


    /* klasa koja sluzi kao pocetni i gameover meni,  */

    private boolean menuPlaying;
    private Game game;
    private Handler handler;
    private MouseInput mouseInput;
    private HUD hud;
    private AudioHandler audioHandler;
     private static Clip clip;
     private static float volume = 0.4f;



    public StartMenu(Game game, Handler handler, HUD hud){
        this.game = game;
        this.hud = hud;

        this.handler = handler;
  // audioHandler.playSound("src/resources/MenuSong.wav", 0.4f);
        menuPlaying = true;

    }

    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        return true;
    }


    public ImageIcon menuIcon = new ImageIcon("resources/MenuCanvas.png");
    public ImageIcon menuBack = new ImageIcon("resources/StartMenu.gif");
    public Image menuImg;
    public Image menuBackImg;

   // Button resumeBtn = new Button("RESUME");
   // Button upgradesBtn = new Button("UPGRADES");

   // public  void menuPlayMusic(){
  //  }
   // public  void menuStopMusic(){
//}

    public void mousePressed(MouseEvent e){
        int mx = e.getX();
        int my = e.getY();
        if(game.gameState == Game.STATE.Start){
        if(mouseOver(mx,my,500,200,200,50)){
            handler.addObject(new Player(Game.WIDTH/2, Game.HEIGHT/2, ID.Player, handler));

            audioHandler.playSound("resources/Click.wav", 0.5f);
            game.gameState = Game.STATE.Game;




        } if(mouseOver(mx,my,500,400,200,50)){
                audioHandler.playSound("resources/Click.wav", 0.5f);
            System.exit(1);

        }
        }if(game.gameState == Game.STATE.GameOver){

            if(!Login.username.isEmpty()){
            Connection conn;
            try {
                conn =
                        DriverManager.getConnection("jdbc:mysql://localhost:3306/spacer",
                                "root","");

                Statement statement = conn.createStatement();

                statement.executeUpdate("UPDATE user " +
                        "SET gold = " + HUD.goldCount +
                        " WHERE username = 'admin'");

                conn.close();
                statement.close();


            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            try {
                conn =
                        DriverManager.getConnection("jdbc:mysql://localhost:3306/spacer",
                                "root","");

                Statement statement = conn.createStatement();

                statement.executeUpdate("UPDATE user " +
                        "SET hp = " + HUD.maxHP +
                        " WHERE username = 'admin'");

                conn.close();
                statement.close();


            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            try {
                conn =
                        DriverManager.getConnection("jdbc:mysql://localhost:3306/spacer",
                                "root","");

                Statement statement = conn.createStatement();

                statement.executeUpdate("UPDATE user " +
                        "SET speed = " + HUD.speed +
                        " WHERE username = 'admin'");

                conn.close();
                statement.close();


            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            try {
                conn =
                        DriverManager.getConnection("jdbc:mysql://localhost:3306/spacer",
                                "root","");

                Statement statement = conn.createStatement();

                statement.executeUpdate("UPDATE user " +
                        "SET dmg = " + HUD.dmg +
                        " WHERE username = 'admin'");

                conn.close();
                statement.close();


            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            try {            // ovo je trebalo da upisuje highscore u tabelu ali sam odustao
                conn =
                        DriverManager.getConnection("jdbc:mysql://localhost:3306/spacer",
                                "root","");

                Statement statement = conn.createStatement();

                ResultSet rs =statement.executeQuery("SELECT * FROM highscore, " +
                        "user WHERE highscore.uid = spacer.user.id AND " +
                        "user.username = 'admin'");

                while(rs.next()){

                }

                conn.close();
                statement.close();


            } catch (SQLException ex) {
                ex.printStackTrace();
            }



            if(HUD.skillDash){

            try {
                conn =
                        DriverManager.getConnection("jdbc:mysql://localhost:3306/spacer",
                                "root","");

                Statement statement = conn.createStatement();

                statement.executeUpdate("UPDATE type " +
                        "SET isUpgraded = " + 1 +
                        " WHERE tid = 1");

                conn.close();
                statement.close();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            }
            if(HUD.dashRecharge){

                try {
                    conn =
                            DriverManager.getConnection("jdbc:mysql://localhost:3306/spacer",
                                    "root","");

                    Statement statement = conn.createStatement();

                    statement.executeUpdate("UPDATE type " +
                            "SET isUpgraded = " + 1 +
                            " WHERE tid = 2");

                    conn.close();
                    statement.close();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            if(HUD.extraCannon){

                try {
                    conn =
                            DriverManager.getConnection("jdbc:mysql://localhost:3306/spacer",
                                    "root","");

                    Statement statement = conn.createStatement();

                    statement.executeUpdate("UPDATE type " +
                            "SET isUpgraded = " + 1 +
                            " WHERE tid = 3");

                    conn.close();
                    statement.close();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
}

            if(mouseOver(mx,my,500, 400, 200, 50)){
                audioHandler.playSound("resources/Click.wav", 0.5f);
               System.exit(1);

            }
        else if(mouseOver(mx,my,500, 300, 200, 50)){
                audioHandler.playSound("resources/Click.wav", 0.5f);





                HUD.PLAYERHEALTH = HUD.maxHP;
            HUD.scoreCount = 0;
                          game.gameState = Game.STATE.Game;
                HUD.waveCount = 0;
                WaveSpawner.enemyCount = 0;
            handler.addObject(new Player(Game.WIDTH/2, Game.HEIGHT/2, ID.Player, handler));


        }

        }


    }

    public void mouseReleased(MouseEvent e){

    }

    private boolean mouseOver(int mx, int my, int x, int y, int width, int height){
        if(mx>x && mx<x+width){
            if(my>y && my<y+height){
                return true;
            }else return false;
        }else return false;

    }



    public void tick(){



    }

    public void render(Graphics g){

        int mx = MouseInfo.getPointerInfo().getLocation().x - Window.locationX ;
        int my = MouseInfo.getPointerInfo().getLocation().y  - Window.locationY;

        if(game.gameState == Game.STATE.Start) {
            Font font = new Font("arial", 1, 25);

            g.setFont(font);
            menuImg = menuIcon.getImage();
            menuBackImg = menuBack.getImage();
            g.drawImage(menuBackImg, 0, 200, this);
            g.drawImage(menuImg, 290, 100, this);

            g.setColor(Color.cyan);

            g.drawRect(500, 200, 200, 50);
            g.drawString("START GAME", 520, 235);

            g.drawRect(500, 300, 200, 50);
            g.drawString("HELP", 565, 335);

            g.drawRect(500, 400, 200, 50);
            g.drawString("EXIT", 570, 435);

            if(mouseOver(mx,my,500, 300, 200, 50)){
                g.drawString("Move on WASD.", 500,500);
                g.drawString("Shoot on mouse.", 500,550);
                g.drawString("Press ESC for menu.", 500,600);
            }

        }else if(game.gameState == Game.STATE.GameOver){
            Font font2 = new Font("arial", 1, 30);

            Font font3 = new Font("arial", 1, 80);

            g.setFont(font2);

            menuBackImg = menuBack.getImage();
            g.drawImage(menuBackImg, 0, 200, this);


            g.setColor(Color.white);

            g.setFont(font3);
            g.drawString("GAME OVER", 370, 200);

            g.setFont(font2);
            g.setColor(Color.black);
            g.fillRect(500, 300, 200, 50);
            g.setColor(Color.cyan);
            g.drawRect(500, 300, 200, 50);
            g.setColor(Color.white);
            g.drawString("RESTART", 530, 335);

            g.setColor(Color.black);
            g.fillRect(500, 400, 200, 50);
            g.setColor(Color.cyan);
            g.drawRect(500, 400, 200, 50);
            g.setColor(Color.white);
            g.drawString("EXIT", 570, 435);

            g.drawString("YOUR SCORE: " + HUD.scoreCount, 445, 600);
            g.drawString("You survived " + (HUD.waveCount-1) +" waves.", 450, 700);

        }


    }
}

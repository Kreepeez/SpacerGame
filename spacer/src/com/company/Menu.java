package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Menu extends MouseAdapter implements ImageObserver {

    /* in-game meni klasa koja iscrtava meni i implementira funkcionalnosti */

    private Game game;
    private Handler handler;
    private MouseInput mouseInput;

    public Menu(Game game, Handler handler){
        this.game = game;

        this.handler = handler;
    }

    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        return true;
    }


    public ImageIcon menuIcon = new ImageIcon("resources/MenuCanvas.png");
    public Image menuImg;


    private AudioHandler audioHandler;
   // Button resumeBtn = new Button("RESUME");
   // Button upgradesBtn = new Button("UPGRADES");

    /* javlja se kliom misa na odredjenoj lokaciji */
    public void mousePressed(MouseEvent e){
        int mx = e.getX();
        int my = e.getY();

        if(Game.gameState == Game.STATE.Menu) {
            if (mouseOver(mx, my, 500,200,200,50)) {
                audioHandler.playSound("resources/Click.wav", 0.5f);
                game.gameState = Game.STATE.Game;

            }
            if (mouseOver(mx, my, 500,300,200,50)) {
                audioHandler.playSound("resources/Click.wav", 0.5f);
                game.gameState = Game.STATE.Upgrade;

            }
            if (mouseOver(mx, my, 500, 400, 200, 50)) {
                audioHandler.playSound("resources/Click.wav", 0.5f);

                if(!Login.username.isEmpty()) {
                    Connection conn;
                    try {
                        conn =
                                DriverManager.getConnection("jdbc:mysql://localhost:3306/spacer",
                                        "root", "");

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
                                        "root", "");

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
                                        "root", "");

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
                                        "root", "");

                        Statement statement = conn.createStatement();

                        statement.executeUpdate("UPDATE user " +
                                "SET dmg = " + HUD.dmg +
                                " WHERE username = 'admin'");

                        conn.close();
                        statement.close();


                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                    if (HUD.skillDash) {

                        try {
                            conn =
                                    DriverManager.getConnection("jdbc:mysql://localhost:3306/spacer",
                                            "root", "");

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
                    if (HUD.dashRecharge) {

                        try {
                            conn =
                                    DriverManager.getConnection("jdbc:mysql://localhost:3306/spacer",
                                            "root", "");

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
                    if (HUD.extraCannon) {

                        try {
                            conn =
                                    DriverManager.getConnection("jdbc:mysql://localhost:3306/spacer",
                                            "root", "");

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
                System.exit(1);

            }
        }
    }

    public void mouseReleased(MouseEvent e){

    }

    /* metod koji provjerava lokaciju misa na ekranu i vraca boolean */

    private boolean mouseOver(int mx, int my, int x, int y, int width, int height){
        if(mx>x && mx<x+width){
            if(my>y && my<y+height){
                return true;
            }else return false;
        }else return false;

    }

    public void tick(){


    }

    /* metod u kojem se iscrtava meni */
    public void render(Graphics g){

        if(Game.gameState == Game.STATE.Menu) {
            Font font = new Font("arial", 1, 25);

            g.setFont(font);
            menuImg = menuIcon.getImage();

            g.drawImage(menuImg, 290, 100, this);

            g.setColor(Color.cyan);

            g.drawRect(500, 200, 200, 50);
            g.drawString("RESUME", 545, 235);

            g.drawRect(500, 300, 200, 50);
            g.drawString("UPGRADES", 530, 335);

            g.drawRect(500, 400, 200, 50);
            g.drawString("EXIT", 570, 435);

            g.drawImage(HUD.coinImage,15, Game.HEIGHT - 100, this);

            g.setColor(Color.white);
            g.drawString("" +HUD.goldCount, 60, Game.HEIGHT - 75);
            g.setColor(Color.red);
            g.fillRect(15,15,HUD.maxHP*2,20);
            g.setColor(Color.green);
            g.fillRect(15,15, (int)HUD.PLAYERHEALTH*2, 20);

            g.setColor(Color.white);
            g.drawRect(14,14,HUD.maxHP*2,21);

        }


    }
}

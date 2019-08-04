package com.company;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    /* klasa koja kontrolise key input */


    private Handler handler;

    private boolean[] keyDown = new boolean[4];
    private Game game;

    public KeyInput(Handler handler, Game game){
        this.handler = handler;
        this.game = game;
    }



    private AudioHandler audioHandler;


    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();


        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);


            /* koriste se booleani da bi se izbjegli sticky keys */
            if (tempObject.getId() == ID.Player) {

                if (key == KeyEvent.VK_W) {
                    tempObject.setVelY(-5 - HUD.speed);
                    keyDown[0] = true;
                }
                if (key == KeyEvent.VK_S) {
                    tempObject.setVelY(5 + HUD.speed);
                    keyDown[1] = true;
                }
                if (key == KeyEvent.VK_D) {
                    tempObject.setVelX(8 +HUD.speed);
                    keyDown[2] = true;
                }
                if (key == KeyEvent.VK_A) {
                    tempObject.setVelX(-8 - HUD.speed);
                    keyDown[3] = true;
                }
                if(HUD.skillDash){
                if (key == KeyEvent.VK_SPACE && !HUD.dash && HUD.dashCD >= 100) {

                    audioHandler.playSound("resources/Dash.wav", 0.5f);
                    HUD.dashSpeed = 10;
                    if (tempObject.getVelY() > 0) {
                        HUD.dash = true;
                        HUD.dashCD = 0;
                        HUD.dashDuration = 10;
                        tempObject.setVelY((int) tempObject.getVelY() + HUD.dashSpeed);

                    }else
                    if (tempObject.getVelY() < 0) {
                        HUD.dash = true;
                        HUD.dashCD = 0;
                        HUD.dashDuration = 10;
                        tempObject.setVelY((int) tempObject.getVelY() - HUD.dashSpeed);
                    }else
                    if (tempObject.getVelX() > 0) {
                        HUD.dash = true;
                        HUD.dashCD = 0;
                        HUD.dashDuration = 10;
                        tempObject.setVelX((int) tempObject.getVelX() + HUD.dashSpeed);
                    }else
                    if (tempObject.getVelX() < 0) {
                        HUD.dash = true;
                        HUD.dashCD = 0;
                        HUD.dashDuration = 10;
                        tempObject.setVelX((int) tempObject.getVelX() - HUD.dashSpeed);
                    }
                }
                }
            }
        }
        if (key == KeyEvent.VK_ESCAPE) {
            if (Game.gameState == Game.STATE.Game || Game.gameState == Game.STATE.Upgrade) {
                game.gameState = Game.STATE.Menu;
            } else if (Game.gameState == Game.STATE.Menu) {
                game.gameState = Game.STATE.Game;
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();


            for (int i = 0; i < handler.object.size(); i++) {
                GameObject tempObject = handler.object.get(i);

                if (tempObject.getId() == ID.Player) {


                    if (key == KeyEvent.VK_W) {
                        keyDown[0] = false;
                    }
                    if (key == KeyEvent.VK_S) {
                        keyDown[1] = false;
                    }
                    if (key == KeyEvent.VK_D) {
                        keyDown[2] = false;
                    }
                    if (key == KeyEvent.VK_A) {
                        keyDown[3] = false;
                    }

                    if (!keyDown[0] && !keyDown[1]) {
                        tempObject.setVelY(0);
                    }
                    if (!keyDown[2] && !keyDown[3]) {
                        tempObject.setVelX(0);
                    }
                }
            }



    }
}

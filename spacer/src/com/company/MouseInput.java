package com.company;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



public class MouseInput extends MouseAdapter {

    /* klasa koja kontrolise mouse input */

    private Handler handler;

    private Game game;


    //public static boolean projectileFired = false;

    public MouseInput(Handler handler){
        this.handler = handler;

    }


    boolean clickDown;

    private int fireCounter = 0;

    /* u svojoj tick metodi ima timer koji stvara delay izmedju projektila */

    public void tick(){

        fireCounter++;

        if(Game.gameState == Game.STATE.Game){
            for (int i = 0; i < handler.object.size(); i++) {
                GameObject tempObject = handler.object.get(i);


                if (tempObject.id == ID.Player) {

                    if(fireCounter >= 6 && clickDown) {
                        handler.addObject(new PlayerProjectile(tempObject.getX(), tempObject.getY(),
                                ID.PlayerProjectile, handler));
                        fireCounter = 0;
                        if(HUD.extraCannon){
                            handler.addObject(new PlayerProjectile(tempObject.getX()+25, tempObject.getY(),
                                    ID.PlayerProjectile, handler));
                        }

                    }

                }

            }

        }
    }


    public void mousePressed(MouseEvent e){
        clickDown = true;
    }
    public void mouseReleased(MouseEvent e){
        clickDown = false;
    }
    public void mouseClicked(MouseEvent e) {



        int mouseId = e.getID();

        /*  stara implementacija
        * bilo je potrebno kliknuti da se kreira projektil i bilo je umarajuce*/

      /*  if(Game.gameState == Game.STATE.Game){
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);


                if (tempObject.id == ID.Player) {



                    if(fireCounter >= 3) {
                        handler.addObject(new PlayerProjectile(tempObject.getX(), tempObject.getY(),
                                ID.PlayerProjectile, handler));
                        fireCounter = 0;

                    }

                }

            }

        }*/
    }

}


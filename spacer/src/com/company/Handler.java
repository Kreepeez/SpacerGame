package com.company;


import java.awt.*;

import java.util.LinkedList;

public class Handler {


    /* ova klasa sluzi za upravljanje objektima
    *
    * sadrzi listu objekata kroz koju se kasnije iterira da bi se ispunila ili provjerila neka forma */

    LinkedList<GameObject> object = new LinkedList<>();


    public Handler handler;



    /* u svojoj tick metodi, iterira se kroz cijelu listu i kreira se priveremeni objekat nad kojim se poziva
    * tick metoda iz njegove klase*/

    public void tick(){

        for(int i = 0; i < object.size(); i++){
            GameObject tempObject = object.get(i);
            tempObject.tick();
        }
    }

    /* prolazi kroz sve objekte i poziva njihov render metod */
    public void render(Graphics g){
        for(int i = 0; i < object.size(); i++){
            GameObject tempObject = object.get(i);
            tempObject.render(g);
        }

    }

    public void addObject(GameObject object){
        this.object.add(object);
    }
    public void removeObject(GameObject object){
        this.object.remove(object);
    }

    public void removeEnemies(){
        for(int i = 0; i < object.size(); i++){
            GameObject tempObject = object.get(i);

            if(tempObject.id == ID.Player){
                object.clear();
                if(Game.gameState != Game.STATE.GameOver){
                    addObject(new Player(tempObject.getX(), tempObject.getY(), ID.Player,handler));
                }
            }
        }
    }


}

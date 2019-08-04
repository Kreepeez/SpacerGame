package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;


public class Explosion extends GameObject implements ImageObserver {
    public Explosion(int x, int y, ID id, Handler handler) {
        super(x, y, id);

   this.handler = handler;
    }
Handler handler;
    @Override
    public Rectangle getBounds() {
        return null;
    }


    ImageIcon explosion = new ImageIcon();
    Image explo = explosion.getImage();



    public void tick() {

        y += velY;
        x += velX;

    }


    public void render(Graphics g) {
       g.drawImage(explo,(int)getX()-50,(int)getY()-50,this);

    }

    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        this.explo = img;

        return true;
    }
}

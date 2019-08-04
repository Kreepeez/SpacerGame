package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;


public class Trail extends GameObject implements ImageObserver {

    /* klasa koja ostavlja trrag iza playera kad koristi skill dash */

    private Handler handler;
    private float alpha = 1;
    private ImageIcon trailIcon = new ImageIcon("resources/PlayerTrail.png");
    private Image trail = trailIcon.getImage();
    private float life;

    public Trail(float x, float y, ID id,Handler handler,float life) {
        super(x, y, id);
        this.handler = handler;
        this.life = life;
    }


    public Rectangle getBounds() {
        return null;
    }


    public void tick() {

        if(alpha>life){
            alpha -= life - 0.001f;
        }else handler.removeObject(this);
    }

    private AlphaComposite makeTransparent(float alpha){
        int type = AlphaComposite.SRC_OVER;
        return (AlphaComposite.getInstance(type, alpha));
    }

    public void render(Graphics g) {
       // g.drawImage(trail,(int) getX(),(int)getY(),this);

        Graphics2D g2d = (Graphics2D)g;

        g2d.setComposite(makeTransparent(alpha));
        g.drawImage(trail,(int) getX(),(int)getY(),this);


       g2d.setComposite(makeTransparent(1));
    }

    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        return true;
    }
}

package com.company;

import javax.swing.*;
import java.awt.*;

import java.awt.event.MouseEvent;

import java.awt.event.MouseMotionListener;

import java.awt.image.ImageObserver;




public class Player extends GameObject implements ImageObserver, MouseMotionListener {

    private Handler handler;

    private MouseInput mouseInput;

    private AudioHandler audioHandler;

    public Rectangle getBounds() {
        if(HUD.PLAYERHEALTH >0){
        return new Rectangle((int) x, (int) y, playerImage.getWidth(this),
                playerImage.getHeight(this));
        }else return new Rectangle(0,0,0,0);
    }

    public Player(float x, float y, ID id, Handler handler) {
        super(x, y, id);

        this.handler = handler;

        mouseInput = new MouseInput(handler);


        HUD.musicPlaying = true;
    }


    Point p = MouseInfo.getPointerInfo().getLocation();

    //int mouseX = p.x;
    //int mouseY = p.y;


    //private Point imagePosition = new Point((int) getX(), (int) getY());

    ImageIcon playerShip = new ImageIcon("resources/Spaceship1.png");
    private ImageIcon explosion = new ImageIcon("resources/Explosion.gif");
    private Image playerImage = playerShip.getImage();

    public void onDeath() {

    }

   // private double imageAngleRad = 0;


    public void tick() {

        collision();
        onDeath();

        x += velX;
        y += velY;

        x = Game.clamp(x, 0, (float) Game.WIDTH - 70);
        y = Game.clamp(y, 0, (float) Game.HEIGHT - 90);

       if(getVelX() > 8+HUD.speed || getVelX()< -8+(-HUD.speed) ||
               getVelY()>8+HUD.speed || getVelY() < -8+ (-HUD.speed)){
            handler.addObject(new Trail(this.getX(),this.getY(),ID.NonSolid,handler, 0.1f));
        }

    }

    private void collision() {
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);

            if (tempObject.id == ID.EnemyLVL1) {
                if (getBounds().intersects(tempObject.getBounds())) {

                }
            }

        }
    }

   // private int centerX = playerImage.getWidth(this) / 2;
   // private int centerY = playerImage.getHeight(this) / 2;
   // private double angle = Math.atan2(centerY - mouseX,
   //         centerX - mouseY) - Math.PI / 2;

    public void render(Graphics g) {

        //super.paintComponent(g);

       /*


        pokusaj implementacije rotiranja player objekta ka poziciji kursora

        Graphics2D g2d = (Graphics2D) g.create();

        double rotation = 0f;

        int width = playerImage.getWidth(this) - 1;
        int height = playerImage.getHeight(this) - 1;

        if (p != null) {

            int x = width / 2;
            int y = height / 2;

            int deltaX = p.x - x;
            int deltaY = p.y - y;

            rotation = -Math.atan2(deltaX, deltaY);

            rotation = Math.toDegrees(rotation) + 180;
        }



        g2d.rotate(Math.toRadians(rotation), width / 2, height / 2);
        g2d.drawImage(playerImage, (int)getX(),(int) getY(), this);
*/

        if(HUD.PLAYERHEALTH <= 0){

            g.drawImage(playerImage,(int) getX()-100, (int) getY()-150, this);
        }else{
            g.drawImage(playerImage,(int) getX(),(int) getY(), this);
        }

    }



    //AffineTransform transform = AffineTransform.getTranslateInstance(100,100);
    //transform.rotate(Math.toRadians(45));

    //Graphics2D g2d = (Graphics2D)g;

    //g2d.rotate(angle, centerX, centerY);

    //((Graphics2D)g).rotate(angle, centerX, centerY);







    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        this.playerImage = img;
        return true;
    }


    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        p = e.getPoint();

       // render(g);

    }
}

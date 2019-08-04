package com.company;

import javax.swing.*;
import java.awt.*;

public class Window extends Canvas {

/* jednostavna klasa koja kreira novi prozor */

    public static int locationX;
    public static int locationY;
    JFrame frame;

    public Window(int width, int height, String title, Game game){

       frame = new JFrame(title);

        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(game);
        frame.setVisible(true);
        frame.toFront();



        game.start();
    }
    public void addLabel(Label l){
        frame.add(l);
    }

    /* u ovoj tick metodi se konstantno provjerava lokacija prozora na ekranu */

    public void tick(){
        locationX = frame.getLocationOnScreen().x +7;
        locationY = frame.getLocationOnScreen().y+30;
    }
}

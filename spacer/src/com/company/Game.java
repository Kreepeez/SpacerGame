package com.company;



import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.sql.*;
import java.util.Random;



/* Glavna klasa u kojoj se nalazi igra */

public class Game extends Canvas implements Runnable {

    Random rand = new Random();

    boolean isListening = false;

    public static boolean loggedIn;


                                                /* width/12*9 cuva aspect ratio */

    public static final int WIDTH = 1200, HEIGHT = WIDTH/12*9;

    private Thread thread;
    private boolean running = false;
    private Handler handler;
    private HUD hud;
    private WaveSpawner waveSpawner;
    private MouseInput mouseInput;

    Window window;

    private StartMenu startMenu;

    private Menu menu;
    private UpgradesMenu upgrade;
    private AudioHandler audioHandler;
  //  private Login login;

 //   float volume = 0.4f;
  //  private Clip clip;

    public enum STATE{
        Start,
      Menu,
        Upgrade,
      Game,
        GameOver,
        Login
    }
    public static STATE gameState = STATE.Start;
                                                  /* konstruktor klase game prima novi prozor */

    public Game(){

        gameState = STATE.Login;




        handler = new Handler();

       // audioHandler = new AudioHandler();

        hud = new HUD(handler);
        startMenu = new StartMenu(this, handler, hud);
        menu = new Menu(this, handler);
        mouseInput = new MouseInput(handler);
        upgrade = new UpgradesMenu(this,handler);
       // audioHandler.playSound("src/resources/GameSong.wav", 0.4f);


        /* u slucaju logovanja, izvlace se podaci iz baze i dodjeljuju se varijablama */

        if(Login.loggedIn) {

            try {
                Connection conn =
                        DriverManager.getConnection("jdbc:mysql://localhost:3306/spacer", "root", "");

                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT gold FROM user WHERE username = 'admin'");
                while (rs.next()) {
                    HUD.goldCount = rs.getInt("gold");

                }

                conn.close();
                st.close();
                rs.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                Connection conn =
                        DriverManager.getConnection("jdbc:mysql://localhost:3306/spacer", "root", "");

                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT hp FROM user WHERE username = 'admin'");
                while (rs.next()) {
                    if (rs.getInt("hp") != 0) {
                        HUD.maxHP = rs.getInt("hp");
                        HUD.PLAYERHEALTH = HUD.maxHP;
                    }
                }

                conn.close();
                st.close();
                rs.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                Connection conn =
                        DriverManager.getConnection("jdbc:mysql://localhost:3306/spacer", "root", "");

                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT speed FROM user WHERE username = 'admin'");
                while (rs.next()) {
                    HUD.speed = rs.getInt("speed");

                }

                conn.close();
                st.close();
                rs.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                Connection conn =
                        DriverManager.getConnection("jdbc:mysql://localhost:3306/spacer", "root", "");

                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT dmg FROM user WHERE username = 'admin'");
                while (rs.next()) {
                    HUD.dmg = rs.getInt("dmg");

                }

                conn.close();
                st.close();
                rs.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                Connection conn =
                        DriverManager.getConnection("jdbc:mysql://localhost:3306/spacer", "root", "");

                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT isUpgraded FROM type WHERE tid = 1");
                while (rs.next()) {
                    if (rs.getInt("isUpgraded") == 1)
                        HUD.skillDash = true;

                }

                conn.close();
                st.close();
                rs.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                Connection conn =
                        DriverManager.getConnection("jdbc:mysql://localhost:3306/spacer", "root", "");

                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT isUpgraded FROM type WHERE tid = 2");
                while (rs.next()) {
                    if (rs.getInt("isUpgraded") == 1)
                        HUD.dashRecharge = true;

                }

                conn.close();
                st.close();
                rs.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                Connection conn =
                        DriverManager.getConnection("jdbc:mysql://localhost:3306/spacer", "root", "");

                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT isUpgraded FROM type WHERE tid = 3");
                while (rs.next()) {
                    if (rs.getInt("isUpgraded") == 1)
                        HUD.extraCannon = true;

                }

                conn.close();
                st.close();
                rs.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }


        /* dodljeluje se key listener na ovu klasu */

        this.addKeyListener(new KeyInput(handler,this));
       // this.addMouseListener(mouseInput);
       if(gameState == STATE.Start){
            this.addMouseListener(startMenu);
        }
       // handler.addObject(new Player(Game.WIDTH/2 , Game.HEIGHT/2 , ID.Player, handler));


        /* kreira se novi prozor */

        window = new Window(WIDTH, HEIGHT, "Spacer", this);

        /*kreira se spawner koji ce kreirati levele */

        waveSpawner = new WaveSpawner(handler, hud);

        /* otvara se login prozor */

        if(!Login.loggedIn) {
            {
                try
                {
                    Login frame=new Login();
                    frame.setSize(300,100);
                    frame.setVisible(true);

                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(null, e.getMessage());}
            }
        }
    }




    public synchronized void start(){
                             /* pri startu aplikacije, inicijalizira se thread sa targetom na ovu klasu i pokrece se */

        thread = new Thread(this);
        thread.start();
        running = true;
    }

    /* importovana metoda iz runnable interfejsa */

    public synchronized void stop(){

        try{
            thread.join();
            running = false;

        }catch(Exception e) {
            e.printStackTrace();
        }
    }




                                                    /* run() metoda implementirana iz Runnable interfejsa */
    public void run() {
         // metod za fokus bez klika
        this.requestFocus();

        /* kreira se osnovna logika koja odrzava aplikaciju i u kojoj se pozivaju glavne tick i render klase Game */

        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 /amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) /ns;
            lastTime = now;
            while(delta >= 1){
                tick();
                delta--;
            }
            if(running){
                render();
                frames++;
            }
            if(System.currentTimeMillis() - timer >1000){
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();

    }

    /* glavna tick metoda u kojoj se pozivaju tick metode iz drugih klasa */

    private void tick(){

        window.tick();

        if(gameState == STATE.Game){

            handler.tick();
            mouseInput.tick();

            //    audioHandler.playSound("src/resources/GameSong.wav", 0.3f);

            if(HUD.PLAYERHEALTH <= 0){
                gameState = STATE.GameOver;
                HUD.PLAYERHEALTH = 100;
                handler.removeEnemies();

            }
            this.removeMouseListener(upgrade);
            this.removeMouseListener(menu);
            this.removeMouseListener(startMenu);
            if(!isListening){
            this.addMouseListener(mouseInput);
            isListening = true;
            }

            hud.tick();
            waveSpawner.tick();

        }else if(gameState == STATE.Start || gameState == STATE.GameOver){
            this.addMouseListener(startMenu);
            isListening = false;
            startMenu.tick();
        }else if (gameState == STATE.Menu){
            this.removeMouseListener(upgrade);
            this.addMouseListener(menu);
            menu.tick();
            isListening = false;

        }else if(gameState == STATE.Upgrade){
            this.removeMouseListener(menu);
            this.addMouseListener(upgrade);
            upgrade.tick();
            isListening = false;
        }else if(gameState == STATE.Login){

        }



    }

    /* glavna render metoda koja poziva render metode iz drugih klasa */

    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        if( gameState == STATE.Game) {


            handler.render(g);

            hud.render(g);
            /* predstavlja ilziju kretanja kroz svemir */
            g.setColor(Color.lightGray);
            g.fillRect(rand.nextInt(WIDTH), rand.nextInt(HEIGHT), 1,50);
        }

        else if (gameState == STATE.Start || gameState == STATE.GameOver){
            startMenu.render(g);
        }
        else if (gameState == STATE.Menu){
            menu.render(g);
        }else if(gameState == STATE.Upgrade){
            upgrade.render(g);
        }else if(gameState == STATE.Login){
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, WIDTH, HEIGHT);
            g.setColor(Color.white);
            g.drawString("Username: admin", 100,100);
            g.drawString("Password: admin", 100,120);
        }




        g.dispose();
        bs.show();
    }

    /* metod koji prima varijablu, njenu minimalnu i maximalnu vrijednost i sprjecava prekoracenje */

    public static float clamp(float var, float min, float max){
        if(var >= max){
            return var = max;
        }
        else if(var <= min){
            return var = min;
        }
        else return var;
    }
   /* public static void outOfBounds(int var, int min, int max){

        for(int i = 0; i <handler.object.size(); i++){
            GameObject tempObject = handler.object.get(i);

            if(tempObject.id == ID.EnemyLVL1){
                if(var < min - 10 || var > max + 10)
                handler.removeObject(tempObject);
            }else if(tempObject.id == ID.PlayerProjectile){
                if(var < min - 10 || var > max + 10)
                    handler.removeObject(tempObject);

            }
        }


    } */

    public static void main(String[] args) {

       /* kreiranje novog game objekta */

            new Game();



    }
}

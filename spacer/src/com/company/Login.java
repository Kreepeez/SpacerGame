package com.company;



import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.*;


import static com.company.AudioHandler.clip;
import static javax.sound.sampled.AudioSystem.getClip;

public class Login extends JFrame implements ActionListener {

    /* klasa koja sluzi za login i da bi se mogla osvariti konekcija sa bazom */


    JButton SUBMIT;
    JPanel panel;
    JLabel label1,label2;
    final JTextField  text1,text2;

    public static String username;
    public static String password;

    public static boolean loggedIn;

    public static int userID;

    Login()
    {
        this.requestFocus();
       this.setLocationRelativeTo(null);
        label1 = new JLabel();
        label1.setText("Username:");
        text1 = new JTextField(15);

        label2 = new JLabel();
        label2.setText("Password:");
        text2 = new JPasswordField(15);

        SUBMIT=new JButton("Login");

        panel=new JPanel(new GridLayout(3,1));
        panel.add(label1);
        panel.add(text1);
        panel.add(label2);
        panel.add(text2);
        panel.add(SUBMIT);
        add(panel,BorderLayout.CENTER);
        SUBMIT.addActionListener(this);
        setTitle("LOGIN");
    }

    /* metod koji se javlja klikom na login button */

    public void actionPerformed(ActionEvent ae)
    {
        username = text1.getText();
        password = text2.getText();

        /* ako user ne unese nikakav username, aplikacija se nece konektovati na bazu tako da se nece
        * pojaviti error i kocenje aplikacije ako server nije dostupan */
        if(!username.isEmpty()) {
            Connection conn;

            String user;
            String pass;


            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            try {

                conn =
                        DriverManager.getConnection("jdbc:mysql://localhost:3306/spacer", "root", "");

                // Statement statement = conn.createStatement();



            /* statement.executeUpdate("INSERT INTO user  " +
                    "VALUES (null, '" + username + "','" + password +"', 0,0,0,10 )");

                    */


                Statement stm = conn.createStatement();

                ResultSet rs = stm.executeQuery("SELECT * FROM user WHERE username = '" + username
                        + "' and password = '" + password + "'");


                // System.out.println(username);

                /* radi dok result set ima iduci clan */
                while (rs.next()) {
                    if (username == null || password == null) {
                        JOptionPane.showMessageDialog(this, "Input fields!");
                        Game.gameState = Game.STATE.Start;
                    }
                    if (username.equals(rs.getString("username")) &&
                            password.equals(rs.getString("password"))) {
                        loggedIn = true;

                    } else {
                        Game.gameState = Game.STATE.Start;
                        loggedIn = true;
                    }
                }

        /* while(rs.next()) {

                user = rs.getString("username");
                pass = rs.getString("password");

                if (user.equals(username) && pass.equals(password)) {
                    Statement stm1 = conn.createStatement();

                    System.out.println(user);
                    System.out.println(pass);

                    ResultSet rs1 = stm1.executeQuery("SELECT * from user WHERE username = 'admin'");

                    while(rs1.next()) {
                        HUD.goldCount = rs1.getInt("gold");
                        System.out.println(HUD.goldCount);
                    }

                    conn.close();
                    statement.close();
                }else{
                    JOptionPane.showMessageDialog(this, "Error.");
                }


            } */

                conn.close();
                stm.close();
                rs.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }


        }

        /* igra pocinje, pusta se pjesma i login prozor nestaje*/

            Game.gameState = Game.STATE.Start;
            this.setVisible(false);


            try {
                File file = new File("resources/GameSong.wav");
                AudioInputStream stream = AudioSystem.getAudioInputStream(file);
                clip = getClip();
                clip.open(stream);


                float volume = 0.4f;
                if (volume < 0f || volume > 1f)
                    throw new IllegalArgumentException("Volume not valid: " + volume);
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(20f * (float) Math.log10(volume));
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);

                stream.close();

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }



    }

}


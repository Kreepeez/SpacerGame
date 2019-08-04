package com.company;


import javax.sound.sampled.*;

import java.io.File;

import static javax.sound.sampled.AudioSystem.getClip;


public class AudioHandler {

    /*
     ova klasa sluzi za upravljanje zvukom
    */



    public static Clip clip;


      /* metod koji prima path do fajla i float volume
      * svrha je da se pusti zvuk*/
    public static void playSound(String filepath, float volume){

        try {
        File file = new File(filepath);
        AudioInputStream stream = AudioSystem.getAudioInputStream(file);
        clip = getClip();
        clip.open(stream);

        /* provjera volumena */
            if (volume < 0f || volume > 1f)
                throw new IllegalArgumentException("Volume not valid: " + volume);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(20f * (float) Math.log10(volume));
        clip.start();

        stream.close();


    } catch (Exception ex) {
        System.out.println(ex.getMessage());
    }
    }

/*metod koji bi trebao da zaustavi zvuk ali nije uspjesno implementiran */
    public void stopSound()
    {

        try {
            clip = getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }


            clip.stop();

            clip.setFramePosition(0);

    }
}

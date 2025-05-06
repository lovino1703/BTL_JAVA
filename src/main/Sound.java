package main;

import javax.sound.sampled.*;
import java.net.URL;

public class Sound {
    Clip clip;
    URL soundURL[] = new URL[30];
    FloatControl fc;
    int volumeScale = 3;
    float volume;
    public Sound(){
        soundURL[0] = getClass().getResource("/sounds/LittlerootTown.wav");
        soundURL[1] = getClass().getResource("/sounds/coin.wav");
        soundURL[2] = getClass().getResource("/sounds/powerup.wav");
        soundURL[3] = getClass().getResource("/sounds/unlock.wav");
        soundURL[4] = getClass().getResource("/sounds/fanfare.wav");
        soundURL[5] = getClass().getResource("/sounds/hitmonster.wav");
        soundURL[6] = getClass().getResource("/sounds/receivedamage.wav");
        soundURL[7] = getClass().getResource("/sounds/swingweapon.wav");
        soundURL[8] = getClass().getResource("/sounds/levelup.wav");
        soundURL[9] = getClass().getResource("/sounds/cursor.wav");
        soundURL[10] = getClass().getResource("/sounds/burning.wav");
        soundURL[11] = getClass().getResource("/sounds/cuttree.wav");
        soundURL[12] = getClass().getResource("/sounds/gameover.wav");
        soundURL[13] = getClass().getResource("/sounds/stairs.wav");
        soundURL[14] = getClass().getResource("/sounds/blocked.wav");
        soundURL[15] = getClass().getResource("/sounds/parry.wav");
    }

    public void setFIle(int i){
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);//get the audio file from soundURL[i]
            clip = AudioSystem.getClip();//create a Clip instance
            clip.open(ais);//open the file using that clip instance
            fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);//let the fc control the volume
            checkVolume();//call the check volume method
        }catch (Exception e) {
        }
    }

    public void play(){
        clip.start();
    }

    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop(){
        clip.stop();
    }

    public void checkVolume(){
        switch (volumeScale){
            case 0: volume = -80f; break;
            case 1: volume = -20f; break;
            case 2: volume = -12f; break;
            case 3: volume = -5f; break;
            case 4: volume = 1f; break;
            case 5: volume = 6f; break;
        }
        fc.setValue(volume);
    }

}

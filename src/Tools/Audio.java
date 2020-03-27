package Tools;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import java.io.File;

public class Audio {
    private static Clip clip;
    private static String source;

    public Audio(String path){
        source = path;
    }

    public void playSound() {
        try {
            clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(Constants.AUDIO_LOCATION + source).getAbsoluteFile());
            clip.open(inputStream);
            clip.start();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }


    public void playSoundLoop() {
        try {
            clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(Constants.AUDIO_LOCATION + source).getAbsoluteFile());
            clip.open(inputStream);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    public void stopAudio() {
        clip.stop();
        clip.close();
    }
}



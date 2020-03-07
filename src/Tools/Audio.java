package Tools;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import java.io.File;

public class Audio {
    private static Clip clip;

    public static void playSound(final String audio) {
        try {
            clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(Constants.AUDIO_LOCATION + audio).getAbsoluteFile());
            clip.open(inputStream);
            clip.start();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }


    public static void playSoundLoop(final String audio) {
        try {
            clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(Constants.AUDIO_LOCATION + audio).getAbsoluteFile());
            clip.open(inputStream);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    public static void stopAudio() {
        clip.stop();
        clip.close();
    }
}



package Tools;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.rmi.server.ExportException;

public class Audio {
    Clip clip;
    AudioInputStream audioInputStream;
    private static String source;
    private long currentFrame;

    public Audio(String path) {
        source = path;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File(Constants.AUDIO_LOCATION + source).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void playSound() {
        try {
            clip.start();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }


    public void playSoundLoop() {
        try {
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

    public void pauseAudio() {
        currentFrame = clip.getMicrosecondPosition();
        stopAudio();
    }

    public void resumeAudio() {
        try {
            resetAudio();
            clip.setMicrosecondPosition(currentFrame);
            playSoundLoop();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void resetAudio() {
        try {
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(Constants.AUDIO_LOCATION + source).getAbsoluteFile());
            clip.open(inputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}



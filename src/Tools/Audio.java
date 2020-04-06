package Tools;

import javax.sound.sampled.*;
import java.io.File;
import javax.sound.sampled.FloatControl;

public class Audio {
    private Clip clip;
    AudioInputStream audioInputStream;
    String source;
    FloatControl gainControl;

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
        clip.start();
    }
    public void playSoundLoop() {
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stopAudio() {
        clip.stop();
    }
    public void closeAudio() {
        clip.close();
    }
    public void setVolume(float volume){
        if(volume < 0f || volume > 1f)
            throw new IllegalArgumentException("Volume not valid: "+ volume);
        gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        //convert volume to linear scale for easier usage
        gainControl.setValue(20f * (float) Math.log10(volume));
    }
    public boolean isRunning(){ return clip.isRunning();}
}



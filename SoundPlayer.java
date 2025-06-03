import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class SoundPlayer {
    public static void playSound(String fileName) {
        try {
            File soundFile = new File(fileName);
            if (!soundFile.exists()) {
                System.out.println("Sound file not found: " + soundFile.getAbsolutePath());
                return;
            }

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}

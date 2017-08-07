package snakes.game;

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/** 
 * @author Jason Skinner, Leroy Nguyen, Dawood Choksi, Alessa Ivascu, Christiana
 *         Papajani
 * This is an abstract class which enables the input/output and control of a particular audio file chosen.
 */
public abstract class AudioFilePlayer {

    private static Thread music;

    /**
     * The AudioFilePlayer constructor always calls upon the runner method, to handle the 
     * running of the audio file.
     */
    public AudioFilePlayer() {
        runner();
    }

    /**
     * This is a static method which has an anonymous inner class inside of it. Inside of it is all
     * the code which gets the clip/music and it plays it continuously
     */
    public static void runner() {
    	
        /**
         * EXAMPLE OF AN ANONYMOUS INNER CLASS
         * This is the anonymous inner class inside of the runner method, used to 
         * take an audio file and attempt to read it, store it, and play it.
         * 
         */
        Runnable play = new Runnable() {

            @Override
            /**
             * This method gets the clip by creating an object called file, places the audio file 
             * in the file variable, sets the position 
             * of the frame to 0 using setFramePosition method and then loops the clip continuously.
              */
            public void run() {

                try {
                	
                	//Attempts to retrieve the file.
                    File file = new File("jb.wav");
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file.getAbsoluteFile());
                    AudioFormat format = audioInputStream.getFormat();

                    // This gets the clip, sets the position of the frame to 0,
                    // then loops the clip continuously.
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.setFramePosition(0);
                    clip.loop(Clip.LOOP_CONTINUOUSLY);

                    long audioFileLength = file.length();
                    int frameSize = format.getFrameSize();
                    float frameRate = format.getFrameRate();
                    float durationInSeconds = (audioFileLength / (frameSize * frameRate));
                    Thread.sleep((long) (durationInSeconds * 1000));
                } catch (Exception e) {
                    System.out.println("");
                }
            }
        };
        music = new Thread(play);
        music.start();
    }

    /**
     * This is a void method which makes it possible to pause the music.
     * @throws InterruptedException when a thread is waiting or 
     * sleeping and another thread interrupts it using the interrupt method in class Thread.
     * The method wait is called whenever the music is interrupted
     */
    public void pause() throws InterruptedException {
        music.wait();
    }

    /**
     * This method resumes the music after it has been paused
     * The notify method is called whenever the music has been resumed
     */
    public void resume() {
        music.notify();
    }
}
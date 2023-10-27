package model;

import com.voicerss.tts.AudioFormat;
import com.voicerss.tts.*;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class VoiceRssAPI {
    private static final String API_KEY = "ee1a861047db41e3aed6cca75554a826";
    private static final String AUDIO_PATH = "src\\\\main\\\\resources\\\\data\\\\audio.mp3";

    public static String language = "en-us";
    public static String Name = "Linda";
    public static double speed = 1;
    public static void speakWord(String word) throws Exception {
        VoiceProvider tts = new VoiceProvider(API_KEY);
        VoiceParameters params = new VoiceParameters(word, AudioFormat.Format_44KHZ.AF_44khz_16bit_stereo);
        params.setBase64(false);
        params.setLanguage(language);
        params.setVoice(Name);
        params.setRate((int) Math.round(-2.9936 * speed * speed + 15.2942 * speed - 12.7612));

        byte[] voice = tts.speech(params);

        FileOutputStream fos = new FileOutputStream(AUDIO_PATH);
        fos.write(voice, 0, voice.length);
        fos.flush();
        fos.close();
        play();
    }

    public static void play() {
        File audioFile = new File(AUDIO_PATH);
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
            Thread.sleep(clip.getMicrosecondLength() / 1000);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}

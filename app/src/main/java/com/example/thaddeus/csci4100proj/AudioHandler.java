package com.example.thaddeus.csci4100proj;

import android.content.Context;
import android.graphics.Paint;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class AudioHandler {
    private MediaPlayer mediaPlayer;
    private SoundPool soundPool;

    private int crashId;

    public AudioHandler(Context context) {
        try{
            mediaPlayer = MediaPlayer.create(context, R.raw.carpe_diem);
            mediaPlayer.setLooping(true);

            soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
            crashId = soundPool.load(context, R.raw.smack, 1);
        } catch (Exception e) {
            mediaPlayer = null;
            e.printStackTrace();
        }
    }

    public void playCrashSound() {
        soundPool.play(crashId, 1.0f, 1.0f, 0, 0, 1);
    }

    public void pause(boolean finishing){
        if(mediaPlayer != null) {
            mediaPlayer.pause();
            if(finishing) {
                mediaPlayer.stop();
                mediaPlayer.release();
            }
        }
    }

    public void resume() {
        if(mediaPlayer != null){
            mediaPlayer.start();
        }
    }
}

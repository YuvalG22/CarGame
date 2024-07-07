package com.example.cargame.Utilities;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;

import com.example.cargame.R;

public class SoundPlayer2 {
    private SoundPool soundPool;
    private int crashSound;

    public SoundPlayer2(Context context) {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(3)
                .setAudioAttributes(audioAttributes)
                .build();

        crashSound = soundPool.load(context, R.raw.crash, 1);
    }

    public void playCrashSound() {
        soundPool.play(crashSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void release() {
        soundPool.release();
        soundPool = null;
    }
}

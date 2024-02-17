package main;


import java.net. URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
    
    Clip clip;
    URL soundURL[] = new URL[30];
    FloatControl fc;
    public int volumeScale = 50; // ボリューム調整用のスケール
    float volume;

    public Sound() {
        // サウンドファイルのURLを設定
        soundURL[0] = getClass().getResource("/res/sound/BGM2.wav");
        soundURL[1] = getClass().getResource("/res/sound/coin.wav");
        soundURL[2] = getClass().getResource("/res/sound/Meat.wav");
        soundURL[3] = getClass().getResource("/res/sound/PUp.wav");
        soundURL[4] = getClass().getResource("/res/sound/PUp.wav");
        soundURL[5] = getClass().getResource("/res/sound/OK.wav");
        soundURL[6] = getClass().getResource("/res/sound/Cancel.wav");
        soundURL[7] = getClass().getResource("/res/sound/Cursor_1.wav");
        soundURL[8] = getClass().getResource("/res/sound/Attack.wav");
        soundURL[9] = getClass().getResource("/res/sound/dying.wav");
        soundURL[10] = getClass().getResource("/res/sound/GameOver.wav");
        soundURL[11] = getClass().getResource("/res/sound/TitleBGM.wav");
        soundURL[12] = getClass().getResource("/res/sound/ball.wav");
    }

    public void setFile(int i) {
        try {
            // サウンドファイルの読み込みとクリップの設定
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);

            // ボリュームコントロールの設定
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();
        } catch (Exception e) {
            // 例外処理（エラー時の動作を追加）
            e.printStackTrace();
        }
    }

    public void play() {
        // サウンドの再生
        clip.start();
    }

    public void loop() {
        // サウンドをループ再生
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        // サウンドの停止
        clip.stop();
    }

    public void checkVolume() {
        // ボリュームスケールを元にボリュームを設定
        if (volumeScale == 0) {
            volume = -80f; // ボリュームスケールが0の場合は最小音量
        } else {
            // ボリュームスケールを元に音量を設定
            volume = 0.3f * (volumeScale - 80);
        }
        fc.setValue(volume);
    }
}
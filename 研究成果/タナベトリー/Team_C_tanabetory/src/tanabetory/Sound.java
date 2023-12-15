package tanabetory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import gamePanel.GamePanel;

public class Sound {
    public Clip bgmClip;
    private List<Clip> seClips; // SEを格納するリスト
    private List<Clip> seTitleClips;
    
    public Sound(TitlePanel titlePanel) {
        loadBGM("sound/bgm/title_bgm.wav");
        
        seTitleClips = new ArrayList<>();
        
        loadTitleSE("sound/se/sentaku.wav");
        loadTitleSE("sound/se/kakutei.wav");
    }

    public Sound(GamePanel gamePanel) {
    	
        loadBGM("sound/bgm/game_bgm.wav");
        
        seClips = new ArrayList<>(); // SEのリストを初期化
        
        // SEのロード、上から0,1...
        loadSE("sound/se/se_jump.wav");			// ジャンプ音 0
        loadSE("sound/se/player_shot.wav"); 	// プレイヤー発射 1
        loadSE("sound/se/enemy_shot.wav");		// エネミー発射 2
        loadSE("sound/se/player_hit.wav");		// プレイヤー被弾 3
        loadSE("sound/se/enemy_hit.wav");		// エネミー被弾 4
        loadSE("sound/se/item.wav");			// アイテム取得 5
        loadSE("sound/se/tanabe_damage.wav");	// プレイヤー被弾 6
        loadSE("sound/se/attack.wav");			// たなべかったー 7
    }
    
    
    public Sound(ResultPanel resultPanel) {
    	loadBGM("sound/bgm/result_bgm.wav");
    }

    public Sound(PosePanel posePanel) {
		
	}

	private void loadBGM(String bgmFilePath) {
        try {
            File bgmFile = new File(bgmFilePath);
            bgmClip = loadClip(bgmFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void playBGM() {
        if (bgmClip != null) {
            bgmClip.start();
            bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
    
    private void loadSE(String seFilePath) {
        try {
            File seFile = new File(seFilePath);
            Clip seClip = loadClip(seFile); // 新しいClipを作成
            if (seClip != null) {
                seClips.add(seClip); // SEをリストに追加
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void loadTitleSE(String seFilePath) {
        try {
            File seFile = new File(seFilePath);
            Clip seClip = loadClip(seFile); // 新しいClipを作成
            if (seClip != null) {
            	seTitleClips.add(seClip); // SEをリストに追加
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Clip loadClip(File audioFile) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(audioFile));
            return clip;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void playSE(int index) {
        if (index >= 0 && index < seClips.size()) {
            Clip seClip = seClips.get(index);
            seClip.setFramePosition(0);
            seClip.start();
        }
    }
    
    public void playTitleSE(int index) {
        if (index >= 0 && index < seTitleClips.size()) {
            Clip seTitleClip = seTitleClips.get(index);
            seTitleClip.setFramePosition(0);
            seTitleClip.start();
        }
    }
    
    public void adjustBGMVolume(float volume) {
        if (bgmClip != null) {
            FloatControl control = (FloatControl) bgmClip.getControl(FloatControl.Type.MASTER_GAIN);
            // 音量を設定（負の値は小さく、正の値は大きくなります）
            control.setValue(volume);
        }
    }
    
    public void stopBGM() {
        if (bgmClip != null && bgmClip.isRunning()) {
            bgmClip.stop();
        }
    }
    
    public void resetBGMPosition() {
        if (bgmClip != null) {
            bgmClip.setFramePosition(0);
        }
    }


}

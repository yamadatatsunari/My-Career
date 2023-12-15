package monster;

import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class MON_Human1 extends Entity{
    GamePanel gp;
	// コンストラクタ
    public MON_Human1(GamePanel gp) {
        super(gp);

        // モンスターの属性と初期設定
        type = 2; // モンスターの種類
        direction = "down"; // 初期移動方向
     
		speed = new Random().nextInt(2) + 1;
		maxLife = new Random().nextInt(gp.player.level * 2) + 10;
		atk = new Random().nextInt(gp.player.level/5+1) + 1;
		life = maxLife; // 現在の体力

        // モンスターの当たり判定領域の設定
        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        // モンスターの画像読み込み
        getImage();
    }

    // モンスターの画像を読み込むメソッド
	public void getImage() {
		try {
			up1 = ImageIO.read(getClass().getResourceAsStream("/res/monster/enemy_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/res/monster/enemy_up_2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/res/monster/enemy_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/res/monster/enemy_down_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/res/monster/enemy_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/res/monster/enemy_left_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/res/monster/enemy_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/res/monster/enemy_right_2.png"));
		
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	 // モンスターの行動を設定するメソッド
    public void setAction() {
        Random random = new Random();
        int i = random.nextInt(4) + 1; // ランダムに行動選択

        actionLockCounter++;

        if (actionLockCounter >= 60) { // 一定時間ごとに行動を変更

            if (i == 1) {
                direction = "up";
            }

            if (i == 2) {
                direction = "right";
            }

            if (i == 3) {
                direction = "left";
            }

            if (i == 4) {
                direction = "down";
            }

            actionLockCounter = 0; // 行動ロックをリセット
        }
    }
    
    public void damageReaction() {
    	actionLockCounter = 0;
    	direction = gp.player.direction;
    }
    
}
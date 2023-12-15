package monster;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;
import object.OBJ_OllUp;

public class MON_Human4 extends Entity {
    GamePanel gp;
    
    // コンストラクタ
    public MON_Human4(GamePanel gp) {
        super(gp);

        // モンスターの属性と初期設定
        type = 2; // モンスターの種類
        direction = "down"; // 初期移動方向
        speed = 3; // 移動速度
        maxLife = 40 + (20 * gp.player.bossCount); // 最大体力
        life = maxLife; // 現在の体力
        atk = 2 + (1 * gp.player.bossCount);
        def = 5;
        
        // モンスターの当たり判定領域の設定
        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 100;
        solidArea.height = 120;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        bosstag = true;
        // モンスターの画像読み込み
        getImage();
    }
    
    public void checkDrop() {
    	dropItem(new OBJ_OllUp());
	}

    // モンスターの画像を読み込むメソッド
	public void getImage() {
		try {

			up1 = ImageIO.read(getClass().getResourceAsStream("/res/monster/boss_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/res/monster/boss_up_2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/res/monster/boss_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/res/monster/boss_down_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/res/monster/boss_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/res/monster/boss_left_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/res/monster/boss_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/res/monster/boss_right_2.png"));
	
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
    
    // グラフィックのアルファ値（透明度）を変更するメソッド
    public void changeAlpha(Graphics2D g2, float alphavalue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphavalue));
    }

    // エンティティの死亡アニメーションを処理するメソッド
	public void dyingAnimation(Graphics2D g2) {
		
		dyingCounter++;
		
		int i = 5;
		
		if(dyingCounter <= i) {changeAlpha(g2, 0f);}
		if(dyingCounter > i && dyingCounter <= i * 2) {changeAlpha(g2, 1f);}
		if(dyingCounter > i * 2 && dyingCounter <= i * 3) {changeAlpha(g2, 0f);}
		if(dyingCounter > i * 3 && dyingCounter <= i * 4) {changeAlpha(g2, 1f);}
		if(dyingCounter > i * 4 && dyingCounter <= i * 5) {changeAlpha(g2, 0f);}
		if(dyingCounter > i * 5 && dyingCounter <= i * 6) {changeAlpha(g2, 1f);}
		if(dyingCounter > i * 6 && dyingCounter <= i * 7) {changeAlpha(g2, 0f);}
		if(dyingCounter > i * 7 && dyingCounter <= i * 8) {changeAlpha(g2, 1f);}
		if(dyingCounter > i * 8) {
			dying = false;
			alive = false;
			checkDrop();
			boss = false;
		}
	}
    
	// エンティティを画面上に描画するメソッド
	public void draw(Graphics2D g2, GamePanel gp) {
	    BufferedImage image = null;
	    

	    // 画面上の表示位置を計算
	    int screenX = worldX - gp.player.worldX + gp.player.screenX;
	    int screenY = worldY - gp.player.worldY + gp.player.screenY;

	    // エンティティの方向に応じて対応するスプライト画像を選択
	    switch (direction) {
	        case "up":
	            if (spriteNum == 1) {
	                image = up1;
	            }
	            if (spriteNum == 2) {
	                image = up2;
	            }
	            break;
	        case "down":
	            if (spriteNum == 1) {
	                image = down1;
	            }
	            if (spriteNum == 2) {
	                image = down2;
	            }
	            break;
	        case "left":
	            if (spriteNum == 1) {
	                image = left1;
	            }
	            if (spriteNum == 2) {
	                image = left2;
	            }
	            break;
	        case "right":
	            if (spriteNum == 1) {
	                image = right1;
	            }
	            if (spriteNum == 2) {
	                image = right2;
	            }
	            break;
	        case "stop":
	            if (spriteNum == 1) {
	                image = down1;
	            }
	            if (spriteNum == 2) {
	                image = down1;
	            }
	            break;
	    }

	    // エンティティのタイプが2でHPバーが表示されている場合、HPバーを描画
	    if (type == 2 && hpBarOn == true) {
	        // HPバーのサイズを計算
	        double oneScale = (double) gp.tileSize * 3 / maxLife;
	        double hpBarValue = oneScale * life;

	        g2.setColor(new Color(35, 35, 35));
	        g2.fillRect(screenX - 1, screenY - 16, gp.tileSize * 3 + 2, 12); // 黒の背景

	        g2.setColor(new Color(255, 0, 30));
	        g2.fillRect(screenX, screenY - 15, (int) hpBarValue, 10); // 赤いHPバー

	        hpBarCounter++;

	        // HPバー表示時間が一定期間を超えたら非表示にする
	        if (hpBarCounter > 600) {
	            hpBarCounter = 0;
	            hpBarOn = false;
	        }
	    }

	    // エンティティが無敵状態の場合、透明度を変更して描画
	    if (invincible == true) {
	        hpBarOn = true; // HPバーを表示
	        hpBarCounter = 0; // HPバーのカウンタをリセット
	        changeAlpha(g2, 0.4F); // 透明度を設定
	    }

	    // エンティティが死亡中の場合、死亡アニメーションを実行
	    if (dying == true) {
	        dyingAnimation(g2);
	    }

	    // スプライト画像を画面に描画
	    g2.drawImage(image, screenX, screenY, gp.tileSize * 3, gp.tileSize * 3, null);

	    changeAlpha(g2, 1F); // 透明度を元に戻す
	}
}
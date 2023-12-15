package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import main.GamePanel;
import object.OBJ_AtkUp;
import object.OBJ_LifeUp;
import object.OBJ_Meat;
import object.SuperObject;

public class Entity {
	
		GamePanel gp;
		
        // 各移動時の画像
        public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
        
        // 各攻撃時の画像
        public BufferedImage attackUp1, attackUp2, attackUp3, attackDown1, attackDown2,
        attackLeft1, attackLeft2, attackRight1, attackRight2;
        
        public int worldX, worldY; // 座標
        public int speed; // 速度
        public int invicibleCounter = 0;  // 無敵状態の継続時間を示すカウンタ
        public int type;  // エンティティのタイプ
        public int maxLife;  // エンティティの最大ライフポイント
        public int life;  // エンティティの現在のライフポイント
        public int attack; // 遠距離攻撃の攻撃力
        public int atk = 1;  // 攻撃力
        public int def = 30; //即死耐性
        public int shotAvailableCounter = 0;
        public int hpBarCounter = 0;  // HPバーの表示時間を示すカウンタ
        public int dyingCounter = 0;  // 死亡アニメーションのためのカウンタ
        public int solidAreaDefaultX, solidAreaDefaultY; // 衝突判定の領域
        public int spriteCounter = 0; // スプライトアニメーションのためのカウンタ
        public int spriteNum = 1; // 現在のスプライト画像番号
        public int actionLockCounter = 0;  // アクションロックのためのカウンタ
        
        public boolean boss = false; //ボスフラグ 新規追加
    	public boolean bosstag = false; //ボスタグ 新規追加
        public boolean alive = true;  // エンティティが生きているかどうかを示すフラグ
        public boolean dying = false;  // エンティティが死亡中であるかを示すフラグ
        public boolean invincible = false;  // 無敵状態かどうかを示すフラグ
        public boolean collisiOn = false;  // 他のオブジェクトとの衝突を示すフラグ
        public boolean attacking = false; // 攻撃中かどうかを示すフラグ
        public boolean hpBarOn = false;  // HPバーを表示するフラグ
        
        public String direction; // 向き
        public String name; // オブジェクトの名前
        public Projectile projectile; // 遠距離攻撃
        public Rectangle solidArea = new Rectangle(0, 0, 48, 48);  // 衝突領域
        public Rectangle attackArea = new Rectangle(0, 0, 0, 0);  // 攻撃領域

        // エンティティのアクションを設定するメソッド
        public void setAction() {}
        
        // ドロップアイテムの確認を行うメソッド
        public void checkDrop() {
    		int i = new Random().nextInt(100)+1;
    		int j = new Random().nextInt(2)+1;
    		
    		if(i <= 30) {
    			if(j == 1) {
    				dropItem(new OBJ_AtkUp());
    			}
    			if(j == 2) {
    				dropItem(new OBJ_LifeUp());
    			}
    		} else if(gp.player.life < gp.player.maxLife) {
    			dropItem(new OBJ_Meat());
    		}
    	}
        // ドロップアイテムが落ちる位置を指定するメソッド
        public void dropItem(SuperObject droppedItem) {
        	for(int i = 0; i < gp.obj.length; i++) {
        		if(gp.obj[i] == null) {
        			gp.obj[i] = droppedItem;
        			gp.obj[i].worldX = worldX;
        			gp.obj[i].worldY = worldY;
        			break;
        		}
        	}
        }
        // エンティティの状態を更新するメソッド
        public void update() {
            setAction();
    		collisiOn = false;
    		
    		gp.cChecker.checkTile(this);
    		gp.cChecker.checkObject(this, false);
    		gp.cChecker.checkEntity(this,  gp.monster1);
        	boolean contactPlayer = gp.cChecker.checkPlayer(this);
        	
        	if(this.type == 2 && contactPlayer == true && gp.player.attacking == false) {
        		if(gp.player.invincible == false){
        			gp.player.life -= this.atk;
        			gp.player.invincible = true;
        		}
        	}

            if (collisiOn == false) {
                // 方向と速度に基づいてエンティティの位置を更新
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;

                }
            }

            spriteCounter++;
            if (spriteCounter > 10) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }

            if (invincible == true) {
                invicibleCounter++;
                if (invicibleCounter > 30) {
                    invincible = false;
                    invicibleCounter = 0;
                }
            }
        }

        // Entityクラスのコンストラクタ
        public Entity(GamePanel gp) {
            this.gp = gp;
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
    	    }

    	    // エンティティのタイプが2でHPバーが表示されている場合、HPバーを描画
    	    if (type == 2 && hpBarOn == true) {
    	        // HPバーのサイズを計算
    	        double oneScale = (double) gp.tileSize / maxLife;
    	        double hpBarValue = oneScale * life;

    	        g2.setColor(new Color(35, 35, 35));
    	        g2.fillRect(screenX - 1, screenY - 16, gp.tileSize + 2, 12); // 黒の背景

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
    	    g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

    	    changeAlpha(g2, 1F); // 透明度を元に戻す
    	}
    
 }
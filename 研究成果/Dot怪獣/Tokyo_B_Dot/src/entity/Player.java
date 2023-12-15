package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.Sound;
import main.UtilityTool;
import object.OBJ_Fireball;

public class Player extends Entity {
    KeyHandler keyH; // キー入力
	Random random = new Random(); // ランダムインスタンス生成
    Sound sound = new Sound(); // サウンドエフェクト (SE) クラスのインスタンス生成

    public final int screenX; // 初期位置X
    public final int screenY; // 初期位置Y

    public int level = 1; // レベル
	public int atkWait = 0; // 攻撃の間
	public int OSKCount = 0; // 一撃必殺の確率計算
	public int bossCount = 0; // ボス
	
	boolean chance = false; // 一撃必殺可能か
	boolean oneShotKill = false; // 一撃必殺

	// プレイヤーキャラクターの初期位置等の設定
    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);

        this.keyH = keyH;

        // プレイヤーキャラクターの画面上の初期位置を設定
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        // プレイヤーキャラクターの当たり判定エリアの設定
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        // 攻撃判定エリアの設定
        attackArea.width = 36;
        attackArea.height = 36;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
    }

    // プレイヤーキャラクターの初期位置を設定
    public void setDefaultPositions() {
        worldX = gp.tileSize * 24;
        worldY = gp.tileSize * 10;
        direction = "down";
    }

    // プレイヤーキャラクターの生命力と無敵状態を初期状態に戻す
    public void restoreLifeAndMan() {
        atk = 5;
        maxLife = 10;
        life = maxLife;
        invincible = false;
    }

    // プレイヤーキャラクターの初期ステータスを設定
    public void setDefaultValues() {
        worldX = gp.tileSize * 24;
        worldY = gp.tileSize * 10;
        speed = 10;
        direction = "down";
        atk = 5;
        maxLife = 10;
        level = 1; //修正
        life = maxLife;
        projectile = new OBJ_Fireball(gp);
    }
    // プレイヤーキャラクターのワールドリセット時のステータスを設定
	public void reset() {
		setDefaultValues();
		getPlayerImage();
		getPlayerAttackImage();
		for(int i = 0; i < gp.monster1.length; i++) {
			gp.monster1[i] = null;
		}
		for(int i = 0; i < gp.monster2.length; i++) {
			gp.monster2[i] = null;
		}
		for(int i = 0; i < gp.monster4.length; i++) {
			gp.monster4[i] = null;
		}
		for(int i = 0; i < gp.obj.length; i++) {
			gp.obj[i] = null;
		}
		gp.ESp.spCount = false;
		gp.ESp.spTime = 0;
		gp.ui.score = 0;
		gp.reset = false;
	}

    // プレイヤーキャラクターの通常状態の画像を読み込む
    public void getPlayerImage() {
        // 通常状態の画像をリサイズして読み込む
        up1 = setup("/res/player/player_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/res/player/player_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/res/player/player_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/res/player/player_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/res/player/player_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/res/player/player_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/res/player/player_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/res/player/player_right_2", gp.tileSize, gp.tileSize);
    }

    // プレイヤーキャラクターの攻撃状態の画像を読み込む
    public void getPlayerAttackImage() {
        // 攻撃状態の画像をリサイズして読み込む
        attackUp1 = setup("/res/player/player_attack_up_1", gp.tileSize, gp.tileSize * 2);
        attackUp2 = setup("/res/player/player_attack_up_2", gp.tileSize, gp.tileSize * 2);
        attackUp3 = setup("/res/player/player_attack_up_3", gp.tileSize, gp.tileSize * 2);
        attackDown1 = setup("/res/player/player_attack_down_1", gp.tileSize, gp.tileSize * 2);
        attackDown2 = setup("/res/player/player_long_attack_down_2", gp.tileSize, gp.tileSize * 3);
        attackLeft1 = setup("/res/player/player_attack_left", gp.tileSize * 2, gp.tileSize);
        attackLeft2 = setup("/res/player/player_attack_left_2", gp.tileSize * 2, gp.tileSize);
        attackRight1 = setup("/res/player/player_attack_right", gp.tileSize * 2, gp.tileSize);
        attackRight2 = setup("/res/player/player_attack_right_2", gp.tileSize * 2, gp.tileSize);
    }

    // 画像の読み込みとリサイズ
    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool utool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = utool.scaleImage(image, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
    
    // プレイヤーの状態を更新するメソッド
    public void update() {
        // プレイヤーキャラクターが攻撃中の場合、攻撃処理を実行
    	if(atkWait <= 15) {
			atkWait++;
		}
    	
		//新規追加-----
		if(gp.reset == true) {
			reset();
		}
    	
    	if (attacking == true) {
			atkWait = 0;
            attacking();
        }

        // キーボードの入力に応じてプレイヤーキャラクターの座標を更新
        else if (keyH.upPressed == true || keyH.downPressed == true ||
                keyH.leftPressed == true || keyH.rightPressed == true) {
            // キーボード入力に応じた向きを設定
            if (keyH.upPressed == true) {
                direction = "up";
            } else if (keyH.downPressed == true) {
                direction = "down";
            } else if (keyH.leftPressed == true) {
                direction = "left";
            } else if (keyH.rightPressed == true) {
                direction = "right";
            }

            // ダッシュ機能の処理
            if (keyH.shiftPressed == true) {
                speed = 6;
            } else {
                speed = 4;
            }
            // デバッグ機能の処理
			if(keyH.Debug1 == true) {
				if(collisiOn == false) {
					collisiOn = true; 
				} else if(collisiOn == true) {
					collisiOn = false;
				}
			}
			if(keyH.Debug2 == true) {
				speed += keyH.debugS;
				if(speed <= 0) {
					speed = 0;
				}
			}
			if(keyH.Debug2 == false) {
				keyH.debugS = 0;
			}
			
            collisiOn = false;
            gp.cChecker.checkTile(this);
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            int monsterIndex = 0;
            // モンスターとの接触を確認
            monsterIndex = gp.cChecker.checkEntity(this, gp.monster1);
            contactMonster1(monsterIndex);
            monsterIndex = gp.cChecker.checkEntity(this, gp.monster2);
            contactMonster2(monsterIndex);
			monsterIndex = gp.cChecker.checkEntity(this, gp.monster4);
			contactMonster4(monsterIndex);

            if (collisiOn == false) {
                // プレイヤーキャラクターの座標を更新
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
        }
    	
    	if(gp.keyH.shotKeyPressed == true && projectile.alive == false && shotAvailableCounter == 30) {
    		projectile.set(worldX, worldY, direction, true, this);
    		
    		gp.projectileList.add(projectile);
    		
    		shotAvailableCounter = 0;
    		
    		gp.playSE(12);
    	}
    	
    	if(shotAvailableCounter < 30) {
    		shotAvailableCounter++;
    	}
    	
        // プレイヤーキャラクターが無敵状態の場合、無敵時間をカウント
        if (invincible == true) {
            invicibleCounter++;
            if (invicibleCounter > 60) { // 無敵時間の設定値 (60フレーム)
                invincible = false;
                invicibleCounter = 0;
            }
        }

        // 攻撃ボタンが押された場合、攻撃状態に移行
        if (keyH.enterPressed == true) {
            attacking = true;
            if(atkWait >= 15) {
            	attacking = true;
            }
        }

        // プレイヤーキャラクターの生命力が0以下になった場合、ゲームオーバー処理に移行
        if (life <= 0) {
            gp.gameState = gp.gameOverState;
            gp.stopMusic();
            gp.playMusic(10);
            playSE(9); // ゲームオーバー時のSEを再生
        }
    }

 // プレイヤーキャラクターの攻撃処理
	public void attacking() {
		spriteCounter++;
		if(direction == "up" && keyH.enterPressed == true) {
			//上方向の処理
			
			if(spriteCounter <= 10) {
				spriteNum = 1;
			}
			if(spriteCounter > 15 && spriteCounter <= 25) {
				spriteNum = 2;
				int currentWorldX = worldX;
				int currentWorldY = worldY;
				int solidAreaWidth = solidArea.width;
				int solidAreaHeight = solidArea.height;
				
				if(chance == false) {
		            OSKCount = new Random().nextInt(100)+1;
					if(OSKCount <= 20) {
						oneShotKill = true;
					}
					chance = true;
				}
				
				switch(direction) {
				case "up": worldY -= attackArea.height; break;
				case "down": worldY += attackArea.height; break;
				case "left": worldX -= attackArea.width; break;
				case "right": worldX += attackArea.width; break;
				}
				
				solidArea.width = attackArea.width;
				solidArea.height = attackArea.height;
				
				int monsterIndex = 0;
				monsterIndex = gp.cChecker.checkEntity(this, gp.monster1);
				damageMonster1(monsterIndex, attack);
				monsterIndex = gp.cChecker.checkEntity(this, gp.monster2);
				damageMonster2(monsterIndex, attack);
				monsterIndex = gp.cChecker.checkEntity(this, gp.monster4);
				damageMonster4(monsterIndex, attack);
				
				worldX = currentWorldX;
				worldY = currentWorldY;
				solidArea.width = solidAreaWidth;
				solidArea.height = solidAreaHeight;
			}
			if(spriteCounter == 25) {
				gp.playSE(8);
			}
			if(spriteCounter > 25 && spriteCounter <= 40) {
				spriteNum = 3;
				int currentWorldX = worldX;
				int currentWorldY = worldY;
				int solidAreaWidth = solidArea.width;
				int solidAreaHeight = solidArea.height;
				
				switch(direction) {
				case "up": worldY -= attackArea.height; break;
				case "down": worldY += attackArea.height; break;
				case "left": worldX -= attackArea.width; break;
				case "right": worldX += attackArea.width; break;
				}
				
				solidArea.width = attackArea.width;
				solidArea.height = attackArea.height;
				
				int monsterIndex = 0;
				monsterIndex = gp.cChecker.checkEntity(this, gp.monster1);
				damageMonster1(monsterIndex, attack);
				monsterIndex = gp.cChecker.checkEntity(this, gp.monster2);
				damageMonster2(monsterIndex, attack);
				monsterIndex = gp.cChecker.checkEntity(this, gp.monster4);
				damageMonster4(monsterIndex, attack);
				
				worldX = currentWorldX;
				worldY = currentWorldY;
				solidArea.width = solidAreaWidth;
				solidArea.height = solidAreaHeight;
			}
			
			if(spriteCounter > 40) {
				spriteNum = 1;
				spriteCounter = 0;
				attacking = false;
				oneShotKill = false;
				gp.keyH.enterPressed = false;
				chance = false;
			}
		} else {
			//それ以外の処理
			if(spriteCounter <= 15) {
				spriteNum = 1;
			}
			if(spriteCounter == 15) {
				if(direction == "down") {
					gp.playSE(12);
				} else {
					gp.playSE(8);
				}
			}
			if(spriteCounter > 15 && spriteCounter <= 40) {
				spriteNum = 2;
				
				int currentWorldX = worldX;
				int currentWorldY = worldY;
				int solidAreaWidth = solidArea.width;
				int solidAreaHeight = solidArea.height;
				
				switch(direction) {
				case "up": worldY -= attackArea.height; break;
				case "down": worldY += attackArea.height*1.6; break;
				case "left": worldX -= attackArea.width; break;
				case "right": worldX += attackArea.width; break;
				}
				
				solidArea.width = attackArea.width;
				solidArea.height = attackArea.height;
				
				int monsterIndex = 0;
				monsterIndex = gp.cChecker.checkEntity(this, gp.monster1);
				damageMonster1(monsterIndex, attack);
				monsterIndex = gp.cChecker.checkEntity(this, gp.monster2);
				damageMonster2(monsterIndex, attack);
				monsterIndex = gp.cChecker.checkEntity(this, gp.monster4);
				damageMonster4(monsterIndex, attack);
				
				worldX = currentWorldX;
				worldY = currentWorldY;
				solidArea.width = solidAreaWidth;
				solidArea.height = solidAreaHeight;
			}
			
			if(spriteCounter > 40) {
				spriteNum = 1;
				spriteCounter = 0;
				attacking = false;
				gp.keyH.enterPressed = false;
			}
		}
	}

    // オブジェクトを拾う処理
    public void pickUpObject(int i) {
		if(i != 999) {
			//gp.obj[i] = null;
			//オブジェクトの名前取得
			String objectName = gp.obj[i].name;
			//オブジェクトごとの処理
			switch(objectName) {
			case "Meat":
				gp.playSE(2);
				if(life < maxLife - 1) {
					life += 2;
				} else if(life < maxLife) {
					life++;
				}
				gp.obj[i] = null;
				break;
			case "AtkUp":
				gp.playSE(3);
				atk++;
				level++;
    			gp.ui.score += 5;
				gp.obj[i] = null;
				break;
			case "LifeUp":
				gp.playSE(3);
				if(maxLife < 30) {
					maxLife += 2;
					level++;
					if(life < maxLife - 1) {
						life += 2;
					} else if(life < maxLife) {
						life++;
					}
				} else {
					if(life > maxLife - 10) {
						life = maxLife;
					} else if(life < maxLife) {
						life += 6;
					}
				}
    			gp.ui.score += 5;
				gp.obj[i] = null;
				break;
			case "OllUp":
				gp.playSE(3);
				if(atk < 20) {
					atk++;
					level++;
				}
				if(maxLife < 30) {
					maxLife += 2;
					level++;
				}
				level++;
				life = maxLife;
    			gp.ui.score++;
				gp.obj[i] = null;
				break;
			}
		}
	}

    // モンスター1との接触処理
    public void contactMonster1(int i) {
        if (i != 999) {
            // モンスター1に接触し、無敵状態でない場合、ダメージを受ける
            if (invincible == false) {
                life -= gp.monster1[i].atk;
                invincible = true;
            }
        }
    }

    // モンスター2との接触処理
    public void contactMonster2(int i) {
        if (i != 999) {
            // モンスター2に接触し、無敵状態でない場合、ダメージを受ける
            if (invincible == false) {
                life -= gp.monster2[i].atk;
                invincible = true;
            }
        }
    }
    
    // モンスター4との接触処理
    public void contactMonster4(int i) {
        if (i != 999) {
            // モンスター4に接触し、無敵状態でない場合、ダメージを受ける
            if (invincible == false) {
                life -= gp.monster4[i].atk;
                invincible = true;
            }
        }
    }

    // モンスター1にダメージを与える処理
    public void damageMonster1(int i, int attack) {
        if (i != 999) {
            if (gp.monster1[i].invincible == false) {
                //playSE(8); // モンスターにダメージを与えた際のSEを再生
				if(direction == "up" && keyH.enterPressed == true) {
					if(oneShotKill == true) {
						gp.monster1[i].life = 0;
		                gp.monster1[i].invincible = true;
						gp.ui.score += ((gp.monster1[i].atk + (gp.monster1[i].maxLife/2))*gp.monster1[i].speed)*0.1+1;
					}
				} else if(keyH.enterPressed == true){
					gp.monster1[i].life -= atk;
	                gp.monster1[i].invincible = true;
				} else {
					gp.monster1[i].life -= atk/2;
	                gp.monster1[i].invincible = true;
				}

                if (gp.monster1[i].life <= 0) {
                    gp.monster1[i].dying = true; // モンスターが死亡状態になる
                    gp.ui.score += (gp.monster1[i].atk + (gp.monster1[i].maxLife/2))*gp.monster1[i].speed;
                }
                
            }
        }
    }

    // モンスター2にダメージを与える処理
    public void damageMonster2(int i, int attack) {
        if (i != 999) {
            if (gp.monster2[i].invincible == false) {
                //playSE(8); // モンスターにダメージを与えた際のSEを再生
				if(direction == "up" && keyH.enterPressed == true) {
					if(oneShotKill == true) {
						gp.monster2[i].life = 0;
		                gp.monster2[i].invincible = true;
						gp.ui.score += ((gp.monster2[i].atk + (gp.monster2[i].maxLife/2))*gp.monster2[i].speed)*0.1+1;
					}
				} else if(keyH.enterPressed == true){
					gp.monster2[i].life -= atk;
	                gp.monster2[i].invincible = true;
				} else {
					gp.monster2[i].life -= atk/2;
	                gp.monster2[i].invincible = true;
				}

                if (gp.monster2[i].life <= 0) {
                    gp.monster2[i].dying = true; // モンスターが死亡状態になる
                    gp.ui.score += (gp.monster2[i].atk + (gp.monster2[i].maxLife/2))*gp.monster2[i].speed;
                }
            }
        }
    }
    
    // モンスター4(BOSS)にダメージを与える処理
    public void damageMonster4(int i, int attack) {
        if (i != 999) {
            if (gp.monster4[i].invincible == false) {
				if(direction == "up" && keyH.enterPressed == true) {

	                gp.monster4[i].invincible = true;
				} else if(keyH.enterPressed == true){
					gp.monster4[i].life -= atk;
	                gp.monster4[i].invincible = true;
				} else {
					gp.monster4[i].life -= atk/2;

	                gp.monster4[i].invincible = true;
				}

                if (gp.monster4[i].life <= 0) {
                    gp.monster4[i].dying = true; // モンスターが死亡状態になる
                    gp.ui.score += ((gp.monster4[i].atk + (gp.monster4[i].maxLife/2))*gp.monster4[i].speed)*2+1;
                }
            }
        }
    }
		
 // プレイヤーキャラクターの描画処理
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        // キャラクターの向きに応じて、描画する画像を選択
        switch (direction) {
            case "up":
                if (attacking == false) {
                    if (spriteNum == 1) {
                        image = up1;
                    }
                    if (spriteNum == 2) {
                        image = up2;
                    }
                    if (spriteNum == 2) {
                        image = up2;
                    }
                }
                if (attacking == true) {
                    tempScreenY = screenY - gp.tileSize;
                    if (spriteNum == 1) {
                        image = attackUp1;
                    }
                    if (spriteNum == 2) {
                        image = attackUp2;
                    }
                    if (spriteNum == 3) {
                        image = attackUp3;
                    }
                }
                break;
            case "down":
                if (attacking == false) {
                    if (spriteNum == 1) {
                        image = down1;
                    }
                    if (spriteNum == 2) {
                        image = down2;
                    }
                }
                if (attacking == true) {
                    if (spriteNum == 1) {
                        image = attackDown1;
                    }
                    if (spriteNum == 2) {
                        image = attackDown2;
                    }
                }
                break;
            case "left":
                if (attacking == false) {
                    if (spriteNum == 1) {
                        image = left1;
                    }
                    if (spriteNum == 2) {
                        image = left2;
                    }
                }
                if (attacking == true) {
                    tempScreenX = screenX - gp.tileSize;
                    if (spriteNum == 1) {
                        image = attackLeft1;
                    }
                    if (spriteNum == 2) {
                        image = attackLeft2;
                    }
                }
                break;
            case "right":
                if (attacking == false) {
                    if (spriteNum == 1) {
                        image = right1;
                    }
                    if (spriteNum == 2) {
                        image = right2;
                    }
                }
                if (attacking == true) {
                    if (spriteNum == 1) {
                        image = attackRight1;
                    }
                    if (spriteNum == 2) {
                        image = attackRight2;
                    }
                }
                break;
        }

        if (invincible == true) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
            // 無敵状態の場合、キャラクターを半透明に描画
        }

        g2.drawImage(image, tempScreenX, tempScreenY, null);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        // 描画設定を元に戻す
    }

	
	public void playSE(int i) {
    	
	    sound.setFile(i);  // 指定された効果音のファイルを設定
	    sound.play();      // 効果音を再生
    }	
}
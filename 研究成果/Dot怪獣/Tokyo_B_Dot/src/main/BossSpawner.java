package main;

import java.util.Random;

import entity.Entity;
import monster.MON_Human4;
import tile.TileManager;

public class BossSpawner {
	GamePanel gp;
	TileManager tl;
	Entity ent = new Entity(gp);
	public int Count = 0;
	public int entId = 0;
	public int spTime = 0;
	public boolean spCount = false;

	//ドロップアイテム
	public void checkBoss() {
		BossSpoawn(new MON_Human4(gp));
	}
	
	// ボススポーン機能
    public void BossSpoawn(Entity spoawnEntity) {

    	if(gp.monster4[1] == null) {
			gp.monster4[1] = (MON_Human4) spoawnEntity;
			entId = new Random().nextInt(2) + 1;
			if(entId == 1) {
				gp.monster4[1].worldX = gp.tileSize * 32;
				gp.monster4[1].worldY = gp.tileSize * 38;
			} else {
				gp.monster4[1].worldX = gp.tileSize * 23;
				gp.monster4[1].worldY = gp.tileSize * 4;
			}
			gp.player.bossCount++;
			Count++;
			ent.boss = true;
		}
    }
    
    public BossSpawner(GamePanel gp) {
    	this.gp = gp;
	}
    
    // ボスの状態を更新するメソッド
    public void update() {
    	if(spTime == 0) {
    		spTime = 1800;
    		if(ent.boss == false) {
        		if(gp.ui.score >= 200+(200*Count) && Count < 5) {
            		checkBoss();
            	} else if(gp.ui.score >= 1000+(500*(Count-4)) && Count >= 5 && Count < 9){
            		checkBoss();
            	} else if(gp.ui.score >= 3000+(1000*(Count-8)) && Count >= 9){
            		checkBoss();
            	}
        	}
        	if(gp.monster4[1] == null) {
    			ent.boss = false;
    		}
    	} else {
    		spTime--;
    	}
    }
}

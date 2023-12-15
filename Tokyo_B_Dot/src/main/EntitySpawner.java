package main;

import java.util.Random;

import entity.Entity;
import monster.MON_Human1;
import monster.MON_Human2;
import tile.TileManager;

public class EntitySpawner {
	GamePanel gp;
	Entity ent;
	TileManager tl;
	public int s = 0;
	public int spTime = 0;
	public int entId1 = 0;
	public int entId2 = 0;
	public boolean spCount = false;

	//ドロップアイテム
	public void checkEntity() {
		entId1 = new Random().nextInt(2)+1;
		
		if(entId1 == 1) {
			entitySpoawn(new MON_Human1(gp));
		}
		if(entId1 == 2) {
			entitySpoawn(new MON_Human2(gp));
		}
	}
	// 敵キャラクタースポーン機能
    public void entitySpoawn(Entity spoawnEntity) {
		int worldX = new Random().nextInt(30) + 10;
		int worldY = new Random().nextInt(30) + 10;
		
		if(entId1 == 1) {
			for(int i = 0; i < gp.monster1.length; i++) {
	    		if(gp.monster1[i] == null) {
	    			gp.monster1[i] = (MON_Human1) spoawnEntity;
			    	gp.monster1[i].worldX = gp.tileSize * worldX;
			    	gp.monster1[i].worldY = gp.tileSize * worldY;
			    	entId2 = i;
			    	checkTile(spoawnEntity);
			    	break;
	    		}
	    	}
		}
		if(entId1 == 2) {
			for(int i = 0; i < gp.monster2.length; i++) {
	    		if(gp.monster2[i] == null) {
	    			gp.monster2[i] = (MON_Human2) spoawnEntity;
			    	gp.monster2[i].worldX = gp.tileSize * worldX;
			    	gp.monster2[i].worldY = gp.tileSize * worldY;
			    	entId2 = i;
			    	checkTile(spoawnEntity);
			    	break;
			    }
	    	}
		}
    }
    
    public EntitySpawner(GamePanel gp, TileManager tl) {
    	this.gp = gp;
    	this.tl = tl;
	}
    
    // 敵キャラクターの状態を更新するメソッド
    public void update() {
    	if(spTime == 0) {
    		spTime = new Random().nextInt(300)+300;
    		if(spCount == false) {
    			s = 10;
    			spCount = true;
    		} else {
    			s = new Random().nextInt(3)+3;
    		}
    		for(int i = 0; i < s; i++) {
    			checkEntity();
    		}
    	} else {
    		spTime--;
    	}
    }
    
    // スポーンした敵キャラクターにも衝突判定を実装
    public void checkTile(Entity entity) {
    	
		int entityLeftWorldX = entity.worldX + entity.solidArea.x;
		int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.worldY + entity.solidArea.y;
		int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
		
		int entityLeftCol = entityLeftWorldX/gp.tileSize;
		int entityRightCol = entityRightWorldX/gp.tileSize;
		int entityTopRow = entityTopWorldY/gp.tileSize;
		int entityBottomRow = entityBottomWorldY/gp.tileSize;
		
		int tileNum1, tileNum2;
		
		switch(entity.direction) {
		case "up":
			entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				switch(entId1) {
				case 1:
					gp.monster1[entId2] = null;
					break;
				case 2:
					gp.monster2[entId2] = null;
					break;
				}
			}
			break;
		case "down":
			entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				switch(entId1) {
				case 1:
					gp.monster1[entId2] = null;
					break;
				case 2:
					gp.monster2[entId2] = null;
					break;
				}
			}
			break;
		case "left":
			entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				switch(entId1) {
				case 1:
					gp.monster1[entId2] = null;
					break;
				case 2:
					gp.monster2[entId2] = null;
					break;
				}
			}
			break;
		case "right":
			entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				switch(entId1) {
				case 1:
					gp.monster1[entId2] = null;
					break;
				case 2:
					gp.monster2[entId2] = null;
					break;
				}
			}
			break;
		}
	}
}

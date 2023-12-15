package main;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    // ゲームオブジェクト（オブジェクト）を設定するメソッド
    public void setObject() {
        // オブジェクトの追加
    	/*
        gp.obj[0] = new OBJ_Meat();
        gp.obj[0].worldX = 23 * gp.tileSize; // X座標
        gp.obj[0].worldY = 15 * gp.tileSize; // Y座標
        
        gp.obj[1] = new OBJ_AtkUp();
        gp.obj[1].worldX = 24 * gp.tileSize; // X座標
        gp.obj[1].worldY = 15 * gp.tileSize; // Y座標
        
        gp.obj[2] = new OBJ_LifeUp();
        gp.obj[2].worldX = 25 * gp.tileSize; // X座標
        gp.obj[2].worldY = 15 * gp.tileSize; // Y座標
    	*/
    }

    // モンスターを設定するメソッド
    public void setMonster() {
        // モンスター1の設定
		/*		gp.monster1[2] = new MON_Human1(gp);
				gp.monster1[2].worldX = gp.tileSize * 21;
				gp.monster1[2].worldY = gp.tileSize * 22;
				
				// モンスター2の設定
				gp.monster2[3] = new MON_Human2(gp);
				gp.monster2[3].worldX = gp.tileSize * 22;
				gp.monster2[3].worldY = gp.tileSize * 23;
				
				gp.monster2[4] = new MON_Human2(gp);
				gp.monster2[4].worldX = gp.tileSize * 23;
				gp.monster2[4].worldY = gp.tileSize * 24;
				
				gp.monster2[5] = new MON_Human2(gp);
				gp.monster2[5].worldX = gp.tileSize * 24;
				gp.monster2[5].worldY = gp.tileSize * 25;
				
				gp.monster2[6] = new MON_Human2(gp);
				gp.monster2[6].worldX = gp.tileSize * 25;
				gp.monster2[6].worldY = gp.tileSize * 26;*/
		
    	/*
		gp.monster4[9] = new MON_Human4(gp);
		gp.monster4[9].worldX = gp.tileSize * 23;
		gp.monster4[9].worldY = gp.tileSize * 4;
    	*/
    }
}
package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.UtilityTool;

public class SuperObject {
	
    public BufferedImage image, image2, image3; // オブジェクトの画像と異なる状態の画像
    public String name; // オブジェクトの名前
    public boolean coliision = false; // 衝突状態のフラグ
    public int worldX, worldY; // ゲームワールド内のXとY座標
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48); // オブジェクトの衝突領域を表す矩形
    public int solidAreaDefaultX = 0; // 衝突領域のデフォルトのX座標
    public int solidAreaDefaultY = 0; // 衝突領域のデフォルトのY座標
    UtilityTool uTool = new UtilityTool(); // ユーティリティツールのインスタンス
    
    // オブジェクトを描画
    public void draw(Graphics2D g2, GamePanel gp) {

        int screenX = worldX - gp.player.worldX + gp.player.screenX; // 画面上のX座標を計算
        int screenY = worldY - gp.player.worldY + gp.player.screenY; // 画面上のY座標を計算

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null); // 画像を描画
    }
}

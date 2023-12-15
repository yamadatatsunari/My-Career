package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Heart extends SuperObject {
	
    GamePanel gp;
	
    // コンストラクタ
    public OBJ_Heart(GamePanel gp) {
        this.gp = gp;

        name = "Heart"; // オブジェクトの名前
        try {
            // ハートアイテムの画像を読み込み
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/heart_1.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/res/objects/heart_2.png"));
            image3 = ImageIO.read(getClass().getResourceAsStream("/res/objects/heart_3.png"));
            
            // 画像をタイルのサイズにスケーリング
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
            image2 = uTool.scaleImage(image2, gp.tileSize, gp.tileSize);
            image3 = uTool.scaleImage(image3, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package main;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class UtilityTool {
	
	public BufferedImage scaleImage(BufferedImage original, int width, int height) {
        // スケーリング後の画像を格納する BufferedImage オブジェクトを生成
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());

        // スケーリングを行うための Graphics2D オブジェクトを作成
        Graphics2D g2 = scaledImage.createGraphics();

        // オリジナル画像を指定された幅と高さにスケーリング
        g2.drawImage(original, 0, 0, width, height, null);

        // 使用した Graphics2D オブジェクトを解放
        g2.dispose();

        // スケーリングされた画像を返す
        return scaledImage;
    }
}
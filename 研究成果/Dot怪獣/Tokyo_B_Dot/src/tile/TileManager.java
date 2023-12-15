package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {

    GamePanel gp; // ゲームパネルへの参照
    public Tile[] tile; // タイルの配列
    public int mapTileNum[][]; // マップ上のタイル番号の配列

    public TileManager(GamePanel gp) {
        this.gp = gp;
        
        tile = new Tile[30]; // 最大15種類のタイル
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow]; // マップ上のタイル番号を保持する2次元配列

        getTileImage(); // タイル画像の読み込み
        loadMap("/res/maps/interior03.txt"); // マップデータの読み込み
    }

    // タイル画像を読み込み
	public void getTileImage() {
		
		try {
            // タイルの各種類の画像を読み込み
            // 各タイルのcollisionフラグがtrueの場合、そのタイルは衝突
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/grass-2.png"));
			
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/rock-1.png"));
			tile[1].collision = true;
			
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/water-2.png"));
			tile[2].collision = true;
			
			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/road-7.png"));
			
			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/tree-6.png"));
			tile[4].collision = true;
			
			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/wall-2.png"));
			tile[5].collision = true;
			
			tile[6] = new Tile();
			tile[6].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/sand-4.png"));
			
			tile[7] = new Tile();
			tile[7].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/rock-2.png"));
			tile[7].collision = true;
			
			tile[8] = new Tile();
			tile[8].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/cliff-4.png"));
			tile[8].collision = true;
			
			tile[9] = new Tile();
			tile[9].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/cliff-7.png"));
			tile[9].collision = true;
			
			tile[10] = new Tile();
			tile[10].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/cliff-1.png"));
			tile[10].collision = true;
			
			tile[11] = new Tile();
			tile[11].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/cliff-6.png"));
			tile[11].collision = true;
			
			tile[12] = new Tile();
			tile[12].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/cliff-12.png"));
			tile[12].collision = true;
			
			tile[13] = new Tile();
			tile[13].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/cliff-2.png"));
			tile[13].collision = true;
			
			tile[14] = new Tile();
			tile[14].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/cliff-9.png"));
			tile[14].collision = true;
			
			tile[15] = new Tile();
			tile[15].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/road-1.png"));
			
			tile[16] = new Tile();
			tile[16].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/road-2.png"));
			
			tile[17] = new Tile();
			tile[17].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/road-5.png"));
			
			tile[18] = new Tile();
			tile[18].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/road-6.png"));
			
			tile[19] = new Tile();
			tile[19].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/road-3.png"));
			
			tile[20] = new Tile();
			tile[20].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/road-4.png"));
			
			tile[21] = new Tile();
			tile[21].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/road-8.png"));
			
			tile[22] = new Tile();
			tile[22].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/road-9.png"));

			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	 // マップデータを読み込む
    public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();
                
                while (col < gp.maxWorldCol) {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // マップ上のタイルを描画
    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow];
            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            // タイルを描画
            g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
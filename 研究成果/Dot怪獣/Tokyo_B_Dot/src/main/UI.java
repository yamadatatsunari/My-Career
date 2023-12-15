package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.OBJ_Heart;
import object.SuperObject;

public class UI {
    // GamePanel クラスへの参照
    GamePanel gp;
    
    // 描画用の 2D グラフィックスコンテキスト
    Graphics2D g2;
    
    // フォントオブジェクト
    Font arial_40, arial_80B;
    
    // ハート画像 (満杯、半分、空)
    BufferedImage heart_full, heart_half, heart_blank;
    
    // 効果音の再生を担当する Sound クラスのインスタンス
    Sound sound = new Sound(); // SE追加
    
    // 数値フォーマット
    DecimalFormat dFormat = new DecimalFormat("#0.00");
    
    // メッセージ表示のフラグ
    public boolean messageOn = false;
    
    // ゲーム終了のフラグ
    public boolean gameFinished = false;
    
    // 表示するメッセージ
    public String message = "";
    
    // プレイ時間
    double Playtime;
    
    // コマンド番号 (メニューでの選択肢)
    public int commandNum = 0;
    public int score = 0;
    
    public UI(GamePanel gp) {
        this.gp = gp;
        
        // フォントの設定
        arial_40 = new Font("メイリオ", Font.PLAIN, 40);
        arial_80B = new Font("メイリオ", Font.BOLD, 80);
        
        // ハートの画像を SuperObject クラスから取得
        SuperObject heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
    }
    
    // 画面にメッセージを表示する
    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }
    
    // 画面を描画する
    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(arial_80B);
        g2.setColor(Color.white);
        
        // ゲームの状態に応じて描画内容を切り替え
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
        if (gp.gameState == gp.playState) {
            drawPlayerLife();
        	drawScore();
        }
        if (gp.gameState == gp.pauseState) {
            drawPlayerLife();
        	drawScore();
            drawPauseScreen();
        }
        if (gp.gameState == gp.optionState) {
            drawPlayerLife();
        	drawScore();
            drawOptionScreen();
        }
        if (gp.gameState == gp.gameOverState) {
            drawPlayerLife();
            drawGameOverScreen();
            drawGOScore();
        }
        
        if (gp.gameState == gp.titleoptionState) {
            drawBackScreen();
            drawOptionScreen();
        }
    }
    
    // プレイヤーの残機を画面に描画
    public void drawPlayerLife() {
        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;
        int i = 0;
        
        // 残機の状態に応じてハート画像を描画
        while (i < gp.player.maxLife / 2) {
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += gp.tileSize;
        }
        
        x = gp.tileSize / 2;
        y = gp.tileSize / 2;
        i = 0;
        
        while (i < gp.player.life) {
            g2.drawImage(heart_half, x, y, null);
            i++;
            if (i < gp.player.life) {
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += gp.tileSize;
        }
    }
    
    public void drawScore() {
    	
    	int x = gp.tileSize/2;
    	int y = gp.tileSize * 2 + 20;
    	
    	//メニュー
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 40F));
        String text = "スコア:";
        g2.setColor(Color.white);
        g2.drawString(text, x+1, y+1);
        g2.drawString(text, x-1, y-1);
        g2.drawString(text, x-1, y+1);
        g2.drawString(text, x+1, y-1);
        g2.setColor(Color.black);
        g2.drawString(text, x, y);
        x += gp.tileSize * 3;
        text = "" + score;
        g2.setColor(Color.white);
        g2.drawString(text, x+1, y+1);
        g2.drawString(text, x-1, y-1);
        g2.drawString(text, x-1, y+1);
        g2.drawString(text, x+1, y-1);
        g2.setColor(Color.black);
        g2.drawString(text, x, y);
    }
    
    //追加分
    public void drawGOScore() {
    	
    	int x = gp.tileSize * 5 + 12;
    	int y = gp.tileSize * 4 + 24;
    	
    	//メニュー
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 40F));
        String text = "スコア:";
        g2.setColor(Color.black);
        g2.drawString(text, x+1, y+1);
        g2.drawString(text, x-1, y-1);
        g2.drawString(text, x-1, y+1);
        g2.drawString(text, x+1, y-1);
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
        x += gp.tileSize * 3;
        text = "" + score;
        g2.setColor(Color.black);
        g2.drawString(text, x+1, y+1);
        g2.drawString(text, x-1, y-1);
        g2.drawString(text, x-1, y+1);
        g2.drawString(text, x+1, y-1);
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
    }
    //------
    
    // タイトル画面を描画
    public void drawTitleScreen() {
        // タイトル画面の背景色を設定
        g2.setColor(new Color(180, 180, 180));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        
        // タイトルテキストを描画
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "ドット怪獣";
        int x = (gp.screenWidth - g2.getFontMetrics().stringWidth(text)) / 2;
        int y = gp.tileSize * 3;
        
        // 影文字
        g2.setColor(Color.gray);
        g2.drawString(text, x + 4, y + 4);
        
        // メイン文字
        g2.setColor(Color.black);
        g2.drawString(text, x, y);
        
        x = gp.screenWidth / 2 - (gp.tileSize * 2) / 2 - 15;
        y += gp.tileSize * 2;
        g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);
        
        // メニュー項目を描画
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 40F));
        text = "生誕";
        x = (gp.screenWidth - g2.getFontMetrics().stringWidth(text)) / 2;
        y += gp.tileSize * 3;
        g2.setColor(Color.black);
        g2.drawString(text, x, y);
        
        if (commandNum == 0) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        text = "オプション";
        x = (gp.screenWidth - g2.getFontMetrics().stringWidth(text)) / 2;
        y += gp.tileSize;
        g2.setColor(Color.black);
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - gp.tileSize, y);
        }
        
        text = "他界";
        x = (gp.screenWidth - g2.getFontMetrics().stringWidth(text)) / 2;
        y += gp.tileSize;
        g2.setColor(Color.black);
        g2.drawString(text, x, y);
        if(commandNum == 2) {
        	g2.drawString(">", x - gp.tileSize, y);
        } 
    }
  
    public void drawBackScreen() {
        // タイトル画面の背景色を設定
        g2.setColor(new Color(180, 180, 180));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
    }
    
 // オプション画面を描画するメソッド
    public void drawOptionScreen() {
        // メニューフレームの描画
        g2.setColor(Color.black);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30F));
        int frameX = gp.tileSize * 4;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 8;
        int frameHeight = gp.tileSize * 10;
        g2.fillRect(frameX, frameY, frameWidth, frameHeight);

        // メニューフレーム内部の白い背景
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30F));
        frameX += gp.tileSize * 0.05;
        frameY += gp.tileSize * 0.05;
        frameWidth -= gp.tileSize * 0.1;
        frameHeight -= gp.tileSize * 0.1;
        g2.fillRect(frameX, frameY, frameWidth, frameHeight);

        // メニューフレームの外側の黒い縁
        g2.setColor(Color.black);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30F));
        frameX += gp.tileSize * 0.05;
        frameY += gp.tileSize * 0.05;
        frameWidth -= gp.tileSize * 0.1;
        frameHeight -= gp.tileSize * 0.1;
        g2.fillRect(frameX, frameY, frameWidth, frameHeight);

        // "オプション" テキストの描画
        String text = "オプション";
        g2.setColor(Color.white);
        int textX = (gp.screenWidth - g2.getFontMetrics().stringWidth(text)) / 2;
        int textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        // オプションメニュー項目の描画
        textX = frameX + gp.tileSize;
        textY += gp.tileSize * 1.5;

        // "サウンド" テキストの描画
        g2.drawString("サウンド", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - gp.tileSize + 20, textY);
        }

        textY += gp.tileSize;

        // "SE" テキストの描画
        g2.drawString("SE", textX, textY);
        if (commandNum == 1) {
            g2.drawString(">", textX - gp.tileSize + 20, textY);
        }

        textY += gp.tileSize;

        // "ゲーム終了" テキストの描画
        g2.drawString("他界", textX, textY);
        if (commandNum == 2) {
            g2.drawString(">", textX - gp.tileSize + 20, textY);
        }

        textY += gp.tileSize * 2;

        // "閉じる" テキストの描画
        g2.drawString("閉じる", textX, textY);
        if (commandNum == 3) {
            g2.drawString(">", textX - gp.tileSize + 20, textY);
        }

        // 音量バーの描画
        // BGM音量
        textX = frameX + gp.tileSize * 4 + 6;
        textY = frameY + gp.tileSize * 2 + 4;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, 100, 20);
        int volumeWidth = 1 * gp.sound.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 20);

        // SE音量
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 100, 20);
        volumeWidth = 1 * gp.se.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 20);

        // BGM音量の数値を表示
        textX = frameX + gp.tileSize * 6 + 30;
        textY = frameY + gp.tileSize * 2 + 24;
        text = "" + gp.sound.volumeScale;
        g2.drawString(text, textX, textY);

        // SE音量の数値を表示
        textY += gp.tileSize;
        text = "" + gp.se.volumeScale;
        g2.drawString(text, textX, textY);
    }
    
 // 一時停止画面を描画するメソッド
    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSE"; // 一時停止画面のテキスト
        int x = (gp.screenWidth - g2.getFontMetrics().stringWidth(text)) / 2;
        int y = (gp.screenHeight + g2.getFontMetrics().getHeight()) / 2;

        // "PAUSE" テキストの描画
        g2.drawString(text, x, y);
    }

    // ゲームオーバー画面を描画するメソッド
    public void drawGameOverScreen() {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));
        String text = "他界したかい？"; // ゲームオーバー画面のテキスト
        int x = (gp.screenWidth - g2.getFontMetrics().stringWidth(text)) / 2;
        int y = gp.tileSize * 3;

        // 影文字
        g2.setColor(Color.black);
        g2.drawString(text, x + 4, y + 4);

        // メイン文字
        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        x = gp.screenWidth / 2 - (gp.tileSize * 2) / 2 - 15;
        y += gp.tileSize * 2;
        g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);
        
        x = gp.screenWidth / 2 - (gp.tileSize * 2) / 2 - 15;
        

        // ゲームオーバー画面のメニュー項目
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 40F));

        // "意志を受け継ぐ" テキストの描画
        text = "意志を受け継ぐ";
        x = (gp.screenWidth - g2.getFontMetrics().stringWidth(text)) / 2;
        y += gp.tileSize * 3;
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - 40, y);
        }

        // "他界" テキストの描画
        text = "タイトル";
        x = (gp.screenWidth - g2.getFontMetrics().stringWidth(text)) / 2;
        y += gp.tileSize;
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - 40, y);
        }
    }
}
package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import monster.MON_Human1;
import monster.MON_Human2;
import monster.MON_Human4;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	public final int originalTileSize = 16;
	public final int scale = 3;
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    public int gameState;
    public boolean reset = false;
    public final int pauseState = 0;
    public final int playState = 1;
    public final int titleState = 2;
    public final int optionState = 10;
    public final int gameOverState = 6;
    public final int titleoptionState = 7;
    
    public KeyHandler keyH = new KeyHandler(this);
    public TileManager tileM = new TileManager(this);

    public Sound se = new Sound();
    public Sound sound = new Sound();


    public Player player = new Player(this, keyH);
    public BossSpawner BSp = new BossSpawner(this);
    public EntitySpawner ESp = new EntitySpawner(this,tileM);
    public CollisionChecker cChecker = new CollisionChecker(this);
    
    public MON_Human1 monster1[] = new MON_Human1[100];
    public MON_Human2 monster2[] = new MON_Human2[100];
    public MON_Human4 monster4[] = new MON_Human4[100];

    public AssetSetter aSetter = new AssetSetter(this);
    public ArrayList<Entity> projectileList = new ArrayList<>();

    
    Thread gameThread;
    
    public UI ui = new UI(this);
    
    int FPS = 60;
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;


    public SuperObject obj[] = new SuperObject[10];
   
    // ゲームパネルの設定
    public GamePanel() {
        
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
    // リトライ時の設定
	public void retry() {
		player.setDefaultPositions();
		player.restoreLifeAndMan();
    	aSetter.setObject();
    	aSetter.setMonster();
    	
		
	}
    // リスタート時の設定
	public void restart() {
		
		player.setDefaultValues();
		player.setDefaultPositions();
		player.restoreLifeAndMan();
    	aSetter.setObject();
    	aSetter.setMonster();

	}
    // セットアップ時の設定
    public void setupGame() {
    	aSetter.setObject();
    	aSetter.setMonster();
    	gameState = titleState;
    	playMusic(11);
    }
    
    // ゲーム開始時の設定
    public void startGameThread() { 
        gameThread = new Thread(this);
        gameThread.start();

    }
    // ゲームのフレームレートを制御する
    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;
        while(gameThread != null) {
            update();
            repaint();
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;
                if(remainingTime < 0) {
                    remainingTime = 0;
                }
                Thread.sleep((long)remainingTime);
                nextDrawTime += drawInterval;
            } 
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    // ゲームパネルの状態を更新するメソッド
    public void update() {
    	if(gameState == playState){
    		
            ESp.update();
            BSp.update();
            player.update();
        
	        for(int i = 0; i < monster1.length; i++) {
	        	if(monster1[i] != null) {
	        		if(monster1[i].alive == true && monster1[i].dying == false) {
	        			monster1[i].update();
	        		}
	        		
	        		if(monster1[i].alive == false) {
	        			monster1[i] = null;
	        		}
	        	}
	        }
	        
	        for(int i = 0; i < monster2.length; i++) {
	        	if(monster2[i] != null) {
	        		if(monster2[i].alive == true && monster2[i].dying == false) {
	        			monster2[i].update();
	        		}
	        		
	        		if(monster2[i].alive == false) {
	        			monster2[i] = null;
	        		}
	        	}
	        }
	        	        
	        for(int i = 0; i < monster4.length; i++) {
	        	if(monster4[i] != null) {
	        		if(monster4[i].alive == true && monster4[i].dying == false) {
	        			monster4[i].update();
	        		}
	        		
	        		if(monster4[i].alive == false) {
	        			monster4[i] = null;
	        		}
	        	}
	        }
	        
	        for(int i = 0; i < projectileList.size(); i++) {
	        	if(projectileList.get(i) != null) {
	        		if(projectileList.get(i).alive == true) {
	        			projectileList.get(i).update();
	        		}
	        		
	        		if(projectileList.get(i).alive == false) {
	        			projectileList.remove(i);
	        		}
	        	}
	        }
        }
        if(gameState == pauseState){

        }
    }
    
    // ゲーム画面の描画
    public void paintComponent(Graphics g) {
    	
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g; 
        
        if (gameState == titleState) {
        	
        	ui.draw(g2);
        	
        } else {
        	tileM.draw(g2);
        	
        	//オブジェクト
            for(int i = 0; i < obj.length; i++) {
            	if(obj[i] != null) {
            		obj[i].draw(g2, this);
            	}
            }
       
            for(int i = 0; i < monster1.length; i++) {
            	if(monster1[i] != null) {
            		monster1[i].draw(g2, this);
            	}
            }
            
            for(int i = 0; i < monster2.length; i++) {
            	if(monster2[i] != null) {
            		monster2[i].draw(g2, this);
            	}
            }
                        
            for(int i = 0; i < monster4.length; i++) {
            	if(monster4[i] != null) {
            		monster4[i].draw(g2, this);
            	}
            }
            
            for(int i = 0; i < projectileList.size(); i++) {
            	if(projectileList.get(i) != null) {
            		projectileList.get(i).draw(g2, this);
            	}
            }
            
            player.draw(g2);

            ui.draw(g2);
            
            g2.dispose();
        }
    }
    
    // 音声再生
     public void playMusic(int i) {
    	
    	sound.setFile(i);
    	sound.play();
    	sound.loop();
    }
    
    // 音声停止
    public void stopMusic() {
    	
    	sound.stop();
    }
    
    // SE再生
    public void playSE(int i) {
    	
    	se.setFile(i);
    	se.play();
    }
}
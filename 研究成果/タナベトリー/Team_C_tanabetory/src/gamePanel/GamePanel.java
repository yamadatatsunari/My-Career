package gamePanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

import tanabetory.Main;
import tanabetory.MainWindow;
import tanabetory.ResultPanel;
import tanabetory.ScreenMode;
import tanabetory.Sound;

public class GamePanel extends JPanel{
	private static final long serialVersionUID = 1L;
	//レイアウト
	BorderLayout layout = new BorderLayout();
	//コンポーネント
	MenuBar menuBar;
	FieldPanel fieldPanel;
	ResultPanel resultPanel;
	Sound sound;
	int w_keyflag = 0;
	int debug_keyflag = 0;
	private final Set<Integer> pressedKeys = new HashSet<>();
	//リスナー
	MultiKeyPressListener multiKeyPressListener;
	
	MainWindow main_Window;
	
	//コンストラクタ
	public GamePanel(MainWindow mainWindow){
		//パネルサイズと貼り付け位置の設定は不要(CardLayoutが勝手に画面サイズに合わせてくれる)
		this.main_Window = mainWindow;
//		this.resultPanel = main_Window.Get_resultPanel();
//		this.resultPanel = new ResultPanel(this);
		this.setLayout(layout);//レイアウトの設定
		this.setBackground(Color.yellow);//背景の色
		//その他の使設定をここに追加
		//パネル生成と設置
		menuBar = new MenuBar(this);
		fieldPanel = new FieldPanel(this);
		this.add(menuBar, BorderLayout.NORTH);
		this.add(fieldPanel, BorderLayout.CENTER);
		// Sound クラスのインスタンスを作成
        sound = new Sound(this);
	}
	
	//コンストラクタが呼ばれた後手動で呼び出す
	public void prepareComponents() {
//		System.out.println("GamePanel.prepareComponents()");
		//以降コンポーネントに関する命令(以下は一例)
		menuBar.prepareComponents();
		fieldPanel.prepareComponents();
		fieldPanel.startGameLoop();
		if(fieldPanel.debug_flag) {
			System.out.println("GamePanel.prepareComponents()");
		}
		//リスナーを設置
		multiKeyPressListener = new MultiKeyPressListener(this);
	}
	
	public void fieldPanelStartGameLoop() {
		if(fieldPanel.debug_flag) {
			System.out.println("GamePanle.fieldPanelStartGameLoop()");
		}
		fieldPanel.gameloop = true;
		
		sound.adjustBGMVolume(-7);
		sound.playBGM();
	}
	public void fieldPanelStopGameLoop() {
		if(fieldPanel.debug_flag) {
			System.out.println("GamePanle.fieldPanelStopGameLoop()");
		}
		fieldPanel.gameloop = false;
	}
	public void Init() {
		if(fieldPanel.debug_flag) {
			System.out.println("--GamePanel.Init()");
		}
		pressedKeys.clear();
		fieldPanel.Init();
	}
	public void resetFlag1() {
		fieldPanel.flag1 = 0;
	}
	public int GetScore() {
		return fieldPanel.GetScore();
	}
	public boolean Get_debugFlag() {
		return fieldPanel.debug_flag;
	}
	public void setResultPanel() {
		this.resultPanel = main_Window.Get_resultPanel();
	}
	public void m_endTrue()
	{
		fieldPanel.m_end = true;
	}
//	public int Get_Keyflag() {
//		return fieldPanel.keyFlag;
//	}
	
	//内部クラス（hが押されたらタイトルへ）
	public class MultiKeyPressListener implements KeyListener {
		//貼り付け先を保持
		JPanel panel;
		
		//コンストラクタ
		public MultiKeyPressListener(JPanel p) {
			// TODO 自動生成されたコンストラクター・スタブ
			super();
			panel = p;
			panel.addKeyListener(this);
		}
		
		@Override
		public synchronized void keyReleased(KeyEvent e) {
		    if(fieldPanel.debug_flag) {
		    	System.out.println("Key released: " + e.getKeyCode());
		    }
		    pressedKeys.remove(e.getKeyCode());
		    if(e.getKeyCode() == 87) {
		    	w_keyflag = 0;
		    }
		    if(e.getKeyCode() == 68 || e.getKeyCode() == 71 || e.getKeyCode() == 66) {
		    	debug_keyflag = 0;
		    }
		}
		
		@Override
	    public synchronized void keyPressed(KeyEvent e) {
	        pressedKeys.add(e.getKeyCode());
	        // ここでpressedKeysをチェックし、必要なアクションを実行します
//	        performAction();
	    }
		
		@Override
	    public void keyTyped(KeyEvent e) {
	        /* このイベントは使用しません */
	    }

	    public void performAction() {
	        if (pressedKeys.contains(KeyEvent.VK_H)) {							//Hが押されていたら
	            // Hキーが押されたときのアクション
	        	if(fieldPanel.debug_flag) {
	        		System.out.println("press_H");
	        	}
//	        	Init();
//				fieldPanel.gameloop = false;
//				System.out.println("GamePanle.fieldPanelStopGameLoop()");
//				fieldPanel.flag1 = 0;
//				pressedKeys.remove(KeyEvent.VK_H);
//				Main.mainWindow.setFrontScreenAndFocus(ScreenMode.TITLE);
	        	fieldPanel.m_end = true;
	        	
	        	sound.stopBGM();					// ゲームBGMの停止
				sound.bgmClip.setFramePosition(0);  // 再生位置を最初に戻す
	        }
	        if (pressedKeys.contains(KeyEvent.VK_P)) {							//Pが押されていたら
	            // Pキーが押されたときのアクション
	        	fieldPanel.gameloop = false;
	        	if(fieldPanel.debug_flag) {
	        		System.out.println("press_P");
	        		System.out.println("GamePanle.fieldPanelStopGameLoop()");
	        	}
				pressedKeys.remove(KeyEvent.VK_P);
				
				sound.stopBGM();
				Main.mainWindow.setFrontScreenAndFocus(ScreenMode.POSE);
	        }
	        if (pressedKeys.contains(KeyEvent.VK_R)) {							//Rが押されていたら
	        	fieldPanel.gameloop = false;
	        	if(fieldPanel.debug_flag) {
		        	System.out.println("press_R");
					System.out.println("リザルト画面を表示");
	        	}
				setResultPanel();
				resultPanel.setScore(fieldPanel.GetScore(), "images/ending.png", 320, 600);
				pressedKeys.remove(KeyEvent.VK_R);
				
				sound.stopBGM();					// ゲームBGMの停止
				sound.bgmClip.setFramePosition(0);  // 再生位置を最初に戻す
				
				Main.mainWindow.setFrontScreenAndFocus(ScreenMode.RESULT);
	        }
	        if (pressedKeys.contains(KeyEvent.VK_W)) {							//Wが押されていたら
	        	if(w_keyflag%120 == 0) {
		        	if(fieldPanel.player.Get_megane() == false)
					{
						if(fieldPanel.player.Get_zansu() > 0)
						{
							sound.playSE(1);
							sound.playSE(7);
							
							fieldPanel.player.Set_megane(true);
							fieldPanel.player.Set_zansu(-1);
						}
					}
	        	}
	        	if(fieldPanel.debug_flag) {
	        		if(w_keyflag%6 == 0) {
			        	if(fieldPanel.player.Get_megane() == false)
						{
							if(fieldPanel.player.Get_zansu() > 0)
							{
								sound.playSE(1);
								
								fieldPanel.player.Set_megane(true);
								fieldPanel.player.Set_zansu(-1);
							}
						}
		        	}
	        	}
	        	w_keyflag++;
	        }
/*************************************************************************************************/	        
	        if (pressedKeys.contains(KeyEvent.VK_D) && pressedKeys.contains(KeyEvent.VK_B) && pressedKeys.contains(KeyEvent.VK_G)) {							//Tが押されていたら
	        	if(debug_keyflag%10 == 0) {
		        	fieldPanel.debug_flag = !fieldPanel.debug_flag;				//debug_flagを反転させる
		        	fieldPanel.redPanel.setVisible(fieldPanel.debug_flag);
					for(int i=0; i<9; i++) {
						fieldPanel.bluePanel[i].setVisible(fieldPanel.debug_flag);
					}
					for(int i=0; i<3; i++) {
						fieldPanel.greenPanel[i].setVisible(fieldPanel.debug_flag);
					}
					for(int i=0; i<6; i++) {
						fieldPanel.purplePanel[i].setVisible(fieldPanel.debug_flag);
					}
					for(int i=0; i<100; i++) {
						fieldPanel.yellowPanel[i].setVisible(fieldPanel.debug_flag);
					}
					for(int i=0; i<15; i++) {
						fieldPanel.orangePanel[i].setVisible(fieldPanel.debug_flag);
					}
					fieldPanel.pinkPanel.setVisible(fieldPanel.debug_flag);
					for(int i=0; i<2; i++) {
						fieldPanel.whitePanel[i].setVisible(fieldPanel.debug_flag);
					}
	        	}
	        	debug_keyflag++;
	        }
/*************************************************************************************************/
	        if (pressedKeys.contains(KeyEvent.VK_SPACE)) {
	        	if(fieldPanel.debug_flag) {
	        		System.out.println("press_SPACE");
	        	}
	        	if(fieldPanel.player.getY() > 0)
	        	{
	        		if(fieldPanel.player.Get_jumpcount() > 0)
	        		{
	        			fieldPanel.player.Setjump(-20);
	        			fieldPanel.player.Set_jumpcount(0);
	        			
	        			// プレイヤーがspaceを押したときにSEを再生
	        			sound.playSE(0);
	        		}
	        	}
	        }
	        if (pressedKeys.contains(KeyEvent.VK_UP)) {
	        	if(fieldPanel.debug_flag) {
	        		System.out.println("press_UP");
	        	}
	        	if(fieldPanel.player.getY() + fieldPanel.player.p_Image.getHeight(null) < panel.getHeight())
				{
	        		if(fieldPanel.player.Get_change() == false) {
						if(fieldPanel.aim[0].m_use == true)
						{
//							System.out.println("flag");
							fieldPanel.player.Set_Chenge(true);
							
							if(fieldPanel.player.Get_set() == 2)
							{
								fieldPanel.player.Set_set(1);
								fieldPanel.tanabeReaper.Set_count(0);
							}
							else if(fieldPanel.player.Get_set() == 3)
							{
								fieldPanel.player.Set_set(2);
								fieldPanel.tanabeReaper.Set_count(0);
							}
							
							fieldPanel.PlayerAnimationManager(fieldPanel.eff[3].m_use, 0);
						}
					}
				}
	        }
	        if (pressedKeys.contains(KeyEvent.VK_DOWN)) {
	        	if(fieldPanel.debug_flag) {
	        		System.out.println("press_DOWN");
	        	}
	        	if(fieldPanel.player.getY() + fieldPanel.player.p_Image.getHeight(null) < panel.getHeight())
				{
	        		if(fieldPanel.player.Get_change() == false) {
						if(fieldPanel.aim[0].m_use == true)
						{
//							System.out.println("flag");
							fieldPanel.player.Set_Chenge(true);
							
							if(fieldPanel.player.Get_set() == 1)
							{
								fieldPanel.player.Set_set(2);
								fieldPanel.tanabeReaper.Set_count(0);
							}
							else if(fieldPanel.player.Get_set() == 2)
							{
								fieldPanel.player.Set_set(3);
								fieldPanel.tanabeReaper.Set_count(0);
							}
							
							fieldPanel.PlayerAnimationManager(fieldPanel.eff[3].m_use, 0);
						}
					}
				}
	        }
	        if (pressedKeys.contains(KeyEvent.VK_LEFT)) {
	        	if(fieldPanel.debug_flag) {
	        		System.out.println("press_LEFT");
	        	}
	        	if(fieldPanel.player.Get_X()+60 > 0)
				{
					//p_Label.setLocation(p_Label.getX() - 5,p_Label.getY());
					//p_Label.Setvelocity(-10);
					fieldPanel.player.SetX(-6);
				}
	        }
	        if (pressedKeys.contains(KeyEvent.VK_RIGHT)) {
	        	if(fieldPanel.debug_flag) {
	        		System.out.println("press_RIGHT");
	        	}
	        	if(fieldPanel.player.Get_X()+120 < panel.getWidth())
				{
					//p_Label.setLocation(p_Label.getX() + 5,p_Label.getY());
					//p_Label.Setvelocity(10);
					fieldPanel.player.SetX(6);
				}
	        }
	        if(w_keyflag >= 72000) {
	        	w_keyflag = 0;
	        }
	        if(debug_keyflag >= 72000) {
	        	debug_keyflag = 0;
	        }
	        if(fieldPanel.debug_flag) {
	        	System.out.println("w_keyflag:"+w_keyflag);
	        	System.out.println("debug_keyflag:"+debug_keyflag);
	        }
	    }
	}
}

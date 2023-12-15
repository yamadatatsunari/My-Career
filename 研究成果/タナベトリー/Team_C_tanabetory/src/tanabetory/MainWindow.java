package tanabetory;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import gamePanel.GamePanel;

public class MainWindow extends JFrame{
	private static final long serialVersionUID = 1L;
	//フィールド
	ScreenMode screenMode = ScreenMode.TITLE;
	//定数
	public final int WIDTH = 1920;		//フレームの幅
	public final int HEIGHT = 1080;		//フレームの高さ
	//レイアウト
	CardLayout layout = new CardLayout();
	//コンポーネント
	TitlePanel titlePanel;
	GamePanel gamePanel;
	PosePanel posePanel;
	ResultPanel resultPanel;
	TutorialPanel tutorialPanel;
//	FieldPanel fieldPanel;
	
	//コンストラクタ
	MainWindow(){
		//ウィンドウ左上のアイコンとタイトル
		this.setTitle("Tanabetory");
		ImageIcon icon = new ImageIcon("images/tanabetory.png");
		this.setIconImage(icon.getImage());
		
		//いつもの
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);							//画面サイズの変更を禁止
		this.getContentPane().setBackground(Color.green);	//背景の色
//		this.getContentPane().setBackground(new Color(0xF6F6F6));	//細かく設定する場合はこちら
		this.setLayout(layout);								//が未芝居のように設定
		this.setPreferredSize(new Dimension(WIDTH,HEIGHT));	//サイズ設定
		this.pack();						//自動サイズ調整（これがないと変なサイズになる）
		this.setLocationRelativeTo(null);	//起動時のスクリーン位置を中央に（packより後で呼ぶ）
//		this.setLocatioin(450,50);			//細かく設定する場合はこちら
		
	}
	
	//コンストラクタが呼ばれた後メインメソッドから最初に手動で呼び出す
	public void preparePanels() {
		//パネルの準備
		gamePanel = new GamePanel(this);
		this.add(gamePanel, "ゲーム画面");
		titlePanel = new TitlePanel(gamePanel);
		this.add(titlePanel, "タイトル画面");
		posePanel = new PosePanel(gamePanel);
		this.add(posePanel, "ポーズ画面");
		resultPanel = new ResultPanel(gamePanel);
		this.add(resultPanel, "リザルト画面");
		this.pack();
		tutorialPanel = new TutorialPanel(gamePanel);
		this.add(tutorialPanel, "チュートリアル画面");
		this.pack();
	}
		
	//preparePanels()が呼ばれた後メインメソッドから更に手動で呼び出す
	public void prepareComponents() {
		gamePanel.prepareComponents();
		titlePanel.prepareComponents();
//		gamePanel.stopGameLoopFieldPanel();
		posePanel.prepareComponents();
		resultPanel.prepareComponents();
		tutorialPanel.prepareComponents();
	}
	
	public ResultPanel Get_resultPanel() {
		return resultPanel;
	}
	
	//スクリーンモードを切り替える
	//メインメソッドから最後に手動で呼び出す
	public void setFrontScreenAndFocus(ScreenMode s) {
		screenMode = s;
		boolean debug_Flag = false;
		//表示される画面の設定
		switch(screenMode) {
		case TITLE:
//			gamePanel.fieldPanelStopGameLoop();
			if(debug_Flag) System.out.println("");
			layout.show(this.getContentPane(), "タイトル画面");
			titlePanel.requestFocus();
			break;
		case GAME:
//			gamePanel.Init();
//			gamePanel.fieldPanelStartGameLoop();
			if(debug_Flag) System.out.println("");
			layout.show(this.getContentPane(), "ゲーム画面");
			gamePanel.requestFocus();
//			fieldPanel.startGameLoop();
			break;
		case POSE:
//			gamePanel.fieldPanelStopGameLoop();
			if(debug_Flag) System.out.println("");
			layout.show(this.getContentPane(), "ポーズ画面");
			posePanel.requestFocus();
			break;
		case RESULT:
			if(debug_Flag) System.out.println("");
			layout.show(this.getContentPane(), "リザルト画面");
			resultPanel.requestFocus();
			break;
		case TUTORIAL:
			if(debug_Flag) System.out.println("");
			layout.show(this.getContentPane(), "チュートリアル画面");
			tutorialPanel.requestFocus();
			break;
		}
	}
}

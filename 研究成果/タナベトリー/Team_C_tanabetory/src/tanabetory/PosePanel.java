package tanabetory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gamePanel.GamePanel;

public class PosePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	//レイアウト
	BorderLayout layout = new BorderLayout();
	//コンポーネント
//	JLabel poseLabel;
	JLabel menuLabel;
	JButton continueButton;
	JButton restartButton;
	JButton homeButton;
	ContinueButtonListener continueButtonListener;
	RestartButtonListener restartButtonListener;
	HomeButtonListener homeButtonListener;
	GamePanel game_Panel;
	
	
	//コンストラクタ
	PosePanel(GamePanel gamePanel){
		game_Panel = gamePanel;
		//パネルサイズと貼り付け位置の設定は不要(CardLayoutが勝手に画面サイズに合わせてくれる)
		this.setBackground(Color.green);//背景の色
		this.setLayout(null);//レイアウトの設定
		//その他の使設定をここに追加
	}
	
	//コンストラクタが呼ばれた後手動で呼び出す
	public void prepareComponents() {
		//ボタン位置決定用変数
		int x = Main.mainWindow.posePanel.getWidth()/10, y = Main.mainWindow.posePanel.getHeight()/12; 
		
		//以降コンポーネントに関する命令(以下は一例)
//		poseLabel = new JLabel();				//ラベル生成
//		poseLabel.setText("ポーズ画面");		//ラベルに文字を記入
//		poseLabel.setBounds(710, 540, 100, 30);	//位置とサイズを指定

		menuLabel = new JLabel();				//ラベル生成
		menuLabel.setText("~MENU~");		//ラベルに文字を記入
		menuLabel.setFont(new Font("MV boil", Font.BOLD, 40));
		menuLabel.setPreferredSize(new Dimension(Main.mainWindow.posePanel.getWidth()-(x*2), Main.mainWindow.posePanel.getHeight()-(y*10)));
//		menuLabel.setHorizontalTextPosition(JLabel.CENTER);
//		menuLabel.setVerticalTextPosition(JLabel.BOTTOM);
		menuLabel.setHorizontalAlignment(JLabel.CENTER);
		menuLabel.setBounds(x, y/2, Main.mainWindow.posePanel.getWidth()-(x*2), Main.mainWindow.posePanel.getHeight()-(y*10));	//位置とサイズを指定
//		menuLabel.setBorder(BorderFactory.createLineBorder(Color.RED, 4));		//位置判定用境界線
		
		//コンティニューボタン
		continueButton = new JButton();
		continueButton.setBounds(x, y*3, Main.mainWindow.posePanel.getWidth()-(x*2), Main.mainWindow.posePanel.getHeight()-(y*10));
		continueButtonListener = new ContinueButtonListener();
		continueButton.addActionListener(continueButtonListener);
		
		continueButton.setText("ゲームを続ける");
		continueButton.setFocusable(false);
		continueButton.setFont(new Font("MV boil", Font.BOLD, 40));
		continueButton.setContentAreaFilled(false);
		continueButton.setBorder(BorderFactory.createLineBorder(Color.black, 3));
		
		//リスタートボタン
		restartButton = new JButton();
		restartButton.setBounds(x, y*6, Main.mainWindow.posePanel.getWidth()-(x*2), Main.mainWindow.posePanel.getHeight()-(y*10));
		restartButtonListener = new RestartButtonListener();
		restartButton.addActionListener(restartButtonListener);
		
		restartButton.setText("はじめから");
		restartButton.setFocusable(false);
		restartButton.setFont(new Font("MV boil", Font.BOLD, 40));
		restartButton.setContentAreaFilled(false);
		restartButton.setBorder(BorderFactory.createLineBorder(Color.black, 3));
		
		//ホームボタン
		homeButton = new JButton();
		homeButton.setBounds(x, y*9, Main.mainWindow.posePanel.getWidth()-(x*2), Main.mainWindow.posePanel.getHeight()-(y*10));
		homeButtonListener = new HomeButtonListener();
		homeButton.addActionListener(homeButtonListener);
		
		homeButton.setText("タイトルに戻る");
		homeButton.setFocusable(false);
		homeButton.setFont(new Font("MV boil", Font.BOLD, 40));
		homeButton.setContentAreaFilled(false);
		homeButton.setBorder(BorderFactory.createLineBorder(Color.black, 3));
		
		//設置
//		this.add(poseLabel);					//ラベルをこのパネルに貼る
		this.add(menuLabel);
		this.add(continueButton);
		this.add(restartButton);
		this.add(homeButton);
	}
	
	//内部クラス（コンティニューボタン用リスナー）
	private class ContinueButtonListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO 自動生成されたメソッド・スタブ
			game_Panel.fieldPanelStartGameLoop();
			Main.mainWindow.setFrontScreenAndFocus(ScreenMode.GAME);
		}
		
	}
		
	//内部クラス（リスタートボタン用リスナー）
	private class RestartButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO 自動生成されたメソッド・スタブ
			game_Panel.Init();
			game_Panel.resetFlag1();
			game_Panel.fieldPanelStartGameLoop();
			Main.mainWindow.setFrontScreenAndFocus(ScreenMode.GAME);
		}
		
	}
	
	//内部クラス（ホームボタン用リスナー）
	private class HomeButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO 自動生成されたメソッド・スタブ
			//タイトル画面に戻る
			game_Panel.Init();
			game_Panel.resetFlag1();
			Main.mainWindow.setFrontScreenAndFocus(ScreenMode.TITLE);
		}
		
	}
}

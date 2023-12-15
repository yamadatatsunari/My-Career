package gamePanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

//メニューバーのパネル（ゲーム画面上部に貼り付ける）
public class MenuBar extends JPanel {
	private static final long serialVersionUID = 1L;
	//コンポーネント
	JButton homeButton;
	JLabel homeLabel;
	JButton poseButton;
	JLabel poseLabel;
	HomeButtonListener homeButtonListener;
	PoseButtonListener poseButtonListener;
//	FieldPanel fieldPanel;
	GamePanel game_Panel;
	
		//コンストラクタ
		public MenuBar(GamePanel gamePanel) {
			game_Panel = gamePanel;
			//パネルサイズと貼り付け位置の設定は不要(CardLayoutが勝手に画面サイズに合わせてくれる)
			this.setPreferredSize(new Dimension(100, 40));	//幅は自動調整されるがこの命令は必要
			this.setBackground(new Color(128, 255, 0, 255));
			this.setLayout(null);
//			fieldPanel = new FieldPanel();
			
		}
		
		//コンストラクタが呼ばれた後手動で呼び出す
		public void prepareComponents() {
			//ホームボタン
			homeButton = new JButton();
			homeButton.setBounds(5, 5, 80, 30);			
			homeButtonListener = new HomeButtonListener();
			homeButton.addActionListener(homeButtonListener);
//			ImageIcon backIcon = new ImageIcon("");
//			homeButton.setIcon(backIcon);
			//ホームボタンのカスタマイズ
			homeButton.setText("HOME");
			homeButton.setFocusable(false);
			homeButton.setFont(new Font("MV boil", Font.BOLD, 16));
			homeButton.setContentAreaFilled(false);
			homeButton.setBorder(BorderFactory.createLineBorder(Color.black, 3));
			
			//ポーズボタン
			poseButton = new JButton();
			poseButton.setBounds(1820, 5, 80, 30);
			poseButtonListener = new PoseButtonListener();
			poseButton.addActionListener(poseButtonListener);
			//ポーズボタンのカスタマイズ
			poseButton.setText("POSE");
			poseButton.setFocusable(false);
			poseButton.setFont(new Font("MV boil", Font.BOLD, 16));
			poseButton.setContentAreaFilled(false);
			poseButton.setBorder(BorderFactory.createLineBorder(Color.black, 3));
			
			//ラベル
			homeLabel = new JLabel("←click this button or press 'h' to home");
			homeLabel.setBounds(100, 5, 250, 30);
			homeLabel.setBorder(BorderFactory.createEtchedBorder(3, Color.black, Color.white));
			
			//設置
			this.add(homeButton);
			this.add(poseButton);
			this.add(homeLabel);
		}
		
		//内部クラス（ホームボタン用リスナー）
		private class HomeButtonListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自動生成されたメソッド・スタブ
				//タイトル画面に戻る
//				game_Panel.fieldPanelStopGameLoop();
//				game_Panel.Init();
//				game_Panel.resetFlag1();
//				tanabetory.Main.mainWindow.setFrontScreenAndFocus(tanabetory.ScreenMode.TITLE);
				game_Panel.m_endTrue();
			}
			
		}

		//内部クラス（ポーズボタン用リスナー）
		private class PoseButtonListener implements ActionListener {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自動生成されたメソッド・スタブ
				//タイトル画面に戻る
				game_Panel.fieldPanelStopGameLoop();
				tanabetory.Main.mainWindow.setFrontScreenAndFocus(tanabetory.ScreenMode.POSE);
			}
			
		}
}

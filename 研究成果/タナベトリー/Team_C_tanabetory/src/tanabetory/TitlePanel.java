package tanabetory;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import gamePanel.GamePanel;

public class TitlePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	//コンポーネント
//	JLabel title;
	JButton startButton;
	JButton exitButton;
	JLabel select;
	JLabel message;
	Menu checkMenu = Menu.START;
	Border border = BorderFactory.createLineBorder(Color.BLACK, 2);//いらなければ消す
	MyKeyListener myKeyListener;
	StartButtonListener startButtonListener;
	ExitButtonListener exitButtonListener;
	GamePanel game_Panel;
	private Image backgroundImage;
	Sound sound;
	
	//列挙型
	public enum Menu{
		START,
		EXIT,
	}
	
	//コンストラクタ
	TitlePanel(GamePanel gamePanel) {
		game_Panel = gamePanel;
		//パネルサイズと貼り付け位置の設定は不要(CardLayoutが勝手に画面サイズに合わせてくれる)
		this.setLayout(null);//レイアウトの設定
//		this.setBackground(Color.cyan);//背景の色
		try {
            backgroundImage = ImageIO.read(new File("images/tanabetory_main.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
		//その他の使設定をここに追加
		sound = new Sound(this);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//画像をパネルサイズにスケール
		Image scaledImage = backgroundImage.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
		if(game_Panel.Get_debugFlag()) {
			System.out.println("TitlePanel.Width:"+this.getWidth()+", TitlePanel.Height:"+this.getHeight());
		}
		//画像を描画
		g.drawImage(scaledImage, 0, 0, this);
		
		sound.adjustBGMVolume(-5);
		sound.playBGM();
	}
	
	//コンストラクタが呼ばれた後手動で呼び出す
	public void prepareComponents() {
		//以降コンポーネントに関する命令(以下は一例)
		double x = Main.mainWindow.titlePanel.getWidth()/10.0, y = Main.mainWindow.titlePanel.getHeight()/10.0;
//		System.out.println("x="+x+",y="+y);
//		System.out.println("x="+(int)x+",x="+(int)y);
		//タイトルロゴ
//		title = new JLabel();				//ラベル生成
//		ImageIcon titleLogo = new ImageIcon("images/tanabe_1.png");
//		title.setIcon(titleLogo);
//		title.setHorizontalAlignment(SwingConstants.CENTER);
//		title.setVerticalAlignment(SwingConstants.BOTTOM);
//		title.setText("Created by 【Our name】");		//ラベルに文字を記入
//		title.setHorizontalTextPosition(JLabel.CENTER);
//		title.setVerticalTextPosition(SwingConstants.BOTTOM);
//		title.setBounds((int)x, 0, (int)x*8, (int)y*7);	//位置とサイズを指定
		
		//STARTボタン
		startButton = new JButton();
		startButton.setBounds((int)(x*0.7), (int)(y*0.8), (int)x*2, (int)y*1);
		startButtonListener = new StartButtonListener();
		startButton.addActionListener(startButtonListener);
		//STARTボタンのカスタマイズ
		startButton.setText("START");
		startButton.setFocusable(false);
		startButton.setHorizontalTextPosition(JButton.CENTER);
		startButton.setVerticalTextPosition(JButton.BOTTOM);
		startButton.setFont(new Font("MV boil", Font.BOLD, 40));
		startButton.setForeground(Color.black);
//		startButton.setBackground(Color.cyan);
		startButton.setContentAreaFilled(false);
		startButton.setBorder(BorderFactory.createEtchedBorder());
		startButton.setBorderPainted(false);
		
		//EXITボタン
		exitButton = new JButton();
		exitButton.setBounds((int)(x*7.3), (int) (y*0.8), (int)x*2, (int)y*1);
		exitButtonListener = new ExitButtonListener();
		exitButton.addActionListener(exitButtonListener);
		//EXITボタンのカスタマイズ
		exitButton.setText("EXIT");
		exitButton.setFocusable(false);
		exitButton.setHorizontalTextPosition(JButton.CENTER);
		exitButton.setVerticalTextPosition(JButton.BOTTOM);
		exitButton.setFont(new Font("MV boil", Font.BOLD, 40));
		exitButton.setForeground(Color.black);
//		exitButton.setBackground(Color.cyan);
		exitButton.setContentAreaFilled(false);
		exitButton.setBorder(BorderFactory.createEtchedBorder());
		exitButton.setBorderPainted(false);
		
		//選択アイコン
		select = new JLabel();
		ImageIcon icon02 = new ImageIcon("images/tanabe_start-removebg-preview.png");
		select.setIcon(icon02);
		select.setOpaque(false);
		select.setBounds(60, 0, 500, 500);
//		select.setBorder(border);			//縁取り（いらなければ消す）
		
		//説明
		message = new JLabel();
		message.setHorizontalAlignment(SwingConstants.CENTER);
		message.setText("選択：←,→　決定：SPACE　or　click!");
		message.setFont(new Font("MV boil", Font.BOLD, 28));
		message.setVerticalAlignment(JLabel.CENTER);
		message.setHorizontalTextPosition(JLabel.CENTER);
		message.setBounds((int)(x*2.8),(int)(y*7),(int)(x*5),(int)(y*0.5));
//		message.setBorder(border);			//縁取り（いらなければ消す）
		
		//配置
		this.setLayout(null);
//		this.add(title);
		this.add(startButton);
		this.add(exitButton);
		this.add(select);
		this.add(message);
		
		//リスナーの設定
		myKeyListener = new MyKeyListener(this);
	}
	
	//内部クラス（選択の制御）
	private class MyKeyListener implements KeyListener{
		//貼り付け先を保持
		TitlePanel panel;
		
		//コンストラクタ
		public MyKeyListener(TitlePanel p) {
			super();
			panel = p;
			panel.addKeyListener(this);
			// TODO 自動生成されたコンストラクター・スタブ
		}
				
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO 自動生成されたメソッド・スタブ
			
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			// TODO 自動生成されたメソッド・スタブ
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			ImageIcon icon03;
			double x = Main.mainWindow.titlePanel.getWidth()/10.0;
			switch(e.getKeyCode()) {
			case KeyEvent.VK_T:
				Main.mainWindow.setFrontScreenAndFocus(ScreenMode.TUTORIAL);
				break;
			case KeyEvent.VK_RIGHT:
				
				//カーソル移動のSE
				sound.playTitleSE(0);
				
				if(checkMenu == Menu.START) {
					select.setLocation(select.getX()+(int)(x*7), select.getY());
					icon03 = new ImageIcon("images/tanabe_exit-removebg-preview.png");
					select.setIcon(icon03);
					select.setOpaque(false);
					checkMenu = Menu.EXIT;
				}
				break;
			case KeyEvent.VK_LEFT:
				
				//カーソル移動のSE
				sound.playTitleSE(0);
				
				if(checkMenu == Menu.EXIT) {
					select.setLocation(select.getX()-(int)(x*7), select.getY());
					icon03 = new ImageIcon("images/tanabe_start-removebg-preview.png");
					select.setIcon(icon03);
					select.setOpaque(false);
					checkMenu = Menu.START;
				}
				break;
			case KeyEvent.VK_SPACE:
				
				sound.stopBGM();			// title用BGMの停止
				sound.resetBGMPosition();	// 再生位置のリセット
				sound.playTitleSE(1);		// 決定のSE
				
				if(checkMenu == Menu.START) {
					//開始
//					gamePanel.fieldPanelStartGameLoop();
					game_Panel.Init();
					game_Panel.fieldPanelStartGameLoop();
					Main.mainWindow.setFrontScreenAndFocus(ScreenMode.GAME);
				}else if (checkMenu == Menu.EXIT) {
					//終了
					System.exit(0);
				}
				break;				
			}
		}
	}
	
	//内部クラス（STARTボタン用リスナー）
	private class StartButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO 自動生成されたメソッド・スタブ
			//ゲーム画面に進む
//			gamePanel.fieldPanelStartGameLoop();
			game_Panel.Init();
			game_Panel.fieldPanelStartGameLoop();
			Main.mainWindow.setFrontScreenAndFocus(ScreenMode.GAME);
		}
		
		
	}
	//内部クラス（EXITボタン用リスナー）
	private class ExitButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO 自動生成されたメソッド・スタブ
			//終了する
			System.exit(0);
		}
		
	}
//		title.setBorder(border);			//縁取り（いらなければ消す）
//		this.add(titleLabel);					//ラベルをこのパネルに貼る
	
//		//選択肢
//		start = new JLabel();
//		start.setText("START");
//		start.setFont(new Font("MV boil", Font.BOLD, 40));
//		start.setHorizontalTextPosition(JLabel.CENTER);
//		start.setVerticalTextPosition(JLabel.BOTTOM);
//		start.setBounds(330, 400, 150, 40);
////		start.setBorder(border);			//縁取り（いらなければ消す）
//
//		
//		exit = new JLabel();
//		exit.setText("EXIT");
//		exit.setFont(new Font("MV boil", Font.BOLD, 40));
//		exit.setHorizontalTextPosition(JLabel.CENTER);
//		exit.setVerticalTextPosition(JLabel.BOTTOM);
//		exit.setBounds(350, 450, 110, 40);
////		exit.setBorder(border);			//縁取り（いらなければ消す）
}

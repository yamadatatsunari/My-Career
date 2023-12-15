package tanabetory;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gamePanel.GamePanel;

public class TutorialPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	//定数フィールド
	Color backgroundColor = Color.BLACK;
	GamePanel game_Panel;
	//コンポーネント
	public Image tutorialImage1;
	JLabel tutorialLabel = new JLabel();
	
	//リスナー
	MyKeyListener myKeyListener;
	
	//コンストラクタ
	public TutorialPanel(GamePanel gamePanel) {
		game_Panel = gamePanel;
		//パネルサイズと貼り付け位置の設定は不要(CardLayoutが勝手に画面サイズに合わせてくれる)
		this.setLayout(null);//レイアウトの設定
		this.setBackground(backgroundColor);//背景の色
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2D = (Graphics2D)g;
		g2D.drawImage(tutorialImage1, 0, 0, tutorialImage1.getWidth(null), tutorialImage1.getHeight(null), null);
	}
	
	//コンストラクタが呼ばれた後手動で呼び出す
	public void prepareComponents() {
//		System.out.println("Tutorial.prepareComponents()");
//		tutorialImage1 = new ImageIcon("images/tutorial.png").getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT);
//		this.setBounds(0, 0, tutorialImage1.getWidth(null), tutorialImage1.getHeight(null));
//		ImageIcon tutorialImage = new ImageIcon(new ImageIcon("images/tutorial.png").getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT));
//		tutorialLabel.setBounds(0, 0, tutorialImage.getIconWidth(), tutorialImage.getIconHeight());
//		tutorialLabel.setIcon(tutorialImage);
//		tutorialLabel.setOpaque(true);
//		myKeyListener = new MyKeyListener(this);
		
		if(game_Panel.Get_debugFlag()) System.out.println("Tutorial.prepareComponents()");
		tutorialImage1 = new ImageIcon("images/tutorial.png").getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT);
		tutorialLabel.setBounds(0, 0, this.getWidth(), this.getHeight());
		tutorialLabel.setIcon(new ImageIcon(tutorialImage1));
		tutorialLabel.setOpaque(true);
		myKeyListener = new MyKeyListener(this);
	}
	
	private class MyKeyListener implements KeyListener {
		//貼り付け先を保持
		TutorialPanel panel;
		
		//コンストラクタ
		public MyKeyListener(TutorialPanel p) {
			super();
			panel = p;
			panel.addKeyListener(this);
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO 自動生成されたメソッド・スタブ
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO 自動生成されたメソッド・スタブ
			switch(e.getKeyCode()) {
			case KeyEvent.VK_H:
				Main.mainWindow.setFrontScreenAndFocus(ScreenMode.TITLE);
				break;
			}
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO 自動生成されたメソッド・スタブ
			
		}
		
	}
}

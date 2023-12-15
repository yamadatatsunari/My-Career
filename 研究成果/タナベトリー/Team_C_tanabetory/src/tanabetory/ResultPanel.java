package tanabetory;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gamePanel.GamePanel;

public class ResultPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	//定数フィールド
	Color backgroundColor = Color.magenta;
	private Image backgroundImage;
	//コンポーネント
//	JLabel resultLabel;
	JLabel scoreLabel;
	GamePanel game_Panel;
	Sound sound;
	//リスナー
	MyKeyListener myKeyListener;
	
	//コンストラクタ
	public ResultPanel(GamePanel gamePanel){
		//パネルサイズと貼り付け位置の設定は不要(CardLayoutが勝手に画面サイズに合わせてくれる)
		this.setLayout(null);//レイアウトの設定
		this.setBackground(backgroundColor);//背景の色
		try {
            backgroundImage = ImageIO.read(new File("images/result_tanabe.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
		game_Panel = gamePanel;
		//その他の使設定をここに追加
		sound = new Sound(this);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//画像をパネルサイズにスケール
		Image scaledImage = backgroundImage.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
		if(game_Panel.Get_debugFlag()) {
			System.out.println("ResultPanel.Width:"+this.getWidth()+", ResultPanel.Height:"+this.getHeight());
		}
		//画像を描画
		g.drawImage(scaledImage, 0, 0, this);
		
		sound.playBGM();
	}
	
	//コンストラクタが呼ばれた後手動で呼び出す
	public void prepareComponents() {
		//以降コンポーネントに関する命令(以下は一例)
//		resultLabel = new JLabel();				//ラベル生成
//		resultLabel.setText("Hボタンを押してホームに戻る");		//ラベルに文字を記入
//		resultLabel.setBounds(100, 200, 1200, 50);	//位置とサイズを指定
//		resultLabel.setFont(new Font("SansSerif", Font.PLAIN, 36));
//		this.add(resultLabel);					//ラベルをこのパネルに貼る
		scoreLabel = new JLabel();				//ラベル生成
		scoreLabel.setText("");
//		scoreLabel.setOpaque(true);
//		scoreLabel.setBackground(Color.RED);
		scoreLabel.setFont(new Font("SansSerif", Font.PLAIN, 36));
		scoreLabel.setBounds(0, 0, 300, 100);	//位置とサイズを指定
		this.add(scoreLabel);					//ラベルをこのパネルに貼る
		//リスナーを設置
		myKeyListener = new MyKeyListener(this);
	}
	
	public void setScore(int score, String PATH, int x, int y) {
		try {
            backgroundImage = ImageIO.read(new File(PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
		scoreLabel.setText("Score : " + game_Panel.GetScore());
		if(game_Panel.Get_debugFlag()) System.out.println("Score : " + score);	//コンソールにスコアを表示
		scoreLabel.setText("Score : " + score);	//ラベルに文字を記入
		scoreLabel.setFont(new Font("SansSerif", Font.PLAIN, 60));
		scoreLabel.setBounds(x, y, 500, 100);	//位置とサイズを指定
		this.revalidate();
	    this.repaint();
	}
	
	//内部クラス（hが押されたらタイトルへ）
	private class MyKeyListener implements KeyListener {
		//貼り付け先を保持
		JPanel panel;
		
		//コンストラクタ
		MyKeyListener(JPanel p){
			super();
			panel = p;
			panel.addKeyListener(this);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO 自動生成されたメソッド・スタブ
			switch(e.getKeyCode()) {
			case KeyEvent.VK_H:
				
				sound.stopBGM();
				sound.resetBGMPosition();
				Main.mainWindow.setFrontScreenAndFocus(ScreenMode.TITLE);
				break;
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO 自動生成されたメソッド・スタブ
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO 自動生成されたメソッド・スタブ
			
		}
	}
}

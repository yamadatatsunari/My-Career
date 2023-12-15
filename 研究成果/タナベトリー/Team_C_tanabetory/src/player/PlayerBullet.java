package player;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class PlayerBullet extends JLabel{
	private static final long serialVersionUID = 1l;	//よくわからんおまじない
	//敵が撃つ弾の処理を行う
	Image pBulletImage;
	int x = 0;	//x座標
	int y = 0;	//y座標
	int vec_x = 15;	//弾のスピード

	int set = 0;

	boolean flag = false;

	public PlayerBullet(){	//コンストラクタ
		//弾のテクスチャを設定
		pBulletImage = new ImageIcon("images/grass2.png").getImage();
		this.setBounds(x, y, pBulletImage.getWidth(null), pBulletImage.getHeight(null));

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;

		g2D.drawImage(pBulletImage, 0, 0, pBulletImage.getWidth(null), pBulletImage.getHeight(null),null);
	}

	public void Update() {
		//毎フレーム処理される
		if(flag == true) {
			x += vec_x;
			if(x > 1920) 
			{
				flag = false;
				x = -100;
			}
		}
		else
		{
			x = -100;
		}

	}

	//別のクラスで使うときに使いやすくするために書く
	public int Get_X() {
		return x;
	}

	public int Get_Y() {
		return y;
	}

	public int Get_VecX() {
		return x;
	}


	public boolean Get_flag()
	{
		return flag;
	}

	public void Set_X(int pos_bx) {
		x = pos_bx;
	}

	public void Set_Y(int pos_by) {
		y = pos_by;
	}

	public void Set_VecX(int vbx) {
		x = vbx;
	}

	public void Set_Bullet(int nx, int ny) {
		x = nx;
		y = ny;
	}

	public void Set_flag(boolean pflag) {
		flag = pflag;
	}

}

package enemy;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class EnemyBullet extends JLabel{
	private static final long serialVersionUID = 1l;	//よくわからんおまじない
	//敵が撃つ弾の処理を行う
	Image eBulletImage;
	int bx = 0;	//x座標
	int by = 0;	//y座標
	int vec_bx = -15;	//弾のスピード
	
	int ebset = 0;
	
	boolean flag = false;
	
	public EnemyBullet(){	//コンストラクタ
		//弾のテクスチャを設定
		eBulletImage = new ImageIcon("images/fire.png").getImage();
		this.setBounds(bx, by, eBulletImage.getWidth(null), eBulletImage.getHeight(null));
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		
		g2D.drawImage(eBulletImage, 0, 0, eBulletImage.getWidth(null), eBulletImage.getHeight(null),null);
	}
	
	public void Init()
	{
		flag = false;
	}
	
	public void Update() {
		//毎フレーム処理される

		if(flag == true) 
		{
			bx += vec_bx;
			if(bx < 0) {
				flag = false;
			}
		}
		else
		{
			bx = 2000;
		}
	}
	
	//別のクラスで使うときに使いやすくするために書く
	public int Get_bX() {
		return bx;
	}
	
	public int Get_bY() {
		return by;
	}
	
	public int Get_VecbX() {
		return vec_bx;
	}
	
	public int Get_Set() {
		return ebset;
	}
	
	public void Set_bX(int pos_bx) {
		bx = pos_bx;
	}
	
	public void Set_bY(int pos_by) {
		by = pos_by;
	}
	
	public void Set_VecbX(int vbx) {
		vec_bx = vbx;
	}
	
	public void Set_ebset(int s) {
		ebset = s;
	}
	
	public void Set_Bullet(int x, int y) {
		bx = x;
		by = y;
	}
	
	public void Set_flag(boolean bflag) {
		flag = bflag;
	}
}

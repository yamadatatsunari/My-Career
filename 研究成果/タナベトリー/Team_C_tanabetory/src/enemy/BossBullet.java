package enemy;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class BossBullet extends JLabel {
	private static final long serialVersionUID = 1l;	//よくわからんおまじない
	//敵が撃つ弾の処理を行う
	Image bBulletImage;
	int bx = 1000;	//x座標
	int by = 540;	//y座標
	int vec_bx = 4;	//弾のスピード
	
	boolean flag = false;
	private double angle;//回転角度
	
	public BossBullet(){	//コンストラクタ
		//弾のテクスチャを設定
		bBulletImage = new ImageIcon("images/shadowball.png").getImage();
		this.setBounds(bx, by, bBulletImage.getWidth(null), bBulletImage.getHeight(null));
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		
		g2D.drawImage(bBulletImage, 0, 0, bBulletImage.getWidth(null), bBulletImage.getHeight(null),null);
	}
	
	public void Init()
	{
		flag = false;
	}
	
	public void Update(int px,int py) {
		//毎フレーム処理される

		if(flag == true) 
		{
			double dx = px - bx;
	        double dy = py - by;
	        
	        angle = Math.atan2(dy, dx);
	        
	        bx += vec_bx * Math.cos(angle);
	        by += vec_bx * Math.sin(angle);
			
			if(bx < 0) 
			{
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
	
	
	public void Set_bX(int pos_bx) {
		bx = pos_bx;
	}
	
	public void Set_bY(int pos_by) {
		by = pos_by;
	}
	
	public void Set_VecbX(int vbx) {
		vec_bx = vbx;
	}
	
	public void Set_Bullet(int x, int y) {
		bx = x;
		by = y;
	}
	
	public void Set_flag(boolean bflag) {
		flag = bflag;
	}

}

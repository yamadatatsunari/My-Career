package gamePanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Trap extends JLabel{
	private static final long serialVersionUID = 1L;//おまじない
	
	ImageIcon trapImage;
	int x = 0;//X座表
	int y = 0;//Y座標
	int vec_x = -13;//X軸の力
	int vec_y = 0;//Y軸の力
	int count = 0;
	
	int set = 0;//上から何番目の足場にいるか
	
	public Trap()//コンストラクタ
	{
		//オリジナル画像の読み込みとサイズ変更
		Image originalImage = new ImageIcon("images/TNB.png").getImage();
		Image newScaleImage = originalImage.getScaledInstance(80, 80, Image.SCALE_DEFAULT);
		//テクスチャ設定
		trapImage = new ImageIcon(newScaleImage);
		this.setBounds(x,y,trapImage.getIconWidth(),trapImage.getIconHeight());
	}
	
	public void Init()
	{
		count = 0;
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		Graphics2D g2D = (Graphics2D) g;
		
		g2D.drawImage(trapImage.getImage(), 0, 0, trapImage.getIconWidth(), trapImage.getIconHeight(), null);
		
	}
	
	public void Update()
	{
		//毎フレーム処理される↓
		x += vec_x;
		if(x < 0)
		{
			x = 1920 + (500 * (9 - count));
			count++;
			if(count >= 9)
			{
				count = 0;
			}
		}
		
		if(set == 1)
		{
			y = 300-trapImage.getIconHeight();
		}
		
		if(set == 2)
		{
			y = 600-trapImage.getIconHeight();
		}
		
		if(set == 3)
		{
			y = 900-trapImage.getIconHeight();
		}
	}
	
	public int Get_X()
	{
		return x;
	}
	
	public int Get_Y()
	{
		return y;
	}
	
	public int Get_VecX()
	{
		return vec_x;
	}
	
	public int Get_VecY()
	{
		return vec_y;
	}
	
	public int Get_Set()
	{
		return set;
	}
	
	public void Set_X(int pos_x)
	{
		x = pos_x;
	}
	
	public void Set_Y(int pos_y)
	{
		y = pos_y;
	}
	
	public void Set_VecX(int vx)
	{
		vec_x = vx;
	}
	
	public void Set_VecY(int vy)
	{
		vec_x = vy;
	}
	
	public void Set_set(int s)
	{
		set = s;
	}
	
}
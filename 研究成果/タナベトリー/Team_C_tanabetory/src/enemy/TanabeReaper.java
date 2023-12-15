package enemy;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class TanabeReaper extends JLabel{
	private static final long serialVersionUID = 1L;
	public Image Tr_Image;//テクスチャ
	
	private int x = 2000;//X座標
	private int y = 600;//Y座標
	private boolean use = false;//フラグ（存在するかしないか）
	private int speed = 5;//移動速度
	private double angle;//回転角度
	private int count;
	
	public TanabeReaper()
	{
		Tr_Image = new ImageIcon("images/tanabe_reeper.png").getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT);
		this.setBounds(x,y,Tr_Image.getWidth(null),Tr_Image.getHeight(null));
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2D = (Graphics2D)g;
		g2D.drawImage(Tr_Image, 0, 0, Tr_Image.getWidth(null), Tr_Image.getHeight(null),null);
	}
	
	public void Init()
	{
		x = 1000;
		y = 600;
		use = false;
		count = 0;
	}
	
	public void Update(int px,int py)
	{
		if(use == true)
		{
			double dx = px - x;
	        double dy = py - y;
	        
	        angle = Math.atan2(dy, dx);
	        
	        x += speed * Math.cos(angle);
	        y += speed * Math.sin(angle);
		}
		else
		{
			x = 2000;
			y = 600;
		}
		count++;
		if(count > 120)
		{
			use = true;
		}
		else
		{
			use = false;
		}
        this.setBounds(x,y,Tr_Image.getWidth(null),Tr_Image.getHeight(null));
//		
	}
	
	public void SetX(int a)
	{
		x = a;
	}
	
	public void SetY(int b)
	{
		y = b;
	}
	
	public void Set_use(boolean bool)
	{
		use = bool;
	}
	
	public void Set_count(int c)
	{
		count = c;
	}
	
	public int Get_X()
	{
		return x;
	}
	
	public int Get_Y()
	{
		return y;
	}
	
	public boolean Get_use() 
	{
		return use;
	}
}

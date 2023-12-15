package player;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import gamePanel.GamePanel;

public class Player extends JLabel {
	private static final long serialVersionUID = 1L;
	
	public Image p_Image;//playerのテクスチャ
	public int hp = 3;
	public int x = 350;
	public int y = 400;
	int velocity = 0;
	int jump = 0;
	int jumpcount = 1;//ジャンプ回数
	int count = 0; //無敵時間用
	GamePanel game_Panel;
	
	boolean change = false;
	int set = 2;
	int ground = 400;//地面
	
	boolean megane = false;
	int zansu = 0;//メガネ弾の残数
	
	//コンストラクタ
	public Player(GamePanel gamePanel){
		game_Panel = gamePanel;
		p_Image = new ImageIcon("").getImage();
		//貼り付け先の位置とラベルのサイズを設定
		this.setBounds(x,y,p_Image.getWidth(null),p_Image.getHeight(null));
	}
	
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2D = (Graphics2D)g;
		g2D.drawImage(p_Image, 0, 0, p_Image.getWidth(null), p_Image.getHeight(null),null);
	}
	
	public void Init() {
		if(game_Panel.Get_debugFlag()) {
			System.out.println("Player.Init()");
		}
		hp = 3;
		x = 350;
		y = 400;
		velocity = 0;
		jump = 0;
		jumpcount = 1;//ジャンプ回数
		count = 0; //無敵時間用
		
		change = false;
		set = 2;
		ground = 400;//地面
		
		megane = false;
		zansu = 0;
		if(game_Panel.Get_debugFlag()) {
			zansu = 60000;
		}
	}
	
	public void SetX(int a)
	{
		x += a;
	}
	
	public void SetY(int b)
	{
		y = b;
	}
	
	public void Setjump(int j)
	{
		jump = j;
	}
	
	public void Setvelocity(int v)
	{
		velocity = v;
	}
	
	public void Set_set(int s)
	{
		set = s;
	}
	
	public void Set_ground(int g)
	{
		ground = g;
	}
	
	public void Set_jumpcount(int c)
	{
		jumpcount = c;
	}
	
	public void Set_Chenge(boolean tf)
	{
		change = tf;
	}
	
	public void Set_count(int n) {
		count = n;
	}
	
	public void Set_hp(int h) {
		hp += h;
	}
	
	public void Set_debug_hp() {
		hp = 3;
	}
	
	public void Set_megane(boolean m)
	{
		megane = m;
	}

	public void Set_zansu(int z)
	{
		zansu += z;
	}
	
	public int Get_X()
	{
		return x;
	}
	
	public int Get_Y()
	{
		return y;
	}
	
	public int Get_hp()
	{
		return hp;
	}
	
	public int Get_set()
	{
		return set;
	}
	
	public int Get_jumpcount()
	{
		return jumpcount;
	}
	public boolean Get_change() {
		return change;
	}
	
	public boolean Get_megane()
	{
		return megane;
	}

	public int Get_zansu()
	{
		return zansu;
	}
	
	public int Get_count()
	{
		return count;
	}
			
	public void Update()
	{
		//毎フレーム処理される↓
		count -=1;
		if(count <= 0)
		{
			count = 0;
		}
		
		if(set == 3)
		{
			ground = 700;
		}
				
		if(set == 2)
		{
			ground = 400;
		}
		
		if(set == 1)
		{
			ground = 100;
		}
		
		if(change == false)
		{
			jump += 1;
			if(jump > 18)
			{
				jump = 18;
			}
			
			y += jump;
			if(y > ground)
			{
				jumpcount = 1;
				y = ground;
			}
		}
		
		if(change == true)
		{
			if(set == 3)
			{
				ground = 700;
				if(y < ground - 50)
				{
					y += 10;
				}
				else
				{
					change = false;
				}
			}
			
			if(set == 2)
			{
				ground = 400;
				if(y < ground - 50)
				{
					y += 10;
				}
				else if(y > ground - 20)
				{
					y -= 10;
				}
				else
				{
					change = false;
				}
			}
			
			if(set == 1)
			{
				ground = 100;
				if(y > ground - 20)
				{
					y -= 10;
				}
				else
				{
					change = false;
				}
			}
		}
	}
}

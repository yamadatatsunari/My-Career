package enemy;


import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gamePanel.GamePanel;

public class BossEnemy extends JLabel {
	// 定数、変数
    final int ENEMYMAX = 3;
    int m_lane; // 次のレーンまでの高さの差
    JPanel m_panel;
    GamePanel game_Panel;

    Image[] p_Image = new Image[ENEMYMAX];
    int frame = 0;
    public JLabel[] m_enemyArray = new JLabel[ENEMYMAX];
    public int[] m_x = new int[ENEMYMAX];
    public int[] m_y = new int[ENEMYMAX];
    public int[] m_width = new int[ENEMYMAX];
    public int[] m_height = new int[ENEMYMAX];
    public int[] m_count = new int[ENEMYMAX];
    public int[] m_vecY = new int[ENEMYMAX];
    public boolean[] m_use = new boolean[ENEMYMAX];
    public int[] m_hp = new int[ENEMYMAX];
    
    public boolean[] m_attack = new boolean[ENEMYMAX];
    public int [] m_atcount = new int[ENEMYMAX];

	public BossEnemy(GamePanel gamePanel)
	{
		game_Panel = gamePanel;
		for(int i=0; i<ENEMYMAX; i++)
		{
			m_use[i] = true;
			m_vecY[i] = 1;
			m_attack[i] = false;
			m_atcount[i] = 0;
		}

		m_hp[0] = 20;
		m_hp[1] = 100;
		m_hp[2] = 100;
		frame = 0;
	}

	public void Init()
	{
		for(int i=0; i<ENEMYMAX; i++)
		{
			m_use[i] = true;
			m_vecY[i] = 1;
			m_attack[i] = false;
			m_atcount[i] = 0;
		}

		m_hp[0] = 20;
		m_hp[1] = 100;
		m_hp[2] = 100;
		frame = 0;
		
		for(int i=0; i<ENEMYMAX; i++)
        {
    		if(i == 0)
    		{
    			m_y[i] = 400;
        		m_x[i] = 2000;   // 1750
     
            	m_enemyArray[i].setBounds(m_x[i], m_y[i], m_width[i], m_height[i]);
            	m_enemyArray[i].setVisible(false); // 最初は表示にします
    		}
    		else
    		{
    			m_y[1] = 100;
    			m_y[2] = 700;
        		m_x[i] = 2000;
    		
            	m_enemyArray[i].setBounds(m_x[i], m_y[i], m_width[i], m_height[i]);
            	m_enemyArray[i].setVisible(true); // 最初は表示にします
    		}
        }
	}

	
	public void SetBossEnemy(JPanel panel,String texture1, String texture2, String texture3, int width1, int height1, int width2, int height2)
	{
    	 p_Image[0] = new ImageIcon(texture1).getImage();
    	 p_Image[1] = new ImageIcon(texture2).getImage();
    	 p_Image[2] = new ImageIcon(texture3).getImage();
    	 this.m_panel = panel;

    	for(int i=0; i<ENEMYMAX; i++)
        {
    		if(i == 0)
    		{
    			m_y[i] = 300;
        		m_x[i] = 2000;   // 1750
        		m_height[i] = height1;
            	m_width[i] = width1;

            	m_enemyArray[i] =  new JLabel(new ImageIcon(p_Image[0].getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
            	m_enemyArray[i].setBounds(m_x[i], m_y[i], m_width[i], m_height[i]);
            	m_enemyArray[i].setVisible(false); // 最初は表示にします
                m_panel.add(m_enemyArray[i]);
    		}
    		else
    		{
    			m_y[1] = 150;
    			m_y[2] = 750;
        		m_x[i] = 2000;
    			m_height[i] = height2;
    			m_width[i] = width2;

    			m_enemyArray[i] =  new JLabel(new ImageIcon(p_Image[i].getScaledInstance(150, 100, Image.SCALE_SMOOTH)));
            	m_enemyArray[i].setBounds(m_x[i], m_y[i], m_width[i], m_height[i]);
            	m_enemyArray[i].setVisible(true); // 最初は表示にします
                m_panel.add(m_enemyArray[i]);
    		}
        }
	}

	public void Update(int vec)
	{
		
		for(int i=0; i<ENEMYMAX; i++)
        {
    		m_y[i] += m_vecY[i] * vec;
    		m_count[i]++;
    		frame++;
    		
    		if(m_use[i] == true && m_attack[i] == false)
    		{
    			m_atcount[i]++;
    		}
    		
    		if(m_atcount[i] > 60 + (i * 60))
			{
				m_attack[i] = true;
				if(game_Panel.Get_debugFlag()) {
					System.out.println(m_atcount[i]);
				}
				m_atcount[i] = 0;
			}
    		
    		if(m_use[0] == false)
			{
				m_use[i] = false;
			}
    		
    		if(m_enemyArray[i] != null)
    		{
    			
    			if(m_hp[i] <= 0)
        		{
        			m_use[i] = false;
        		}
    			
    			// ふよふよ
    			if(m_x[i] >= 1500)
    			{
    				m_x[i]--;
    			}
    			if(m_count[i] % 4 == 0)
    			{
    				m_enemyArray[i].setBounds(m_x[i], m_y[i], m_width[i], m_height[i]);
    			}
    			if(m_count[i] > 20)
        		{
    				m_vecY[i] = m_vecY[i] * (-1);
    				m_count[i] = 0;

        		}
    			// 殺
    			if(m_use[i] == false)
    			{
    				m_enemyArray[i].setVisible(false);
    			}
    			if(m_use[0] == false)
    			{
    				m_enemyArray[0].setVisible(false);
    				m_enemyArray[1].setVisible(false);
    				m_enemyArray[2].setVisible(false);
    			}
    		}
        }
	}
	
	public void Set_attack(int i,boolean b)
	{
		m_attack[i] = b;
	}
	
	public void Set_atcount(int i,int a)
	{
		m_atcount[i] = a;
	}
	
	public void Set_hp(int i,int a)
	{
		m_hp[i] += a;
	}
	
	public int Get_X(int i)
	{
		return m_x[i];
	}
	
	public int Get_Y(int i)
	{
		return m_y[i];
	}
	
	public boolean Get_use(int i)
	{
		return m_use[i];
	}
	
	public boolean Get_attack(int i)
	{
		return m_attack[i];
	}
	
	public int Get_hp(int i)
	{
		return m_hp[i];
	}
}

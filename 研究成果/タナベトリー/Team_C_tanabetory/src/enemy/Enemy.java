package enemy;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Enemy extends JLabel{
	private static final long serialVersionUID = 1L;

    // 定数、変数
    final int ENEMYMAX = 15;
    Image p_Image;    //enemyのテクスチャ
    JPanel m_panel;
    int m_height;
    int m_width;
    int m_lane; // 次のレーンまでの高さの差
    public int m_wave;		// 1, 2, 3
    
    public JLabel[] m_enemyArray = new JLabel[ENEMYMAX];
    public int[] m_x = new int[ENEMYMAX];
    public int[] m_y = new int[ENEMYMAX];
    public int[] m_count = new int[ENEMYMAX];
    public int[] m_vecY = new int[ENEMYMAX];
    public int[] m_acount = new int [ENEMYMAX];
    public boolean[] aflag = new boolean[ENEMYMAX];
    public boolean[] m_use = new boolean[ENEMYMAX];
    public boolean[] m_waveuse = new boolean[ENEMYMAX];
    public int[] m_hp = new int[ENEMYMAX];

    //コンストラクタ
    public Enemy(){
    	for(int i=0; i<ENEMYMAX; i++)
        { 
    		m_vecY[i] = 1;
    		m_acount[i] = 0;
    		aflag[i] = false;
    		m_use[i] = true;
    		m_waveuse[i] = true;
    		m_hp[i] = 2;
        }
    	
    	for(int i=1; i<7; i++)
    	{
    		m_waveuse[i] = false;
    	}
    	
    	m_lane = 300;
    	m_wave = 1;
    }
    
    public void Init() 
	{
    	for(int i=0; i<ENEMYMAX; i++)
        { 
    		m_vecY[i] = 1;
    		m_acount[i] = 0;
    		aflag[i] = false;
    		m_use[i] = true;
    		m_waveuse[i] = true;
    		m_hp[i] = 2;
        }
    	
    	m_lane = 300;
    	m_wave = 1;
    	
    	
    	m_y[0] = 450;
 		m_y[1] = 150;
 		m_y[2] = 750;
 		for(int a = 0; a<4; a++)
 		{
 			m_y[3 + (a*3)] = 150;
     		m_y[4 + (a*3)] = 450;
     		m_y[5 + (a*3)] = 750;
 		}
 		
    	for(int i=0; i<ENEMYMAX; i++)
        {
    		m_x[i] = 2000;  // 1100
    	
        	m_enemyArray[i].setBounds(m_x[i], m_y[i], m_width, m_height);
        	m_enemyArray[i].setVisible(false); // 最初は表示にします
        	m_enemyArray[0].setVisible(true); // 最初は表示にします
        }
	}
    
    public void SetEnemy(JPanel panel, String texture, int width, int height)
    {
    	m_height = height;
    	m_width = width;
    	this.m_panel = panel;
    	
    	 p_Image = new ImageIcon(texture).getImage();
    	
    	m_y[0] = 450;
 		m_y[1] = 150;
 		m_y[2] = 750;
 		for(int a = 0; a<4; a++)
 		{
 			m_y[3 + (a*3)] = 150;
     		m_y[4 + (a*3)] = 450;
     		m_y[5 + (a*3)] = 750;
 		}
 		
    	for(int i=0; i<ENEMYMAX; i++)
        {
    		m_x[i] = 2000;
    		
        	m_enemyArray[i] =  new JLabel(new ImageIcon(p_Image.getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
        	m_enemyArray[i].setBounds(m_x[i], m_y[i], m_width, m_height);
        	m_enemyArray[0].setVisible(true);  // 最初は表示にします
        	m_enemyArray[i].setVisible(false); // 最初は非表示にします
            panel.add(m_enemyArray[i]);
        }
    }
    
    public int Get_X(int i)
    {
        return m_x[i];
    }
    
    public int Get_Y(int i)
    {
        return m_y[i];
    }
    
    public boolean Get_Use(int i)
    {
        return m_use[i];
    }
    
    public boolean Get_aflag(int f) {
    	return aflag[f];
    }
    
    public int Get_hp(int i)
    {
    	return m_hp[i];
    }
    
    public void Set_aflag(int i, boolean f) {
    	aflag[i] = f;
    }
    
    public void Set_use(int i, boolean m) {
    	m_use[i] = m;
    }
    
    public void Set_hp(int i,int h)
    {
    	m_hp[i] += h;
    }
    
    public void EnemyUpdate(float vec)
    {
    	for(int i=0; i<ENEMYMAX; i++)
        {
    		m_y[i] += m_vecY[i] * vec;
    		m_count[i]++;
    		// 攻撃処理
    		if(m_use[i] == true && m_x[i] <1920) {
    			if(m_hp[i] <= 0)
    			{
    				m_use[i] = false;
    			}
    			m_acount[i]++;		//攻撃カウントを増やす
    		} else {
    			m_acount[i] = 0;
    		}
    		// ふわふわ
    		if(m_enemyArray[i] != null)
    		{
    			if(m_count[i] % 4 == 0)
    			{
    				m_enemyArray[i].setBounds(m_x[i], m_y[i], m_width, m_height);
    			}
    			
    			if(m_count[i] > 20)
        		{
    				m_vecY[i] = m_vecY[i] * (-1);
    				m_count[i] = 0;
    				
        		}
    			if(m_use[i] == false)
    			{
    				m_enemyArray[i].setVisible(false);
    			}
    		}
    		
    		if(m_acount[i] > 60) 
    		{
    			aflag[i] = true;
    			//m_acount[i] = 0;
    		}
    		if(aflag[i] == true) 
    		{
    			m_acount[i] = -600;
    		}
        }
    	
    	// ウェーブ処理
    	switch(m_wave)
    	{
    	case 1:
    		if(m_x[0] >= 1700)
			{
				m_x[0]--;
			}
    		if(m_use[0] == false )
    		{
    			m_wave = 2;
    			m_waveuse[1] = true;
    		}
    		break;
    	case 2:
    		for(int i=1; i<3; i++)
    		{
    			if(m_x[i] >= 1700)
    			{
    				m_x[i]--;
    			}
    		}
    		if(m_waveuse[1] == true)
    		{
    			m_enemyArray[1].setVisible(true);
    			m_enemyArray[2].setVisible(true);
    			m_waveuse[2] = true;
    			m_waveuse[1] = false;
    		}
    		if(m_use[1] == false && m_use[2] == false)
    		{
    			m_wave = 3;
    		}
    		break;
    	case 3:
    		SetWaveEnemy(3, 4, 5, 3);
    		break;	
    	case 4:
    		SetWaveEnemy(6, 7, 8, 4);
    		break;
    	case 5:
    		SetWaveEnemy(9, 10, 11, 5);
    		break;
    	case 6:
    		SetWaveEnemy(12, 13, 14, 6);
    		break;
    	}
    	
    }

    void SetWaveEnemy(int i0, int i1, int i2, int wave)
    {
    	for(int i=i0; i<i2+1; i++)
		{
			if(m_x[i] >= 1700)
			{
				m_x[i]--;
			}
		}
		if(m_waveuse[wave-1] == true)
		{
			m_enemyArray[i0].setVisible(true);
			m_enemyArray[i1].setVisible(true);
			m_enemyArray[i2].setVisible(true);
			m_waveuse[wave] = true;
			m_waveuse[wave-1] = false;
		}
		if(m_use[i0] == false && m_use[i1] == false && m_use[i2] == false)
		{
			m_wave = wave + 1;
		}
    }
	
}
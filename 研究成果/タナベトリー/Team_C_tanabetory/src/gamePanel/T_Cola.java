package gamePanel;

import java.awt.Image;
import java.security.SecureRandom;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class T_Cola extends JLabel{
private static final long serialVersionUID = 1L;//おまじない
//定数、変数
public final int COLAMAX = 2;
Image c_Image;    //enemyのテクスチャ
int m_height;
int m_width;
int m_lane; // 次のレーンまでの高さの差

SecureRandom m_rand;
int m_randY;

public JLabel[] m_itemArray = new JLabel[COLAMAX];
public int[] m_x = new int[COLAMAX];
public int[] m_y = new int[COLAMAX];
boolean [] cola_true  = new boolean[COLAMAX];
public int[] count = new int [COLAMAX];


//コンストラクタ
T_Cola(){
    m_lane = 300;

    m_rand = new SecureRandom();          
}

public void SetCola(JPanel panel, String texture, int width, int height)
{
	m_height = height;
	m_width = width;

	 c_Image = new ImageIcon(texture).getImage();

	for(int i=0; i<COLAMAX; i++)
    {
		m_randY = m_rand.nextInt(3) * m_lane + 200; // 200, 500, 800 のいずれかのY座標をランダムに選択

		m_y[i] = m_randY;
		m_x[i] = m_x[i] + i * 150;
		cola_true[i] = true;
		count[i] = 0;

    	m_itemArray[i] =  new JLabel(new ImageIcon(c_Image.getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
    	m_itemArray[i].setBounds(m_x[i], m_y[i], m_width, m_height);
    	m_itemArray[i].setVisible(true); // 最初は表示にします
        panel.add(m_itemArray[i]);
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
public boolean Get_cola_true(int i)
{
	return cola_true[i];
}

public int Get_count(int i)
{
	return count[i];
}

public void Set_cola_true(int i,boolean a)
{
	cola_true[i] = a;
}

public void Set_count(int i, int c)
{
	count[i] = c;
}

public void Update(int vec)
{

	for(int i=0; i<COLAMAX; i++)
    {
		if (cola_true[i] == true)
		{
			m_x[i] += vec;
		}

		if(m_itemArray[i] != null)
		{
			m_itemArray[i].setBounds(m_x[i], m_y[i], m_width, m_height);

			if(m_x[i] < -110 )
    		{
				//m_itemArray[i].setVisible(false);
    			//this.remove(m_itemArray[i]);
    			//m_itemArray[i] = null;
    		}
		}

		if (m_x[i] <= 0|| cola_true[i] == false) 
		{
			m_rand = new SecureRandom();          
			m_randY = m_rand.nextInt(3) * m_lane + 200; // 200, 500, 800 のいずれかのY座標をランダムに選択
			m_y[i] = m_randY;
			m_x[i] = 2500;
			count[i] -= 1;
			if(count[i] < 0)
			{
				count[i] = 0;
				cola_true[i] = true;
			}

		}


    }

  }
}
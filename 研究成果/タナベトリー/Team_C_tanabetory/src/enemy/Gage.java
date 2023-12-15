package enemy;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Gage extends JLabel {
	// 変数
	int m_x, m_y;
	int m_width, m_height;
	int m_hp;
	int m_hpMax;
	Image m_image1;   // 緑
	Image m_image2;   // 赤
	JLabel m_label1 = new JLabel();
	JLabel m_label2 = new JLabel();
    JPanel m_panel;
	
	public Gage()
	{
		m_x = 0;
		m_y = 0;
		m_width = 0;
		m_height = 0;
		m_hpMax = 20;
	}
	
	public void SetGage(int width, int height, String texture1, String texture2, JPanel panel)
	{
		m_width = width;
		m_height = height;
		
		m_image1 = new ImageIcon(texture1).getImage();
		m_image2 = new ImageIcon(texture2).getImage();
		
		m_label1 =  new JLabel(new ImageIcon(m_image1.getScaledInstance(200, 50, Image.SCALE_SMOOTH)));
    	m_label1.setBounds(m_x, m_y, m_width, m_height);
    	m_label1.setVisible(true);
        panel.add(m_label1);
        m_label2 =  new JLabel(new ImageIcon(m_image2.getScaledInstance(200, 50, Image.SCALE_SMOOTH)));
    	m_label2.setBounds(m_x, m_y, m_width, m_height);
    	m_label2.setVisible(true);
        panel.add(m_label2);
	}
	
	public void Init()
	{
		m_hpMax = 20;
		m_x = 300;
		m_y = 200;
	}
	
	public void SetPos(int x, int y)
	{
		m_x = x;
		m_y = y;
		
		m_label1.setBounds(m_x, m_y, (m_width * (m_hpMax - m_hp))/m_hpMax, m_height);
		m_label2.setBounds(m_x, m_y, m_width, m_height);
	}
	
	public void Update(int hp)
	{
		m_hp = m_hpMax - hp;
	}
}

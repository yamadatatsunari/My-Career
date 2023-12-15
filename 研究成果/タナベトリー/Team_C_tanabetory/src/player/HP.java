package player;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HP extends JLabel{
	// 変数
		static final public int m_LIFEMAX = 3;
		public int m_nowLife;
		private Image m_imagePls;
		private Image m_imageMin;
		private File m_imageFilePls;
		private File m_imageFileMin;
		private JPanel m_panel;
		
		private int m_x, m_y;
		private int m_width, m_height;
		
		private JLabel[] m_labelMinArray;
		public JLabel[] m_labelPlsArray;
		
		public HP()
		{
			// HPに最大値を代入
			m_nowLife = m_LIFEMAX;
			
			try
			{
				// テクスチャの読み込み
				m_imageFilePls = new File("images/PlsLife.png");
		        m_imagePls = ImageIO.read(m_imageFilePls);
		        m_imageFileMin = new File("images/MinLife.png");
		        m_imageMin = ImageIO.read(m_imageFileMin);
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		
		public void SetLife(JPanel panel, int kinds, int x, int y, int width, int height)
		{
			this.m_panel = panel;
			m_x = x;
	    	m_y = y;
	    	m_height = height;
	    	m_width = width;
	    	
	    	m_labelPlsArray = new JLabel[m_LIFEMAX];
			m_labelMinArray = new JLabel[m_LIFEMAX];
	        for (int i = 0; i < m_LIFEMAX; i++) 
	        {
	        	m_labelPlsArray[i] = new JLabel(new ImageIcon(m_imagePls.getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
	            m_labelPlsArray[i].setBounds(m_x + i * 80, m_y, m_width, m_height);
	            m_labelPlsArray[i].setVisible(true); // 最初は表示にします
	            //m_panel.add(m_labelPlsArray[i]);
	        	
	            m_labelMinArray[i] = new JLabel(new ImageIcon(m_imageMin.getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
	            m_labelMinArray[i].setBounds(m_x + i * 80, m_y, m_width, m_height);
	            m_labelMinArray[i].setVisible(false); // 最初は非表示にします
	            m_panel.add(m_labelMinArray[i]);
	        }
			
	    	for (int i = 0; i < m_LIFEMAX; i++) 
			{
	    		m_labelPlsArray[i].setVisible(true);
	            m_panel.add(m_labelPlsArray[i]);
				m_labelMinArray[i].setVisible(false);
	            m_panel.add(m_labelMinArray[i]);
	        } 
		}
		
		public void SetStartLife()
		{
			for (int i = 0; i < m_LIFEMAX; i++) 
			{
				
				m_labelPlsArray[i].setVisible(true); // 最初は表示にします
				//m_panel.add(m_labelPlsArray[i]);
			}
		}
		
		public void updatePlayerLife(int amount) 
		{
			 m_nowLife = amount;

			 if (m_nowLife < 0) 
			 {
				 m_nowLife = 0;
			 }

			 for (int i = m_LIFEMAX - 1; i >= 0; i--) 
			 {
			    
				 if (i >= m_nowLife) 
				 {
					 m_labelMinArray[i].setVisible(true);
					 m_labelPlsArray[i].setVisible(false);
			     } 
				 else 
			     {
			         m_labelMinArray[i].setVisible(false);
			     }
			 }
		}
}

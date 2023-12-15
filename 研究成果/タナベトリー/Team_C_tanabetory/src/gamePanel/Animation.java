package gamePanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

import tanabetory.Main;

public class Animation extends JLabel {
	// 変数
	int m_x1, m_y1;
	int m_x2, m_y2;
	int m_x3, m_y3;
	int uv_x1, uv_y1;
	int uv_x2, uv_y2;
	int m_width, m_height;
	int m_index;
	int m_indexNum_x;   // 横の分割数の総数
	int m_indexNum_y;   // 縦の分割数の総数
	int m_count_x, m_count_y;
	int m_framecount;
	int m_frameMax;
	public boolean m_use;
	File m_file;
	BufferedImage m_image;
	
	Animation()
	{
		// 初期化
		m_index = 0;
		m_indexNum_x = 0;
		m_indexNum_y = 0;
		m_count_x = 0;
		m_count_y = 0;
		m_use = true;
	}
	
	public void SetAnimation(String texture, int x1, int y1, int x2, int y2, int indexNum_x, int indexNum_y, int frameMax)
	{
		m_x1 = x1;
		m_y1 = y1;
		m_x2 = x2;
		m_y2 = y2;
		m_indexNum_x = indexNum_x;
		m_indexNum_y = indexNum_y;
		m_frameMax = frameMax;
		
		m_x3 = x2 - x1;
		m_y3 = y2 - y1;
		
		try
		{
			m_file = new File(texture);
			m_image = ImageIO.read(m_file);
			this.setBounds(0,0,Main.mainWindow.WIDTH,Main.mainWindow.HEIGHT);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		if(m_indexNum_y != 0 && m_indexNum_x != 0)
		{
			uv_x1 = m_count_x * (m_image.getWidth()/m_indexNum_x);
			uv_y1 = m_count_y * (m_image.getHeight()/m_indexNum_y);
			uv_x2 = m_count_x * (m_image.getWidth()/m_indexNum_x) + m_image.getWidth()/m_indexNum_x;
			uv_y2 = m_count_y * (m_image.getHeight()/m_indexNum_y) + m_image.getHeight()/m_indexNum_y;
		}
		else if(m_indexNum_y == 0 && m_indexNum_x == 0)
		{
			uv_x1 = m_count_x;
			uv_y1 = m_count_y;
			uv_x2 = m_image.getWidth();
			uv_y2 = m_image.getHeight();
		}
		else if(m_indexNum_x == 0)
		{
			uv_x1 = m_count_x;
			uv_y1 = m_count_y * (m_image.getHeight()/m_indexNum_y);
			uv_x2 = m_image.getWidth();
			uv_y2 = m_count_y * (m_image.getHeight()/m_indexNum_y) + m_image.getHeight()/m_indexNum_y;
		}
		else
		{
			uv_x1 = m_count_x * (m_image.getWidth()/m_indexNum_x);
			uv_y1 = m_count_y;
			uv_x2 = m_count_x * (m_image.getWidth()/m_indexNum_x) + m_image.getWidth()/m_indexNum_x;
			uv_y2 = m_image.getHeight();
		}
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		if(m_use == true)
		{
			if(m_framecount > m_frameMax)
			{
				if(m_count_x < m_indexNum_x -1)
				{
					m_count_x++;
				}
				else
				{
					m_count_x = 0;
					
					if(m_count_y < m_indexNum_y)
					{
						m_count_y++;
					}
					else
					{
						m_count_y = 0;
					}
				}
				
				if(m_indexNum_y != 0 && m_indexNum_x != 0)
				{
					uv_x1 = m_count_x * (m_image.getWidth()/m_indexNum_x);
					uv_y1 = m_count_y * (m_image.getHeight()/m_indexNum_y);
					uv_x2 = m_count_x * (m_image.getWidth()/m_indexNum_x) + m_image.getWidth()/m_indexNum_x;
					uv_y2 = m_count_y * (m_image.getHeight()/m_indexNum_y) + m_image.getHeight()/m_indexNum_y;
				}
				else if(m_indexNum_y == 0 && m_indexNum_x == 0)
				{
					uv_x1 = m_count_x;
					uv_y1 = m_count_y;
					uv_x2 = m_image.getWidth();
					uv_y2 = m_image.getHeight();
				}
				else if(m_indexNum_x == 0)
				{
					uv_x1 = m_count_x;
					uv_y1 = m_count_y * (m_image.getHeight()/m_indexNum_y);
					uv_x2 = m_image.getWidth();
					uv_y2 = m_count_y * (m_image.getHeight()/m_indexNum_y) + m_image.getHeight()/m_indexNum_y;
				}
				else
				{
					uv_x1 = m_count_x * (m_image.getWidth()/m_indexNum_x);
					uv_y1 = m_count_y;
					uv_x2 = m_count_x * (m_image.getWidth()/m_indexNum_x) + m_image.getWidth()/m_indexNum_x;
					uv_y2 = m_image.getHeight();
				}
				
				m_framecount = 0;
			}
			
			//System.out.println(m_count_x);
			m_framecount++;
			g2d.drawImage(m_image, m_x1, m_y1, m_x2, m_y2, uv_x1, uv_y1, uv_x2, uv_y2, this);
		}
	}
	
	public void Update(int x, int y)
	{
		m_x1 = x;
		m_y1 = y;
		m_x2 = m_x3 + x;
		m_y2 = m_y3 + y;
		
		repaint();
	}
}

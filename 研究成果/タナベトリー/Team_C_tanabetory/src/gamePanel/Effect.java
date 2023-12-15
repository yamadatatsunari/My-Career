package gamePanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

import tanabetory.Main;

public class Effect extends JLabel{
	// 変数
		public int m_EffectMax;
		int m_width, m_height;
		int m_indexNum_x;   // 横の分割数の総数
		int m_indexNum_y;   // 縦の分割数の総数
		int m_framecount;
		int m_frameMax;
		boolean m_enduse = false;
		File m_file;
		BufferedImage m_image;
		Graphics m_g;
		
		List<Integer> m_x1 = new ArrayList<>();
		List<Integer> m_y1 = new ArrayList<>();
		List<Integer> m_x2 = new ArrayList<>();
		List<Integer> m_y2 = new ArrayList<>();
		List<Integer> m_x3 = new ArrayList<>(); 
		List<Integer> m_y3 = new ArrayList<>();
		List<Integer> uv_x1 = new ArrayList<>();
		List<Integer> uv_y1 = new ArrayList<>();
		List<Integer> uv_x2 = new ArrayList<>(); 
		List<Integer> uv_y2 = new ArrayList<>();
		List<Integer> m_count_x = new ArrayList<>();
		List<Integer> m_count_y = new ArrayList<>();
		List<Boolean> m_use = new ArrayList<>();
		List<Boolean> m_one = new ArrayList<>();
		
		Effect()
		{
			// 初期化
			m_indexNum_x = 0;
			m_indexNum_y = 0;
		}
		
		public void SetAnimation(String texture, int x1, int y1, int x2, int y2, int indexNum_x, int indexNum_y, int frameMax, int max)
		{
			try
			{
				m_file = new File(texture);
				m_image = ImageIO.read(m_file);
				this.setBounds(0,0, Main.mainWindow.WIDTH,Main.mainWindow.HEIGHT);
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			
			// 引数を代入
			m_EffectMax = max;
			m_indexNum_x = indexNum_x;
			m_indexNum_y = indexNum_y;
			m_frameMax = frameMax;
			
			for(int i=0; i<m_EffectMax; i++)
			{
				m_x1.add(i,x1);
				m_y1.add(i,y1);
				m_x2.add(i,x2);
				m_y2.add(i,y2);
				m_x3.add(i,x2-x1);
				m_y3.add(i,y2-y1);
				m_count_x.add(i,0);
				m_count_y.add(i,0);
				
				m_use.add(i,false);
				m_one.add(i,false);
				
				if(m_indexNum_y != 0 && m_indexNum_x != 0)
				{
					uv_x1.add(i,m_count_x.get(i) * (m_image.getWidth()/m_indexNum_x));
					uv_y1.add(i,m_count_y.get(i) * (m_image.getHeight()/m_indexNum_y));
					uv_x2.add(i,m_count_x.get(i) * (m_image.getWidth()/m_indexNum_x) + m_image.getWidth()/m_indexNum_x);
					uv_y2.add(i,m_count_y.get(i) * (m_image.getHeight()/m_indexNum_y) + m_image.getHeight()/m_indexNum_y);
				}
				else if(m_indexNum_y == 0 && m_indexNum_x == 0)
				{
					uv_x1.add(i,m_count_x.get(i));
					uv_y1.add(i,m_count_y.get(i));
					uv_x2.add(i,m_image.getWidth());
					uv_y2.add(i,m_image.getHeight());
				}
				else if(m_indexNum_x == 0)
				{
					uv_x1.add(i,m_count_x.get(i));
					uv_y1.add(i,m_count_y.get(i) * (m_image.getHeight()/m_indexNum_y));
					uv_x2.add(i,m_image.getWidth());
					uv_y2.add(i,m_count_y.get(i) * (m_image.getHeight()/m_indexNum_y) + m_image.getHeight()/m_indexNum_y);
				}
				else
				{
					uv_x1.add(i,m_count_x.get(i) * (m_image.getWidth()/m_indexNum_x));
					uv_y1.add(i,m_count_y.get(i));
					uv_x2.add(i,m_count_x.get(i) * (m_image.getWidth()/m_indexNum_x) + m_image.getWidth()/m_indexNum_x);
					uv_y2.add(i,m_image.getHeight());
				}
			}
		}
		
		public void SetEffectPos(int x, int y, int number)
		{
			m_x1.set(number, x);
			m_y1.set(number, y);
			m_x2.set(number, m_x3.get(number) + x);
			m_y2.set(number, m_y3.get(number) + y);
		}
		
		public void Reset()
		{
			for(int i=0; i<m_EffectMax; i++)
			{
				if(m_indexNum_y != 0 && m_indexNum_x != 0)
				{
					uv_x1.set(i,m_count_x.get(i) * (m_image.getWidth()/m_indexNum_x));
					uv_y1.set(i,m_count_y.get(i) * (m_image.getHeight()/m_indexNum_y));
					uv_x2.set(i,m_count_x.get(i) * (m_image.getWidth()/m_indexNum_x) + m_image.getWidth()/m_indexNum_x);
					uv_y2.set(i,m_count_y.get(i) * (m_image.getHeight()/m_indexNum_y) + m_image.getHeight()/m_indexNum_y);
				}
				else if(m_indexNum_y == 0 && m_indexNum_x == 0)
				{
					uv_x1.add(i,m_count_x.get(i));
					uv_y1.add(i,m_count_y.get(i));
					uv_x2.add(i,m_image.getWidth());
					uv_y2.add(i,m_image.getHeight());
				}
				else if(m_indexNum_x == 0)
				{
					uv_x1.set(i,m_count_x.get(i));
					uv_y1.set(i,m_count_y.get(i) * (m_image.getHeight()/m_indexNum_y));
					uv_x2.set(i,m_image.getWidth());
					uv_y2.set(i,m_count_y.get(i) * (m_image.getHeight()/m_indexNum_y) + m_image.getHeight()/m_indexNum_y);
				}
				else
				{
					uv_x1.set(i,m_count_x.get(i) * (m_image.getWidth()/m_indexNum_x));
					uv_y1.set(i,m_count_y.get(i));
					uv_x2.set(i,m_count_x.get(i) * (m_image.getWidth()/m_indexNum_x) + m_image.getWidth()/m_indexNum_x);
					uv_y2.set(i,m_image.getHeight());
				}
			}
		}
		
		@Override
		protected void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			
			for(int i=0; i<m_EffectMax; i++)
			{
				if(m_use.get(i) == true)
				{
					if(m_framecount > m_frameMax)
					{
						if(m_count_x.get(i) < m_indexNum_x -1)
						{
							m_count_x.set(i, m_count_x.get(i)+1);
						}
						else
						{
							m_count_x.set(i, 0);
							
							if(m_count_y.get(i) < m_indexNum_y)
							{
								m_count_y.set(i, m_count_y.get(i)+1);
							}
							else
							{
								m_use.set(i, false);
								m_count_x.set(i, 0);
								m_count_y.set(i, 0);
							}
						}
						
						
						if(m_indexNum_y != 0 && m_indexNum_x != 0)
						{
							uv_x1.set(i,m_count_x.get(i) * (m_image.getWidth()/m_indexNum_x));
							uv_y1.set(i,m_count_y.get(i) * (m_image.getHeight()/m_indexNum_y));
							uv_x2.set(i,m_count_x.get(i) * (m_image.getWidth()/m_indexNum_x) + m_image.getWidth()/m_indexNum_x);
							uv_y2.set(i,m_count_y.get(i) * (m_image.getHeight()/m_indexNum_y) + m_image.getHeight()/m_indexNum_y);
						}
						else if(m_indexNum_y == 0 && m_indexNum_x == 0)
						{
							uv_x1.add(i,m_count_x.get(i));
							uv_y1.add(i,m_count_y.get(i));
							uv_x2.add(i,m_image.getWidth());
							uv_y2.add(i,m_image.getHeight());
						}
						else if(m_indexNum_x == 0)
						{
							uv_x1.set(i,m_count_x.get(i));
							uv_y1.set(i,m_count_y.get(i) * (m_image.getHeight()/m_indexNum_y));
							uv_x2.set(i,m_image.getWidth());
							uv_y2.set(i,m_count_y.get(i) * (m_image.getHeight()/m_indexNum_y) + m_image.getHeight()/m_indexNum_y);
						}
						else
						{
							uv_x1.set(i,m_count_x.get(i) * (m_image.getWidth()/m_indexNum_x));
							uv_y1.set(i,m_count_y.get(i));
							uv_x2.set(i,m_count_x.get(i) * (m_image.getWidth()/m_indexNum_x) + m_image.getWidth()/m_indexNum_x);
							uv_y2.set(i,m_image.getHeight());
						}
						
						m_framecount = 0;
					}
					else
					{
						m_enduse = false;
					}
					m_framecount++;
					
					g2d.drawImage(m_image, m_x1.get(i), m_y1.get(i), m_x2.get(i), m_y2.get(i), uv_x1.get(i), uv_y1.get(i), uv_x2.get(i), uv_y2.get(i), this);
				}
			}
		}
		
		public void Update()
		{
			repaint();
		}
}

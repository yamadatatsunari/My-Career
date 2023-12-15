package gamePanel;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Scroll extends JLabel implements ActionListener{
    // 変数
    Image m_image;
    File m_imageFile;
    JLabel m_label1;
    JLabel m_label2;
    JPanel m_panel;
    int m_x1;  // 1つ目の画像のX座標
    int m_x2;  // 2つ目の画像のX座標
    int m_scx;
    int m_speed;
    int m_y;
    int m_height;
    int m_width;
    boolean m_use;

    // コンストラクタ
    public Scroll() 
    {
    	 m_x1 = 0;
         m_x2 = 0;
    	m_scx = 0;
    	m_speed = 0;
        m_y = 0;
        m_height = 0;
        m_width = 0;

    }
    
    public void SetTexture(String texture, int x,int y, int width, int height, JPanel imagePanel, boolean use)
	{
    	m_x1 = x;
        m_x2 = x + width;
		m_y = y;
		m_width = width;
		m_height = height;
		this.m_panel = imagePanel;
		m_use = use;
	
		try 
        {
			// Load the image
            m_imageFile = new File(texture);
            m_image = ImageIO.read(m_imageFile);

            // 画像を2つ使って無限にスクロールさせる
            m_label1 = new JLabel(new ImageIcon(m_image.getScaledInstance(m_width, m_height, Image.SCALE_SMOOTH)));
            m_label2 = new JLabel(new ImageIcon(m_image.getScaledInstance(m_width, m_height, Image.SCALE_SMOOTH)));

            // 2つのラベルを隣接させて配置
            //m_label1.setBounds(m_x1, m_y, m_width, m_height);
            //m_label2.setBounds(m_x2 + m_width, m_y, m_width, m_height);

            // ラベルを画像パネルに追加
            imagePanel.add(m_label1);
            imagePanel.add(m_label2);
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
	}
    
    @Override
    protected void paintComponent(Graphics g)
    {
    	
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
    	if(m_use == true)
    	{
    		m_label1.setVisible(m_use);
    		m_label2.setVisible(m_use);
    		m_x1 -= m_speed;
            m_x2 -= m_speed;

            // 1つ目の画像が画面外にスクロールしたら、2つ目の画像の右隣に配置
            if (m_x1 + m_width <= 0) {
                m_x1 = m_x2 + m_width;
            }

            // 2つ目の画像が画面外にスクロールしたら、1つ目の画像の右隣に配置
            if (m_x2 + m_width <= 0) {
                m_x2 = m_x1 + m_width;
            }

            m_label1.setBounds(m_x1, m_y, m_width, m_height);
            m_label2.setBounds(m_x2, m_y, m_width, m_height);
    	}
    	else
    	{
    		m_label1.setVisible(m_use);
    		m_label2.setVisible(m_use);
    	}
    }
    

    public void SetVec(int vec)
    {
    	m_speed = vec;
    }
}

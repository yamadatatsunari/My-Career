package player;

import javax.swing.JLabel;

public class PR_Megane {
private int megane = 0;
    
    private int m_x, m_y, m_width, m_heigth;
    public JLabel m_label;
    
    public PR_Megane( int x, int y, int width, int heigth){
    	megane = 0;
    	m_x = x;
    	m_y = y;
    	m_width = width;
    	m_heigth = heigth;
    	
    	m_label = new JLabel();
    	
    	m_label.setText("score : " + String.valueOf(megane));
    	
    	m_label.setBounds(x, y, width, heigth);
    }
    
    public void Update(int amount,int x,int y) {
        megane = amount;
        m_x = x;
        m_y = y;
        
        m_label.setText("残めがね :" + String.valueOf(megane));
    	
    	m_label.setBounds(m_x, m_y, m_width, m_heigth);
    }

    public int Get_Megane() {
        return megane;
    }

    public void Init()
    {
    	megane = 0;
    }
}
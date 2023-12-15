package gamePanel;

import javax.swing.JLabel;

public class Score {
    private int score = 0;
    
    int m_x, m_y, m_width, m_heigth;
    public JLabel m_label;
    
    public Score( int x, int y, int width, int heigth){
    	score = 0;
    	m_x = x;
    	m_y = y;
    	m_width = width;
    	m_heigth = heigth;
    	
    	m_label = new JLabel();
    	
    	m_label.setText("score : " + String.valueOf(score));
    	
    	m_label.setBounds(x, y, width, heigth);
    }
    
    

    public void increaseScore(int amount) {
    	score += amount;
        if(score < 0) {
        	score = 0;
        }
        
        m_label.setText("score : " + String.valueOf(score));
    	
    	m_label.setBounds(m_x, m_y, m_width, m_heigth);
    }

    public int getScore() {
        return score;
    }



    public void Init()
    {
    	score = 0;
    }

//    public void setScore(int initialScore) {
//        score = initialScore;
//    }
    
    
}
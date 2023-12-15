package AMS.Frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class RestFrame extends JFrame {
    private JButton buttonClockIn;
    private JButton buttonClockOut;
    private JButton buttonBack;
    private JLabel labelTime;
    private JLabel labelTime1;
    private JLabel labelTime2;

    private String loggedInUser = "デフォルトユーザー";

    public RestFrame(String title) {
        setTitle(title);
        setSize(400, 600);
        setLayout(null);
        setResizable(false);

        JPanel panelCenter = new JPanel();
        panelCenter.setLayout(null);

        Color grayColor = new Color(192, 192, 192);
        panelCenter.setBackground(grayColor);

        labelTime = new JLabel();
        getCurrentTime();
        labelTime.setBounds(100, 80, 400, 40);
        labelTime.setFont(new Font("Serif", Font.BOLD, 40));

        labelTime1 = new JLabel();
        getCurrentTime1();
        labelTime1.setBounds(140, 120, 100, 40);
        labelTime1.setFont(new Font("Serif", Font.BOLD, 40));

        labelTime2 = new JLabel();
        getCurrentTime2();
        labelTime2.setBounds(240, 128, 200, 40);
        labelTime2.setFont(new Font("Serif", Font.BOLD, 20));

        buttonClockIn = new JButton("休憩開始");
        buttonClockIn.setBounds(100, 250, 200, 45); 
        buttonClockIn.setFont(new Font("Serif", Font.BOLD, 30));

        buttonClockOut = new JButton("休憩終了");
        buttonClockOut.setBounds(100, 350, 200, 45);  
        buttonClockOut.setFont(new Font("Serif", Font.BOLD, 30));

        buttonBack = new JButton("戻る");
        buttonBack.setBounds(150, 490, 100, 40);
        buttonBack.setFont(new Font("Serif", Font.BOLD, 30));
        buttonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame2 mainFrame2 = new MainFrame2();
                setVisible(false);
                mainFrame2.setVisible(true);
            }
        });

        buttonClockIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CompleteFrame completeFrame = new CompleteFrame("休憩開始", loggedInUser);
                setVisible(false);
                completeFrame.setVisible(true);
            }
        });

        buttonClockOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CompleteFrame completeFrame = new CompleteFrame("休憩終了", loggedInUser);
                setVisible(false);
                completeFrame.setVisible(true);
            }
        });

        panelCenter.add(labelTime);
        panelCenter.add(labelTime1);
        panelCenter.add(labelTime2);
        panelCenter.add(buttonClockIn);
        panelCenter.add(buttonClockOut);
        panelCenter.add(buttonBack);
        panelCenter.setBounds(0, 0, 400, 600);

        add(panelCenter);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // タイマーを使って1秒ごとに日付と時刻を更新
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getCurrentTime();
                getCurrentTime1();
                getCurrentTime2();
            }
        });
        timer.start();
    }

    public void run() {
        getCurrentTime();
        getCurrentTime1();
        getCurrentTime2();
    }

    private void getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String dateTime = sdf.format(new Date());
        labelTime.setText(dateTime);
    }

    private void getCurrentTime1() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String dateTime = sdf.format(new Date());
        labelTime1.setText(dateTime);
    }

    private void getCurrentTime2() {
        SimpleDateFormat sdf = new SimpleDateFormat("ss");
        String dateTime = sdf.format(new Date());
        labelTime2.setText(dateTime);
    }

    public static void main(String[] args) {
        RestFrame restFrame = new RestFrame("休憩");
        restFrame.setVisible(true);
    }
}
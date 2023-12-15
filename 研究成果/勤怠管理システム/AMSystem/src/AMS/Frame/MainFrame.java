package AMS.Frame;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MainFrame extends JFrame implements ActionListener {
    private JButton buttonEdit;
    private JButton buttonEmployeeList;
    private JButton buttonBack;
    private JLabel labelTime;
    private JLabel labelTime1;
    private JLabel labelTime2;

    public MainFrame() {
        setTitle("勤怠管理システム");
        setSize(400, 600);
        setLayout(null);
        setResizable(false); // サイズ固定

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

        buttonEdit = new JButton("編集する");
        buttonEdit.setBounds((400 - 170) / 2, 250, 170, 50); // 中央に配置
        buttonEdit.setFont(new Font("Serif", Font.BOLD, 30));
        buttonEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EmployeeFrame employeeFrame = new EmployeeFrame("社員一覧");
                setVisible(false);
                employeeFrame.setVisible(true);
            }
        });

        buttonEmployeeList = new JButton("社員一覧");
        buttonEmployeeList.setBounds((400 - 240) / 2, 350, 240, 60); // 中央に配置
        buttonEmployeeList.setFont(new Font("Serif", Font.BOLD, 35));
        buttonEmployeeList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EmployeeListFrame employeeListFrame = new EmployeeListFrame("社員一覧");
                setVisible(false);
                employeeListFrame.setVisible(true);
            }
        });

        buttonBack = new JButton("戻る");
        buttonBack.setBounds((400 - 100) / 2, 490, 100, 40); // 中央に配置
        buttonBack.setFont(new Font("Serif", Font.BOLD, 30));
        buttonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginFrame loginFrame = new LoginFrame();
                setVisible(false);
                loginFrame.setVisible(true);
            }
        });

        panelCenter.add(labelTime);
        panelCenter.add(labelTime1);
        panelCenter.add(labelTime2);
        panelCenter.add(buttonEdit);
        panelCenter.add(buttonEmployeeList);
        panelCenter.add(buttonBack);
        panelCenter.setBounds(0, 0, 400, 600);

        add(panelCenter);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
    }
}

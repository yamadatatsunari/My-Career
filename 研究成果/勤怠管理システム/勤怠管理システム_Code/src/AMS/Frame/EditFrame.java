package AMS.Frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import AMS.Management.LoginManager;

public class EditFrame extends JFrame implements ActionListener {
    private JButton buttonEdit, buttonEdit1, buttonEdit2, buttonEdit3;
    private JButton buttonEmployeeList;
    private JButton buttonBack;
    private JButton buttonDelete;
    private JLabel labelTime;
    private JLabel labelTime1;
    private JLabel labelTime2;
    private String employeeNameToDelete;
    private Connection sqlCon;
    private Statement sqlStmt;
    private String sqlUserID = "puser";
    private String sqlPassword = "1234";

    // MySQLに接続するためのメソッド
	private void connectDB() {
		try {
			Class.forName("org.gjt.mm.mysql.Driver");
			//MySQLに接続
			String url = "jdbc:mysql://localhost?useUnicode=true&characterEncoding=SJIS";
			sqlCon = DriverManager.getConnection(url, sqlUserID, sqlPassword);
			sqlStmt = sqlCon.createStatement();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

    // MySQLから切断するためのメソッド
    private void closeDB() {
        try {
            if (sqlStmt != null) {
                sqlStmt.close();
            }
            if (sqlCon != null) {
                sqlCon.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public EditFrame(String actionType, String employeeName) {
        String SaveName = employeeName;

        setTitle("勤怠管理システム");
        setSize(400, 600);
        setLayout(null);
        setResizable(false); // サイズ固定

        JPanel panelCenter = new JPanel();
        panelCenter.setLayout(null);
        panelCenter.setBackground(new Color(192, 192, 192)); // 背景色を灰色に設定

        labelTime = new JLabel();
        getCurrentTime();
        labelTime.setBounds(100, 210, 400, 40);
        labelTime.setFont(new Font("Serif", Font.BOLD, 40));

        labelTime1 = new JLabel();
        getCurrentTime1();
        labelTime1.setBounds(140, 250, 100, 40);
        labelTime1.setFont(new Font("Serif", Font.BOLD, 40));

        labelTime2 = new JLabel();
        getCurrentTime2();
        labelTime2.setBounds(240, 258, 200, 40);
        labelTime2.setFont(new Font("Serif", Font.BOLD, 20));

        buttonEdit = new JButton("出勤");
        buttonEdit.setBounds(35, 300, 160, 50);
        buttonEdit.setFont(new Font("Serif", Font.BOLD, 30));
        buttonEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String SaveAct = "出勤";
                CompleteFrame completeFrame = new CompleteFrame(SaveAct, SaveName);
                setVisible(false);
                completeFrame.setVisible(true);
            }
        });

        buttonEdit1 = new JButton("休憩開始");
        buttonEdit1.setBounds(215, 300, 160, 50);
        buttonEdit1.setFont(new Font("Serif", Font.BOLD, 30));
        buttonEdit1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String SaveAct = "休憩開始";
                CompleteFrame completeFrame = new CompleteFrame(SaveAct, SaveName);
                setVisible(false);
                completeFrame.setVisible(true);
            }
        });

        buttonEdit2 = new JButton("退勤");
        buttonEdit2.setBounds(35, 370, 160, 50);
        buttonEdit2.setFont(new Font("Serif", Font.BOLD, 30));
        buttonEdit2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String SaveAct = "退勤";
                CompleteFrame completeFrame = new CompleteFrame(SaveAct, SaveName);
                setVisible(false);
                completeFrame.setVisible(true);
            }
        });

        buttonEdit3 = new JButton("休憩終了");
        buttonEdit3.setBounds(215, 370, 160, 50);
        buttonEdit3.setFont(new Font("Serif", Font.BOLD, 30));
        buttonEdit3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String SaveAct = "休憩終了";
                CompleteFrame completeFrame = new CompleteFrame(SaveAct, SaveName);
                setVisible(false);
                completeFrame.setVisible(true);
            }
        });

        buttonEmployeeList = new JButton("カレンダー");
        buttonEmployeeList.setBounds(40, 50, 320, 150);
        buttonEmployeeList.setFont(new Font("Serif", Font.BOLD, 35));
        buttonEmployeeList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("カレンダー");
            }
        });

        // 社員削除ボタン
        buttonDelete = new JButton("社員削除");
        buttonDelete.setBounds(120, 430, 160, 50);
        buttonDelete.setFont(new Font("Serif", Font.BOLD, 30));
        buttonDelete.addActionListener(this);
        panelCenter.add(buttonDelete);

        // 削除対象の社員名をセット
        employeeNameToDelete = employeeName;


        buttonBack = new JButton("戻る");
        buttonBack.setBounds(150, 490, 100, 40);
        buttonBack.setFont(new Font("Serif", Font.BOLD, 30));
        buttonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginManager loginManager = LoginManager.getInstance();
                String loggedInPosition = loginManager.getLoggedInPosition();

                if ("社長".equals(loggedInPosition)) {
                    MainFrame MainFrame = new MainFrame();
                    setVisible(false);
                    MainFrame.setVisible(true);
                } else {
                    MainFrame2 MainFrame2 = new MainFrame2();
                    setVisible(false);
                    MainFrame2.setVisible(true);
                }
            }
        });

        panelCenter.add(labelTime);
        panelCenter.add(labelTime1);
        panelCenter.add(labelTime2);
        panelCenter.add(buttonEdit);
        panelCenter.add(buttonEdit1);
        panelCenter.add(buttonEdit2);
        panelCenter.add(buttonEdit3);
        panelCenter.add(buttonEmployeeList);
        panelCenter.add(buttonBack);
        panelCenter.add(buttonDelete);
        panelCenter.setBounds(0, 0, 400, 600);

        add(panelCenter);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

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
        EditFrame mainFrame = new EditFrame("アクション", "社員名");
        mainFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonDelete) {
            int result = JOptionPane.showConfirmDialog(this, "本当に削除しますか？", "確認", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                if (deleteEmployeeFromDatabase()) {
                    JOptionPane.showMessageDialog(this, "削除が完了しました。");
                } else {
                    JOptionPane.showMessageDialog(this, "削除に失敗しました。", "エラー", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private boolean deleteEmployeeFromDatabase() {
        try {
            // データベースに接続
            connectDB();

            // 削除クエリの実行
            String sql = "DELETE FROM employee.employees WHERE name = ?";
            try (PreparedStatement preparedStatement = sqlCon.prepareStatement(sql)) {
                preparedStatement.setString(1, employeeNameToDelete);

                // クエリの実行
                int affectedRows = preparedStatement.executeUpdate();

                return affectedRows > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            // データベース接続をクローズ
            closeDB();
        }
    }
}

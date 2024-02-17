package AMS.Frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import AMS.Management.LoginManager;

public class CompleteFrame extends JFrame {

    private JLabel label;
    private String attendanceType;
    private String employeeName;

    // データベース関連の変数
    private Connection sqlCon;
    private Statement sqlStmt;
    private String sqlUserID = "puser";
    private String sqlPassword = "1234";

    public CompleteFrame(String actionType, String employeeName) {
        this.attendanceType = actionType;
        this.employeeName = employeeName;

        setTitle("完了");
        setSize(400, 600);
        setLayout(null);
        setResizable(false);

        JPanel panelCenter = new JPanel();
        panelCenter.setLayout(null);

        Color grayColor = new Color(192, 192, 192);
        panelCenter.setBackground(grayColor);

        // コンストラクタ内のラベル作成部分を修正
        LoginManager loginManager = LoginManager.getInstance();
        String loggedInPosition = loginManager.getLoggedInPosition();

        // ログイン中のユーザーの職位によって表示内容を変更
        if ("社長".equals(loggedInPosition)) {
            label = new JLabel(actionType + "しました: " + employeeName);
        } else {
            // ログイン中のユーザーの名前を取得して表示
            String loggedInUserName = LoginManager.getInstance().getLoggedInUserFullName();
            label = new JLabel(actionType + "しました: " + loggedInUserName);
        }

        label.setBackground(grayColor);
        Font labelFont = new Font("Dialog", Font.PLAIN, 20);
        label.setFont(labelFont);
        panelCenter.add(label);

        JButton okButton = new JButton("OK");
        okButton.setBounds(150, 400, 100, 30);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // ログイン中のユーザーの状態を更新
                updateEmployeeStatus();

                // ボタンが押された時点でログイン状態を確認し、遷移
                // ログイン中のユーザーの職位を取得
                String loggedInPosition = loginManager.getLoggedInPosition();

                // ログイン中の職位が "社長" の場合は MainFrame に遷移
                if ("社長".equals(loggedInPosition)) {
                    MainFrame mainFrame = new MainFrame();
                    setVisible(false);
                    mainFrame.setVisible(true);
                } else {
                    // それ以外の場合は MainFrame2 に遷移
                    MainFrame2 mainFrame2 = new MainFrame2();
                    setVisible(false);
                    mainFrame2.setVisible(true);
                }
            }
        });
        panelCenter.add(okButton);
        panelCenter.setBounds(0, 0, 400, 600);

        add(panelCenter);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // コンポーネントのサイズ変更イベントをリッスン
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // 画面の中央に配置
                int labelX = (getWidth() - label.getWidth()) / 2;
                int labelY = 150;  // 固定のY座標
                label.setLocation(labelX, labelY);
            }
        });

        // ラベルのサイズを文字列に合わせて設定
        setLabelSize();
    }

    private void setLabelSize() {
        FontMetrics fontMetrics = getFontMetrics(label.getFont());
        int stringWidth = fontMetrics.stringWidth(label.getText());
        int stringHeight = fontMetrics.getHeight();

        label.setSize(stringWidth, stringHeight);
    }

    private void updateEmployeeStatus() {
        connectDB();

        try {
            LoginManager loginManager = LoginManager.getInstance();
            String loggedInPosition = loginManager.getLoggedInPosition();
            String loggedInUserName = loginManager.getLoggedInUserFullName();

            String sql = "UPDATE employee.employees SET status = ? WHERE name = ?";
            PreparedStatement preparedStatement = sqlCon.prepareStatement(sql);

            switch (attendanceType) {
                case "出勤":
                    preparedStatement.setString(1, "出勤");
                    break;
                case "退勤":
                    preparedStatement.setString(1, "退勤");
                    break;
                case "休憩開始":
                    preparedStatement.setString(1, "休憩");
                    break;
                case "休憩終了":
                    preparedStatement.setString(1, "出勤"); // 休憩終了後は出勤とする例
                    break;
                default:
                    // その他の場合は何もしないか、エラー処理を追加する
                    break;
            }

            // ログインユーザーの情報を使ってデータベースを更新
            if ("社長".equals(loggedInPosition)) {
                preparedStatement.setString(2, employeeName);
            } else {
                preparedStatement.setString(2, loggedInUserName);
            }

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDB();
        }
    }

    // MySQLに接続するためのメソッド
    private void connectDB() {
        try {
            Class.forName("org.gjt.mm.mysql.Driver");
            String url = "jdbc:mysql://localhost?useUnicode=true&characterEncoding=SJIS";
            sqlCon = DriverManager.getConnection(url, sqlUserID, sqlPassword);
            sqlStmt = sqlCon.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // MySQLから切断するためのメソッド
    private void closeDB() {
        try {
            if (sqlCon != null && !sqlCon.isClosed()) {
                sqlCon.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CompleteFrame completeFrame = new CompleteFrame("アクション", "社員名");
        completeFrame.setVisible(true);
    }
}

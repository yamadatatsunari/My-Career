package AMS.Frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import AMS.Management.LoginManager;

public class LoginFrame extends JFrame {
    private JTextField textFieldID;
    private JPasswordField passwordField;
    private JLabel errorLabel;

    private Connection sqlCon;
    private Statement sqlStmt;
    private String sqlUserID = "puser";
    private String sqlPassword = "1234";

    // MySQLに接続するためのメソッド
    private void connectDB() {
        try {
            Class.forName("org.gjt.mm.mysql.Driver");
            // MySQLに接続
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

    public LoginFrame() {
        setTitle("勤怠管理システム");
        setSize(400, 600);
        setLayout(null);
        setResizable(false);

        JPanel panelCenter = new JPanel();
        panelCenter.setLayout(null);
        panelCenter.setBounds(0, 0, 400, 500);

        Color grayColor = new Color(192, 192, 192);
        panelCenter.setBackground(grayColor); // 背景色を灰色に設定

        JLabel labelTitle = new JLabel("勤怠管理システム");
        labelTitle.setBounds(40, 80, 400, 40);
        labelTitle.setFont(new Font("Serif", Font.BOLD, 40));

        JLabel labelID = new JLabel("ID:");
        labelID.setBounds(50, 190, 100, 20);

        textFieldID = new JTextField(20);
        textFieldID.setBounds(50, 220, 300, 20);

        JLabel labelPassword = new JLabel("パスワード:");
        labelPassword.setBounds(50, 270, 100, 20);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(50, 300, 300, 20);

        // 新規登録ボタン
        JButton buttonRegister = new JButton("新規登録");
        buttonRegister.setBounds(150, 470, 100, 30);
        buttonRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistrationFrame registrationFrame = new RegistrationFrame();
                setVisible(false);
                registrationFrame.setVisible(true);
            }
        });

        JButton buttonLogin = new JButton("ログイン");
        buttonLogin.setBounds(150, 430, 100, 30);
        buttonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String loginID = textFieldID.getText();
                String password = new String(passwordField.getPassword());

                if (validateUserWithDatabase(loginID, password)) {
                    // ログインマネージャーにユーザー名と職位をセット
                    LoginManager loginManager = LoginManager.getInstance();
                    loginManager.setLoggedInUserName(loginID);
                    loginManager.setLoggedInPosition(getUserPosition(loginID));
                    loginManager.setLoggedInUserFullName(getUserFullName(loginID)); // 追加

                    // ログイン中の職位が "社長" の場合は MainFrame に遷移
                    if ("社長".equals(loginManager.getLoggedInPosition())) {
                        MainFrame mainFrame = new MainFrame();
                        setVisible(false);
                        mainFrame.setVisible(true);
                    } else {
                        // それ以外の場合は MainFrame2 に遷移
                        MainFrame2 mainFrame2 = new MainFrame2();
                        setVisible(false);
                        mainFrame2.setVisible(true);
                    }
                } else {
                    showError("IDまたはパスワードが違います。");
                }
            }
        });

        // エラーメッセージを表示するLabel
        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);
        errorLabel.setBounds(50, 350, 300, 20);

        panelCenter.add(labelTitle);
        panelCenter.add(labelID);
        panelCenter.add(textFieldID);
        panelCenter.add(labelPassword);
        panelCenter.add(passwordField);
        panelCenter.add(buttonLogin);
        panelCenter.add(errorLabel);
        panelCenter.add(buttonRegister);
        panelCenter.setBounds(0, 0, 400, 600);

        add(panelCenter);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private boolean validateUserWithDatabase(String loginID, String password) {
        connectDB();
        try {
            // プリペアドステートメントを作成
            String sql = "SELECT * FROM employee.employees WHERE loginid = ? AND password = ?";
            try (PreparedStatement preparedStatement = sqlCon.prepareStatement(sql)) {
                // プリペアドステートメントに値をセット
                preparedStatement.setString(1, loginID);
                preparedStatement.setString(2, password);

                // クエリを実行
                ResultSet resultSet = preparedStatement.executeQuery();
                return resultSet.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            closeDB();
        }
    }

    private String getUserPosition(String loginID) {
        connectDB();
        try {
            // プリペアドステートメントを作成
            String sql = "SELECT position FROM employee.employees WHERE loginid = ?";
            try (PreparedStatement preparedStatement = sqlCon.prepareStatement(sql)) {
                // プリペアドステートメントに値をセット
                preparedStatement.setString(1, loginID);

                // クエリを実行
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getString("position");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDB();
        }
        return null;
    }

    private String getUserFullName(String loginID) {
        connectDB();
        try {
            // プリペアドステートメントを作成
            String sql = "SELECT name FROM employee.employees WHERE loginid = ?";
            try (PreparedStatement preparedStatement = sqlCon.prepareStatement(sql)) {
                // プリペアドステートメントに値をセット
                preparedStatement.setString(1, loginID);

                // クエリを実行
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getString("name");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDB();
        }
        return null;
    }

    private void showError(String message) {
        errorLabel.setText(message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }
}

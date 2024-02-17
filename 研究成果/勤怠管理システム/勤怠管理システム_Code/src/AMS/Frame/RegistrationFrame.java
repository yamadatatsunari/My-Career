package AMS.Frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class RegistrationFrame extends JFrame {
    private JComboBox<String> comboBoxPosition; // 職位を選択するコンボボックス
    private JTextField textFieldName;
    private JTextField textFieldEmail;
    private JPasswordField passwordField;
    private JTextField textFieldLoginID;

    private Connection sqlCon;
    private Statement sqlStmt;
    private String sqlUserID = "puser";
    private String sqlPassword = "1234";

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

    public RegistrationFrame() {
        setTitle("新規登録");
        setSize(400, 600);
        setLayout(null);
        setResizable(false);

        Panel panelCenter = new Panel();
        panelCenter.setLayout(null);
        panelCenter.setBounds(0, 0, 400, 600);

        Color GrayColor = new Color(192, 192, 192);
        panelCenter.setBackground(GrayColor);

        JLabel labelTitle = new JLabel("新規登録");
        labelTitle.setBounds(110, 40, 400, 40);
        labelTitle.setFont(new Font("Serif", Font.BOLD, 40));

        JLabel labelPosition = new JLabel("職位:");
        labelPosition.setBounds(50, 80, 100, 20);

        // 職位を選択するコンボボックス
        comboBoxPosition = new JComboBox<>(new String[]{"社長", "人事部", "経理部", "総務部", "法務部", "営業部", "最強部"});
        comboBoxPosition.setBounds(50, 110, 300, 20);

        JLabel labelName = new JLabel("名前:");
        labelName.setBounds(50, 150, 100, 20);

        textFieldName = new JTextField(20);
        textFieldName.setBounds(50, 180, 300, 20);

        JLabel labelEmail = new JLabel("Eメール:");
        labelEmail.setBounds(50, 220, 100, 20);

        textFieldEmail = new JTextField(20);
        textFieldEmail.setBounds(50, 250, 300, 20);

        JLabel labelPassword = new JLabel("パスワード:");
        labelPassword.setBounds(50, 290, 100, 20);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(50, 320, 300, 20);

        JLabel labelLoginID = new JLabel("ログインID:");
        labelLoginID.setBounds(50, 360, 100, 20);

        textFieldLoginID = new JTextField(20);
        textFieldLoginID.setBounds(50, 390, 300, 20);

        JButton buttonRegister = new JButton("新規登録");
        buttonRegister.setBounds(150, 450, 100, 30);
        buttonRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String position = comboBoxPosition.getSelectedItem().toString();
                String name = textFieldName.getText();
                String email = textFieldEmail.getText();
                String password = new String(passwordField.getPassword());
                String loginID = textFieldLoginID.getText();

                if (validateInput(position, name, email, password, loginID)) {
                    if (registerUser(position, name, email, password, loginID)) {
                        // 登録が成功した場合は、登録画面を閉じ、LoginFrameに遷移
                        setVisible(false);
                        LoginFrame loginFrame = new LoginFrame();
                        loginFrame.setVisible(true);
                    } else {
                        showError("新規登録に失敗しました。");
                    }
                } else {
                    showError("全ての項目を入力してください。");
                }
            }
        });

        JButton buttonBack = new JButton("戻る");
        buttonBack.setBounds(150, 490, 100, 30);
        buttonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginFrame loginFrame = new LoginFrame();
                setVisible(false);
                loginFrame.setVisible(true);
            }
        });

        panelCenter.add(labelTitle);
        panelCenter.add(labelPosition);
        panelCenter.add(comboBoxPosition);
        panelCenter.add(labelName);
        panelCenter.add(textFieldName);
        panelCenter.add(labelEmail);
        panelCenter.add(textFieldEmail);
        panelCenter.add(labelPassword);
        panelCenter.add(passwordField);
        panelCenter.add(labelLoginID);
        panelCenter.add(textFieldLoginID);
        panelCenter.add(buttonRegister);
        panelCenter.add(buttonBack);
        panelCenter.setBounds(0, 0, 400, 600);

        add(panelCenter);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private boolean validateInput(String position, String name, String email, String password, String loginID) {
        return !position.isEmpty() && !name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !loginID.isEmpty();
    }

    private boolean registerUser(String position, String name, String email, String password, String loginID) {
        connectDB();
        try {
            // プリペアドステートメントを作成
            String sql = "INSERT INTO employee.employees (position, name, email, status, password, loginid) VALUES (?, ?, ?, '退勤', ?, ?)";
            try (PreparedStatement preparedStatement = sqlCon.prepareStatement(sql)) {
                // プリペアドステートメントに値をセット
                preparedStatement.setString(1, position);
                preparedStatement.setString(2, name);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, password);
                preparedStatement.setString(5, loginID);

                // クエリを実行
                int result = preparedStatement.executeUpdate();
                return result > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            closeDB();
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "エラー", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RegistrationFrame registrationFrame = new RegistrationFrame();
            registrationFrame.setVisible(true);
        });
    }
}

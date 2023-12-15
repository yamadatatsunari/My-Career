package AMS.Frame;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import AMS.Management.LoginManager;

public class EmployeeFrame extends Frame {
    private String attendanceType;
    private Panel panel;
    private ScrollPane scrollPane;

    // データベース関連の変数
    private Connection sqlCon;
    private Statement sqlStmt;
    private String sqlUserID = "puser";
    private String sqlPassword = "1234";
    

    public EmployeeFrame(String title) {
        setTitle(title);
        setSize(400, 600);
        setLayout(null);
        setResizable(false);

        Panel panelCenter = new Panel();
        panelCenter.setLayout(null);
        panelCenter.setBounds(0, 0, 400, 600);

        Color GrayColor = new Color(192, 192, 192);
        panelCenter.setBackground(GrayColor);

        Label labelMessage = new Label("*編集する社員を選択してください");
        labelMessage.setBounds(40, 500, 320, 40);
        labelMessage.setFont(new Font("Serif", Font.BOLD, 20));
        panelCenter.add(labelMessage);

        scrollPane = new ScrollPane();
        scrollPane.setBounds(50, 50, 300, 400);

        panel = new Panel();
        panel.setLayout(null);

        displayEmployeeNames();

        scrollPane.add(panel);
        panelCenter.add(scrollPane);
        add(panelCenter);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

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
            sqlStmt.close();
            sqlCon.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<String> getEmployeeNames() {
        List<String> employeeNames = new ArrayList<>();

        try {
            connectDB();
            String sql = "SELECT name FROM employee.employees;";
            ResultSet rs = sqlStmt.executeQuery(sql);

            while (rs.next()) {
                String name = rs.getString("name");
                employeeNames.add(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDB();
        }

        return employeeNames;
    }

    private void displayEmployeeNames() {
        List<String> employeeNames = getEmployeeNames();

        for (int i = 0; i < employeeNames.size(); i++) {
            Button employeeButton = new Button(employeeNames.get(i));
            employeeButton.setBounds(50, 50 * i, 200, 30);
            this.panel.add(employeeButton);

            employeeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String employeeName = e.getActionCommand();
                    LoginManager loginManager = LoginManager.getInstance();
                    String loggedInPosition = loginManager.getLoggedInPosition();

                    if ("社長".equals(loggedInPosition)) {
                        System.out.println("ユーザーはログインしています");
                        EditFrame editFrame = new EditFrame(attendanceType, employeeName);
                        setVisible(false);
                        editFrame.setVisible(true);
                    } else {
                        System.out.println("ユーザーはログインしていません");
                        CompleteFrame completeFrame = new CompleteFrame(attendanceType, employeeName);
                        setVisible(false);
                        completeFrame.setVisible(true);
                    }
                }
            });
        }
    }

    public void setAttendanceType(String type) {
        this.attendanceType = type;
    }

    public static void main(String[] args) {
        EmployeeFrame employeeFrame = new EmployeeFrame("社員リスト");
        employeeFrame.setVisible(true);
    }
}

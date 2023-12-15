package AMS.Frame;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import AMS.Management.Employee;
import AMS.Management.LoginManager;


public class EmployeeListFrame extends JFrame {
    private JTextPane textPane;
    
    Connection sqlCon;
	Statement sqlStmt;
	String sqlUserID = "puser";
	String sqlPassword = "1234";
		
	////MySQLに接続するためのメソッド
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
	////MySQLから切断するためのメソッド
	private void closeDB() {
		try {
			sqlStmt.close();
			sqlCon.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

    public EmployeeListFrame(String title) {
        displayAllEmployeeStatus();
        setTitle(title);
        setSize(400, 600);
        setLayout(null);
        setResizable(false); // サイズ固定

        JPanel panelCenter = new JPanel();
        panelCenter.setLayout(null);
        panelCenter.setBounds(0, 0, 400, 500);

        Color grayColor = new Color(192, 192, 192);
        panelCenter.setBackground(grayColor); // 背景色を灰色に設定

        textPane = new JTextPane();
        textPane.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textPane); // textPaneをJScrollPaneにセット
        scrollPane.setBounds(50, 50, 300, 400);
        panelCenter.add(scrollPane);
        
     // 戻るボタンを画面下部に配置
        JButton backButton = new JButton("戻る");
        backButton.setBounds(150, 460, 100, 30);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	LoginManager loginManager = LoginManager.getInstance();
                // ログイン中のユーザーの職位を取得
                String loggedInPosition = loginManager.getLoggedInPosition();

                // ログイン中の職位が "社長" の場合は MainFrame に遷移
                if ("社長".equals(loggedInPosition)) {
                    MainFrame MainFrame = new MainFrame();
                    setVisible(false);
                    MainFrame.setVisible(true);
                } else {
                    // それ以外の場合は MainFrame2 に遷移
                    MainFrame2 MainFrame2 = new MainFrame2();
                    setVisible(false);
                    MainFrame2.setVisible(true);
                }
            }
        });
        panelCenter.add(backButton);

        add(panelCenter);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        


    }

    private void displayAllEmployeeStatus() {
        new SwingWorker<List<Employee>, Void>() {
            @Override
            protected List<Employee> doInBackground() throws Exception {
                List<Employee> employees = new ArrayList<>();
                connectDB();

                System.out.println("Before SQL Query"); // デバッグ用のログ

                try {
                    String sql = "SELECT * FROM employee.employees;";
                    ResultSet rs = sqlStmt.executeQuery(sql);

                    System.out.println("After SQL Query"); // デバッグ用のログ

                    while (rs.next()) {
                        String position = rs.getString("position");
                        String name = rs.getString("name");
                        String email = rs.getString("email");
                        String status = rs.getString("status");

                        Employee employee = new Employee(position, name, email, status);
                        employees.add(employee);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    closeDB();
                }
                return employees;
            }

            @Override
            protected void done() {
                try {
                    List<Employee> employees = get();
                    StyledDocument doc = textPane.getStyledDocument();

                    for (Employee employee : employees) {
                        // データを整形してStyledDocumentに追加
                        doc.insertString(doc.getLength(), employee.getPosition() + "\n", null);
                        doc.insertString(doc.getLength(), employee.getName() + ": " + employee.getEmail() + " - ", null);

                        // ステータスに応じて色を設定
                        switch (employee.getStatus()) {
                            case "出勤":
                                doc.insertString(doc.getLength(), "●", getGreenAttribute());
                                break;
                            case "退勤":
                                doc.insertString(doc.getLength(), "●", getRedAttribute());
                                break;
                            case "休憩":
                                doc.insertString(doc.getLength(), "●", getBlueAttribute());
                                break;
                            default:
                                break;
                        }

                        doc.insertString(doc.getLength(), "\n\n", null);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // 非同期処理が完了したときにフレームを表示する
                setVisible(true);
            }
        }.execute();
    }

    // 色を設定するためのAttributeを取得するメソッド
    private SimpleAttributeSet getGreenAttribute() {
        SimpleAttributeSet attributeSet = new SimpleAttributeSet();
        StyleConstants.setForeground(attributeSet, Color.GREEN);
        return attributeSet;
    }

    private SimpleAttributeSet getRedAttribute() {
        SimpleAttributeSet attributeSet = new SimpleAttributeSet();
        StyleConstants.setForeground(attributeSet, Color.RED);
        return attributeSet;
    }

    private SimpleAttributeSet getBlueAttribute() {
        SimpleAttributeSet attributeSet = new SimpleAttributeSet();
        StyleConstants.setForeground(attributeSet, Color.BLUE);
        return attributeSet;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EmployeeListFrame employeeListFrame = new EmployeeListFrame("社員リスト");
            employeeListFrame.setVisible(true);

            // メソッドが呼ばれていることを確認
            System.out.println("Before Display");

            try {
                // ここで全社員のデータを表示
                employeeListFrame.displayAllEmployeeStatus();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

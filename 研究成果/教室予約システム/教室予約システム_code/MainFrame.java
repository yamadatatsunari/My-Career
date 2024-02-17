package client_system;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
public class MainFrame extends Frame implements ActionListener, WindowListener{
    // ReservationControlクラスのインスタンス生成
	ReservationControl reservationControl;
    // パネルインスタンスの生成
    Panel panelNorth;       // 上部パネル
    Panel panelNorthSub1;   // 上部パネルの上
    Panel panelNorthSub2;   // 上部パネルの下
    Panel panelCenter;      // 中央パネル
    Panel panelSouth;       // 下部パネル
    
    // ボタンインスタンスの生成
    Button buttonLog;               // 「ログイン」ボタン
    Button buttonExplanation;       // 「教室概要」ボタン
    Button buttonReservation;       // 「新規予約」ボタン
    Button buttonReservationed;     // 「予約状況確認」ボタン
    Button buttonSelfReservationed; // 「自己予約確認」ボタン
    Button buttonReservationCancel; // 「予約キャンセル」ボタン
    
    // コンボボックスのインスタンスの生成
    ChoiceFacility choiceFacility;  // 教室選択用コンボボックス 
    
    // テキストフィールドのインスタンスの生成
    TextField	tfLoginID;          // ログインID表示用テキストフィールド
    
    // テキストエリアのインスタンスの生成
    TextArea textMessage;           // 結果表示用メッセージ欄
    
    // MainFrameコンストラクタ
    public MainFrame(ReservationControl rc) {
        reservationControl = rc;
        // ボタンの生成
        buttonLog = new Button(" ログイン ");
        buttonExplanation = new Button("教室概要");
        buttonReservation = new Button("新規予約");
        buttonReservationed = new Button("予約状況確認");
        buttonSelfReservationed = new Button("自己予約確認");
        buttonReservationCancel = new Button("予約キャンセル");
        
        // 教室選択用コンボボックスの生成
        List<String>facilityId = new ArrayList<String>();
        facilityId = rc.getFacilityId();
        choiceFacility = new ChoiceFacility(facilityId);
        
        // ログインID表示ボックスの生成
        tfLoginID = new TextField("末ログイン",12);
        tfLoginID.setEditable(false);
        
        // 上と中央パネルを使うため、レイアウトマネージャにBorderLayoutを設定
        setLayout(new BorderLayout());
        
        // 上部パネルの上パネルに「教室予約システム」というラベルと【ログイン】ボタンを追加
        panelNorthSub1 = new Panel();
        panelNorthSub1.add(new Label("教室予約システム "));
        panelNorthSub1.add(buttonLog);
        panelNorthSub1.add(new Label(" ログインID:"));
        panelNorthSub1.add(tfLoginID);
        
        //上部パネルの下パネルに「教室選択」を追加
        panelNorthSub2 = new Panel();
        panelNorthSub2.add(new Label("教室"));
        panelNorthSub2.add( choiceFacility);
        //上部パネルの下パネルに「教室概要ボタン」を追加
        panelNorthSub2.add(new Label(" "));
        panelNorthSub2.add(buttonExplanation);
        //上部パネルの下パネルに「予約状況確認ボタン」を追加
        panelNorthSub2.add(new Label(" "));
        panelNorthSub2.add(buttonReservationed);
        //上部パネルの下パネルに「自己予約確認ボタン」を追加
        panelNorthSub2.add(new Label(" "));
        panelNorthSub2.add(buttonSelfReservationed);
        //上部パネルの下パネルに「予約キャンセルボタン」を追加
        panelNorthSub2.add(new Label(" "));
        panelNorthSub2.add(buttonReservationCancel);
        //上部パネルに上下二つのパネルを追加
        panelNorth = new Panel(new BorderLayout());
        panelNorth.add(panelNorthSub1, BorderLayout.NORTH);
        panelNorth.add(panelNorthSub2, BorderLayout.CENTER);
        
        //メイン画面(MainFrame)に上パネルを追加
        add(panelNorth, BorderLayout.NORTH);
        //中央パネルにテキストメッセージ欄を設定
        panelCenter = new Panel();
        textMessage = new TextArea( 20, 30);
        textMessage.setEditable(false);
        panelCenter.add(textMessage);
        //文字のフォントサイズを変更
        Font newFont = new Font("Arial", Font.PLAIN, 20);  // フォント名、スタイル、サイズを指定
        textMessage.setFont(newFont);  // textMessageに新しいフォントを設定
        panelCenter.add(textMessage);
        // メイン画面(MainFrame)に中央パネルを追加
        add(panelCenter,BorderLayout.CENTER);
        // 下部パネルに新規予約ボタンを追加
        panelSouth = new Panel();
        panelSouth.add( buttonReservation);
        // メイン画面(MainFrame)に下部パネルを追加
        add( panelSouth, BorderLayout.SOUTH);
        
        // ボタンのアクションリスナの追加
        buttonLog.addActionListener(this);
        buttonExplanation.addActionListener(this);
        buttonReservation.addActionListener(this);
        buttonReservationed.addActionListener(this);
        buttonSelfReservationed.addActionListener(this);
        buttonReservationCancel.addActionListener(this);
        addWindowListener(this);
    }
    
    @Override
    public void windowOpened(WindowEvent e) {
        // TODO 自動生成されたメソッド・スタブ
    	
    }
    
    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
        
    }
    
    @Override
    public void windowClosed(WindowEvent e) {
    	// TODO 自動生成されたメソッド・スタブ
    	
    }
    
    @Override
    public void windowIconified(WindowEvent e) {
    	// TODO 自動生成されたメソッド・スタブ
    	
    }
    
    @Override
    public void windowDeiconified(WindowEvent e) {
    	// TODO 自動生成されたメソッド・スタブ
    	
    }
    
    @Override
    public void windowActivated(WindowEvent e) {
    	// TODO 自動生成されたメソッド・スタブ
    	
    }
    
    @Override
    public void windowDeactivated(WindowEvent e) {
    	// TODO 自動生成されたメソッド・スタブ
    	
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String result = new String();
        if(e.getSource()== buttonLog) {                             // ログインボタンが押されとき
            result = reservationControl.loginLogout( this);         // LoginLogoutメソッドを呼び出す
        }else if(e.getSource() == buttonExplanation) {              // 教室概要ボタンが押されたとき
        	result = reservationControl                             // getFacilityExplanationを呼び出す
        			.getFacilityExplanation(choiceFacility.getSelectedItem());
        }else if(e.getSource() == buttonReservation) {              // 新規予約ボタンが押されたとき
        	result = reservationControl.makeReservation( this);     // makeReservationを呼び出す
        }else if(e.getSource() == buttonReservationed) {            // 予約状況確認ボタンが押されたとき
        	result = reservationControl.getReservationed( this);    // getReservationedを呼び出す
        }else if(e.getSource() == buttonSelfReservationed) {        // 自己予約確認ボタンが押されたとき
            result = reservationControl.getSelfReservationed(this); // getSelfReservationedを呼び出す
        }else if(e.getSource() == buttonReservationCancel) {        // 予約キャンセルボタンが押されたとき
            result = reservationControl.getReservationCancel(this); // getReservationCancelを呼び出す
        }
        textMessage.setText(result);  // テキストエリアにControllerからの戻り値を表示
    }
}
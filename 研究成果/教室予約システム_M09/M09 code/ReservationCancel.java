package client_system;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
public class ReservationCancel extends Dialog implements ActionListener,WindowListener,ItemListener {
	
	boolean canceled;
	ReservationControl rc;
	
    //パネル
    Panel  panelNorth;
    Panel  panelSouth;
	
    //入力コンポーネント
    ChoiceReservationId choicereservationid;
	
    //ボタン
    Button         buttonOK;
    Button         buttonCancel;
    
    //コンストラクタ
	public ReservationCancel(Frame owner, ReservationControl rc) {
	    //基底クラスのコンストラクタを呼び出す
	    super(owner,"予約キャンセル",true);
	
	    this.rc = rc;
	
	    //初期値キャンセルを設定
	    canceled = true;
	    
	    //教室選択ボックスの作成
	    List<String> reservationId = new ArrayList<String>();
	    reservationId = rc.getReservationId();
	    choicereservationid = new ChoiceReservationId(reservationId);

	    //ボタンの生成
	    buttonOK     = new Button("予約削除");
	    buttonCancel = new Button("キャンセル");
	
	    //パネルの生成
	    panelNorth   = new Panel();
	    panelSouth   = new Panel();
	    
	    //上部パネルに教室選択ボックス,年月日入力欄を配置
	    panelNorth.add(new Label("予約ID: "));
	    panelNorth.add(choicereservationid);

		//下部パネルに2つのボタンを追加
		panelSouth.add( buttonCancel);
		panelSouth.add( new Label(" "));
		panelSouth.add( buttonOK);
		
		// ReservationDialogをBorderLayoutに設定し,3つのパネルを追加
		setLayout( new BorderLayout());
		add( panelNorth, BorderLayout.NORTH);
		add( panelSouth, BorderLayout.SOUTH);
		
		// Window Listenerを追加
		addWindowListener(this);
		
		//ボタンにアクションリスナを追加
		buttonOK.addActionListener( this);
		buttonCancel.addActionListener( this);

		//大きさの設定,Windowのサイズ変更不可の設定
		this.setBounds(100,100,500, 150);
		setResizable(false);
	}
    	
    @Override
    public void windowOpened(WindowEvent e){ 
  	    // TODO 自動生成されたメソッド スタブ
  	
    }
  
    @Override
    public void windowClosing(WindowEvent e){ 
  	    setVisible( false);
  	    dispose();
  	
    }
  
    @Override
    public void windowClosed(WindowEvent e){ 
  	    // TODO 自動生成されたメソッド スタブ
  	
    }
  
    @Override
    public void windowIconified(WindowEvent e){ 
  	    // TODO 自動生成されたメソッド スタブ
  	
    }
  
    @Override
    public void windowDeiconified(WindowEvent e) { 
    	// TODO 自動生成されたメソッド スタブ
  	
    }
  
    @Override
    public void windowActivated(WindowEvent e) { 
    	// TODO 自動生成されたメソッド スタブ
  	
    }
  
    @Override
    public void windowDeactivated(WindowEvent e) { 
    	// TODO 自動生成されたメソッド スタブ
  	
    }
  
    @Override
    public void actionPerformed (ActionEvent e) { 
    	if(e.getSource() == buttonCancel){
    	    setVisible(false);
    	    dispose();
    	}else if(e.getSource() == buttonOK) {
    		canceled = false;
    		setVisible(false);
    		dispose();
    	}
    }
	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
}
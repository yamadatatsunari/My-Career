package client_system;

public class ReservationSystem {
	public static void main( String argv[]) {
		// ReservationControlクラスのインスタンス生成
		ReservationControl reservationControl = new ReservationControl();
		// MainFrameクラスのインスタンス生成
		MainFrame          mainFrame          = new MainFrame(reservationControl);
		// mainFrameのWindowを生成
		mainFrame.setBounds(5, 5, 900, 455);
		// mainFrameのWindowを可視化
		mainFrame.setVisible(true);
	}

}

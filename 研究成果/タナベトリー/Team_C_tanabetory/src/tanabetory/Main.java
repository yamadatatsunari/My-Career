package tanabetory;

public class Main {
	public static MainWindow mainWindow;
	public static void main(String[] args) {
		mainWindow = new MainWindow();		//ウィンドウのみを生成
		mainWindow.preparePanels();			//ペインに直接貼るパネルのみを生成
		mainWindow.prepareComponents();		//その他のコンポーネントを生成
		mainWindow.setFrontScreenAndFocus(ScreenMode.TITLE);//起動後最初に表示すべき画面を手前に持ってきてそれに注目させる
		mainWindow.setVisible(true);		//最後にウィンドウを可視化
	}
}

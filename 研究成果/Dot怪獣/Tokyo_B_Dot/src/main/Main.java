package main;

import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
        //JFrame はウィンドウを表すクラス
        JFrame window = new JFrame(); //JFrameをwindowに代入
        GamePanel gamePanel = new GamePanel(); //GamePanelをgamePnaelに代入
        
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ウィンドウのクローズボタン（"X" ボタン）をクリックした際にプログラムが終了
        window.setResizable(false); //ウィンドウのサイズ変更を無効に設定(プレイヤーがウィンドウのサイズを変更できない)
        window.setTitle("RPG"); //ゲームウィンドウのタイトルを設定します
        window.add(gamePanel); //ゲームパネルがウィンドウ内に表示
        window.pack(); //ウィンドウをコンポーネントの最適なサイズに調整
        window.setLocationRelativeTo(null); //ウィンドウを画面中央に配置
        window.setVisible(true); //ウィンドウを表示可能状態に設定
        
        gamePanel.setupGame();
        gamePanel.startGameThread(); //ゲームを実行
    }
}
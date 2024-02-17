package client_system;
import  java.awt.*;

public class ChoiceHour extends Choice {
	//コンストラクタ
	ChoiceHour(){
		resetRange(9, 21);  //時間の範囲を9時～21時
	}
	
	//指定できる時刻の範囲を設定するメソッド
	public void resetRange(int start, int end) {
		removeAll();
		while(start <= end) {
			String h = String.valueOf(start);
			if(h.length() == 1) {
				h = "0" + h;
			}
			add(h);
			start++;
		}
	}
	
	//設定されている一番早い時刻を返すメソッド
	public String getFirst() {
		return getItem(0);
	}
	
	//設定されている一番遅い時刻を返すメソッド
    public String getLast() {
    	return getItem( getItemCount()-1);	
    }
}
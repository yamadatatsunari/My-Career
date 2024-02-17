package client_system;
import java.sql.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReservationControl {
	//MySQLに接続するためのデータ
	Connection sqlCon;
	Statement sqlStmt;
	String sqlUserID = "puser";
	String sqlPassword = "1234";
	
	//予約システムのユーザID及びLogin状態
	String reservationUserID;
	private boolean flagLogin;
	
	////ReservationControlクラスのコンストラクタ
	ReservationControl(){
		flagLogin = false;
	}
	
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
	////ログイン・ログアウトボタンの処理
	public String loginLogout(MainFrame frame) {
		String res = "";
		if(flagLogin) {
			flagLogin = false;
			frame.buttonLog.setLabel("ログイン");
			frame.tfLoginID.setText("未ログイン");
		}else {
			//ログインダイアログ生成+表示
			LoginDialog Id = new LoginDialog(frame);
			Id.setBounds( 100, 100, 350, 150);
			Id.setResizable( false);
			Id.setVisible(true);
			Id.setModalityType( Dialog.ModalityType.APPLICATION_MODAL);
			
			if( Id.canceled) {
				return "";
			}
			
			//OKボタンが押下されていたら
			reservationUserID = Id.tfUserID.getText();
			String password   = Id.tfPassword.getText();
			
			connectDB();
			try {
				//ユーザの情報を所得するクエリ
				String sql = "SELECT * FROM db_reservation.user WHERE user_id = '" + reservationUserID + "';";
				ResultSet rs = sqlStmt.executeQuery( sql);
				//パスワードチェック
				if( rs.next()) {
					String password_form_db = rs.getString("password");
					if( password_form_db.equals(password)) {
						flagLogin = true;
						frame.buttonLog.setLabel("ログアウト");
						frame.tfLoginID.setText(reservationUserID);
						res = "";
					}else {
						res = "IDまたはパスワードが違います.";
					}
				}else {
					res = "IDが違います.";
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			closeDB();
		}
		return res;
	}
	
	///　教室概要ボタン押した時の処理を行うメソッド
	public String getFacilityExplanation(String facility_id) {
		String res = "";
		String exp = "";
		String openTime = "";
		String closeTime = "";
		connectDB();
		try {
			String sql = "SELECT * FROM db_reservation.facility WHERE facility_id='" + facility_id + "';";
			ResultSet rs = sqlStmt.executeQuery(sql);
			if(rs.next()) {
				exp = rs.getString("explanation");
				openTime = rs.getString("open_time");
				closeTime = rs.getString("close_time");
				//教室概要データの作成
				res = exp + " 利用可能時間:" + openTime.substring(0, 5) + "～" + closeTime.substring(0, 5);
			}else {
				res = "教室予約番号が違います.";
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		closeDB();
		return res;
	}
	
	///全てのfacility_idを所得するメソッド
	public List getFacilityId() {
		List<String>facilityId = new ArrayList<String>();
		connectDB();
		try {
			String sql = "SELECT * FROM db_reservation.facility;";
			ResultSet rs = sqlStmt.executeQuery(sql);
			while(rs.next()){
				facilityId.add(rs.getString("facility_id"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		closeDB();
		return facilityId;
	}
	
	///全てのreservation_idを所得するメソッド
	public List getReservationId() {
		List<String>reservationId = new ArrayList<String>();
		connectDB();
		try {
			String sql = "SELECT * FROM db_reservation.reservation;";
			ResultSet rs = sqlStmt.executeQuery(sql);
			while(rs.next()){
				reservationId.add(rs.getString("reservation_id"));
				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		closeDB();
		return reservationId;
	}

	// 予約状況確認ボタン押下時のメソッド
	public String getReservationed(MainFrame frame) {
		
		String res = "";
			
		//予約確認画面生成
		Reservationed1 rd = new Reservationed1(frame, this);
			
		//予約確認画面を表示
		rd.setVisible(true);
		if(rd.canceled) {
		return res;
			}
		//予約確認画面から年月日を所得
		String ryear_str  = rd.tfYear.getText();
		String rmonth_str = rd.tfMonth.getText();
		String rday_str   = rd.tfDay.getText();
		
		//月と日が一桁だったら、前に0を付加
		if(rmonth_str.length() == 1) {
		rmonth_str = "0" + rmonth_str;
		}
		if(rday_str.length() == 1) {
		rday_str = "0" + rday_str;
		}
		String rdate = ryear_str + "-" + rmonth_str + "-" + rday_str;
		
			//入力された日付が正しいかのチェック
			try {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				df.setLenient(false);
				String convData = df.format(df.parse(rdate));
				if((!rdate.equals(convData))||(ryear_str.length()!= 4)) {
					res = "日付の書式を修正してください （年:西暦4桁, 月:1～12, 日:1～31(毎月末日まで)）";
					return res;
				}
			}catch(ParseException p) {
				res = "日付の値を修正してください";
				return res;
			}
			
			//予約確認画面から教室名,開始時刻,終了時刻を所得
			String ReservationNumber = "";
			String facility = rd.choiceFacility.getSelectedItem();
			String user = "";
			String Day = "";
			String startTime = "";
			String endTime = "";
			connectDB();
			try {
				String sql = "SELECT * FROM db_reservation.reservation WHERE facility_id = '"+facility+"' AND day = '"+rdate+"';";		
				ResultSet rs = sqlStmt.executeQuery(sql);
				
			    if (rs.next()) {
			        do {
			            ReservationNumber = rs.getString("reservation_id");
			            facility  = rs.getString("facility_id");
			            user      = rs.getString("user_id");
			            Day       = rs.getString("day");
			            startTime = rs.getString("start_time");
			            endTime   = rs.getString("end_time");
			            
			            // 教室概要データの作成
			            res += "ID:" + ReservationNumber + " " + facility + "教室 " + user + " 予約日: " + Day + "  " + startTime.substring(0, 5) + "～" + endTime.substring(0, 5) + "\n";
			        } while (rs.next());
			    } else {
			        res = "予約が存在しません";
			    }
			}catch (Exception e) {
				e.printStackTrace();
			}
			closeDB();
		return res;
	}
		
	
	// 新規予約ボタン押下時のメソッド
	public String makeReservation(MainFrame frame) {
	    String res = "";

	    if (flagLogin) {

	        // 新規予約画面生成
	        ReservationDialog rd = new ReservationDialog(frame, this);

	        // 新規予約画面を表示
	        rd.setVisible(true);
	        if (rd.canceled) {
	            return res;
	        }

	        // 新規予約画面から年月日を取得
	        String ryear_str = rd.tfYear.getText();
	        String rmonth_str = rd.tfMonth.getText();
	        String rday_str = rd.tfDay.getText();

	        // 月と日が一桁だったら、前に0を付加
	        if (rmonth_str.length() == 1) {
	            rmonth_str = "0" + rmonth_str;
	        }

	        if (rday_str.length() == 1) {
	            rday_str = "0" + rday_str;
	        }

	        String rdate = ryear_str + "-" + rmonth_str + "-" + rday_str;

	        // 入力された日付が正しいかのチェック
	        try {
	            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	            df.setLenient(false);
	            String convData = df.format(df.parse(rdate));
	            if ((!rdate.equals(convData)) || (ryear_str.length() != 4)) {
	                res = "日付の書式を修正してください （年:西暦4桁, 月:1～12, 日:1～31(毎月末日まで)）";
	                return res;
	            }

	        } catch (ParseException p) {
	            res = "日付の値を修正してください";
	            return res;
	        }

	        // 新規予約画面から教室名,開発時刻,終了時刻を取得
	        String facility = rd.choiceFacility.getSelectedItem();
	        String st = rd.startHour.getSelectedItem() + ":" + rd.startMinute.getSelectedItem() + ":00";
	        String et = rd.endHour.getSelectedItem() + ":" + rd.endMinute.getSelectedItem() + ":00";

	        if (st.compareTo(et) >= 0) {
	            res = "開始時刻と終了時刻が同じか終了時刻の方が早くなっています";

	        } else {
	            connectDB();
	            try {
	                // 予約日時を取得
	                Calendar justNow = Calendar.getInstance();
	                SimpleDateFormat resDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	                String now = resDate.format(justNow.getTime());

	                if (rdate.compareTo(now.substring(0, 10)) <= 0) {
	                    res = "過去の日付は予約できません";
	                    return res;
	                }

	                // 重複する予約があるかチェック
	                String Sql = "SELECT * FROM db_reservation.reservation WHERE facility_id = '" + facility +
	                        "' AND day  = '" + rdate +
	                        "' AND (start_time < '" + et + "' AND end_time > '" + st + "');";

	                ResultSet rs = sqlStmt.executeQuery(Sql);

	                // 他の予約と重複している場合はエラーメッセージを返す
	                if (rs.next()) {
	                    res = "指定された時間帯ではすでに予約が存在します";
	                    return res;

	                } else {
	                    // 予約情報をMySQLに書き込む
	                    String sql = "INSERT INTO db_reservation.reservation" +
	                            "(facility_id,user_id,date,day,start_time,end_time)VALUES('" +
	                            facility + "','" + reservationUserID + "','" + now + "','" +
	                            rdate + "','" + st + "','" + et + "');";

	                    sqlStmt.executeUpdate(sql);
	                    res = "予約されました";
	                }


	            } catch (Exception e) {
	                e.printStackTrace();
	                res = "データベース接続またはSQL実行中にエラーが発生しました";

	            } finally {
	                closeDB();
	            }
	        }

	    } else {
	        res = "ログインしてください";
	    }
	    return res;
	}
	
	
	//指定教室の利用可能開始・終了時間を取得する
	public int[]getAvailableTime(String facility){
		int[] abailableTime = {0, 0};
		connectDB();
		try {
			String sql = "SELECT * FROM db_reservation.facility WHERE facility_id ='" + facility + "';";
			ResultSet rs = sqlStmt.executeQuery( sql);
			while (rs.next()) {
				String timeData = rs.getString("open_time");
				timeData = timeData.substring( 0,2);
				abailableTime[0] = Integer.parseInt(timeData);
				timeData = rs.getString("close_time");
				timeData = timeData.substring( 0,2);
				abailableTime[1] = Integer.parseInt(timeData);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return abailableTime;
	}
	
	//自己予約確認機能
	public String getSelfReservationed(MainFrame frame) {
        String res = "";

        if(!flagLogin) {
            return "ログインしてください";
        }

        connectDB();
        try {

            String sql = "SELECT * FROM db_reservation.reservation WHERE user_id = '"+ reservationUserID +"';";
            
            ResultSet rs = sqlStmt.executeQuery(sql);

            if(rs.next()) {
            	do {
	            	String ReservationNumber = rs.getString("reservation_id");
	                String facility = rs.getString("facility_id");
	                String day = rs.getString("day");
	                String startTime = rs.getString("start_time").substring(0, 5);
	                String endTime = rs.getString("end_time").substring(0, 5);
	
	                res += "ID:" +ReservationNumber +" " +facility+ "教室" + " 予約日:"  + day + "  " + startTime + "～" + endTime + "\n";
	            } while (rs.next());
	        } else {
	            res = "予約が存在しません.";
	        }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        closeDB();
        return res;
    }
	
	
	// 予約キャンセルボタン押下時のメソッド
public String getReservationCancel(MainFrame frame) {
		
		String res = "";
		if (flagLogin) {
			//予約キャンセル画面生成
			ReservationCancel rd = new ReservationCancel(frame, this);
				
			//予約キャンセル画面を表示
			rd.setVisible(true);
			if(rd.canceled) {
			return res;
				}
			
				// 予約キャンセル画面から予約番号,教室名,ユーザー名,予約日,開始時刻,終了時刻を所得
				String ReservationNumber = rd.choicereservationid.getSelectedItem();
				String facility = "";
				String rday = "";
				String startTime = "";
				String endTime = "";
				
				// MySQLに接続
				connectDB();
				try {
					// 予約IDとユーザの情報を取得するクエリを生成
					String sql = "SELECT * FROM db_reservation.reservation WHERE reservation_id = '"+ReservationNumber+"' AND user_id = '"+reservationUserID+"';";
					ResultSet rs = sqlStmt.executeQuery(sql); // クエリを実行して,結果セットを取得
					// 
					if(rs.next()) {    // 予約IDとユーザが一致した時
						ReservationNumber = rs.getString("reservation_id"); // reservation_idの取得
						facility = rs.getString("facility_id");             // facility_idの取得
						rday = rs.getString("day");                         // dayの取得
						startTime = rs.getString("start_time");             // start_timeの取得
						endTime = rs.getString("end_time");                 // end_timeの取得
						// 予約キャンセルデータの作成
						res = "ID:" + ReservationNumber + " " + facility + "教室" 
							    + " 予約日:" + rday + " " + startTime+ "～"
							    + endTime+ "の予約をキャンセルしました.";
						// MySQLにある予約情報を削除する
						String deletesql = "DELETE FROM db_reservation.reservation WHERE reservation_id = '" + ReservationNumber + "';";
						sqlStmt.executeUpdate(deletesql);	
					}else {
					res = "指定された予約IDは他のユーザの予約です";	
					}
				}catch (Exception e) {    // エラーが発生したとき
					e.printStackTrace();
				}
				closeDB();   // MySQLの切断
		}else{       // 未ログイン状態の場合
			res = "ログインしてください";
		}
			return res;
	}

}
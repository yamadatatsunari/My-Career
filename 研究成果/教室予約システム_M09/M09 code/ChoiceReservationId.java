package client_system;
import java.awt.*;
import java.util.List;

public class ChoiceReservationId extends Choice{
	/// ChoiceReservationIdコンストラクタ
	ChoiceReservationId( List<String>reservation){
		for(String id : reservation) {
			add(id);
		}
	}
}

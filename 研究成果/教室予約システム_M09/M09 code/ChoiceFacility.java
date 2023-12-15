package client_system;
import java.awt.*;
import java.util.List;

public class ChoiceFacility extends Choice{
	/// ChoiceFacilityコンストラクタ
	ChoiceFacility( List<String>facility){
		for(String id : facility) {
			add(id);
		}
	}
}
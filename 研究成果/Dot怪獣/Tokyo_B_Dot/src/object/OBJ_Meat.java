package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Meat extends SuperObject {
    
    public OBJ_Meat() {
        
        name = "Meat";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/object/Meet.png"));
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
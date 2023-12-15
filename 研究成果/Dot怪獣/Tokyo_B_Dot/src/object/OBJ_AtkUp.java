package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_AtkUp extends SuperObject {
    
    public OBJ_AtkUp() {
        
        name = "AtkUp";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/object/AtkUp.png"));
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
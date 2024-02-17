package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_OllUp extends SuperObject {
    
    public OBJ_OllUp() {
        
        name = "OllUp";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/object/OllUp.png"));
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
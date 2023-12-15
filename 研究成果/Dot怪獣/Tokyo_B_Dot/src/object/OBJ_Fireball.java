package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Projectile;
import main.GamePanel;
public class OBJ_Fireball extends Projectile{
	
	GamePanel gp;
	
	public OBJ_Fireball(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = "Fireball";
		speed = 8;
		maxLife = 80;
		life = maxLife;
		attack = 2;
		alive = false;
		getImage();
	}
	
	public void getImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/res/projectile/ball.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/res/projectile/ball.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/res/projectile/ball.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/res/projectile/ball.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/res/projectile/ball.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/res/projectile/ball.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/res/projectile/ball.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/res/projectile/ball.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


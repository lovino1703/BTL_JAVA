package object;

import entity.Projectiles;
import main.GamePanel;

import java.awt.*;

public class OBJ_Rock extends Projectiles {
    GamePanel gp;
    public static final String objName = "Rock";
    public OBJ_Rock(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = objName;
        speed = 5;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        useCost = 1;
        alive = false;
        getImage();
    }

    public void getImage(){
        up1 = setUp("/projectiles/rock_down_1", gp.tileSize, gp.tileSize);
        up2 = setUp("/projectiles/rock_down_1", gp.tileSize, gp.tileSize);//stand still
        down1 = setUp("/projectiles/rock_down_1", gp.tileSize, gp.tileSize);
        down2 = setUp("/projectiles/rock_down_1", gp.tileSize, gp.tileSize);//stand still
        left1 = setUp("/projectiles/rock_down_1", gp.tileSize, gp.tileSize);
        left2 = setUp("/projectiles/rock_down_1", gp.tileSize, gp.tileSize);//stand still
        right1 = setUp("/projectiles/rock_down_1", gp.tileSize, gp.tileSize);
        right2 = setUp("/projectiles/rock_down_1", gp.tileSize, gp.tileSize);//stand still
    }

    public Color getParticleColor(){
        Color color = new Color(40, 50, 0);
        return color;
    }
    public int getParticleSize(){
        int size = 10;// 6 pixel
        return size;
    }
    public int getParticleSpeed(){
        int speed = 1;
        return speed;
    }
    public int getParticleMaxLife(){
        int maxLife = 20;
        return maxLife;
    }
}

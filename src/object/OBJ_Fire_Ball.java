package object;

import entity.Entity;
import entity.Projectiles;
import main.GamePanel;

import javax.swing.*;
import java.awt.*;

public class OBJ_Fire_Ball extends Projectiles {
    GamePanel gp;
    public static final String objName = "Fire ball";
    public OBJ_Fire_Ball(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = objName;
        speed = 10;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        knockBackPower = 5;
        useCost = 1;
        alive = false;
        getImage();
    }

    public void getImage(){
        up1 = setUp("/projectiles/fireball_up_1", gp.tileSize, gp.tileSize);
        up2 = setUp("/projectiles/fireball_up_2", gp.tileSize, gp.tileSize);//stand still
        down1 = setUp("/projectiles/fireball_down_1", gp.tileSize, gp.tileSize);
        down2 = setUp("/projectiles/fireball_down_2", gp.tileSize, gp.tileSize);//stand still
        left1 = setUp("/projectiles/fireball_left_1", gp.tileSize, gp.tileSize);
        left2 = setUp("/projectiles/fireball_left_2", gp.tileSize, gp.tileSize);//stand still
        right1 = setUp("/projectiles/fireball_right_1", gp.tileSize, gp.tileSize);
        right2 = setUp("/projectiles/fireball_right_2", gp.tileSize, gp.tileSize);//stand still
    }

    public boolean haveResource(Entity user){
        boolean haveResource= false;
        if(user.mana >= useCost){
            haveResource = true;
        }
        return haveResource;
    }

    public void subtractResource(Entity user){
        user.mana -= useCost;
    }

    public Color getParticleColor(){
        Color color = new Color(240, 50, 0);
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

package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Heart extends Entity {
    public static final String objName = "Heart";
    public OBJ_Heart(GamePanel gp){
        super(gp);
        type = type_Pick_Up_Item;
        name = objName;
        value = 2;
        down1 = setUp("/objects/heart_full", gp.tileSize, gp.tileSize);
        image1 = setUp("/objects/heart_full", gp.tileSize, gp.tileSize);
        image2 = setUp("/objects/heart_half", gp.tileSize, gp.tileSize);
        image3 = setUp("/objects/heart_blank", gp.tileSize, gp.tileSize);
    }
    public boolean use(Entity entity){
        gp.playSE(2);
        gp.ui.addMessage("Life + "+ value );
        entity.life += value;
        return true;
    }
}

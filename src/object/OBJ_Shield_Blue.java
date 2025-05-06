package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Blue extends Entity {
    public static final String objName = "Blue Shield";
    public OBJ_Shield_Blue(GamePanel gp) {
        super(gp);
        name = objName;
        down1 = setUp("/objects/shield_blue", gp.tileSize, gp.tileSize);
        defeseValue = 2;
        description = "[" + name + "]\nMade by platinum";
        type = type_Shield;
        price = 250;
    }
}

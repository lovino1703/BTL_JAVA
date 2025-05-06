package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Wood extends Entity {
    public static final String objName = "Wood Shield";
    public OBJ_Shield_Wood(GamePanel gp) {
        super(gp);
        name = objName;
        down1 = setUp("/objects/shield_wood", gp.tileSize, gp.tileSize);
        defeseValue = 1;
        description = "[" + name + "]\nMade by wood";
        type = type_Shield;
        price = 35;
    }
}

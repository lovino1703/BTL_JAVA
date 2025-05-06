package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe extends Entity {
    public static final String objName = "Axe";
    public OBJ_Axe(GamePanel gp) {
        super(gp);
        name = objName;
        down1 = setUp("/objects/axe", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nIt can cut down trees";
        attackArea.width = 30;
        attackArea.height = 30;
        type = type_Axe;
        attackValue = 2;
        price = 75;
        knockBackPower = 10;
    }
}

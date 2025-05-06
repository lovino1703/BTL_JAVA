package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword_Normal extends Entity {
    public static final String objName = "Normal Sword";
    public OBJ_Sword_Normal(GamePanel gp) {
        super(gp);
        name = objName;
        down1 = setUp("/objects/sword_normal", gp.tileSize, gp.tileSize);
        attackValue = 1;
        description = "[" + name + "]\nAn old sword";
        attackArea.width = 36;
        attackArea.height = 36;
        type = type_Sword;
        price = 20;
        knockBackPower = 2;
    }
}

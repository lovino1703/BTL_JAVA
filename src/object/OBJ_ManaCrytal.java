package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_ManaCrytal extends Entity {
    GamePanel gp;
    public static final String objName = "Mana crystal";
    public OBJ_ManaCrytal(GamePanel gp) {
        super(gp);
        name = objName;
        type = type_Pick_Up_Item;
        this.gp = gp;
        down1 = setUp("/objects/manacrystal_full", gp.tileSize, gp.tileSize);
        value = 1;
        image1 = setUp("/objects/manacrystal_full", gp.tileSize, gp.tileSize);
        image2 = setUp("/objects/manacrystal_blank", gp.tileSize, gp.tileSize);
    }
    public boolean use(Entity entity){
        gp.playSE(2);
        gp.ui.addMessage("Mana + "+ value );
        entity.mana += value;
        return true;
    }
}
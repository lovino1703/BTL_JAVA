package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Bronze_Coin extends Entity {
    GamePanel gp;
    public static final String objName = "Bronze coin";
    public OBJ_Bronze_Coin(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_Pick_Up_Item;
        value = 1;
        name = objName;
        down1 = setUp("/objects/coin_bronze", gp.tileSize, gp.tileSize);
    }

    public boolean use(Entity entity){
        gp.playSE(1);
        gp.ui.addMessage("coin + "+ value );
        entity.coin += value;
        return true;
    }


}

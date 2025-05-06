package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Red_Potion extends Entity {
    public static final String objName = "Red Potion";
    public OBJ_Red_Potion(GamePanel gp) {
        super(gp);
        type = type_Consumables;
        value = 5;
        name = objName;
        down1 = setUp("/objects/potion_red", gp.tileSize, gp.tileSize);
        description = "[Red Potion]\n Heal you by " + value + "life!";
        price = 25;
        stackable = true;
    }

    public boolean use(Entity entity){
        gp.gameState = gp.dialougeState;
        gp.ui.currentDialogue = "you drank the " + name + "!\n" + "your life has recovered by " + value;
        entity.life += value;
        gp.playSE(2);
        return true;
//        if(gp.player.life > gp.player.maxLife){
//            gp.player.life = gp.player.maxLife;
//        }
    }
}

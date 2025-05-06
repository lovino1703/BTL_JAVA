package object;
import entity.Entity;
import main.GamePanel;

public class OBJ_Chest extends Entity {
    GamePanel gp;
    public static final String objName = "Chest";
    public OBJ_Chest(GamePanel gp){
        super(gp);
        this.gp = gp;


        type = type_obstacle;
        name = objName;
        image1 = setUp("/objects/chest", gp.tileSize, gp.tileSize);
        image2 = setUp("/objects/chest_opened", gp.tileSize, gp.tileSize);
        down1 = image1;
        collision = true;

        solidArea.x = 4;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

    }
    public void setLoot(Entity loot){
        this.loot = loot;
    }
    public void interact(){
        gp.gameState = gp.dialougeState;
        if(opened == false){
            gp.playSE(3);

            StringBuilder sb = new StringBuilder();
            sb.append("you opened the chest and find a " + loot.name + "!");

            if(gp.player.canObtainItem(loot) == false){
                sb.append("\n... But you dont have enough spacing to\n carry it!");
            }else{
                sb.append("\nYou obtained the " + loot.name + "!");
                down1 = image2;
                opened = true;
            }
            gp.ui.currentDialogue = sb.toString();
        }
        else{
            gp.ui.currentDialogue = "its empty!";
        }
    }
}

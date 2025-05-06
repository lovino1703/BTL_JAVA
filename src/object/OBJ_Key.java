package object;
import entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity {
    GamePanel gp;
    public static final String objName = "Key";
    public OBJ_Key(GamePanel gp){
        super(gp);
        this.gp = gp;
        type = type_Consumables;
        name = objName;
        down1 = setUp("/objects/key", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nIt opens the door";
        price = 100;
        stackable = true;
    }

    public boolean use(Entity entity){
        gp.gameState = gp.dialougeState;

        int objIndex = getDetected(entity, gp.obj, "Door");
        if(objIndex != 999){
            gp.ui.currentDialogue = "you used the "+ name + " to open the door";
            gp.playSE(3);
            gp.obj[gp.currentMap][objIndex] = null;
            return true;
        }else{
            gp.ui.currentDialogue = "what r you doin mate ?";
            return false;
        }
    }
}

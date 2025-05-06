package object;
import entity.Entity;
import main.GamePanel;

public class OBJ_Door extends Entity {
    GamePanel gp;
    public static final String objName = "Door";
    public OBJ_Door(GamePanel gp){
        super(gp);
        this.gp = gp;
        type = type_obstacle;
        name = objName;
        down1 = setUp("/objects/door", gp.tileSize, gp.tileSize);
        collision = true;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = gp.tileSize;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public void interact(){
        gp.gameState = gp.dialougeState;
        gp.ui.currentDialogue = "you need a key to open this!";
    }
}

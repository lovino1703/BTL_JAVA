package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Lantern extends Entity {
    public static final String objName = "Lantern";
    public OBJ_Lantern(GamePanel gp) {
        super(gp);
        name = objName;
        type = type_Light;
        down1 = setUp("/objects/lantern", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nIlluminate your \nsurroundings.";
        price = 200;
        lightRadius = 250;
    }
}

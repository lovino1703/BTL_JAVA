package main;

import entity.Entity;
import object.*;

public class EntityGenerator {
    GamePanel gp;

    public EntityGenerator(GamePanel gp){
        this.gp = gp;
    }

    public Entity getObjects(String itemname){

        Entity obj = null;

        switch (itemname){
            case OBJ_Axe.objName: obj = new OBJ_Axe(gp); break;
            case OBJ_Boots.objName: obj = new OBJ_Boots(gp); break;
            case OBJ_Bronze_Coin.objName: obj = new OBJ_Bronze_Coin(gp); break;
            case OBJ_Chest.objName: obj = new OBJ_Chest(gp); break;
            case OBJ_Door.objName: obj = new OBJ_Door(gp); break;
            case OBJ_Fire_Ball.objName: obj = new OBJ_Fire_Ball(gp); break;
            case OBJ_Heart.objName: obj = new OBJ_Heart(gp); break;
            case OBJ_Key.objName: obj = new OBJ_Key(gp); break;
            case OBJ_Lantern.objName: obj = new OBJ_Lantern(gp); break;
            case OBJ_ManaCrytal.objName: obj = new OBJ_ManaCrytal(gp); break;
            case OBJ_Red_Potion.objName: obj = new OBJ_Red_Potion(gp); break;
            case OBJ_Rock.objName: obj = new OBJ_Rock(gp); break;
            case OBJ_Shield_Blue.objName: obj = new OBJ_Shield_Blue(gp); break;
            case OBJ_Shield_Wood.objName: obj = new OBJ_Shield_Wood(gp); break;
            case OBJ_Sword_Normal.objName: obj = new OBJ_Sword_Normal(gp); break;


        }
        return obj;
    }
}

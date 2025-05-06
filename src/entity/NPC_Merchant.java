package entity;

import main.GamePanel;
import object.*;

public class NPC_Merchant extends  Entity{
    public NPC_Merchant(GamePanel gp){
        super(gp);
        diretion = "down";
        speed = 0;
        getBaldImage();
        setDialogue();

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        setItem();
    }

    public void getBaldImage(){
        up1 = setUp("/npc/merchant_down_1", gp.tileSize, gp.tileSize);
        up2 = setUp("/npc/merchant_down_2", gp.tileSize, gp.tileSize);//stand still
        down1 = setUp("/npc/merchant_down_1", gp.tileSize, gp.tileSize);
        down2 = setUp("/npc/merchant_down_2", gp.tileSize, gp.tileSize);//stand still
        left1 = setUp("/npc/merchant_down_1", gp.tileSize, gp.tileSize);
        left2 = setUp("/npc/merchant_down_2", gp.tileSize, gp.tileSize);//stand still
        right1 = setUp("/npc/merchant_down_1", gp.tileSize, gp.tileSize);
        right2 = setUp("/npc/merchant_down_2", gp.tileSize, gp.tileSize);//stand still
    }

    public void setDialogue(){
        dialogues[0] = "he he,so you found me.\nI have some good stuff\nwanna trade ?";
    }

    public void setItem(){
        inventory.add(new OBJ_Red_Potion(gp));
        inventory.add(new OBJ_Key(gp));
        inventory.add(new OBJ_Sword_Normal(gp));
        inventory.add(new OBJ_Axe(gp));
        inventory.add(new OBJ_Shield_Wood(gp));
        inventory.add(new OBJ_Shield_Blue(gp));

    }

    public void speak(){
        super.speak();
        gp.gameState = gp.tradeState;
        gp.ui.npc  = this;

    }
}

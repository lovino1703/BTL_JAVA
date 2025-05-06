package entity;

import main.GamePanel;
import main.UtilityTools;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Random;

public class NPC_bald_man extends Entity{

    public NPC_bald_man(GamePanel gp){
        super(gp);
        diretion = "down";
        speed = 2;
        getBaldImage();
        setDialogue();

//        solidArea.x = 0;
//        solidArea.y = 16;
//        solidArea.width = 48;
//        solidArea.height = 32;
//        solidAreaDefaultX = solidArea.x;
//        solidAreaDefaultY = solidArea.y;

        //I don't understand why it got fixed ??
        solidArea.x = 4;
        solidArea.y = 19;
        solidArea.width = 41;
        solidArea.height = 29;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

    }

    public void getBaldImage(){
        up1 = setUp("/npc/bald_up_1", gp.tileSize, gp.tileSize);
        up2 = setUp("/npc/bald_up_2", gp.tileSize, gp.tileSize);//stand still
        down1 = setUp("/npc/bald_down_1", gp.tileSize, gp.tileSize);
        down2 = setUp("/npc/bald_down_2", gp.tileSize, gp.tileSize);//stand still
        left1 = setUp("/npc/bald_left_1", gp.tileSize, gp.tileSize);
        left2 = setUp("/npc/bald_left_2", gp.tileSize, gp.tileSize);//stand still
        right1 = setUp("/npc/bald_right_1", gp.tileSize, gp.tileSize);
        right2 = setUp("/npc/bald_right_2", gp.tileSize, gp.tileSize);//stand still
    }

    public void setDialogue(){
        dialogues[0] = "hello kid!";
        dialogues[1] = "Did you just move here?";
        dialogues[2] = "Everyone here welcomes you, kid.";
        dialogues[3] = "why don't you try talking to \npeople";

    }

    public void setAction(){
        if(onPath == true){
//            int goalCol = 12;
//            int goalRow = 9;
            int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;
            int count = 0;
            System.out.println(count + " : " + diretion);
            searchPath(goalCol, goalRow);
        }else{
            actionLockCounter++;
            if(actionLockCounter == 120){
                Random random = new Random();
                int i = random.nextInt(100) + 1;//pick up number from 1 - 100
                if(i <= 25){
                    diretion = "up";
                }
                if(i > 25 && i <= 50){
                    diretion = "down";
                }
                if(i > 50 && i <=75){
                    diretion = "left";
                }
                if(i > 75){
                    diretion = "right";
                }
                actionLockCounter = 0;
            }
        }
    }

    public void speak() {
        super.speak();
        onPath = true;
    }
}

package monster;
import entity.Entity;
import main.GamePanel;
import object.OBJ_Bronze_Coin;
import object.OBJ_Heart;
import object.OBJ_ManaCrytal;
import object.OBJ_Rock;

import java.util.Random;

public class MON_GreenSlime extends Entity {

    public MON_GreenSlime(GamePanel gp) {
        super(gp);
        name = "Green Slime";
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 4;
        life = maxLife;
        attack = 5;
        defense = 0;
        solidArea.x = 3;
        solidArea.y = 18;
        exp = 2;
        projectile = new OBJ_Rock(gp);
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
        type = type_Monster;
    }


    public void getImage(){
        up1 = setUp("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        up2 = setUp("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
        down1 = setUp("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        down2 = setUp("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
        left1 = setUp("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        left2 = setUp("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
        right1 = setUp("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        right2 = setUp("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
    }

    public void setAction(){
        if(onPath == true){
            // check if it stops chasing
            checkStopChasingOrNot(gp.player, 15, 100);
            //search the direction to go
            searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
            //check if it shoots a projectile
            checkShootOrNot(170, 30);
        }else{
            //check if it starts chasing
            checkStartChasingOrNot(gp.player, 5, 100);
            //get a random direction if its not onpath
            getRandomDirection();
        }
    }
    public void damageReaction() {
        super.damageReaction();
        actionLockCounter = 0;
//        diretion = gp.player.diretion;
        onPath = true;
    }
    public void checkDrop() {
        super.checkDrop();
        //CAST A DICE
        int i = new Random().nextInt(100) + 1;

        //SET THE MONSTER DROP
        if( i < 50){
            dropItem(new OBJ_Bronze_Coin(gp));
        }
        if(i >= 55 && i < 75){
            dropItem(new OBJ_Heart(gp));
        }
        if(i >= 75){
            dropItem(new OBJ_ManaCrytal(gp));
        }
    }
}

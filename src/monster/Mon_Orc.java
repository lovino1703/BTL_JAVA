package monster;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Bronze_Coin;
import object.OBJ_Heart;
import object.OBJ_ManaCrytal;
import object.OBJ_Rock;

import java.util.Random;

public class Mon_Orc extends Entity {
    public Mon_Orc(GamePanel gp) {
        super(gp);
        name = "Orc";
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 10;
        life = maxLife;
        attack = 8;
        defense = 2;
        solidArea.x = 4;
        solidArea.y = 4;
        exp = 10;
        solidArea.width = 40;
        solidArea.height = 44;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width = 48;
        attackArea.height = 40;
        getImage();
        getAttackImage();
        type = type_Monster;
        attack_motion1_duration = 40;
        attack_motion2_duration = 85;
        knockBackPower = 5;
    }


    public void getImage(){
        up1 = setUp("/monster/orc_up_1", gp.tileSize, gp.tileSize);
        up2 = setUp("/monster/orc_up_2", gp.tileSize, gp.tileSize);
        down1 = setUp("/monster/orc_down_1", gp.tileSize, gp.tileSize);
        down2 = setUp("/monster/orc_down_2", gp.tileSize, gp.tileSize);
        left1 = setUp("/monster/orc_left_1", gp.tileSize, gp.tileSize);
        left2 = setUp("/monster/orc_left_2", gp.tileSize, gp.tileSize);
        right1 = setUp("/monster/orc_right_1", gp.tileSize, gp.tileSize);
        right2 = setUp("/monster/orc_right_2", gp.tileSize, gp.tileSize);
    }
    public void getAttackImage(){
        attackUp1 = setUp("/monster/orc_attack_up_1", gp.tileSize, gp.tileSize * 2);
        attackUp2 = setUp("/monster/orc_attack_up_2", gp.tileSize, gp.tileSize * 2);
        attackDown1 = setUp("/monster/orc_attack_down_1", gp.tileSize, gp.tileSize * 2);
        attackDown2 = setUp("/monster/orc_attack_down_2", gp.tileSize, gp.tileSize * 2);
        attackLeft1 = setUp("/monster/orc_attack_left_1", gp.tileSize * 2, gp.tileSize);
        attackLeft2 = setUp("/monster/orc_attack_left_2", gp.tileSize * 2, gp.tileSize);
        attackright1 = setUp("/monster/orc_attack_right_1", gp.tileSize * 2, gp.tileSize);
        attackRight2 = setUp("/monster/orc_attack_right_2", gp.tileSize * 2, gp.tileSize);
    }
    public void setAction(){
        if(onPath == true){
            // check if it stops chasing
            checkStopChasingOrNot(gp.player, 15, 100);
            //search the direction to go
            searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
        }else{
            //check if it starts chasing
            checkStartChasingOrNot(gp.player, 5, 100);
            //get a random direction if its not onpath
            getRandomDirection();
        }

        //check if its attack
        if(attacking == false){
            checkAttackOrNot(30, gp.tileSize * 4, gp.tileSize);
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
        //CAST A DIE
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

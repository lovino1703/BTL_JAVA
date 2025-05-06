package entity;
import main.GamePanel;
import main.KeyHandler;
import main.UtilityTools;
import object.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;

public class Player extends Entity{
    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    public int standCounter = 0;
    public boolean attackCancel = false;
    public boolean lightUpdated = false;

    public Player(GamePanel gp, KeyHandler keyH){
        super(gp);
        this.keyH = keyH;

        //set the size and coordinates of the rectangle
        this.solidArea = new Rectangle();
        solidArea.x = 1;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        //ATTACK AREA
//        attackArea.width = 36;
//        attackArea.height = 36;

        //set the player staying in the center of the frame
        screenX = gp.screenWidth/2 - (gp.tileSize)/2;
        screenY = gp.screenHeight/2 - (gp.tileSize)/2;

        this.setDefaultValue();

    }

    public void setDefaultValue(){
        setDefaultPosition();
        defaultSpeed = 4;
        speed = defaultSpeed;
        diretion = "down";
        //PLAYER STATUS
        level = 1;
        maxLife = 6;
        life = maxLife;
        maxMana = 4;
        mana = maxMana;
        strength = 1;
        dexterity = 1;
        exp = 0;
        nextlevelExp = 5;
        coin = 500;
        attack_motion1_duration = 5;
        attack_motion2_duration = 25;
        currentWeapon = new OBJ_Sword_Normal(gp);
        currentShield = new OBJ_Shield_Wood(gp);
        currentLight = null;;
        projectile = new OBJ_Fire_Ball(gp);
        attack = getAttack();
        defense = getDefense();
        setItems();
        this.getImage();
        this.getAttackImage();
        this.getGuardImage();
    }

    public void setDefaultPosition(){
//        worldX = gp.tileSize * 12;
//        worldY = gp.tileSize * 13;
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        diretion = "down";
    }

    public void reStoreStatus(){
        life = maxLife;
        mana = maxMana;
        speed = defaultSpeed;
        transparent = false;
        invincible = false;
        attacking = false;
        guarding = false;
        knockBack = false;
        lightUpdated = true;
    }

    public void setItems(){
        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
    }

    public int getAttack(){
        attackArea = currentWeapon.attackArea;
        return attack = strength * currentWeapon.attackValue;
    }

    public  int getDefense(){
        return defense = dexterity * currentShield.defeseValue;
    }

    public int getCurrentWeaponSlot(){
        int currentWeaponSlot = 0;
        for(int i = 0; i < inventory.size(); i++){
            if(inventory.get(i) == currentWeapon){
                currentWeaponSlot = i;
                break;
            }
        }
        return currentWeaponSlot;
    }
    public int getCurrentShieldSlot(){
        int currentShieldSlot = 0;
        for(int i = 0; i < inventory.size(); i++){
            if(inventory.get(i) == currentShield){
                currentShieldSlot = i;
                break;
            }
        }
        return currentShieldSlot;
    }

    //load Image into RAM by save it into a BufferedImage object
    public void getImage(){
        up1 = setUp("/player/boy_up_1", gp.tileSize, gp.tileSize);
        up2 = setUp("/player/boy_up_2", gp.tileSize, gp.tileSize);//stand still
        down1 = setUp("/player/boy_down_1", gp.tileSize, gp.tileSize);
        down2 = setUp("/player/boy_down_2", gp.tileSize, gp.tileSize);//stand still
        left1 = setUp("/player/boy_left_1", gp.tileSize, gp.tileSize);
        left2 = setUp("/player/boy_left_2", gp.tileSize, gp.tileSize);//stand still
        right1 = setUp("/player/boy_right_1", gp.tileSize, gp.tileSize);
        right2 = setUp("/player/boy_right_2", gp.tileSize, gp.tileSize);//stand still



    }

    public void getAttackImage(){
        if(currentWeapon.type == type_Sword){
            attackUp1 = setUp("/player/boy_attack_up_1", gp.tileSize, gp.tileSize * 2);
            attackUp2 = setUp("/player/boy_attack_up_2", gp.tileSize, gp.tileSize * 2);
            attackDown1 = setUp("/player/boy_attack_down_1", gp.tileSize, gp.tileSize * 2);
            attackDown2 = setUp("/player/boy_attack_down_2", gp.tileSize, gp.tileSize * 2);
            attackLeft1 = setUp("/player/boy_attack_left_1", gp.tileSize * 2, gp.tileSize);
            attackLeft2 = setUp("/player/boy_attack_left_2", gp.tileSize * 2, gp.tileSize);
            attackright1 = setUp("/player/boy_attack_right_1", gp.tileSize * 2, gp.tileSize);
            attackRight2 = setUp("/player/boy_attack_right_2", gp.tileSize * 2, gp.tileSize);
        }
        if(currentWeapon.type == type_Axe){
            attackUp1 = setUp("/player/boy_axe_up_1", gp.tileSize, gp.tileSize * 2);
            attackUp2 = setUp("/player/boy_axe_up_2", gp.tileSize, gp.tileSize * 2);
            attackDown1 = setUp("/player/boy_axe_down_1", gp.tileSize, gp.tileSize * 2);
            attackDown2 = setUp("/player/boy_axe_down_2", gp.tileSize, gp.tileSize * 2);
            attackLeft1 = setUp("/player/boy_axe_left_1", gp.tileSize * 2, gp.tileSize);
            attackLeft2 = setUp("/player/boy_axe_left_2", gp.tileSize * 2, gp.tileSize);
            attackright1 = setUp("/player/boy_axe_right_1", gp.tileSize * 2, gp.tileSize);
            attackRight2 = setUp("/player/boy_axe_right_2", gp.tileSize * 2, gp.tileSize);
        }
    }

    public void getGuardImage(){
        guardUp = setUp("/player/boy_guard_up", gp.tileSize, gp.tileSize);
        guardDown = setUp("/player/boy_guard_down", gp.tileSize, gp.tileSize);
        guardLeft = setUp("/player/boy_guard_left", gp.tileSize, gp.tileSize);
        guardRight = setUp("/player/boy_guard_right", gp.tileSize, gp.tileSize);
    }

    public void update(){
        if(knockBack == true){

            collisionON = false;
            gp.cChecker.checkTile(this);
            gp.cChecker.checkObject(this, true);
            gp.cChecker.checkEntity(this, gp.npc);
            gp.cChecker.checkEntity(this, gp.monster);
            gp.cChecker.checkEntity(this, gp.iTile);

            checkCollision();
            if(collisionON == true){
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }else if(collisionON == false){
                switch (knockBackDirection){
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            } knockBackCounter++;
            if(knockBackCounter == 10){
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
        }
        else if(attacking == true){
            attacking();
        }
        else if(keyH.spacePressed == true){
            guarding = true;
        }
        else if (keyH.rightPressed == true || keyH.upPressed == true ||
                keyH.downPressed == true || keyH.leftPressed == true || keyH.enterPressed == true) {
                if (keyH.rightPressed == true) {
                    diretion = "right";
                }
                if (keyH.leftPressed == true) {
                    diretion = "left";
                }
                if (keyH.downPressed == true) {
                    diretion = "down";
                }
                if (keyH.upPressed == true) {
                    diretion = "up";
                }
                //CHECK TILE COLLISION
                collisionON = false;
                gp.cChecker.checkTile(this);
                //CHECK OBJECT COLLISION
                int objIndex = gp.cChecker.checkObject(this, true);
                pickUpObject(objIndex);
                //CHECK NPC COLLISION
                int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
                interactNPC(npcIndex);
                //CHECK MONSTER COLLISION
                int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
                contactMonster(monsterIndex);
                //CHECK INTERACTIVE TILE COLLISION
                int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);


                //CHECK EVENT
                gp.eHandler.checkEvent();



                //IF COLLISION IS FALSE, PLAYER CAN MOVE
                if(collisionON == false && keyH.enterPressed == false){
                    switch (diretion){
                        case "up": worldY -= speed; break;
                        case "down": worldY += speed; break;
                        case "left": worldX -= speed; break;
                        case "right": worldX += speed; break;
                    }
                }

                if(keyH.enterPressed == true && attackCancel == false) {
                    gp.playSE(7);
                    attacking = true;
                    spriteCounter = 0;
                }

                attackCancel = false;
                gp.keyH.enterPressed = false;
                guarding = false;

                spriteCounter++;
                if (spriteCounter > 12) {
                    if (spriteNum == 1) {
                        spriteNum = 2;
                    } else if (spriteNum == 2) {
                        spriteNum = 1;
                    }
                    spriteCounter = 0;
                }
            }else {
                standCounter++;
                if (standCounter > 20) {
                    spriteNum = 2;
                    standCounter = 0;
                }
                guarding = false;
            }

        if(gp.keyH.shotKeyPressed == true && projectile.alive == false && shotAvailableCounter == 30
                && projectile.haveResource(this) == true){
            //SET DEFAULT DIRECTION, COORDINATES AND USER
            projectile.set(worldX, worldY, diretion, true, this);
            //SUBTRACT THE MANA AFTER A SHOT
            projectile.subtractResource(this);

            //CHECK VACANCY
            for(int i = 0; i  < gp.projectile[1].length; i++){
                if(gp.projectile[gp.currentMap][i] == null){
                    gp.projectile[gp.currentMap][i] = projectile;
                    break;
                }
            }
            shotAvailableCounter = 0;
            gp.playSE(10);
        }

            //This need to be outside of the key if statement
        if(invincible == true){
            invincibleCounter++;
            if(invincibleCounter > 60){
                invincible = false;
                transparent = false;
                invincibleCounter = 0;
            }
        }
        if(shotAvailableCounter < 30){
            shotAvailableCounter++;
        }
        if(life > maxLife){
            life = maxLife;
        }
        if(mana > maxMana){
            mana = maxMana;
        }

        if(life <= 0){
            gp.gameState = gp.gameOverState;
            gp.ui.commandNum = -1;
            gp.stopMusic();
            gp.playSE(12);
        }

        System.out.println(gp.gameState);
    }

    public  void pickUpObject(int i){
        if(i != 999){
            //PICK ONLY ITEM
            if(gp.obj[gp.currentMap][i].type == type_Pick_Up_Item){ //FIXED
                gp.obj[gp.currentMap][i].use(this); //FIXED
                gp.obj[gp.currentMap][i] = null; //FIXED
            }
            //OBSTACLE
            else if(gp.obj[gp.currentMap][i].type == type_obstacle){
                if(keyH.enterPressed == true){
                    attackCancel = true;
                    gp.obj[gp.currentMap][i].interact();
                }
            }
            //INVENTORY ITEM
            else{
                String text;
                if(canObtainItem(gp.obj[gp.currentMap][i]) == true){
                    gp.playSE(1);
                    text = "Got a " + gp.obj[gp.currentMap][i].name + "!"; //FIXED
                }
                else{
                    text = "you can not pick up anymore!";
                }
                gp.ui.addMessage(text);
                gp.obj[gp.currentMap][i] = null; //FIXED!!
            }
        }
    }

    public void interactNPC(int i){
        if(keyH.enterPressed == true){
            if(i != 999){
                    attackCancel = true;
                    gp.gameState = gp.dialougeState;
                    gp.npc[gp.currentMap][i].speak(); //FIXED
            }
        }
    }

    public void contactMonster(int i){
        if(i != 999){
            if(invincible == false && gp.monster[gp.currentMap][i].dying == false){ //FIXED
                gp.playSE(6);
                int damage = gp.monster[gp.currentMap][i].attack - defense; //FIXED
                if(damage < 1){
                    damage = 1;
                }
                life -= damage;
                invincible = true;
                transparent = true;
            }
        }
    }

    public void damageMonster(int monsterIndex,Entity attacker ,int attack, int knockBackPower){
        if(monsterIndex != 999){
            if(gp.monster[gp.currentMap][monsterIndex].invincible == false){ //FIXED
                gp.playSE(5);

                if(knockBackPower > 0){
                    setKnockBack(gp.monster[gp.currentMap][monsterIndex], attacker, knockBackPower);
                }


                int damage = attack - gp.monster[gp.currentMap][monsterIndex].defense; //FIXED
                if(damage < 0){
                    damage = 0;
                }
                gp.monster[gp.currentMap][monsterIndex].life -= damage; //FIXED
                gp.ui.addMessage(damage + " damage !");
                gp.monster[gp.currentMap][monsterIndex].invincible = true; //FIXED
                gp.monster[gp.currentMap][monsterIndex].damageReaction(); //FIXED
                if(gp.monster[gp.currentMap][monsterIndex].life <= 0){ //FIXED
                    gp.monster[gp.currentMap][monsterIndex].dying = true; //FIXED
                    gp.ui.addMessage("Killed the "+gp.monster[gp.currentMap][monsterIndex].name + "!"); //FIXED
                    gp.ui.addMessage("EXP + "+gp.monster[gp.currentMap][monsterIndex].exp + "!"); //FIXED
                    exp += gp.monster[gp.currentMap][monsterIndex].exp; //FIXED
                    checkLevelUp();
                }
            }
        }
    }



    public void damageInteractiveTile( int i ){
        if( i != 999 && gp.iTile[gp.currentMap][i].destructible == true //FIXED
                && gp.iTile[gp.currentMap][i].isCorrectItem(this) == true && gp.iTile[gp.currentMap][i].invincible == false){ //FIXED
            gp.iTile[gp.currentMap][i].playSE(); //FIXED
            gp.iTile[gp.currentMap][i].life--; //FIXED
            gp.iTile[gp.currentMap][i].invincible = true; //FIXED

            generateParticle(gp.iTile[gp.currentMap][i], gp.iTile[gp.currentMap][i]); //FIXED
            if(gp.iTile[gp.currentMap][i].life == 0){ //FIXED
                gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedForm(); //FIXED
            }
        }
    }

    public void damageProjectile(int i){

        if(i != 999){
            Entity projectile = gp.projectile[gp.currentMap][i];
            projectile.alive = false;
            generateParticle(projectile,projectile);
        }
    }

    public void checkLevelUp(){
        if(exp >= nextlevelExp){
            level++;
            nextlevelExp = nextlevelExp * 3;
            maxLife += 2;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();
            gp.playSE(8);
            gp.gameState = gp.dialougeState;
            gp.ui.currentDialogue = "you're level " + level + " now!\n"+"you feel stronger!";
        }
    }

    public void selectItem(){
        int itemIndex = gp.ui.getItemIndexBySlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);
        if(itemIndex < inventory.size()){
            Entity selectedItem = inventory.get(itemIndex);
            if(selectedItem.type == type_Sword || selectedItem.type == type_Axe){
                currentWeapon = selectedItem;
                attack = getAttack();
                getAttackImage();
            }
            if(selectedItem.type == type_Shield){
                currentShield = selectedItem;
                defense = getDefense();
            }
            if(selectedItem.type == type_Light){
                if(currentLight == selectedItem){
                    currentLight = null;
                }
                else{
                    currentLight = selectedItem;
                }
                lightUpdated = true;
            }
            if(selectedItem.type == type_Consumables){
                if (selectedItem.use(this) == true){
                    if(selectedItem.amount > 1){
                        selectedItem.amount--;
                    }else{
                        inventory.remove(itemIndex);
                    }
                }
            }
        }
    }

    public int searchItemInInventory(String itemName){
        int itemIndex = 999;
        for(int i = 0; i < inventory.size(); i++){
            if(inventory.get(i).name == itemName){
                itemIndex = i;
                break;
            }
        }
        return  itemIndex;
    }

    public boolean canObtainItem(Entity item){
        boolean canObtain = false;
        Entity newItem = gp.eGenerator.getObjects(item.name);// use for save and load
        // CHECK IF ITEM IS STACKABLE
        if(newItem.stackable == true){
            int index = searchItemInInventory(newItem.name);
            if(index != 999){
                inventory.get(index).amount++;
                canObtain = true;
            }
            else {// ITS A NEW ITEM, NEED TO CHECK VACANCY
                if(inventory.size() != maxInventorySize){
                    inventory.add(newItem);
                    canObtain = true;
                }
            }
        }else {
            //NOT STACKABLE SO CHECK VACANCY
            if(inventory.size() != maxInventorySize){
                inventory.add(newItem);
                canObtain = true;
            }
        }
        return canObtain;
    }

    public void draw(Graphics2D g2){
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;
        switch (diretion){
            case "up":
                if(attacking == false){
                    if(spriteNum == 1){image = up1;}
                    if(spriteNum == 2){image = up2;}
//                    break;
                }
                if(attacking == true){
                    tempScreenY = screenY - gp.tileSize;
                    if(spriteNum == 1){image = attackUp1;}
                    if(spriteNum == 2){image = attackUp2;}
//                    break;
                }
                if(guarding == true){
                    image = guardUp;
                }
                break;
            case "down":
                if(attacking == false){
                    if(spriteNum == 1) {image = down1;}
                    if(spriteNum == 2){image = down2;}
//                    break;
                }
                if(attacking == true){
                    if(spriteNum == 1){image = attackDown1;}
                    if(spriteNum == 2){image = attackDown2;}
//                    break;
                }
                if(guarding == true){
                    image = guardDown;
                }
                break;
            case "left":
                if(attacking == false){
                    if(spriteNum == 1) {image = left1;}
                    if(spriteNum == 2) {image = left2;}
//                    break;
                }
                if(attacking == true){
                    tempScreenX = screenX - gp.tileSize;
                    if(spriteNum == 1) {image = attackLeft1;}
                    if(spriteNum == 2) {image = attackLeft2;}
//                    break;
                }
                if(guarding == true){
                    image = guardLeft;
                }
                break;
            case "right":
                if(attacking == false){
                    if(spriteNum == 1) {image = right1;}
                    if(spriteNum == 2 ){image = right2;}
//                    break;
                }
                if(attacking == true){
                    if(spriteNum == 1){image = attackright1;}
                    if(spriteNum == 2){image = attackRight2;}
//                    break;
                }
                if(guarding == true){
                    image = guardRight;
                }
                break;
        }

        if(transparent == true){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }
        g2.drawImage(image, tempScreenX, tempScreenY, null);
//        g2.setColor(Color.red);
////        g2.drawRect(screenX + solidAreaDefaultX, screenY + solidAreaDefaultY, solidArea.width, solidArea.height);

        //Reset alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}

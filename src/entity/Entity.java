package entity;
import main.GamePanel;
import main.UtilityTools;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Entity {
    public GamePanel gp;

    //CHARACTER ATTRIBUTE
    public int worldX,worldY; //the coordinates compare to the world map
    public int speed; //npc, player, monster movement speed
    public int defaultSpeed;
    public int strength; //npc, player, monster movement strength
    public int dexterity; //npc, player, monster movement dexterrity
    public int attack; //npc, player, monster movement attack
    public int defense; //npc, player, monster movement defense
    public int exp; //npc, player current exp, monster esp drop
    public int level; //player level, can alse be used on npc, monster
    public int nextlevelExp; //player nex level exp
    public int coin; //player coins
    public int maxLife;//npc, player, monster max mana
    public int life; //npc, player, monster current life
    public int maxMana; //npc, player, monster max mana
    public int mana; //npc, player, monster current mana
    public Entity currentWeapon; //player current weapon
    public Entity currentShield; //player current shield
    public Entity currentLight;
    public Projectiles projectile; //declare a projectile object
    //PLAYER INVINCIBLE TIME
    public boolean invincible = false;
    public boolean transparent = false;
    public int spriteNum = 1;
    //making a part of the player become solid
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);//create an abstract rectangle
    public boolean collisionON = false;
    //Attack Area
    public Rectangle attackArea  = new Rectangle(0,0,0,0);
    //default solid area
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;


    //ITEM ATTRIBUTES
    public ArrayList<Entity> inventory = new ArrayList<>(); //a list that contain entity stands for player's current item
    public final int maxInventorySize = 20; //max items that player can have at a time
    public int attackValue; //the damage that current weapon deals
    public int defeseValue; //the defense value that current shield has
    public String description = ""; //the description for ech item
    public int useCost; //the use cost of an item, or projectile, such as fire-ball
    public int value; //the coins u have when you sell an item
    public int price; //the price when you buy an item
    public boolean stackable = false;
    public int amount = 1;
    public int lightRadius;


    //image
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;//npc,player,object,projectile moving image
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackright1, attackRight2,
    guardUp, guardDown, guardLeft, guardRight;//attack & guard image
    public String diretion = "down";//initial dirention

    //ATTACK
    public boolean attacking = false;

    //DIALOGUE STRING ARRAY
    String dialogues[] = new String[20];
    public int dialogueIndex = 0;

    //COUNTER
    public int actionLockCounter = 0;
    public int invincibleCounter = 0;
    public int shotAvailableCounter= 0;
    public int spriteCounter = 0;
    public int dyingCounter = 0;
    int knockBackCounter = 0;
    public int hpBarCounter = 0;

    //attacker
    public Entity attacker;



    //OBJECT OR IT CAN BE ANYTHING DERIVE FROM THIS CLASS
    public BufferedImage image1, image2, image3;
    public boolean collision = false;
    public String name;

    //ENTITY TYPE
    public int type; //0 is player, 1 is NPC, two is player
    public final  int type_player = 0;
    public final int type_NPC = 1;
    public final int type_Monster = 2;
    public final int type_Sword = 3;
    public final int type_Axe = 4;
    public final int type_Shield = 5;
    public final int type_Consumables = 6;
    public final int type_Pick_Up_Item = 7;
    public final int type_obstacle = 8;
    public final int type_Light = 9;
    //MONSTER STATE
    public boolean alive = true;
    public boolean dying = false;

    //KNOCKBACK COUNTER
    //KNOCKBACK POWER ON WEAPON
    public int knockBackPower = 0;

    //HEALTH BAR
    public boolean hpBarOn = false;

    //CHEST
    public Entity loot;
    public boolean opened = false;

    public boolean onPath = false;
    public boolean knockBack = false;
    public String knockBackDirection;
    public boolean guarding = false;

    public int attack_motion1_duration;
    public int attack_motion2_duration;

    public Entity(GamePanel gp){
        this.gp = gp;
    }
    public int getLeftX(){
        return worldX + solidArea.x;
    }
    public int getRightX(){
        return worldX + solidArea.x + solidArea.width;
    }
    public int getTopY(){
        return worldY + solidArea.y;
    }
    public int getBottomY(){
        return worldY + solidArea.y + solidArea.height;
    }
    public int getCol(){
        return (worldX + solidArea.x)/gp.tileSize;
    }
    public int getRow(){
        return (worldY + solidArea.y)/gp.tileSize;
    }
    public int getXDistance(Entity target){
        int xDistance = Math.abs(worldX - target.worldX);
        return xDistance;
    }
    public int getYDistance(Entity target){
        int yDistance = Math.abs(worldY - target.worldY);
        return yDistance;
    }
    public int getTileDistance(Entity target){
        int tileDistance = (getXDistance(target) + getYDistance(target)) / gp.tileSize;
        return tileDistance;
    }
    public int getGoalCol(Entity target){
        int goalCol = (target.worldX + target.solidArea.x) / gp.tileSize;
        return goalCol;
    }
    public int getGoalRow(Entity target){
        int goalRow = (target.worldY + target.solidArea.y) / gp.tileSize;
        return goalRow;
    }

    public void resetCounter(){
        actionLockCounter = 0;
        invincibleCounter = 0;
        shotAvailableCounter= 0;
        spriteCounter = 0;
        dyingCounter = 0;
        knockBackCounter = 0;
        hpBarCounter = 0;
    }
    public void setLoot(Entity loot){}
    public void setAction(){}
    public  void damageReaction(){}
    public void speak(){
        if(dialogues[dialogueIndex] == null){
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch (gp.player.diretion){
            case "up": diretion = "down";break;
            case "down": diretion = "up";break;
            case "left": diretion = "right";break;
            case "right": diretion = "left";break;
        }
    }
    public void interact(){

    }
    public void checkCollision(){

        collisionON = false;
        gp.cChecker.checkTile(this);//it can change the value of collisionON
        gp.cChecker.checkObject(this, false);//return object index if collision happens
        gp.cChecker.checkEntity(this, gp.npc);//return Entity index if collision happens
        gp.cChecker.checkEntity(this, gp.monster);//return Entity index if collision happens
        gp.cChecker.checkEntity(this, gp.iTile);//return Entity index if collision happens
        boolean contactPlayer = gp.cChecker.checkPlayer(this);//return the contact boolean(true/false)

        if(this.type == type_Monster && contactPlayer == true){
            damagePlayer(attack);
        }
    }
    public void update(){

        if(knockBack == true){

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
        else{
            setAction();//set npcs,monsters action
            checkCollision();//check collision of this
//        gp.cChecker.checkPlayer(this);

            if(collisionON == false){
                switch (diretion){
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }
            spriteCounter++;
            if (spriteCounter > 24) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }


        if(invincible == true){
            invincibleCounter++;
            if(invincibleCounter > 40){
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if(shotAvailableCounter < 30){
            shotAvailableCounter++;
        }
    }
    public void checkAttackOrNot(int rate, int straight, int horizontal){
        boolean targetInRange = false;
        int xDis = getXDistance(gp.player);
        int yDis = getYDistance(gp.player);

        switch (diretion){
            case "up":
                if(gp.player.worldY < worldY && yDis < straight && xDis < horizontal){
                    targetInRange = true;
                }
                break;
            case "down":
                if(gp.player.worldY > worldY && yDis < straight && xDis < horizontal){
                    targetInRange = true;
                }
                break;
            case "left":
                if(gp.player.worldX < worldX && xDis < straight && yDis < horizontal){
                    targetInRange = true;
                }
                break;
            case "right":
                if(gp.player.worldX > worldX && xDis < straight && yDis < horizontal){
                    targetInRange = true;
                }
                break;
        }
        if(targetInRange == true){
            //check if it initiates an attack
            int i = new Random().nextInt(rate);
            if(i == 0){
                attacking = true;
                spriteNum = 1;
                spriteCounter = 0;
                shotAvailableCounter = 0;
            }
        }
    }
    public void checkDrop(){

    }
    public void dropItem(Entity droppedItem){
        for(int i = 0; i < gp.obj[1].length; i++){
            if(gp.obj[gp.currentMap][i] == null){
                gp.obj[gp.currentMap][i] = droppedItem;
                gp.obj[gp.currentMap][i].worldX = worldX;//the death monster worldX
                gp.obj[gp.currentMap][i].worldY = worldY;//the death monster worldY
                break;
            }
        }
    }
    public Color getParticleColor(){
        Color color = null;
        return color;
    }
    public int getParticleSize(){
        int size = 0;// 6 pixel
        return size;
    }
    public int getParticleSpeed(){
        int speed = 0;
        return speed;
    }
    public int getParticleMaxLife(){
        int maxLife = 0;
        return maxLife;
    }
    public void generateParticle(Entity generator, Entity target){
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();

        Particle p1 = new Particle(gp, target, color, size, speed, maxLife, -2, -1);
        Particle p2 = new Particle(gp, target, color, size, speed, maxLife, 2, -1);
        Particle p3 = new Particle(gp, target, color, size, speed, maxLife, -2, 1);
        Particle p4 = new Particle(gp, target, color, size, speed, maxLife, 2, 1);
        gp.particleList.add(p1);
        gp.particleList.add(p2);
        gp.particleList.add(p3);
        gp.particleList.add(p4);

    }
    public void checkShootOrNot(int rate, int shotInterval){
        int i = new Random().nextInt(rate) ;
        if(i == 0 && projectile.alive == false && shotAvailableCounter == shotInterval){
            projectile.set(worldX, worldY, diretion, true, this);
            //CHECK VACANCY
                for(int ii = 0; ii < gp.projectile[1].length; ii++){
                    if(gp.projectile[gp.currentMap][ii] == null){
                        gp.projectile[gp.currentMap][ii] = projectile;
                        break;
                    }
                }
            shotAvailableCounter = 0;
        }
    }
    public void checkStartChasingOrNot(Entity target, int distance, int rate){
        if(getTileDistance(target) < distance){
            int i = new Random().nextInt(rate);
            if(i == 0){
                onPath = true;
            }
        }
    }
    public void checkStopChasingOrNot(Entity target, int distance, int rate){
        if(getTileDistance(target) > distance){
            int i = new Random().nextInt(rate);
            if(i == 0){
                onPath = false;
            }
        }
    }
    public void getRandomDirection(){
        actionLockCounter++;
        if(actionLockCounter == 120){
            Random random = new Random();
            int i = random.nextInt(100) + 1;//pick up number from 1 - 100
            if(i <= 25){diretion = "up";}
            if(i > 25 && i <= 50){diretion = "down";}
            if(i > 50 && i <=75){diretion = "left";}
            if(i > 75){diretion = "right";}
            actionLockCounter = 0;
        }
    }
    public void attacking(){
        spriteCounter++;
        if(spriteCounter <= attack_motion1_duration){
            spriteNum = 1;
        }
        if(spriteCounter > attack_motion1_duration && spriteCounter <= attack_motion2_duration){
            spriteNum = 2;

            //save the current WorldX or WorldY, solid area
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            //Adjust player's worldX and worldy for the attack area
            switch (diretion){
                case "up": worldY -= attackArea.height; break;
                case "down": worldY += attackArea.height; break;
                case "left": worldX -= attackArea.width; break;
                case "right": worldX += attackArea.width; break;
            }
            //attack area becomes solid area
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            if(type == type_Monster){
                if(gp.cChecker.checkPlayer(this) == true){
                    damagePlayer(attack);
                }
            }
            else{ //player
                //check monster collision with th update WorldX, WorldY and solidArea
                int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
                gp.player.damageMonster(monsterIndex, this, attack, currentWeapon.knockBackPower);

                int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
                gp.player.damageInteractiveTile(iTileIndex);

                int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
                gp.player.damageProjectile(projectileIndex);
            }


            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

        }

        if(spriteCounter > attack_motion2_duration){
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }
    public String getOppositeDirection(String diretion){
        String oppositeDirection = "";
        switch (diretion){
            case "up": oppositeDirection = "down";break;
            case "down": oppositeDirection = "up";break;
            case "left": oppositeDirection = "right";break;
            case "right": oppositeDirection = "left";break;
        }
        return oppositeDirection;
    }
    public void damagePlayer(int attack){

        if(gp.player.invincible == false){
            //check the value of damage
            int damage = attack - gp.player.defense;
            //get an opposite direction of this attacker
            String canGuardDirection = getOppositeDirection(diretion);
            if(gp.player.guarding == true && gp.player.diretion == canGuardDirection){
                damage = 0;
                gp.playSE(14);
            }
            else{
                //not guarding
                //we can give damage
                gp.playSE(6);

                if(damage < 1){
                    damage = 1;
                }
            }
            if(damage != 0){
                gp.player.transparent = true;

            }
            setKnockBack(gp.player, this, this.knockBackPower);
            gp.player.life -= damage;
            gp.player.invincible = true;
        }
    }
    public void setKnockBack(Entity target, Entity attacker, int knockBackPower){
        this.attacker= attacker;
        target.knockBackDirection = attacker.diretion;
        target.speed += knockBackPower;
        target.knockBack = true;
    }
    public void draw(Graphics2D g2){
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + gp.tileSize> gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize> gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize< gp.player.worldY + gp.player.screenY  ) {

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
                    break;
            }

            //MONSTER HP
            if(type == 2 && hpBarOn == true){
                double oneScale = (double)gp.tileSize/maxLife;
                double hpBarValue = oneScale*life;
                g2.setColor(new Color(35, 35, 35));
                g2.fillRect(screenX - 2, screenY - 17, gp.tileSize + 4, 14);
                g2.setColor(new Color(255, 0, 30));
                g2.fillRect(screenX, screenY - 15, (int)hpBarValue, 10);

                hpBarCounter++;

                if(hpBarCounter == 600){
                    hpBarCounter = 0;
                    hpBarOn = false;
                }

            }

            if(invincible == true){
                hpBarOn = true;
                hpBarCounter = 0;
                changeAlpha(g2, 0.4F);
            }
            if(dying == true){
                dyingAnimation(g2);
            }
            g2.drawImage(image, tempScreenX, tempScreenY,null);
            changeAlpha(g2, 1F);
        }
    }
    public void dyingAnimation(Graphics2D g2){
        dyingCounter++;
        int i = 5;
        if(dyingCounter <= 5){changeAlpha(g2,0f);}
        if(dyingCounter > i && dyingCounter <= i * 2){changeAlpha(g2,1f);}
        if(dyingCounter > i * 2 && dyingCounter <= i * 3){changeAlpha(g2,0f);}
        if(dyingCounter > i * 3 && dyingCounter <= i * 4){changeAlpha(g2,1f);}
        if(dyingCounter > i * 4 && dyingCounter <= i * 5){changeAlpha(g2,0f);}
        if(dyingCounter > i * 5 && dyingCounter <= i * 6){changeAlpha(g2,1f);}
        if(dyingCounter > i * 6 && dyingCounter <= i * 7){changeAlpha(g2,0f);}
        if(dyingCounter > i * 7 && dyingCounter <= i * 8){changeAlpha(g2,1f);}
        if(dyingCounter > i * 8){
            alive = false;
        }
    }
    public void changeAlpha(Graphics2D g2, float alphaValue){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }
    public BufferedImage setUp(String imagePath, int width, int height){
        UtilityTools uTool = new UtilityTools();
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath+".png"));
            image = uTool.scaledImage(image, width, height);
            return  image;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public boolean use(Entity entity){return false;}
    public void searchPath(int goalCol, int goalRow){
        int startCol = (worldX + solidArea.x)/gp.tileSize;
        int startRow = (worldY + solidArea.y)/gp.tileSize;

        gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);

        if(gp.pFinder.search() == true){
            //Next worldX & worldY
            int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;

            //Entity's solidArea coordinates
            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;

            if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize){
                diretion = "up";
            }
            else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize){
                diretion = "down";
            }
            else if(enTopY >= nextY && enBottomY < nextY + gp.tileSize){
                //left or right
                if(enLeftX > nextX){
                    diretion = "left";
                }
                if(enLeftX < nextX){
                    diretion = "right";
                }
            }
            else if(enTopY >  nextY && enLeftX > nextX){
                //up or left
                diretion = "up";
                checkCollision();
                if(collisionON == true){
                    diretion = "left";
                }
            }
            else if(enTopY > nextY && enLeftX < nextX){
                //up or right
                diretion = "up";
                checkCollision();
                if(collisionON == true){
                    diretion = "right";
                }
            }
            else if(enTopY < nextY && enLeftX > nextX){
                //down or left
                diretion = "down";
                checkCollision();
                if(collisionON == true){
                    diretion = "left";
                }
            }
            else if(enTopY < nextY && enLeftX < nextX){
                //down or right
                diretion = "down";
                checkCollision();
                if(collisionON == true){
                    diretion = "right";
                }
            }

//            int nextCol = gp.pFinder.pathList.get(0).col;
//            int nextRow = gp.pFinder.pathList.get(0).row;
//            if(nextCol == goalCol && nextRow == goalRow){
//                onPath = false;//stop moving when touched the goal
//            }
        }
    }
    //interact with the door
    public int getDetected(Entity user, Entity target[][], String targetName){
        int index = 999;
        // check the surrounding object
        int nextWorldX = user.getLeftX();
        int nextWorldY = user.getTopY();

        switch (user.diretion){
            case "up": nextWorldY = user.getTopY() - gp.player.speed; break;
            case "down": nextWorldY = user.getBottomY() + gp.player.speed; break;
            case "left": nextWorldX = user.getLeftX() - gp.player.speed; break;
            case "right": nextWorldX = user.getRightX() + gp.player.speed; break;
        }
        int col = nextWorldX/gp.tileSize;
        int row = nextWorldY/gp.tileSize;

        for(int i = 0; i < target[1].length; i++){
            if(target[gp.currentMap][i] != null){
                if(target[gp.currentMap][i].getCol() == col &&
                        target[gp.currentMap][i].getRow() == row &&
                        target[gp.currentMap][i].name.equals(targetName)){
                    index = i;
                    break;
                }
            }
        }
        return index;
    }
}

package main;

import entity.Entity;
import entity.EventRect;

public class EventHandler {
    GamePanel gp;
    EventRect eventRect[][][];
    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    int tempMap, tempCol, tempRow;

    public EventHandler(GamePanel gp) {
        this.gp = gp;
        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
        int map = 0, col = 0, row = 0;
        while (map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow) {
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;
            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row++;

                if(row == gp.maxWorldRow){
                    row = 0;
                    map++;
                }
            }
        }
    }


    public void checkEvent(){
        //check if the player moved 1 tile away from the event tile
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        int col, row;
        if(distance >= gp.tileSize){
            canTouchEvent = true;
        }

        if(canTouchEvent == true) {
            if (hit(0, 27, 16, "right") == true) {damagePit(gp.dialougeState);}
            else if (hit(0, 23, 12, "up") == true) {healingPool(gp.dialougeState);}
            else if (hit(0, 10, 39, "any") == true) {teleport(1, 12, 13);}
            else if (hit(1, 12, 13, "any") == true) {teleport(0, 10, 39);}
            else if (hit(1, 12, 9, "up") == true) {speak(gp.npc[1][0]);}

//            col = 23;
//            row = 17;
//            if (hit(col, row, "any") == true) {
//                damagePit(col, row, gp.dialougeState);
//            }
//        if(hit(23,12, "up") == true){teleport(gp.dialougeState);}
        }
    }

    public boolean hit(int map, int col, int row, String reqDirection){
        boolean hit = false;
        if(map == gp.currentMap){
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
            eventRect[map][col][row].x = col * gp.tileSize + eventRect[map][col][row].x; //event world x
            eventRect[map][col][row].y = row * gp.tileSize + eventRect[map][col][row].y; //event world y

            if(gp.player.solidArea.intersects(eventRect[map][col][row]) /*&& eventRect[map][col][row].eventDone == false*/){
                if(gp.player.diretion.contentEquals(reqDirection) || reqDirection.contentEquals("any")){
                    hit = true;
                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;
                }
            }

            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }
        return hit;
    }

    public void damagePit(int gameState){
        gp.gameState = gameState;
        gp.ui.currentDialogue = "you fall into a pit!";
        gp.player.life -= 1;
        canTouchEvent = false;

    }

    public void healingPool(int gameState){
        if(gp.keyH.enterPressed == true){
            gp.gameState = gameState;
            gp.player.attackCancel = true;
            gp.ui.currentDialogue = "you drank the water \nyour life has been recovered!\n"
                    +"(the progress has been saved)";
            gp.player.life = gp.player.maxLife;
            gp.player.mana = gp.player.maxMana;
            canTouchEvent = false;
            gp.aSetter.setMonster();
            gp.saveLoad.save();
        }
    }

    public void teleport(int map, int col, int row){
        gp.gameState = gp.transitionState;
        tempMap = map;
        tempCol = col;
        tempRow = row;

//        gp.currentMap = map;
//        gp.player.worldX = gp.tileSize * col;
//        gp.player.worldY = gp.tileSize * row;
//        previousEventX = gp.player.worldX;
//        previousEventY = gp.player.worldY;
        canTouchEvent = false;
        gp.playSE(13);

    }

    public void speak(Entity entity){
        if(gp.keyH.enterPressed == true){
            gp.gameState = gp.dialougeState;
            gp.player.attackCancel = true;
            entity.speak();
        }
    }
}

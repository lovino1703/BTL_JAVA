package main;

import entity.Entity;
import object.OBJ_Bronze_Coin;
import object.OBJ_Chest;
import object.OBJ_Heart;
import object.OBJ_ManaCrytal;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font zeldaFont;
    public  boolean messageOn = false;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public boolean gameFinish = false;
    public String currentDialogue = "";
    public int commandNum = 0;
    public int titleScreenState = 0; //0: the first screen, 1: the second screen
    BufferedImage heart_Full, heart_Half, heart_Blank, crystal_Full, crystal_Blank, coin;
    public int playerSlotCol = 0;
    public int playerSlotRow = 0;
    public int npcSlotCol = 0;
    public int npcSlotRow = 0;
    int subState = 0;
    int counter = 0;
    public Entity npc;


    public UI(GamePanel gp){
        this.gp = gp;
        InputStream is = getClass().getResourceAsStream("/font/zelda.ttf");
        try {
            zeldaFont = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //CHARACTER HUD OBJECT
        Entity heart = new OBJ_Heart(gp);
        heart_Blank = heart.image3;
        heart_Half = heart.image2;
        heart_Full = heart.image1;
        Entity crystal = new OBJ_ManaCrytal(gp);
        crystal_Full = crystal.image1;
        crystal_Blank = crystal.image2;
        Entity bronze_coin = new OBJ_Bronze_Coin(gp);
        coin = bronze_coin.down1;
    }

    public void draw(Graphics2D g2){
        this.g2 = g2;//to use this g2 in other methods
        g2.setFont(zeldaFont);
        g2.setColor(Color.white);

        //TITLE STATE
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
        //PLAY STATE
        if(gp.gameState == gp.playSate){
            //do other stuff later
            drawPlayerLife();
            drawMessage();
        }
        //PAUSE STATE
        if(gp.gameState == gp.pauseState){
            drawPlayerLife();
            drawPauseScreen();
        }
        //CHARACTER STATE
        if(gp.gameState == gp.characterState){
            drawCharacterScreen();
            drawInventory(gp.player, true);
        }
        //DIALOGUE STATE
        if(gp.gameState == gp.dialougeState){
            drawDialogueScreen();
        }
        //OPTION STATE
        if(gp.gameState == gp.optionState){
            drawOptionScreen();
        }
        //GAME OVER STATE
        if(gp.gameState == gp.gameOverState){
            drawGameOverScreen();
        }
        //TRANSITION STATE
        if(gp.gameState == gp.transitionState){
            drawTransition();
        }
        //TRADE STATE
        if(gp.gameState == gp.tradeState){
            drawTradeScreen();
        }

    }

    public void drawPlayerLife(){
        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;
        //DRAW BLANK HEART
        while(i < gp.player.maxLife/2){
            g2.drawImage(heart_Blank, x, y, null);
            i++;
            x += gp.tileSize;
        }

        //RESET
        x = gp.tileSize/2;
        y = gp.tileSize/2;
        i = 0;

        //DRAW PLAYER CURRENT LIFE
        while(i < gp.player.life){
            g2.drawImage(heart_Half, x, y, null);
            i++;
            if(i < gp.player.life){
                g2.drawImage(heart_Full, x, y, null);
            }
            i++;
            x += gp.tileSize;
        }

        //DRAW MAX MANA
        x = (gp.tileSize/2)-5;
        y = (int)(gp.tileSize*1.5);
        i = 0;
        while(i < gp.player.maxMana){
            g2.drawImage(crystal_Blank, x, y, null);
            i++;
            x += 35;
        }

        //DRAW MANA
        x = (gp.tileSize/2)-5;
        y = (int)(gp.tileSize*1.5);
        i = 0;
        while( i < gp.player.mana){
            g2.drawImage(crystal_Full, x, y, null);
            i++;
            x += 35;
        }
    }
    //DRAW TITLE SCREEN
    public void drawTitleScreen(){
        if(titleScreenState == 0){
            String text;
            int x,y;
            gp.tsi.draw(g2);
            Color c = new Color(0, 0, 0);
            g2.setColor(c);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20F));
            text = "PLAY";
            x = centerTheTextX(text);
            y = gp.tileSize * 7 + gp.tileSize;
            g2.drawString(text, x + 3, y + 3);
            c = new Color(122, 47, 8);
            g2.setColor(c);
            g2.drawString(text, x, y);
            if(commandNum == 0){
                g2.drawString(">", x - gp.tileSize, y);
            }


            c = new Color(0, 0, 0);
            g2.setColor(c);
            text = "QUIT";
            x = centerTheTextX(text);
            y += gp.tileSize;
            g2.drawString(text, x + 3, y + 3);
            c = new Color(122, 47, 8);
            g2.setColor(c);
            g2.drawString(text, x, y);
            if(commandNum == 1){
                g2.drawString(">", x - gp.tileSize, y);
            }
        }else if(titleScreenState == 1) {
            String text;
            int x,y;
            gp.tsi.draw(g2);
            Color c = new Color(0, 0, 0);
            g2.setColor(c);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20F));
            text = "NEW GAME";
            x = centerTheTextX(text);
            y = gp.tileSize * 7 + gp.tileSize;
            g2.drawString(text, x + 3, y + 3);
            c = new Color(122, 47, 8);
            g2.setColor(c);
            g2.drawString(text, x, y);
            if(commandNum == 0){
                g2.drawString(">", x - gp.tileSize, y);
            }

            c = new Color(0, 0, 0);
            g2.setColor(c);
            text = "CONTINUE";
            x = centerTheTextX(text);
            y += gp.tileSize;
            g2.drawString(text, x + 3, y + 3);
            c = new Color(122, 47, 8);
            g2.setColor(c);
            g2.drawString(text, x, y);
            if(commandNum == 1){
                g2.drawString(">", x - gp.tileSize, y);
            }
        }
    }
    //DRAW GAME OVER SCREEN
    public void drawGameOverScreen(){

        g2.setColor(new Color(0,0,0,150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 90f));
        text = "GAME OVER";
        g2.setColor(Color.black);//shadow
        x = centerTheTextX(text);
        y = gp.tileSize * 4;
        g2.drawString(text, x, y);
        g2.setColor(Color.white);
        g2.drawString(text, x - 4, y - 4);


        //RETRY
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32f));
        text = "Retry";
        x = centerTheTextX(text);
        y += gp.tileSize*3;
        g2.setColor(Color.black);
        g2.drawString(text, x, y);
        g2.setColor(Color.white);
        g2.drawString(text, x - 4, y - 4);
        if(commandNum == 0){
            g2.drawString(">", x - 40, y);
        }
        //BACK TO TITLE SCREEN
        text = "Back to Title";
        x=centerTheTextX(text);
        y += 90;
        g2.setColor(Color.black);
        g2.drawString(text, x, y);
        g2.setColor(Color.white);
        g2.drawString(text, x - 4, y - 4);
        if(commandNum == 1){
            g2.drawString(">", x - 40, y);
        }
    }

    //DRAW DIALOGUE
    public  void drawDialogueScreen(){
        int x = gp.tileSize * 3;
        int y = gp.tileSize /2 ;
        int width = gp.screenWidth - (gp.tileSize * 6);
        int height = gp.tileSize * 4;
        drawSubWindow(x, y, width, height);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 15F));
         x += gp.tileSize;
         y += gp.tileSize;
         for(String line : currentDialogue.split("\n")){
             g2.drawString(line, x, y);
             y += 40;
         }
    }

    public void drawSubWindow(int x, int y, int width, int height){
        Color c = new Color(0,0,0,200);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);
        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    public void drawCharacterScreen(){
        //CREATE A FRAME
        final int frameX = gp.tileSize * 2;
        final  int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize * 5;
        final int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX,frameY,frameWidth,frameHeight);

        //TEXT
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(13F));

        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 32;

        //NAMES
        g2.drawString("Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Life", textX, textY);
        textY += lineHeight;
        g2.drawString("Mana", textX, textY);
        textY += lineHeight;
        g2.drawString("Strength", textX, textY);
        textY += lineHeight;
        g2.drawString("Dexterity", textX, textY);
        textY += lineHeight;
        g2.drawString("Attack", textX, textY);
        textY += lineHeight;
        g2.drawString("Defense", textX, textY);
        textY += lineHeight;
        g2.drawString("EXP", textX, textY);
        textY += lineHeight;
        g2.drawString("Next level", textX, textY);
        textY += lineHeight;
        g2.drawString("Coin", textX, textY);
        textY += lineHeight + 10;
        g2.drawString("Weapon", textX, textY);
        textY += lineHeight + 20;
        g2.drawString("Shield", textX, textY);
        textY += lineHeight;

        //VALUES
        int tailX = (frameX + frameWidth) - 30;
        //reset textY
        textY = frameY + gp.tileSize;
        String value;

        value = String.valueOf(gp.player.level);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.mana + "/" + gp.player.maxMana);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.strength);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.dexterity);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.attack);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.defense);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.exp);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.nextlevelExp);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.coin);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY - 24, null);
        textY += gp.tileSize;
        g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY - 24, null);

    }

    public void drawInventory(Entity entity, boolean cursor){

        int frameX = 0;
        int frameY = 0;
        int frameWidth = 0;
        int frameHeight = 0;
        int slotCol = 0;
        int slotRow = 0;

        if(entity == gp.player){
            frameX = gp.tileSize * 12;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize * 6;
            frameHeight = gp.tileSize * 5;
            slotCol = playerSlotCol;
            slotRow = playerSlotRow;
        }
        else{
            frameX = gp.tileSize * 2;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize * 6;
            frameHeight = gp.tileSize * 5;
            slotCol = npcSlotCol;
            slotRow = npcSlotRow;
        }
        //FRAME
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //SLOT
        final int slotXStart = frameX + 20;
        final int slotYStart = frameY + 20;
        int slotX = slotXStart;
        int slotY = slotYStart;
        int slotSize = gp.tileSize + 3;

        //DRAW PLAYER'S ITEM
        for(int i = 0; i < entity.inventory.size(); i++){
            //DRAW EQUIPPED CURSOR
            if(entity.inventory.get(i) == entity.currentWeapon ||
                    entity.inventory.get(i) == entity.currentShield ||
                    entity.inventory.get(i) == entity.currentLight){
                g2.setColor(new Color(240, 190, 90));
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 15, 15);
            }
            g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);

            //DISPLAY THE AMOUNT
            if(entity.inventory.get(i).amount > 1){
                g2.setFont(g2.getFont().deriveFont(12F));
                int amountX, amountY;
                String s = ""+entity.inventory.get(i).amount;
                amountX = getXForAlignToRightText(s, slotX + 44);
                amountY = slotY + gp.tileSize;
                //SHADOW
                g2.setColor(new Color(60, 60, 60));
                g2.drawString(s, amountX, amountY);
                //NUMBER
                g2.setColor(Color.white);
                g2.drawString(s, amountX - 3, amountY - 3);
            }

            slotX += slotSize;
            if(i == 4 || i == 9 || i == 14){
                slotX = slotXStart;
                slotY += slotSize;
            }
        }

        //CURSOR
        if(cursor == true){
            int cursorX = slotXStart + (slotSize * slotCol);
            int cursorY = slotYStart + (slotSize * slotRow);
            int cursorWidth = gp.tileSize;
            int cursorHeight = gp.tileSize;

            //DRAW CURSOR
            g2.setColor(Color.white);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

            //DESCRIPTION FRAME
            int dFrameX = frameX;
            int dFrameY = frameY + frameHeight;
            int dFrameWidth = frameWidth;
            int dFrameHeight = gp.tileSize * 3;

            //DRAW DESCRIPTION TEXT
            int textX = dFrameX + 20;
            int textY = dFrameY + gp.tileSize;
            g2.setFont(g2.getFont().deriveFont(12F));
            int itemIndex = getItemIndexBySlot(slotCol, slotRow);
            if(itemIndex < entity.inventory.size()){
                drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
                for(String line : entity.inventory.get(itemIndex).description.split("\n")){
                    g2.drawString(line, textX, textY);
                    textY += 32;
                }
            }
        }
    }

    public void drawOptionScreen(){
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(17F));

        int frameX = gp.tileSize * 6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 8;
        int frameheight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameheight);

        switch (subState){
            case 0: option_top(frameX, frameY);break;
            case 1: options_fullScreenNotification(frameX, frameY);break;
            case 2: options_control(frameX, frameY);break;
            case 3: options_endGameConfirmation(frameX, frameY);break;
        }

        gp.keyH.enterPressed = false;
    }

    public void option_top(int frameX, int frameY){
        int textX;
        int textY;

        //TITLE
        String text = "Options";
        textX = centerTheTextX(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);


        g2.setFont(g2.getFont().deriveFont(15F));
        //FULL SCREEN ON / OFF
        textX = frameX + gp.tileSize;
        textY += gp.tileSize * 2;
        g2.drawString("Full Screen", textX, textY);
        if(commandNum == 0){
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed == true){
                if(gp.fullScreenOn == false){
                    gp.fullScreenOn = true;
                }
                else if(gp.fullScreenOn == true){
                    gp.fullScreenOn = false;
                }
                subState = 1;
            }
        }


        //MUSIC
        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString("Music", textX, textY);
        if(commandNum == 1){
            g2.drawString(">", textX - 25, textY);
        }

        //SE
        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString("SE", textX, textY);
        if(commandNum == 2){
            g2.drawString(">", textX - 25, textY);
        }

        //CONTROL
        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString("Control", textX, textY);
        if(commandNum == 3){
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed == true){
                subState = 2;
                commandNum = 0;
            }
        }

        //END GAME
        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString("End Game", textX, textY);
        if(commandNum == 4){
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed == true){
                subState = 3;
                commandNum = 0;
            }
        }

        //BACK
        textX = frameX + gp.tileSize;
        textY += gp.tileSize * 2;
        g2.drawString("Back", textX, textY);
        if(commandNum == 5){
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed == true){
                gp.gameState = gp.playSate;
                commandNum = 0;
            }
        }

        //FULL SCREEN CHECK BOX
        textX = frameX + (int)(gp.tileSize * 5);
        textY = frameY + gp.tileSize * 2 + 24;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY,24, 24);
        if(gp.fullScreenOn == true){
            g2.fillRect(textX, textY, 24, 24);
        }

        //MUSIC VOLUME
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24); // 120 / 5 = 24
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        //SE VOLUME
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24);
        volumeWidth = 24 * gp.se.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        gp.config.saveConfig();
    }

    public void options_fullScreenNotification(int frameX, int frameY){
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 3;
        g2.setFont(g2.getFont().deriveFont(15F));
        currentDialogue = "The change will \ntake effect after \nrestarting the game";
         for(String line : currentDialogue.split("\n")){
             g2.drawString(line,textX, textY);
             textY+= 40;
         }

         //BACK
        textY = frameY + gp.tileSize * 9;
         g2.drawString("Back", textX, textY);
         if(commandNum == 0){
             g2.drawString(">", textX - 25, textY);
             if(gp.keyH.enterPressed == true){
                 subState = 0;
             }
         }
    }

    public void options_control(int frameX, int frameY){
        int textX;
        int textY;

        //TITLE
        String text = "Control";
        textX = centerTheTextX(text);
        textY = frameY + gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(15F));
        g2.drawString(text, textX, textY);

        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString("Move",textX, textY); textY+= gp.tileSize;
        g2.drawString("Confirm/Attack",textX, textY); textY+= gp.tileSize;
        g2.drawString("Shoot/Cast",textX, textY); textY+= gp.tileSize;
        g2.drawString("Charater Screen",textX, textY); textY+= gp.tileSize;
        g2.drawString("Pause",textX, textY); textY+= gp.tileSize;
        g2.drawString("Options",textX, textY); textY+= gp.tileSize;

        textX = frameX + gp.tileSize*6;
        textY = frameY + gp.tileSize*2;
        g2.drawString("WASD", textX, textY); textY+= gp.tileSize;
        g2.drawString("ENTER", textX, textY); textY+= gp.tileSize;
        g2.drawString("F", textX, textY); textY+= gp.tileSize;
        g2.drawString("C", textX, textY); textY+= gp.tileSize;
        g2.drawString("P", textX, textY); textY+= gp.tileSize;
        g2.drawString("ESC", textX, textY); textY+= gp.tileSize;


        //BACK
        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize*9;
        g2.drawString("Back", textX, textY);
        if(commandNum == 0){
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed == true){
                subState = 0;
                commandNum = 3;
            }
        }



    }

    public void options_endGameConfirmation(int frameX, int frameY){
         int textX = frameX + gp.tileSize;
         int textY = frameY + gp.tileSize;

         currentDialogue = "Quit the game \nand return to \n title screen?";

         for(String line: currentDialogue.split("\n")){
             g2.drawString(line, textX, textY);
             textY+= 40;
         }

         //YES
        String text = "YES";
         textX = centerTheTextX(text);
         textY += gp.tileSize * 3;
         g2.drawString(text, textX, textY);
         if(commandNum == 0){
             g2.drawString(">", textX - 25, textY);
             if(gp.keyH.enterPressed == true){
                 subState = 0;
                 gp.gameState = gp.titleState;
                 gp.resetGame(true);
             }
         }

         //NO
        text = "NO";
        textX = centerTheTextX(text);
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if(commandNum == 1){
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed == true){
                subState = 0;
                commandNum = 4;
            }
        }

    }

    public void drawTransition(){
        counter++;
        g2.setColor(new Color(0, 0, 0 , counter * 5));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        if(counter == 50){
            counter = 0;
            gp.gameState = gp.playSate;
            gp.currentMap = gp.eHandler.tempMap;
            gp.player.worldX = gp.tileSize * gp.eHandler.tempCol;
            gp.player.worldY = gp.tileSize * gp.eHandler.tempRow;
            gp.eHandler.previousEventX = gp.player.worldX;
            gp.eHandler.previousEventY = gp.player.worldY;

        }
    }

    public void drawTradeScreen(){
        switch (subState) {
            case 0: trade_select(); break;
            case 1: trade_buy(); break;
            case 2: trade_sell(); break;
        }
        gp.keyH.enterPressed = false;
    }

    public void trade_select(){

        drawDialogueScreen();

        //DRAW WINDOW
        int x = gp.tileSize * 15;
        int y = gp.tileSize * 4;
        int width = gp.tileSize * 3;
        int height = (int)(gp.tileSize * 3.5);
        drawSubWindow(x, y, width, height);

        //DRAW ITEMS
        x += gp.tileSize;
        y += gp.tileSize;
        g2.drawString("Buy", x, y);
        if(commandNum == 0){
            g2.drawString(">", x - 24, y);
            if(gp.keyH.enterPressed == true){
                subState = 1;
            }
        }
        y += gp.tileSize;


        g2.drawString("Sell", x, y);
        if(commandNum == 1){
            g2.drawString(">", x - 24, y);
            if(gp.keyH.enterPressed == true){
                subState = 2;
            }
        }
        y += gp.tileSize;

        g2.drawString("Leave", x, y);
        if(commandNum == 2){
            g2.drawString(">", x - 24, y);
            if(gp.keyH.enterPressed == true){
                commandNum = 0;
                gp.gameState = gp.dialougeState;
                currentDialogue = "come back anytime, he he!";
            }
        }
        y += gp.tileSize;

    }

    public void trade_buy(){

        //DRAW PLAYER INVENTORY
        drawInventory(gp.player, false);
        //DRAW NPC INVENTORY
        drawInventory(npc, true);

        //DRAW HINT WINDOW
        int x = gp.tileSize * 2;
        int y = gp.tileSize * 9;
        int width = gp.tileSize * 6;
        int height = gp.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("[ESC] Back", x + 24, y + 60);

        //DRAW PLAYER COIN WINDOW
        x = gp.tileSize * 12;
        y = gp.tileSize * 9;
        width = gp.tileSize * 6;
        height = gp.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("Your Coin: " + gp.player.coin, x + 24, y + 60);

        //DRAW PRICE WINDOW
        int itemIndex = getItemIndexBySlot(npcSlotCol, npcSlotRow);
        if(itemIndex < npc.inventory.size()){
            x = (int)(gp.tileSize*5.5);
            y = (int)(gp.tileSize*5.5);
            width = (int)(gp.tileSize*2.5);
            height = gp.tileSize;
            drawSubWindow(x, y, width, height);
            g2.drawImage(coin, x + 10, y + 8, 32, 32, null);

            int price = npc.inventory.get(itemIndex).price;
            String text = "" + price;
            x = getXForAlignToRightText(text, gp.tileSize*8 - 20);
            g2.drawString(text, x, y + 34);

            //BUY ITEM
            if(gp.keyH.enterPressed == true){
                if(npc.inventory.get(itemIndex).price > gp.player.coin){
                    subState = 0;
                    gp.gameState = gp.dialougeState;
                    currentDialogue = "Oops, you need more coins for this.";
                    drawDialogueScreen();
                }
                else{
                    if(gp.player.canObtainItem(npc.inventory.get(itemIndex)) == true){
                        gp.player.coin -= npc.inventory.get(itemIndex).price;
                    }
                    else{
                        subState = 0;
                        gp.gameState = gp.dialougeState;
                        currentDialogue = "No more available slot!";
                    }
                }
//                else if(gp.player.inventory.size() == gp.player.maxInventorySize){
//                    subState = 0;
//                    gp.gameState = gp.dialougeState;
//                    currentDialogue = "No more available slot!";
//                }
//                else{
//                    gp.player.coin -= npc.inventory.get(itemIndex).price;
//                    gp.player.inventory.add(npc.inventory.get(itemIndex));
//                }
            }
        }
    }

    public void trade_sell(){

        //DRAW PLAYER INVENTORY
        drawInventory(gp.player, true);
         int x;
         int y;
         int width;
         int height;
        //DRAW DESCRIPTION WINDOW
        x = gp.tileSize * 2;
        y = gp.tileSize * 9;
        width = gp.tileSize * 6;
        height = gp.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("[ESC] Back", x + 24, y + 60);

        //DRAW PLAYER COIN WINDOW
        x = gp.tileSize * 12;
        y = gp.tileSize * 9;
        width = gp.tileSize * 6;
        height = gp.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("Your Coin: " + gp.player.coin, x + 24, y + 60);

        //DRAW PRICE WINDOW
        int itemIndex = getItemIndexBySlot(playerSlotCol, playerSlotRow);
        if(itemIndex < gp.player.inventory.size()){
            x = (int)(gp.tileSize*15.5);
            y = (int)(gp.tileSize*5.5);
            width = (int)(gp.tileSize*2.5);
            height = gp.tileSize;
            drawSubWindow(x, y, width, height);
            g2.drawImage(coin, x + 10, y + 8, 32, 32, null);

            int price = gp.player.inventory.get(itemIndex).price-20;
            String text = "" + price;
            x = getXForAlignToRightText(text, gp.tileSize*18 - 20);
            g2.drawString(text, x, y + 34);

            //SELL ITEM
            if(gp.keyH.enterPressed == true){
                if(gp.player.inventory.get(itemIndex) == gp.player.currentWeapon ||
                        gp.player.inventory.get(itemIndex) == gp.player.currentShield){
                    commandNum = 0;
                    subState = 0;
                    gp.gameState = gp.dialougeState;
                    currentDialogue = "can't sell equipped items!";
                }else{
                    if(gp.player.inventory.get(itemIndex).amount > 1){
                        gp.player.inventory.get(itemIndex).amount--;
                    }
                    else{
                        gp.player.inventory.remove(itemIndex);
                    }
                    gp.player.coin += price;
                }
            }
        }

    }

    public int getItemIndexBySlot(int slotCol, int slotRow){
        int itemIndex = slotCol + (slotRow * 5);
        return itemIndex;
    }

    public void drawMessage(){
        int messageX = gp.tileSize;
        int messageY = gp.tileSize * 4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 12F));
        for(int i = 0; i < message.size(); i++){
            if(message.get(i) != null){
                g2.setColor(Color.black);
                g2.drawString(message.get(i), messageX+2, messageY+2);//draw the shadow
                g2.setColor(Color.white);
                g2.drawString(message.get(i), messageX, messageY);//draw the message
                int counter = messageCounter.get(i) + 1;//làm cho counter của message đó tăng lên 1
                messageCounter.set(i, counter);
                messageY += 50;//di chuển xuống dòng dưới để vẽ tiếp

                if(messageCounter.get(i) > 180){
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }

    }

    public void drawPauseScreen(){
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80));
        String text = "PAUSE";
        int x = centerTheTextX(text);
        int y = gp.screenHeight/2;
        g2.drawString(text, x, y);
    }

    public int centerTheTextX(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }

    public int getXForAlignToRightText(String text, int tailX){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
        return x;
    }

    public void addMessage(String s){
        message.add(s);
        messageCounter.add(0);
    }

}

package data;

import entity.Entity;
import main.GamePanel;
import object.*;

import java.io.*;

public class SaveLoad {
    GamePanel gp;
    public SaveLoad(GamePanel gp){
        this.gp = gp;
    }



    public void save(){
        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));
            DataStorage ds = new DataStorage();
            //PLAYER STATS
            ds.level = gp.player.level;
            ds.maxLife = gp.player.maxLife;
            ds.life = gp.player.life;
            ds.maxMana = gp.player.maxMana;
            ds.mana = gp.player.mana;
            ds.strength = gp.player.strength;
            ds.dexterity = gp.player.dexterity;
            ds.exp = gp.player.exp;
            ds.nextLevelExp = gp.player.nextlevelExp;
            ds.coin = gp.player.coin;

            //PLAYER INVENTORY
            for(int i = 0; i < gp.player.inventory.size(); i++){
                ds.itemNames.add(gp.player.inventory.get(i).name);
                ds.itemQuantities.add(gp.player.inventory.get(i).amount);
            }
            //PLAYER EQUIPMENT
            ds.currentWeaponSlot = gp.player.getCurrentWeaponSlot();
            ds.currentShieldSlot = gp.player.getCurrentShieldSlot();

            //OBJECTS ON MAP
            ds.mapObjectNames = new String[gp.maxMap][gp.obj[1].length];
            ds.mapObjectWorldX = new int[gp.maxMap][gp.obj[1].length];
            ds.mapObjectWorldY = new int[gp.maxMap][gp.obj[1].length];
            ds.mapObjectLootNames = new String[gp.maxMap][gp.obj[1].length];
            ds.mapObjectOpened = new boolean[gp.maxMap][gp.obj[1].length];

            //loop through each object in the map and save into the ds's arrays
            for(int mapNum = 0; mapNum < gp.maxMap; mapNum++){
                for(int i = 0; i < gp.obj[1].length; i++){

                    if(gp.obj[mapNum][i] == null){
                        ds.mapObjectNames[mapNum][i] = "NA";
                    }
                    else{
                        ds.mapObjectNames[mapNum][i] = gp.obj[mapNum][i].name;//save name
                        ds.mapObjectWorldX[mapNum][i] = gp.obj[mapNum][i].worldX;//save coordinate x
                        ds.mapObjectWorldY[mapNum][i] = gp.obj[mapNum][i].worldY;//save coordinate y
                        if(gp.obj[mapNum][i].loot != null){
                            ds.mapObjectLootNames[mapNum][i] = gp.obj[mapNum][i].loot.name;
                        }
                        ds.mapObjectOpened[mapNum][i] = gp.obj[mapNum][i].opened;
                    }
                }
            }

            //write the DataStorage Object
            oos.writeObject(ds);

        }catch(Exception e){
            System.out.println("Save Exception!");
        }
    }


    public void load(){
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.dat")));

            //read the DataStorage Object
            DataStorage ds = (DataStorage)ois.readObject();

            gp.player.level = ds.level;
            gp.player.maxLife = ds.maxLife;
            gp.player.life = ds.life;
            gp.player.maxMana = ds.maxMana;
            gp.player.mana = ds.mana;
            gp.player.strength = ds.strength;
            gp.player.dexterity = ds.dexterity;
            gp.player.exp = ds.exp;
            gp.player.nextlevelExp = ds.nextLevelExp;
            gp.player.coin = ds.coin;

            //PLAYER INVENTORY
            gp.player.inventory.clear();
            for(int i = 0; i < ds.itemNames.size(); i++){
                gp.player.inventory.add(gp.eGenerator.getObjects(ds.itemNames.get(i)));
                gp.player.inventory.get(i).amount = ds.itemQuantities.get(i);
            }
            //PLAYER EQUIPMENT
            gp.player.currentWeapon = gp.player.inventory.get(ds.currentWeaponSlot);
            gp.player.currentShield = gp.player.inventory.get(ds.currentShieldSlot);
            gp.player.getAttack();
            gp.player.getDefense();
            gp.player.getAttackImage();

            //OBJECTS ON MAP
            for(int mapNum = 0; mapNum < gp.maxMap; mapNum++){
                for(int i = 0; i < gp.obj[1].length; i++){
                    if(ds.mapObjectNames[mapNum][i].equals("NA")){
                        gp.obj[mapNum][i] = null;
                    }else{
                        gp.obj[mapNum][i] = gp.eGenerator.getObjects(ds.mapObjectNames[mapNum][i]);//load object into the array in GamePanel
                        gp.obj[mapNum][i].worldX = ds.mapObjectWorldX[mapNum][i];//load object array (gp) worldX
                        gp.obj[mapNum][i].worldY = ds.mapObjectWorldY[mapNum][i];//load object array (gp) worldY
                        if(ds.mapObjectLootNames[mapNum][i] != null){
                            gp.obj[mapNum][i].loot = gp.eGenerator.getObjects(ds.mapObjectLootNames[mapNum][i]);//load the loot into the chest
                        }
                        gp.obj[mapNum][i].opened = ds.mapObjectOpened[mapNum][i];//load if the chest is opened
                        if(gp.obj[mapNum][i].opened == true){
                            gp.obj[mapNum][i].down1 = gp.obj[mapNum][i].image2;
                        }
                    }
                }
            }
        }catch(Exception e){
            System.out.println("Load Exception!");
        }
    }
}

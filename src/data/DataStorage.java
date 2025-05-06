package data;

import java.io.Serializable;
import java.util.ArrayList;

public class DataStorage implements Serializable {
    //PLAYER STATS
    int level;
    int maxLife;
    int life;
    int maxMana;
    int mana;
    int strength;
    int dexterity;
    int exp;
    int nextLevelExp;
    int coin;

    //PLAYER INVENTORY
    ArrayList<String> itemNames = new ArrayList<>();
    ArrayList<Integer> itemQuantities = new ArrayList<>();
    int currentWeaponSlot;
    int currentShieldSlot;

    //OBJECT ON MAP
    String mapObjectNames[][];//name of the object on the map
    int mapObjectWorldX[][];//object worldX
    int mapObjectWorldY[][];//object worldY
    String mapObjectLootNames[][];//the loot Object inside the chest (if it hasn't been opened)
    boolean mapObjectOpened[][];//check if the chest or door is opened

}

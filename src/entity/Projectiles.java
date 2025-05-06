package entity;

import main.GamePanel;

public class Projectiles extends  Entity{
    Entity user;
    public Projectiles(GamePanel gp) {
        super(gp);
    }
    public void set(int worldX, int worldY, String diretion, boolean alive, Entity user){
            this.worldX = worldX;
            this.worldY = worldY;
            this.diretion = diretion;
            this.alive = alive;
            this.user = user;
            this.life = this.maxLife;
    }

    public void update(){
        if(user == gp.player){
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            if(monsterIndex != 999){
                gp.player.damageMonster(monsterIndex, this, attack, knockBackPower);
                generateParticle(user.projectile, gp.monster[gp.currentMap][monsterIndex]);
                alive = false;
            }
        }
        if(user != gp.player){
            boolean contactPlayer = gp.cChecker.checkPlayer(this);
            if(gp.player.invincible == false && contactPlayer == true){
                damagePlayer(attack);
                generateParticle(user.projectile, user.projectile);
                alive = false;
            }
        }
        switch (diretion){
            case "up": worldY -= speed; break;
            case "down": worldY += speed; break;
            case "left": worldX -= speed; break;
            case "right": worldX += speed; break;
        }
        life--;
        if(life <= 0){
            alive = false;
        }
        spriteCounter++;
        if (spriteCounter > 12) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }
    public boolean haveResource(Entity user){
        boolean haveResource= false;
        return haveResource;
    }

    public void subtractResource(Entity user){
    }
}

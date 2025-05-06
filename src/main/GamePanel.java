package main;
import ai.PathFinder;
import data.SaveLoad;
import entity.Entity;
import entity.Player;
import environment.EnvironmentManager;
import screenImage.TitleScreenImage;
import tile_interactive.InteractiveTile;
import tiles.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements  Runnable {
    /*implement Runnable: ể có thể truyền được class vào trong constructor của thread
    implement Runnable để có thể truyền được class vào trong constructor của thread,
    Thread giúp chạy một vòng lặp game (game loop) riêng biệt để liên tục cập
    nhật trạng thái và vẽ lại màn hình — từ đó tạo nên chuyển động mượt mà cho nhân vật
     */

    //tile setting
    final int originalTileSize = 16;//16x16 tile, là một ô vuông nhỏ của hình ảnh, có kích thước cố định,
                                    //là đơn vị nhỏ nhất để xây dựng bản đồ game
    /* với tile 16x16 thì sẽ trở nên rát nhỏ đối với màn hình hiện tại ( resolution cao )
    * --> nên chúng ta phải scale
    * để làm cho nhân vật hay hình ảnh với tile 16x16 trỏ nên cân xứng hơn, không bị nhỏ quá*/
    final int scale  = 3;// makes 16x16 looks like 48x48
    public final int tileSize = originalTileSize * scale;//48x48 tile

    //monitor size
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int screenWidth = maxScreenCol * tileSize;//960 pixel
    public final int screenHeight = maxScreenRow * tileSize;//576 pixel

    //WORLD SETTING
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int maxMap = 10;
    public int currentMap = 0;

    //Key Handler
    public KeyHandler keyH = new KeyHandler(this);

    //CollisionChecker Instantiate
    public CollisionChecker cChecker = new CollisionChecker(this);

    //CHECK FULL SCREEN
    public boolean fullScreenOn = false;

    //FPS
    int FPS = 60;

    //ENVIRONMENT MANAGER - for lighting
    EnvironmentManager eManager = new EnvironmentManager(this);

    //SAVE AND LOAD
    public EntityGenerator eGenerator = new EntityGenerator(this);
    SaveLoad saveLoad = new SaveLoad(this);

    //FOR FULLSCREEN
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;

    //PLAYER
    public Player player = new Player(this, keyH);
    public TileManager tileM = new TileManager(this);

    //OBJECT & NPCs & MONSTER
    public Entity obj[][] = new Entity[maxMap][20];
    public Entity npc[][] = new Entity[maxMap][10];
    public Entity monster[][] = new Entity[maxMap][20];
    public InteractiveTile iTile[][] = new InteractiveTile[maxMap][50];
    public Entity projectile[][] = new Entity[maxMap][50];
    public ArrayList<Entity> particleList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>(); //put player, npcs, objects into this ArrayList then sort by their worldY


    //AssetSetter
    public AssetSetter aSetter = new AssetSetter(this);

    //SOUND
    Sound music = new Sound();
    Sound se = new Sound();

    //UI
    public UI ui = new UI(this);

    //GAMESTATE
    public int gameState;
    public int titleState = 0;
    public final int playSate = 1;
    public final int pauseState = 2;
    public final int dialougeState = 3;
    public final int characterState = 4;
    public final int optionState = 5;
    public final int gameOverState = 6;
    public final int transitionState = 7;
    public final int tradeState = 8;

    //DRAW GAMESTATE IMAGE
    TitleScreenImage tsi = new TitleScreenImage(this);

    //EVENT HANDLER
    public EventHandler eHandler = new EventHandler(this);

    //CONFIG
    Config config = new Config(this);

    //THREAD
    Thread gameThread;

    //PATH FINDER
    public PathFinder pFinder = new PathFinder(this);

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);//vẽ trước hình ảnh sau đó mới đẩy lên màn hình, giúp chuyển động mượt mà
        this.addKeyListener(keyH);//recognize key input
        setFocusable(true);//let the component listen to the key
//        setupGame();
    }

    public void setupGame(){
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTile();
//        eManager.setup();
        gameState = titleState;

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.createGraphics();// ve le temp screen truoc
        if(fullScreenOn == true){
            setFullScreen();
        }
    }


    //reset game
    public void resetGame(boolean restart){
        player.setDefaultPosition();
        player.reStoreStatus();
        player.resetCounter();
        aSetter.setNPC();
        aSetter.setMonster();

        if(restart == true){
            player.setDefaultValue();
            aSetter.setObject();
            aSetter.setInteractiveTile();
//            eManager.lighting.resetDay();
        }
    }

    public void setFullScreen(){
        //GET MONITOR ENVIRONMENT
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);
        //GET FULL SCREEN WIDTH AND HEIGHT
        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();//resolve => automaticly call the run method
    }

    //    @Override
    //    public void run(){
    //        double drawTimeFromStartToNextStart = 1000000000/FPS;
    //        double nextDrawTime = System.nanoTime() + drawTimeFromStartToNextStart;
    //        while(gameThread != null){
    //            /*
    //            there r 2 things u need to do in this loop:
    //                -update infomation such as character position
    //                -draw: draw the screen with the updated infomation
    //             */
    //
    //            //1: UPDATE
    //            update();
    //
    //            //paintComponent
    //            repaint();
    //            try{
    //                double remainingTime = nextDrawTime - System.nanoTime();
    //                remainingTime = remainingTime/1000000;
    //                if(remainingTime < 0){
    //                    remainingTime = 0;
    //                }
    //                Thread.sleep((long)remainingTime);
    //                nextDrawTime += drawTimeFromStartToNextStart;
    //            }catch(InterruptedException e){
    //                e.printStackTrace();
    //                System.out.println("theres a fvckin error!");
    //            }
    //        }
    //    }
    @Override
    public void run(){
    double drawTimeFromStartToNextStart = 1000000000/FPS;
    double delta = 0;
    long lastTime = System.nanoTime();
    long currentTime;
    while(gameThread != null){
        currentTime = System.nanoTime();
        delta += (currentTime - lastTime) / drawTimeFromStartToNextStart;
        lastTime = currentTime;
    //        System.out.println(currentTime);
        if(delta >= 1){
                        /*
            there r 2 things u need to do in this loop:
                -update infomation such as character position
                -draw: draw the screen with the updated infomation
             */

            //1: UPDATE
            update();

            //paintComponent
            drawToTempScreen();
            drawToScreen();
            delta--;
        }
    }
    }

    public void update(){

        if(gameState == playSate){
            //update npc location and direction
            player.update();
            for(int i = 0; i < npc[1].length; i++){
                if(npc[currentMap][i] != null){
                    npc[currentMap][i].update();
                }
            }

            //update monster location and direction
            for(int i = 0; i < monster[1].length; i++){
                if(monster[currentMap][i] != null){
                    if(monster[currentMap][i].alive == true && monster[currentMap][i].dying == false){
                        monster[currentMap][i].update();
                    }
                    if(monster[currentMap][i].alive == false){
                        monster[currentMap][i].checkDrop();
                        monster[currentMap][i] = null;
                    }
                }
            }

            //update projectile location and direction
            for(int i = 0; i < projectile[1].length; i++){
                if(projectile[currentMap][i] != null){
                    if(projectile[currentMap][i].alive == true){
                        projectile[currentMap][i].update();
                    }
                    if(projectile[currentMap][i].alive == false){
                        projectile[currentMap][i] = null;
                    }
                }
            }
            //update particle location and dỉrection
            for(int i = 0; i < particleList.size(); i++){
                if(particleList.get(i) != null){
                    if(particleList.get(i).alive == true){
                        particleList.get(i).update();
                    }
                    if(particleList.get(i).alive == false){
                        particleList.remove(i);
                    }
                }
            }
            for(int i = 0; i < iTile[1].length; i++){
                if(iTile[currentMap][i] != null){
                    iTile[currentMap][i].update();
                }
            }
//            eManager.update();
        }
        //kiểm tra nếu game state = pause state
//        if(gameState == pauseState){
//            //do nothing nothing can move
//        }
    }

    //vẽ ra tempScreen: sau dó sẽ scale cả screen lên thành full screen
    public void drawToTempScreen(){

        //DEBUG
        long drawStart = 0;
        if(keyH.showDebugText == true){
            drawStart = System.nanoTime();
        }
        //TITLE SCREEN
        if(gameState == titleState){
            ui.draw(g2);
        }
        //OTHERS
        else{
            //TILE
            tileM.draw(g2);//draw map

            //INTERACTIVE TILE
            for(int i = 0; i < iTile[1].length; i++){
                if(iTile[currentMap][i] != null){
                    iTile[currentMap][i].draw(g2);//draw interactive tiles
                }
            }

            //ADD ENTITIES INTO THE LIST
            entityList.add(player);//add the player into Entities list

            for(int i = 0; i < npc[1].length; i++){
                if(npc[currentMap][i] != null){
                    entityList.add(npc[currentMap][i]);//add all NPCS to the Entities list
                }
            }

            for(int i = 0; i < obj[1].length; i++){
                if(obj[currentMap][i] != null){
                    entityList.add(obj[currentMap][i]);//add all objects
                }
            }

            for(int i = 0; i < monster[1].length; i++){
                if(monster[currentMap][i] != null){
                    entityList.add(monster[currentMap][i]);//add all monster
                }
            }

            for(int i = 0; i < projectile[1].length; i++){
                if(projectile[currentMap][i] != null){
                    entityList.add(projectile[currentMap][i]);//add all projectiles
                }
            }

            for(int i = 0; i < particleList.size(); i++){
                if(particleList.get(i) != null){
                    entityList.add(particleList.get(i));//add all particle list
                }
            }

            //SORT
            Collections.sort(entityList, new Comparator<Entity>() {// sort the array by worldY
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });

            //DRAW ENTITIES
            for(int i = 0; i < entityList.size(); i++){
                entityList.get(i).draw(g2);//draw all the Entities list
            }

            //EMPTY ENTITIES LIST
            entityList.clear();//clear the list

            //ENVIRONMENT
//            eManager.draw(g2);//draw the lighting

            //UI
            ui.draw(g2);//draw the ui

        }
        if(keyH.showDebugText == true){
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;

            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            g2.setColor(Color.white);
            int x = 10, y = 400;
            int lineHeight = 20;

            g2.drawString("WorldX" + player.worldX, x, y); y+= lineHeight;
            g2.drawString("WorldY" + player.worldY, x, y); y+= lineHeight;
            g2.drawString("Col" + ((player.worldX + player.solidArea.x) / tileSize), x, y); y+= lineHeight;
            g2.drawString("Row" + ((player.worldY + player.solidArea.y) / tileSize), x, y); y+= lineHeight;
            g2.drawString("time: "+ passed , x, y);
        }
    }


    public void drawToScreen(){
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }

    public void playMusic(int i){
    music.setFIle(i);
    music.play();
    music.loop();
    }

    public void stopMusic(){
    music.stop();
    }

    public void playSE(int i){
    se.setFIle(i);
    se.play();
    }
}

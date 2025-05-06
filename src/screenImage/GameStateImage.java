package screenImage;

import main.GamePanel;
import main.UtilityTools;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GameStateImage {
    GamePanel gp;
    BufferedImage titleImage;
    public GameStateImage(GamePanel gp){
        this.gp = gp;
    }

    public BufferedImage setUp(String imagePath){
        UtilityTools uTool = new UtilityTools();
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath+".png"));
            image = uTool.scaledImage(image, gp.screenWidth, gp.screenHeight);
            return  image;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics2D g2){
        g2.drawImage(titleImage, 0, 0, null);
    }
}

package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UtilityTools {
    public BufferedImage scaledImage(BufferedImage origial, int width, int height){
        BufferedImage scaledImage = new BufferedImage(width, height, origial.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(origial, 0, 0, width, height, null);
        g2.dispose();

        return scaledImage;
    }
}

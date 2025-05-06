package screenImage;

import main.GamePanel;

import java.awt.*;

public class TitleScreenImage extends GameStateImage {
    public TitleScreenImage(GamePanel gp){
        super(gp);
        readTitleImage();
    }

    public void readTitleImage(){
        titleImage = setUp("/titleImage/NES - The Legend of Zelda - Title Screen");
    }


}

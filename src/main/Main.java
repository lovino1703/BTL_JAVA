package main;

import javax.swing.JFrame;

public class Main {
    public static JFrame window;
    public static void main(String[] args) {
        window  = new JFrame();//create a new instance of JFrame, a small window
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//exit using the "X" button
        window.setResizable(false);//cannot resizeable
        window.setTitle("1st game ever!");//title of the window

        GamePanel gamePanel = new GamePanel();//create a piece of canvas
        window.add(gamePanel);//put GamePanel into the window



        gamePanel.config.loadConfig();
        if(gamePanel.fullScreenOn == true){
            window.setUndecorated( true);
        }
        window.pack();//make the window fits to the panel
        window.setLocationRelativeTo(null);//centered display
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}
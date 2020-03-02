package Game;

import Graphics.GameScene;
import Tools.Constants;

import javax.swing.*;
import java.awt.*;

public class Game extends JFrame {

    public static int height = Constants.WINDOW_HEIGHT;
    public static int width = Constants.WINDOW_WIDTH;

    // Init the Game along with the window
    public Game(){
        setTitle(Constants.WINDOW_NAME);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(new GameScene(width, height));
        setResizable(true);
        pack();

        setLocationRelativeTo(null);
        setVisible(true);
    }


    // For Settings puroposes
    public void changesize(int width, int height) {
        setPreferredSize(new Dimension(width, height));
    }


}

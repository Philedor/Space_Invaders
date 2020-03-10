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

        // To call to start the game
        add(new GameScene(width, height, 2));
        setResizable(true);
        pack();

        setLocationRelativeTo(null);
        setVisible(true);
    }


}

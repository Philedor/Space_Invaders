package Game;

import Graphics.Scene;
import Tools.Constants;

import javax.swing.*;

public class Game extends JFrame {

    public static int height = Constants.WINDOW_HEIGHT;
    public static int width = Constants.WINDOW_WIDTH;

    // Init the Game along with the window
    public Game(){
        setTitle(Constants.WINDOW_NAME);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new Scene(width, height));
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Game Main Loop
    public static void Loop(){

    }

    // End the Game by killing all Processes and clearing the memory
    public static void Kill(){

    }


}

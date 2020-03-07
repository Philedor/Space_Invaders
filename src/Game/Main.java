package Game;

import Tools.Constants;

import java.awt.*;

public class Main {
    public static void main(String[] args){
        Run();
    }


    public static void Run(){
        EventQueue.invokeLater(() -> {
            Game game = new Game();
            game.setVisible(true);
        });
    }



}



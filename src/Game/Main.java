package Game;

import Tools.Constants;

import java.awt.*;

public class Main {
    public static void main(String[] args){
        Run();
    }


    public static void Run(){
        Gen_enemyblock(6, 6);
        EventQueue.invokeLater(() -> {
            Game game = new Game();
            game.setVisible(true);
        });
    }


    // Used to generate enemy coordinates and just copy paste them
    private static void Gen_enemyblock(int rows, int columns){
        int width = Constants.GAME_MAX_WIDTH * 3 / 4 - (Constants.GAME_MIN_WIDTH*2);
        int height = Constants.GAME_MIN_HEIGHT / 3 - Constants.GAME_MAX_HEIGHT;
        int minw = width + Constants.GAME_MIN_WIDTH * 2;
        int minh = Constants.GAME_MAX_HEIGHT;

        System.out.println("{ ");
        for (int i = 0 ; i < rows; i++){
            int y = height * i / rows + minh;
            for (int j = 0; j < columns; j++){
                int x = width * j /columns + minw;
                System.out.println("{" + x + ", " + y +"},");
            }
        }

        System.out.println("};");
    }

}



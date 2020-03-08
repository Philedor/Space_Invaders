package Tools;

import javax.print.DocFlavor;
import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.*;


public class Constants {


    // Window Settings
    public static final int WINDOW_HEIGHT =   1000;
    public static final int WINDOW_WIDTH =    700;
    public static final String WINDOW_NAME =  "Space Invaders";

    // Game Initialisation constants
    public static final int PLAYER_STARTING_X =         230;
    public static final int PLAYER_STARTING_Y =         600;
    public static final double PLAYER_STARTING_ANGLE =   90;     // In degrees

    public static final double ENEMY_STARTING_ANGLE =    -90;

    // Image importation infos
    //Player
    public static final String  PLAYER_SPRITE =                     "resources/sprites/player/player.png";
    public static final int     NB_PLAYER_SPRITE =                  9;
    public static final String  PLAYER_DEATH_SPRITE =               "resources/sprites/player.png";
    public static final int     NB_PLAYER_DEATH_SPRITE =            1;
    // Enemy
    public static final String  ENEMY_SPRITE =                      "resources/sprites/enemies/enemy.png";
    public static final int     NB_ENEMY_SPRITE =                   2;
    public static final String  ENEMY_DEATH_SPRITE =                "resources/sprites/alien.png";
    public static final int     NB_ENEMY_DEATH_SPRITE =             1;

    // Projectile
    public static final String  PROJECTILE_SPRITE =                 "resources/sprites/missiles/missile.png";
    public static final int     NB_PROJECTILE_SPRITE =              1;
    public static final String  PROJECTILE_EXPLOSION_SPRITE =       "resources/sprites/projectile_explosion.png";
    public static final int     NB_PROJECTILE_EXPLOSION_SPRITE=     6;

    //Audio
    public static final String AUDIO_LOCATION=                        "resources/audio/";
    public static final String ATTACK_SOUND =                         "pew.wav";
    public static final String BACKGROUND_MUSIC=                      "bg_loop.wav";


    // Game Settings
    public static final int GAME_MAX_HEIGHT =           10;
    public static final int GAME_MIN_HEIGHT =           WINDOW_HEIGHT-10;
    public static final int GAME_MAX_WIDTH =            WINDOW_WIDTH-10;
    public static final int GAME_MIN_WIDTH =            10;
    public static final double MAX_RIGHT_ROTATION =     45;
    public static final double MAX_LEFT_ROTATION =      135;
    public static final int GAME_OVER_Y =               GAME_MIN_HEIGHT - 150;


    // Game Balancing
    public static final int PROJECTILE_SPEED =          10;         // Pixel per Update

    public static final int PLAYER_HP =                 3;
    public static final int PLAYER_DAMAGE =             5;
    public static final long PLAYER_ATTACK_SPEED =      100;       //  time in ms between attacks
    public static final int PLAYER_SPEED =              3;
    public static final double PLAYER_TURN_SPEED =      2;

    public static final int ENEMY_HP =                  1;
    public static final int ENEMY_SHOOT_DMG =           1;
    public static final long ENEMY_ATTACK_SPEED =       100;
    public static final int ENEMY_SPEED =               15;
    public static final int GRAVITY =                   15;
    public static final int CONTACT_DAMAGE =            1;


    // Bindings : their number is their index in the keycode list
    public static int[] keycodes = {
            VK_W,       // 0 = UP
            VK_S,       // 1 = DOWN
            VK_A,       // 2 = LEFT
            VK_D,       // 3 = RIGHT
            VK_E,       // 4 = TURN RIGHT
            VK_Q,       // 5 = TURN LEFT
            VK_SPACE,   // 6 = SHOOT
            VK_M,       // M = SWITCH SHOOT MODE
    };


    //levels

    public static int[][] LEVEL1 = Gen_enemyblock(6, 6);

    public static int[][]  Gen_enemyblock(int rows, int columns){
        int[][] ans = new int[rows*columns][2];
        int width = Constants.GAME_MAX_WIDTH * 3 / 4 - (Constants.GAME_MIN_WIDTH);
        int height = Constants.GAME_MIN_HEIGHT / 3 - Constants.GAME_MAX_HEIGHT;
        
        for (int i = 0 ; i < rows; i++){
            int y = height * i / rows + Constants.GAME_MAX_HEIGHT;
            for (int j = 0; j < columns; j++){
                int x = width * j /columns + Constants.GAME_MIN_WIDTH;
                ans[i * columns + j][0] = x;
                ans[i * columns + j][1] = y;
            }
        }
        return ans;
    }
}

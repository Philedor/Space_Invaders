package Tools;

import javax.print.DocFlavor;
import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.*;


public class Constants {


    // Window Settings
    public static final int     WINDOW_HEIGHT =         1000;
    public static final int     WINDOW_WIDTH =           700;
    public static final String  WINDOW_NAME =   "Space Invaders";
    public static final int     BACKGROUND_SPEED =         1;

    // Game Initialisation constants
    public static final int PLAYER_STARTING_X =         230;
    public static final int PLAYER_STARTING_Y =         600;
    public static final double PLAYER_STARTING_ANGLE =   90;     // In degrees


    public static final int PLAYER1_STARTING_X =         180;
    public static final int PLAYER1_STARTING_Y =         600;
    public static final int PLAYER2_STARTING_X =         280;
    public static final int PLAYER2_STARTING_Y =         600;

    public static final double ENEMY_STARTING_ANGLE =    -90;

    // Image importation infos
    //Player
    public static final String  PLAYER_SPRITE =                     "resources/sprites/player/player.png";
    public static final int     NB_PLAYER_SPRITE =                  9;
    public static final String  PLAYER_DEATH_SPRITE =               "resources/sprites/player/explosion/explosion.png";
    public static final int     NB_PLAYER_DEATH_SPRITE =            5;
    public static final int     TIME_BETWEEN_ANIMATIONS =           150;
    // Enemy
    public static final String  ENEMY_SPRITE =                      "resources/sprites/enemies/enemy.png";
    public static final int     NB_ENEMY_SPRITE =                   2;
    public static final String  ENEMY_DEATH_SPRITE =                "resources/sprites/enemies/explosion/explosion.png";
    public static final int     NB_ENEMY_DEATH_SPRITE =             5;

    // Projectile
    public static final String  PROJECTILE_SPRITE =                 "resources/sprites/missiles/missile.png";
    public static final int     NB_PROJECTILE_SPRITE =              1;
    public static final String  PROJECTILE_EXPLOSION_SPRITE =       "resources/sprites/missiles/explosion/explosion.png";
    public static final int     NB_PROJECTILE_EXPLOSION_SPRITE =    4;

    //HUD Elements
    public static final String  BACKGROUND_IMAGE =                  "resources/background.png";
    public static final String  HP_DISPLAY =                        "resources/sprites/hud/health/health.png";
    public static final int     NB_HP_DISPLAY =                     4;
    public static final String  SMALL_DISPLAY =                     "resources/sprites/hud/sdisplay/sdisplay.png";
    public static final int     NB_SMALL_DISPLAY=                   7;
    public static final String  TOP_HUD =                           "resources/sprites/hud/top/top.png";
    public static final int     NB_TOP_HUD =                        1;

    //Audio
    public static final String AUDIO_LOCATION=                        "resources/audio/";
    public static final String ATTACK_SOUND =                         "shoot.wav";
    public static final String BACKGROUND_MUSIC=                      "bg_loop2.wav";


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
    public static final int ENEMY_SPEED =               2;
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

    // Bindings : their number is their index in the keycode list
    public static int[] keycodes2 = {
            VK_NUMPAD8,       // 0 = UP
            VK_NUMPAD5,       // 1 = DOWN
            VK_NUMPAD4,       // 2 = LEFT
            VK_NUMPAD6,       // 3 = RIGHT
            VK_NUMPAD9,       // 4 = TURN RIGHT
            VK_NUMPAD7,       // 5 = TURN LEFT
            VK_DECIMAL,       // 6 = SHOOT
            VK_M,       // M = SWITCH SHOOT MODE
    };


    //levels

    public static int[][] LEVEL1 = Gen_enemyblock(6, 6);

    public static int[][]  Gen_enemyblock(int rows, int columns){
        int[][] ans = new int[rows*columns][2];
        int width = Constants.GAME_MAX_WIDTH * 3 / 4 - (Constants.GAME_MIN_WIDTH);
        int height = Constants.GAME_MIN_HEIGHT / 3 - Constants.GAME_MAX_HEIGHT;
        
        for (int i = 0 ; i < rows; i++){
            //+50 to offset from the top
            int y = height * i / rows + Constants.GAME_MAX_HEIGHT + 50;
            for (int j = 0; j < columns; j++){
                int x = width * j /columns + Constants.GAME_MIN_WIDTH;
                ans[i * columns + j][0] = x;
                ans[i * columns + j][1] = y;
            }
        }
        return ans;
    }
}

package Tools;

import javax.print.DocFlavor;
import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.*;


public class Constants {


    // Window Settings
    public static final int WINDOW_HEIGHT =   720;
    public static final int WINDOW_WIDTH =    480;
    public static final String WINDOW_NAME =  "Space Invaders";

    // Game Initialisation constants
    public static final int PLAYER_STARTING_X =         230;
    public static final int PLAYER_STARTING_Y =         600;
    public static final double PLAYER_STARTING_ANGLE =   90;     // In degrees

    public static final double ENEMY_STARTING_ANGLE =    -90;

    // Image importation infos
    //Player
    public static final String  PLAYER_SPRITE =                     "resources/sprites/player.png";
    public static final int     NB_PLAYER_SPRITE =                  1;
    public static final String  PLAYER_DEATH_SPRITE =               "resources/sprites/player.png";
    public static final int     NB_PLAYER_DEATH_SPRITE =            1;
    // Enemy
    public static final String  ENEMY_SPRITE =                      "resources/sprites/alien.png";
    public static final int     NB_ENEMY_SPRITE =                   1;
    public static final String  ENEMY_DEATH_SPRITE =                "resources/sprites/alien.png";
    public static final int     NB_ENEMY_DEATH_SPRITE =             1;

    // Projectile
    public static final String  PROJECTILE_SPRITE =                 "resources/sprites/missile.png";
    public static final int     NB_PROJECTILE_SPRITE =              1;
    public static final String  PROJECTILE_EXPLOSION_SPRITE =       "resources/sprites/projectile_explosion.png";
    public static final int     NB_PROJECTILE_EXPLOSION_SPRITE=     1;


    // Game Settings
    public static final int GAME_MIN_WIDTH =         10;
    public static final int GAME_MAX_WIDTH =         445;
    public static final int GAME_MIN_HEIGHT =        680;
    public static final int GAME_MAX_HEIGHT =        10;
    public static final double MAX_RIGHT_ROTATION =  45;
    public static final double MAX_LEFT_ROTATION =   135;
    public static final double GAME_FPS =            64.0;

    // Game Balancing
    public static final int PLAYER_HP =                 3;
    public static final int PROJECTILE_SPEED =          15;         // Pixel per Update
    public static final int PLAYER_DAMAGE =             1;
    public static final long PLAYER_ATTACK_SPEED =      100;          //  time in ms between attacks
    public static final int PLAYER_SPEED =              5;
    public static final double PLAYER_TURN_SPEED =      2;

    public static final int ENEMY_HP =                  1;
    public static final int ENEMY_SHOOT_DMG =           1;
    public static final long ENEMY_ATTACK_SPEED =       10000;
    public static final int ENEMY_SPEED =               10;
    public static final int GRAVITY =                   10;
    public static final int CONTACT_DAMAGE =            1;


    // Bindings : their number is their index in the keycode list
    public static int[] keycodes = {
            VK_W,       // 0 = UP
            VK_S,       // 1 = DOWN
            VK_A,       // 2 = LEFT
            VK_D,       // 3 = RIGHT
            VK_E,       // 4 = TURN RIGHT
            VK_Q,       // 5 =TURN LEFT
            VK_SPACE,   // 6 =SHOOT
    };


    //levels

    public static final int[][] LEVEL1 = {
            {10, 10},
            {43, 10},
            {77, 10},
            {110, 10},
            {144, 10},
            {177, 10},
            {211, 10},
            {244, 10},
            {278, 10},
            {311, 10},
            {10, 46},
            {43, 46},
            {77, 46},
            {110, 46},
            {144, 46},
            {177, 46},
            {211, 46},
            {244, 46},
            {278, 46},
            {311, 46},
            {10, 82},
            {43, 82},
            {77, 82},
            {110, 82},
            {144, 82},
            {177, 82},
            {211, 82},
            {244, 82},
            {278, 82},
            {311, 82},
            {10, 118},
            {43, 118},
            {77, 118},
            {110, 118},
            {144, 118},
            {177, 118},
            {211, 118},
            {244, 118},
            {278, 118},
            {311, 118},
            {10, 154},
            {43, 154},
            {77, 154},
            {110, 154},
            {144, 154},
            {177, 154},
            {211, 154},
            {244, 154},
            {278, 154},
            {311, 154},
            {10, 190},
            {43, 190},
            {77, 190},
            {110, 190},
            {144, 190},
            {177, 190},
            {211, 190},
            {244, 190},
            {278, 190},
            {311, 190},
    };

}

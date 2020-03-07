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
    public static final int     NB_PROJECTILE_EXPLOSION_SPRITE=     1;

    //Audio
    public static final String AUDIO_LOCATION=                        "resources/audio/";
    public static final String ATTACK_SOUND =                         "pew.wav";
    public static final String BACKGROUND_MUSIC=                      "bg_loop.wav";


    // Game Settings
    public static final int GAME_MAX_HEIGHT =        10;
    public static final int GAME_MIN_HEIGHT =        WINDOW_HEIGHT-10;
    public static final int GAME_MAX_WIDTH =         WINDOW_WIDTH-10;
    public static final int GAME_MIN_WIDTH =         10;
    public static final double MAX_RIGHT_ROTATION =  45;
    public static final double MAX_LEFT_ROTATION =   135;
    public static final double GAME_FPS =            64.0;

    // Game Balancing
    public static final int PLAYER_HP =                 3;
    public static final int PROJECTILE_SPEED =          15;         // Pixel per Update
    public static final int PLAYER_DAMAGE =             1;
    public static final long PLAYER_ATTACK_SPEED =      150;       //  time in ms between attacks
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
            {79, 10},
            {148, 10},
            {217, 10},
            {286, 10},
            {355, 10},
            {424, 10},
            {493, 10},
            {562, 10},
            {631, 10},
            {10, 82},
            {79, 82},
            {148, 82},
            {217, 82},
            {286, 82},
            {355, 82},
            {424, 82},
            {493, 82},
            {562, 82},
            {631, 82},
            {10, 154},
            {79, 154},
            {148, 154},
            {217, 154},
            {286, 154},
            {355, 154},
            {424, 154},
            {493, 154},
            {562, 154},
            {631, 154},
            {10, 226},
            {79, 226},
            {148, 226},
            {217, 226},
            {286, 226},
            {355, 226},
            {424, 226},
            {493, 226},
            {562, 226},
            {631, 226},
            {10, 298},
            {79, 298},
            {148, 298},
            {217, 298},
            {286, 298},
            {355, 298},
            {424, 298},
            {493, 298},
            {562, 298},
            {631, 298},
            {10, 370},
            {79, 370},
            {148, 370},
            {217, 370},
            {286, 370},
            {355, 370},
            {424, 370},
            {493, 370},
            {562, 370},
            {631, 370},
    };

}

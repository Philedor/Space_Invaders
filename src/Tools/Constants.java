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
    public static final int PLAYER_STARTING_X =         0;
    public static final int PLAYER_STARTING_Y =         1;
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
    public static final int GAME_MAX_WIDTH =         470;
    public static final int GAME_MIN_HEIGHT =        710;
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

}

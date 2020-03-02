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
    public static final float PI =                      (float) Math.PI;
    public static final int PLAYER_STARTING_X =         200;
    public static final int PLAYER_STARTING_Y =         600;
    public static final float PLAYER_STARTING_ANGLE =   PI / 2f;     // In radians

    public static final float ENEMY_STARTING_ANGLE =    -PI/2f;

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
    public static final String  PROJECTILE_EXPLOSION_SPRITE =       "resources/sprites/missile.png";
    public static final int     NB_PROJECTILE_EXPLOSION_SPRITE=     1;


    // Game Settings
    public static final int GAME_MIN_WIDTH =         10;
    public static final int GAME_MAX_WIDTH =         450;
    public static final int GAME_MIN_HEIGHT =        700;
    public static final int GAME_MAX_HEIGHT =        10;
    public static final float MAX_RIGHT_ROTATION =   PI/4f;
    public static final float MAX_LEFT_ROTATION =    PI*0.75f;
    public static final double GAME_FPS =            64.0;

    // Game Balancing
    public static final int PLAYER_HP =                 3;
    public static final int PLAYER_DMG =                1;
    public static final int PROJECTILE_SPEED =          15;         // Pixel per Update
    public static final int ENEMY_HP =                  1;
    public static final int ENEMY_SHOOT_DMG =           1;
    public static final long PLAYER_ATTACK_SPEED =     2500;          // time between attacks in ms
    public static final int PLAYER_SPEED =              1;
    public static final float PLAYER_TURNSPEED =        2f;
    public static final long ENEMY_ATTACK_SPEED =      2000;
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

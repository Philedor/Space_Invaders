package Entities;

import Graphics.GameScene;
import Tools.Constants;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import static Tools.Constants.keycodes;

public class Player extends Entity{

    private List<Projectile> projectiles;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean isShooting = false;

    private int hp;
    private int dmg;
    private int moveSpeed;
    private double turnSpeed;
    private long atkSpeed;
    private long lastShoot;


    public Player() {
        super(Team.PLAYER, Constants.PLAYER_STARTING_X, Constants.PLAYER_STARTING_Y, Constants.PLAYER_STARTING_ANGLE, Constants.PLAYER_SPRITE, Constants.NB_PLAYER_SPRITE);

        projectiles = new ArrayList<>();
        hp = Constants.PLAYER_HP;
        dmg = Constants.PLAYER_DAMAGE;
        moveSpeed = Constants.PLAYER_SPEED;
        turnSpeed = Constants.PLAYER_TURN_SPEED;
        atkSpeed = Constants.PLAYER_ATTACK_SPEED;
        lastShoot = System.currentTimeMillis() - atkSpeed;  // To allow te player to shoot from the beginning
    }

    public void Shoot(){
        if(System.currentTimeMillis() - lastShoot > atkSpeed){
            projectiles.add(new Projectile(team, posX, posY, angle, dmg));
            lastShoot = System.currentTimeMillis();
        }
    }


    public void Update() {
        posX = Math.max(Math.min(posX + dx, Constants.GAME_MAX_WIDTH), Constants.GAME_MIN_WIDTH) ;
        posY = Math.min(Math.max(posY + dy, Constants.GAME_MAX_HEIGHT), Constants.GAME_MIN_HEIGHT);
        angle = Math.max(Math.min(angle + dangle, Constants.MAX_LEFT_ROTATION), Constants.MAX_RIGHT_ROTATION);
        if (isShooting) Shoot();
    }

    public void damage(int dmg){
        hp -= dmg;
        if (hp <= 0)
            live = false;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if     (key == keycodes[0] )     {dy = -moveSpeed;      upPressed = true;}
        if     (key == keycodes[1] )     {dy =  moveSpeed;      downPressed = true;}

        if     (key == keycodes[2] )     {dx = -moveSpeed;      leftPressed = true;}
        if     (key == keycodes[3] )     {dx =  moveSpeed;      rightPressed = true;}

        if     (key == keycodes[4] )     dangle = -turnSpeed;
        if     (key == keycodes[5] )     dangle = turnSpeed;

        if     (key == keycodes[6])    isShooting = true;

    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        // Up/Down
        if     (key == keycodes[0]) {
                dy = 0;
        }
        if     (key == keycodes[1])  {
                dy = 0;
        }

        // Left/Right
        if     (key == keycodes[2]) {
            leftPressed = false;
            if (!rightPressed)
                dx = 0;
        }
        if     (key == keycodes[3]){
            rightPressed = false;
            if (!leftPressed)
                dx = 0;
        }

        // Turn
        if     (key == keycodes[4] )     dangle = 0;
        if     (key == keycodes[5] )     dangle = 0;

        if     (key == keycodes[6])    isShooting = false;

    }

    public List<Projectile> getProjectiles(){return projectiles;}


}

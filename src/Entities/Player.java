package Entities;

import Tools.Constants;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import static Tools.Constants.keycodes;
import static java.awt.event.KeyEvent.VK_Y;

public class Player extends Entity{

    private List<Projectile> projectiles;
    public boolean leftPressed = false;
    public boolean rightPressed = false;
    private boolean upPressed = false;
    private boolean downPressed = false;
    public boolean isShooting = false;

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
            projectiles.add(new Projectile(team, posX+14, posY, angle, dmg));
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

        if (key == VK_Y) System.out.println(posX + "  " + posY);

    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();
        // Up/Down
        if     (key == keycodes[0]) {
            upPressed = false;
            if(!downPressed)
                dy = 0;
            else dy = moveSpeed;
        }
        if     (key == keycodes[1])  {
            downPressed = false;
            if(!upPressed)
                dy = 0;
            else dy = -moveSpeed;
        }

        // Left/Right
        if     (key == keycodes[2]) {
            leftPressed = false;
            if (!rightPressed)
                dx = 0;
            else dx = moveSpeed;
        }
        if     (key == keycodes[3]){
            rightPressed = false;
            if (!leftPressed)
                dx = 0;
            else dx = -moveSpeed;

        }

        // Turn
        if     (key == keycodes[4] )     dangle = 0;
        if     (key == keycodes[5] )     dangle = 0;

        if     (key == keycodes[6])    isShooting = false;


    }

    public List<Projectile> getProjectiles(){return projectiles;}

    public void animate(){
        if (animation_counter < 24) {
            //animation counter is used to display one frame per animation for a little longer
            ++animation_counter;
            if (animation_counter < 8) {
                animationframe = 0;
            } else if (animation_counter < 16) {
                animationframe = 1;
            } else if (animation_counter < 24) {
                animationframe = 2;
            }
        } else {
            animation_counter = 0;
        }

        // if condition to add for left, right and explosion animation
        if (dx > 0) {
            //at player3.png the right flying animation starts and has 3 animations
            currentSprite = 3 + animationframe;
        } else if (dx < 0) {
            //at player6.png the left flying animation starts and has 3 animations
            currentSprite = 6 + animationframe;
        } else {
            currentSprite = animationframe;
        }
    }


}

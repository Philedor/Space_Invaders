package Entities;

import Game.Game;
import Graphics.GameScene;
import Tools.Audio;
import Tools.Constants;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static Tools.Constants.ATTACK_SOUND;
import static Tools.Constants.keycodes;
import static java.awt.event.KeyEvent.VK_P;
import static java.awt.event.KeyEvent.VK_Y;

public class Player extends Entity{

    private List<Projectile> projectiles;
    public boolean leftPressed = false;
    public boolean rightPressed = false;
    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean turnrightpressed = false;
    private boolean turnleftpressed = false;
    public boolean isShooting = false;
    public boolean invincible = false;
    private int imunitycd = 0;
    private int shootingmode = 0;

    private int hp;
    private int dmg;
    private int moveSpeed;
    private double turnSpeed;
    private long atkSpeed;
    private long lastShoot;

    public float opacity = 1;



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
            //Audio.playSound(ATTACK_SOUND);
            if (shootingmode == 0){
                projectiles.add(new Projectile(team, posX+(width/2), posY+(height/2), angle, dmg));
            }

            if (shootingmode == 1) {
                projectiles.add(new Projectile(team, posX + (width / 2), posY + (height / 2), angle, dmg));
                projectiles.add(new Projectile(team, posX + (width / 2), posY + (height / 2), angle + ((double)(45 / 2)), dmg));
                projectiles.add(new Projectile(team, posX + (width / 2), posY + (height / 2), angle - ((double)(45 / 2)), dmg));
            }

            if (shootingmode == 2) {
                projectiles.add(new Projectile(team, posX + (width / 2), posY + (height / 2), angle, dmg));
                projectiles.add(new Projectile(team, posX + (width / 5), posY + (height / 2), angle, dmg));
                projectiles.add(new Projectile(team, posX + (4 * width / 5), posY + (height / 2), angle, dmg));
            }
            lastShoot = System.currentTimeMillis();
        }
    }


    private long time;

    public void Update() {
        //TODO add death animation managementthing
        if (invincible){
            imunitycd -= 1;
            if (imunitycd%2 == 1)
                opacity = 0.4f;
            else opacity = 1;
            if (imunitycd <= 0){
                imunitycd = 0;
                invincible = false;
                System.out.println(System.currentTimeMillis() - time);
            }
        }
        posX = Math.max(Math.min(posX + dx, Constants.GAME_MAX_WIDTH - width), Constants.GAME_MIN_WIDTH) ;
        posY = Math.min(Math.max(posY + dy, Constants.GAME_MAX_HEIGHT), Constants.GAME_MIN_HEIGHT - height);
        angle = Math.max(Math.min(angle + dangle, Constants.MAX_LEFT_ROTATION), Constants.MAX_RIGHT_ROTATION);
        if (isShooting) Shoot();
    }

    public void damage(int dmg){
        setinvincible();
        time = System.currentTimeMillis();
        hp -= dmg;
        if (hp <= 0){
            live = false;
        }
    }

    public void setinvincible(){
        invincible = true;
        imunitycd = (int)(68 * 1.5); // 68 time nbsecondds we want it to last
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if     (key == keycodes[0] )     {dy = -moveSpeed;      upPressed = true;}
        if     (key == keycodes[1] )     {dy =  moveSpeed;      downPressed = true;}

        if     (key == keycodes[2] )     {dx = -moveSpeed;      leftPressed = true;}
        if     (key == keycodes[3] )     {dx =  moveSpeed;      rightPressed = true;}

        if     (key == keycodes[4] )     {dangle = -turnSpeed;  turnrightpressed = true;}
        if     (key == keycodes[5] )     {dangle = turnSpeed;   turnleftpressed = true;}

        if     (key == keycodes[6])    isShooting = true;
        if     (key == keycodes[7])      {shootingmode = (shootingmode + 1) % 3;}
        if     (key == VK_P)            {GameScene.setPause(!GameScene.getPause());}

        //if (key == VK_Y) System.out.println(GameScene.enemies.size());

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
        if     (key == keycodes[4] )     {
            turnrightpressed = false;
            if (!turnleftpressed)
                dangle = 0;
            else dangle = turnSpeed;
        }
        if     (key == keycodes[5] )     {
            turnleftpressed = false;
            if (!turnrightpressed)
                dangle = 0;
            else dangle = -turnSpeed;
        }

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

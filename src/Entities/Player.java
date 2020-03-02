package Entities;

import Graphics.GameScene;
import Tools.Constants;


import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import static Tools.Constants.keycodes;

public class Player extends Entity{

    private static boolean leftPressed = false;
    private static boolean rightPressed = false;
    private static boolean upPressed = false;
    private static boolean downPressed = false;
    private static boolean isSHooting = false;
    private static List<Projectile> projectiles;

    public Player(){
        super(/*Team.PLAYER,*/ Constants.NB_PLAYER_DEATH_SPRITE,Constants.PLAYER_HP, Constants.PLAYER_ATTACK_SPEED,
                0, Constants.PLAYER_DMG, Constants.PLAYER_STARTING_X, Constants.PLAYER_STARTING_Y,
                Constants.PLAYER_SPEED, Constants.PLAYER_STARTING_ANGLE, Constants.PLAYER_TURNSPEED,
                Constants.NB_PLAYER_SPRITE, Constants.PLAYER_SPRITE, Constants.PLAYER_DEATH_SPRITE);
        CanTurn = true;
        CanShoot = true;
        projectiles = new ArrayList<>();
    }

    public static void Shoot(){
        if(System.currentTimeMillis() - lastshoot > attackSpeed){
            Projectile projectile = new Projectile(angle, posX , posY, /*team,*/ shootDMG);
            GameScene.AddProj(projectile);
            lastshoot = System.currentTimeMillis();

        }
    }

    public void Update() {
        posX = Math.max(Math.min(posX + deltaX, Constants.GAME_MAX_WIDTH), Constants.GAME_MIN_WIDTH) ;
        posY = Math.min(Math.max(posY + deltaY, Constants.GAME_MAX_HEIGHT), Constants.GAME_MIN_HEIGHT);
        angle = Math.max(Math.min(angle + deltaTheta, Constants.MAX_LEFT_ROTATION), Constants.MAX_RIGHT_ROTATION);
        if(isSHooting) Shoot();

    }



    public static void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if     (key == keycodes[2] && CanMoveHorizontally)     {deltaX = -moveSpeed;    leftPressed = true;}
        if     (key == keycodes[3] && CanMoveHorizontally)     {deltaX =  moveSpeed;     rightPressed = true;}

        if     (key == keycodes[0] && CanMoveVertically)       {deltaY = -moveSpeed;    upPressed = true;}
        if     (key == keycodes[1] && CanMoveVertically)       {deltaY =  moveSpeed;    downPressed = true;}

        if     (key == keycodes[4] && CanTurn)     deltaTheta = -turnSpeed;
        if     (key == keycodes[5] && CanTurn)     deltaTheta = turnSpeed;

        if     (key == keycodes[6] && CanShoot)    isSHooting = true;

    }

    public static void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if     (key == keycodes[2]) {
            leftPressed = false;
            if (!rightPressed)
                deltaX = 0;
            else deltaX = moveSpeed;
        }
        if     (key == keycodes[3] && CanMoveHorizontally){
            rightPressed = false;
            if (!leftPressed)
                deltaX = 0;
            else deltaX = -moveSpeed;

        }

        if     (key == keycodes[0] && CanMoveVertically)  {
            upPressed = false;
            if (!downPressed)
                deltaY = 0;
            else deltaY = moveSpeed;
        }

        if     (key == keycodes[1] && CanMoveVertically)  {
            downPressed = false;
            if(!upPressed)
                deltaY = 0;
            else deltaY = -moveSpeed;
        }

        if     (key == keycodes[4] && CanTurn)     deltaTheta = 0;
        if     (key == keycodes[5] && CanTurn)     deltaTheta = 0;

        if     (key == keycodes[6] && CanShoot)    isSHooting = false;

    }

    public List<Projectile> getProjectiles()    {return projectiles;}


}

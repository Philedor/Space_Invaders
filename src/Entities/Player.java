package Entities;


import Tools.Audio;
import Tools.Constants;


import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

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

    public int[] keycodes;
    public int score = 0;



    public Player(Team team) {
        super(team, team == Team.PLAYER1? Constants.PLAYER1_STARTING_X : Constants.PLAYER2_STARTING_X, Constants.PLAYER_STARTING_Y, Constants.PLAYER_STARTING_ANGLE, Constants.PLAYER_SPRITE, Constants.NB_PLAYER_SPRITE);

        projectiles = new ArrayList<>();
        hp = Constants.PLAYER_HP;
        dmg = Constants.PLAYER_DAMAGE;
        moveSpeed = Constants.PLAYER_SPEED;
        turnSpeed = Constants.PLAYER_TURN_SPEED;
        atkSpeed = Constants.PLAYER_ATTACK_SPEED;
        lastShoot = System.currentTimeMillis() - atkSpeed;  // To allow the player to shoot from the beginning
        if (team == Team.PLAYER1)
            keycodes = Constants.keycodes;
        else
            keycodes = Constants.keycodes2;
    }

    public void Shoot(){
        if(System.currentTimeMillis() - lastShoot > atkSpeed){
            Audio shoot = new Audio("shoot.wav");
            shoot.playSound(0.1f);

            if (shootingmode == 0){
                projectiles.add(new Projectile(team, posX+(width/2)-1, posY+(height/2), angle, dmg));
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


    public void Update() {
        //TODO add death animation management thing
        if (invincible){
            imunitycd -= 1;
            if (imunitycd%2 == 1)
                opacity = 0.4f;
            else opacity = 1;
            if (imunitycd <= 0){
                imunitycd = 0;
                invincible = false;
            }
        }
        posX = Math.max(Math.min(posX + dx, Constants.GAME_MAX_WIDTH - width), Constants.GAME_MIN_WIDTH) ;
        posY = Math.min(Math.max(posY + dy, Constants.GAME_MAX_HEIGHT), Constants.GAME_MIN_HEIGHT - height);
        angle = Math.max(Math.min(angle + dangle, Constants.MAX_LEFT_ROTATION), Constants.MAX_RIGHT_ROTATION);
        if (isShooting) Shoot();
    }

    public void damage(int dmg){
        setinvincible();
        hp -= dmg;
        if (hp <= 0){
            dying = true;
            live = false;

            if (team == Team.PLAYER1)
                System.out.println("p1 dead");
            else
                System.out.println("p2 dead");
            System.out.println(score);
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

    public void animateMovement() {
        animateLoop(Constants.NB_PLAYER_SPRITE/3);
        // if condition to add for left, right and explosion animation
        if (dx > 0) {
            //at player3.png the right flying animation starts and has 3 animations
            currentSprite = 3 + animationFrame;
        } else if (dx < 0) {
            //at player6.png the left flying animation starts and has 3 animations
            currentSprite = 6 + animationFrame;
        } else {
            currentSprite = animationFrame;
        }
    }

    public int getHp(){ return hp;}

}

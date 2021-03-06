package Entities;

import Tools.Constants;

import java.util.ArrayList;
import java.util.List;

public class Enemy extends Entity {

    private static List<Projectile> projectiles = new ArrayList<>();
    private int hp;
    private int shootdmg;
    public int contactdmg;
    private int moveSpeed;
    private long atkSpeed;
    private long lastShoot;
    public static long enemyLastMove;
    public static long enemyMoveTime = 1;
    public static boolean enemywentdown = false;


    public Enemy(int x, int y) {
        super(Team.ENEMIES, x, y, Constants.ENEMY_STARTING_ANGLE, Constants.ENEMY_SPRITE, Constants.NB_ENEMY_SPRITE);

        hp = Constants.ENEMY_HP;
        shootdmg = Constants.ENEMY_SHOOT_DMG;
        contactdmg = Constants.CONTACT_DAMAGE;
        moveSpeed = - Constants.ENEMY_SPEED;
        currentSprite = (int) (Math.random()*2);
        atkSpeed = (int)(Math.random() * Math.random() * Math.random() * 1000000);   // just added a random number
        lastShoot = (long) (System.currentTimeMillis() + (Math.random() * 100));
    }


    public void Shoot(){

        if(System.currentTimeMillis() - lastShoot > atkSpeed){
            projectiles.add(new Projectile(team, posX + (getwidth()/2), posY, angle, shootdmg));
            lastShoot = System.currentTimeMillis();
        }
    }


    public void damage(int dmg){
        hp -= dmg;
        if (hp <= 0) {
            Kill();
        }
    }

    public void Kill() {
        dx = 0;
        dy = 0;
        LoadSpriteSheet(Constants.NB_ENEMY_DEATH_SPRITE, Constants.ENEMY_DEATH_SPRITE, 0);

        dying = true;
    }

    public void MoveSideways()  {
        posX += moveSpeed;
        if (posX < Constants.GAME_MIN_WIDTH)
            posX = Constants.GAME_MIN_WIDTH;
        if(posX > Constants.GAME_MAX_WIDTH)
            posX = Constants.GAME_MAX_WIDTH;
    }
    public void MoveDown() { posY += Constants.GRAVITY;}

    // Used to modify movements of the enemy
    public void InvertDirection()    {moveSpeed = -moveSpeed;}

    public static List<Projectile> getProjectiles(){return projectiles;}

}

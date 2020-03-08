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
    public static long enemyMoveTime = 800;
    public static boolean enemywentdown = false;


    public Enemy(int x, int y) {
        super(Team.ENEMIES, x, y, Constants.ENEMY_STARTING_ANGLE, Constants.ENEMY_SPRITE, Constants.NB_ENEMY_SPRITE);

        hp = Constants.ENEMY_HP;
        shootdmg = Constants.ENEMY_SHOOT_DMG;
        contactdmg = Constants.CONTACT_DAMAGE;
        moveSpeed = - Constants.ENEMY_SPEED;
        atkSpeed = Constants.ENEMY_ATTACK_SPEED;
        lastShoot = (long) (System.currentTimeMillis() + (Math.random() * 100));
    }


    public void Shoot(){
        if(System.currentTimeMillis() - lastShoot > atkSpeed){
            projectiles.add(new Projectile(team, posX, posY, angle, shootdmg));
            lastShoot = System.currentTimeMillis();
        }
    }


    public void damage(int dmg){
        hp -= dmg;
        if (hp <= 0) {
            live = false;
        }
        // TODO load death animation
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

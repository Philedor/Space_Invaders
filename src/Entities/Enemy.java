package Entities;

import Graphics.GameScene;
import Tools.Constants;

import java.util.ArrayList;
import java.util.List;

public class Enemy extends Entity {
    protected int packposX;
    protected int packposY;

    protected static List<Projectile> projectiles;

    public Enemy(int ppx, int ppy, boolean mostL, boolean mostR){
        super(/*Team.ENEMIES,*/Constants.NB_ENEMY_DEATH_SPRITE,Constants.ENEMY_HP, Constants.ENEMY_ATTACK_SPEED,
                Constants.ENEMY_SHOOT_DMG, Constants.ENEMY_SHOOT_DMG ,ppx , ppy,
                Constants.ENEMY_SPEED, Constants.ENEMY_STARTING_ANGLE, 0,
                Constants.NB_ENEMY_SPRITE, Constants.ENEMY_SPRITE, Constants.ENEMY_DEATH_SPRITE );

        packposX = ppx;
        packposY = ppy;

        GetRealCoord();

        projectiles =   new ArrayList<>();


    }

    public static void Shoot(){
        if(System.currentTimeMillis() - lastshoot > attackSpeed){
            Projectile projectile = new Projectile(angle, posX, posY, /*team,*/ shootDMG);
            GameScene.AddProj(projectile);
            //projectiles.add( projectile );
            lastshoot = System.currentTimeMillis();
        }
    }

    // Call thiese functions in the update things.
    // We just change the sign of moveSpeed to change the direction and make it go either left or right
    public void MoveSideways()  { posX += moveSpeed;}
    public void MoveDown() { posY += Constants.GRAVITY;}

    // Used to modify movements of the enemy
    public void InvertDirection()    {moveSpeed = -moveSpeed;}


    // real int posx and posy based on position in the pack
    public void GetRealCoord(){
        // TODO
        // posx = offset * packposX
        // posy = offset * packposY
    }


    public List<Projectile> getProjectiles()    {return projectiles;}

}

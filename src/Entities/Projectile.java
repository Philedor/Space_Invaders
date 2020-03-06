package Entities;

import Graphics.GameScene;
import Tools.Constants;

public class Projectile extends Entity {

    private int hp;
    private int dmg;

    public Projectile(Team team, int posX, int posY, double angle, int pdmg) {
        super(team, posX, posY, angle, Constants.PROJECTILE_SPRITE, Constants.NB_PROJECTILE_SPRITE,
                (int) (CalculateDeltaX(angle, Constants.PROJECTILE_SPEED)), (int) (CalculateDeltaY(angle, Constants.PROJECTILE_SPEED)));

        hp = 1;
        dmg = pdmg;
    }

    public void Update(){
        if (live){
            posX += dx;
            posY += dy;
        }

    }

    public void Kill(){
        hp = 0;
        live = false;
        dx = 0;
        dy = 0;

        LoadSprites(Constants.NB_PROJECTILE_EXPLOSION_SPRITE, Constants.PROJECTILE_EXPLOSION_SPRITE);
    }

    public int getDmg() {return dmg;}

    private static double CalculateDeltaX(double angle, int speed)     {return Math.cos(Math.toRadians(angle)) * (double) speed;}
    private static double CalculateDeltaY(double angle, int speed)     {return - Math.sin(Math.toRadians(angle)) * (double) speed;}
}

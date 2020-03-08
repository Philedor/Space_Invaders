package Entities;

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

    public Projectile(Team team, int posX, int posY, double angle, int pdmg, int php) {
        super(team, posX, posY, angle, Constants.PROJECTILE_SPRITE, Constants.NB_PROJECTILE_SPRITE,
                (int) (CalculateDeltaX(angle, Constants.PROJECTILE_SPEED)), (int) (CalculateDeltaY(angle, Constants.PROJECTILE_SPEED)));

        hp = php;
        dmg = pdmg;
    }

    public void Update(){
        if (live){
            posX += dx;
            posY += dy;

            if (posX < 0||
            posX > Constants.GAME_MAX_WIDTH ||
            posY < 0 ||
            posY > Constants.GAME_MIN_HEIGHT)
                Kill();
        }
    }

    public void damage(int dmg){
        hp -= dmg;
        if (hp <= 0)
            Kill();
    }

    public void Kill(){
        live = false;
        dx = 0;
        dy = 0;

        LoadSprites(Constants.NB_PROJECTILE_EXPLOSION_SPRITE, Constants.PROJECTILE_EXPLOSION_SPRITE);
    }

    public int getDmg() {return dmg;}

    private static double CalculateDeltaX(double angle, int speed)     {return Math.cos(Math.toRadians(angle)) * (double) speed;}
    private static double CalculateDeltaY(double angle, int speed)     {return - Math.sin(Math.toRadians(angle)) * (double) speed;}
}

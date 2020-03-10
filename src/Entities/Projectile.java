package Entities;

import Tools.Constants;

public class Projectile extends Entity {

    private int dmg;

    public Projectile(Team team, int posX, int posY, double angle, int pdmg) {
        super(team, posX, posY, angle, Constants.PROJECTILE_SPRITE, Constants.NB_PROJECTILE_SPRITE,
                (int) (CalculateDeltaX(angle, Constants.PROJECTILE_SPEED)), (int) (CalculateDeltaY(angle, Constants.PROJECTILE_SPEED)), Constants.NB_PROJECTILE_EXPLOSION_SPRITE);

        hp = 1;
        dmg = pdmg;
    }

    public Projectile(Team team, int posX, int posY, double angle, int pdmg, int php) {
        super(team, posX, posY, angle, Constants.PROJECTILE_SPRITE, Constants.NB_PROJECTILE_SPRITE,
                (int) (CalculateDeltaX(angle, Constants.PROJECTILE_SPEED)), (int) (CalculateDeltaY(angle, Constants.PROJECTILE_SPEED)), Constants.NB_PROJECTILE_EXPLOSION_SPRITE);

        hp = php;
        dmg = pdmg;
    }

    public void Update(){
        if (!dying){
            posX += dx;
            posY += dy;

            if (posX < 0||
            posX > Constants.GAME_MAX_WIDTH ||
            posY < 0 ||
            posY > Constants.GAME_MIN_HEIGHT)
                live = false;
        }
        else {
            if (deathframes <= 0)
                live = false;
            deathframes -= 1;
        }
    }

    public void damage(int dmg){
        hp -= dmg;
        if (hp <= 0)
            Kill();
    }

    public void Kill(){
        dx = 0;
        dy = 0;

        LoadSprites(Constants.NB_PROJECTILE_EXPLOSION_SPRITE, Constants.PROJECTILE_EXPLOSION_SPRITE);
        //adjusting for new hitbox size
        posX = posX - getwidth()/2;
        dying = true;
    }

    public int getDmg() {return dmg;}

    private static double CalculateDeltaX(double angle, int speed)     {return Math.cos(Math.toRadians(angle)) * (double) speed;}
    private static double CalculateDeltaY(double angle, int speed)     {return - Math.sin(Math.toRadians(angle)) * (double) speed;}
}

package Entities;

import Tools.Constants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Projectile extends Entity {

    // Constructor
    /** Projectile Constructor
     * @param  pangle the angle at which it is shot
     * @param  x the x coordinate from where it is shot
     * @param  y the y coordinate from where it is shot*/
    public Projectile (float pangle, int x, int y,/* Team pteam, */int dmg){
        super(/*team,*/ Constants.NB_PROJECTILE_EXPLOSION_SPRITE,1, 0, dmg, 0,
                x, y, Constants.PROJECTILE_SPEED, pangle, 0, Constants.NB_PROJECTILE_SPRITE,
                Constants.PROJECTILE_SPRITE, Constants.PROJECTILE_EXPLOSION_SPRITE, 0, -Constants.PROJECTILE_SPEED);

        //deltaX = (int) CalculateDeltaX();
        //deltaY = (int) CalculateDeltaY();


    }

    public void Update(){
        this.posX += deltaX;
        this.posY += deltaY;
        System.out.println("" + deltaX + "        " + deltaY);

        if (!this.live)
            framesTillDeath -= 1;

        /*else if ((     posX >= Constants.GAME_MAX_WIDTH) ||
                ( posX <= Constants.GAME_MIN_WIDTH) ||
                ( posY >= Constants.GAME_MAX_HEIGHT) ||
                ( posY <= Constants.GAME_MIN_HEIGHT) ){
            this.Kill();*/
        //}

    }





    public int getFrames_until_explosion() {return framesTillDeath;}

    public int getDMG()                 {return this.onContactDMG;}
    protected void setDMG(int dmg)      {this.onContactDMG = dmg;}



}

package Entities;

import Tools.Constants;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Entity {

    // Used to identify which side an entity belongs to
    public enum Team {
        PLAYER,
        ENEMIES
    };

    // In game related stats / date

    protected static boolean CanMoveHorizontally = true;
    protected static boolean CanMoveVertically = true;
    protected static boolean CanTurn = false;
    protected static boolean CanShoot = false;
    protected static Team team;
    protected boolean live = true;
    protected int framesTillDeath;

    protected int hp;
    protected static long attackSpeed;
    protected int onContactDMG;
    protected static int shootDMG;

    // Position and orientation
    protected static int posX;
    protected static int posY;
    protected static int moveSpeed;

    protected static int deltaX;
    protected static int deltaY;

    protected static float angle;           // in radians
    protected static float turnSpeed;
    protected static float deltaTheta = 0;

    // Graphics data

    protected int nb_sprites;
    protected int currentsprite = 0;
    protected static ArrayList<Image> sprites;
    protected static int width;
    protected static int height;
    protected String deathsprites;



    protected static long lastshoot;

    public Entity(/*Team pteam, */int pdeathframes, int php, long pattackSpeed, int pcdmg, int psdmg, int px, int py, int pms, float pangle, float pts, int pnbs, String ppath, String pdeathsprites){
        /*team =  pteam;*/
        framesTillDeath = pdeathframes;
        hp = php;
        attackSpeed = pattackSpeed;
        onContactDMG = pcdmg;
        shootDMG = psdmg;
        posX = px;
        posY = py;
        deltaX = 0;
        deltaY = 0;
        moveSpeed = pms;
        angle = pangle;
        turnSpeed = pts;

        LoadSprites(ppath, pnbs);

        lastshoot = System.currentTimeMillis();
        deathsprites = pdeathsprites;

    }


    public Entity(/*Team pteam, */int pdeathframes, int php, long pattackSpeed, int pcdmg, int psdmg, int px, int py, int pms, float pangle, float pts, int pnbs, String ppath, String pdeathsprites, int dx, int dy){
        /*team =  pteam;*/
        framesTillDeath = pdeathframes;
        hp = php;
        attackSpeed = pattackSpeed;
        onContactDMG = pcdmg;
        shootDMG = psdmg;
        posX = px;
        posY = py;
        deltaX = dx;
        deltaY = dy;
        moveSpeed = pms;
        angle = pangle;
        turnSpeed = pts;

        LoadSprites(ppath, pnbs);

        lastshoot = System.currentTimeMillis();
        deathsprites = pdeathsprites;

    }


    /** Sprites Loading, can load multiple sprites to allow animation later on
     * @param  path the path with sprite name + extension
     * @param  nb the number of sprites*/
    public void LoadSprites(String path, int nb){

        sprites = new ArrayList<>();
        // Useful values so that we only use one memory space for each
        // Or just facilitate the process
        Image tmp;
        ImageIcon ii;
        int n = path.length();
        String pathroot = path.substring(0, n-4);
        String end = path.substring(n-4);
        String tmppath;

        // Save this one since we know it
        this.nb_sprites = nb;

        for(int i = 0; i < nb; i++){
            tmppath = pathroot + i +end;
            sprites.add(new ImageIcon(tmppath).getImage());
        height = this.getImage().getHeight(null);
        width = this.getImage().getWidth(null);
        }
    }



    public void damage(int dmg){
        this.hp -= dmg;
        if (this.hp <= 0 )
            this.Kill();
    }

    public void Kill(){
        this.live = false;
        this.deltaX = 0;
        this.deltaY = 0;
        LoadSprites(deathsprites, framesTillDeath);

    }

    // Getters
    // For game management
    public int getHP()    {return this.hp;}
    public int getPosX()   {return posX;}
    public int getPosY()   {return posY;}

    public void setDeltaX(int x){deltaX = x; }
    public void setDeltaY(int y){deltaY = y; }

    // For Update and Drawing
    public Image getImage()  {return this.sprites.get(currentsprite);}

    // For collision management, defines the hitbox
    public Rectangle getBounds() { return new Rectangle(posX, posY, width, height);}

    private float CalculateDeltaX(float angle, int moveSpeed)     {return (float) Math.cos(angle) / (float)moveSpeed;}
    private float CalculateDeltaY(float angle, int moveSpeed)     {return (float) Math.sin(angle) / (float)moveSpeed;}

}


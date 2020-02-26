package Entities;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Character{

    protected static LinkedList<Projectile> projectiles;

    // game infos / stats
    protected static boolean CanMove = false;
    protected static boolean CanTurn = false;
    protected static boolean CanShoot = false;
    protected int hp;
    protected float attackSpeed;     // Attack per second


    // Sprites
    protected ArrayList<Image> sprites;
    protected int nb_sprites;
    protected int currentsprite = 0;

    // Position
    protected static int posX;
    protected static int posY;
    protected static int moveSpeed;

    protected int width;
    protected int height;

    protected static float angle;           // in radians
    protected static float turnSpeed = 0;


    /** Sprites Loading, can load multiple sprites to allow animation later on
     * @param  path the path with sprite name + extension
     * @param  nb the number of sprites*/
    public void LoadSprites(String path, int nb){

        this.sprites = new ArrayList<>();
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
            ii = new ImageIcon(tmppath);
            tmp = ii.getImage();
            this.sprites.add(tmp);
        }
    }

    public static void Shoot(){ projectiles.add( new Projectile(angle, posX, posY)); }

    // Getters
    // For game management
    public int getHP()    {return this.hp;}
    public int getPosX()   {return posX;}
    public int getPosY()   {return posY;}

    // For Update and Drawing
    public Image getImage()  {return this.sprites.get(this.currentsprite);}
    public LinkedList<Projectile> getProjectiles()    {return projectiles;}

    // For collision management, defines the hitbox
    public Rectangle getBounds() { return new Rectangle(posX, posY, width, height);}

}

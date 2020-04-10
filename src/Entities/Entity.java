package Entities;


import Tools.Constants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class Entity {


    // Used to identify which side an entity belongs to
    public enum Team {
        PLAYER1,
        PLAYER2,
        ENEMIES
    }

    Team team;
    boolean live;
    boolean dying;


    protected int posX;
    protected int posY;
    protected int dx;
    protected int dy;
    protected double angle;
    protected double dangle;

    protected int hp;

    // Graphics
    protected int nbSprites;
    protected List<Image> sprites;
    public int currentSprite;
    protected long lastFrame;
    public int animationFrame;


    protected int width;
    protected int height;

    public Entity(Team pteam, int x, int y, double pangle, String path, int nbSprites) {
        //constructor used for players and enemies
        team = pteam;
        live = true;
        dying = false;
        posX = x;
        posY = y;
        angle = pangle;
        currentSprite = 0;
        animationFrame = 0;
        lastFrame = System.currentTimeMillis();

        dangle = 0;
        dx = 0;
        dy = 0;

        LoadSpriteSheet(nbSprites, path, 0);
    }

    public Entity(Team pteam, int x, int y, double pangle, String path, int nbSprites, int pdx, int pdy){
        //used for loading missiles
        team = pteam;
        live = true;
        dying = false;
        posX = x;
        posY = y;
        angle = pangle;

        dangle = 0;
        dx = pdx;
        dy = pdy;
        if(getTeam() == Team.ENEMIES)
            LoadSpriteSheet(nbSprites, path, 1);
        else
            LoadSpriteSheet(nbSprites, path, 0);
    }

    public Entity(int x, int y, String path, int nbSprites, int num){
        posX = x;
        posY = y;

        LoadSpriteSheet(nbSprites, path, num);
    }


    protected void LoadSprites(int nb, String path){

        nbSprites = nb;
        sprites = new ArrayList<>();
        int n = path.length();

        String start = path.substring(0, n-4);
        String end = path.substring(n-4);
        String tmp;

        // Get the path for each sprites and Load the corresponding images
        for (int i = 0; i < nb; i++){
            tmp = start + i + end;
            sprites.add(new ImageIcon(tmp).getImage());
        }

        currentSprite = 0;
        width = getSprite(0).getWidth(null);
        height = getSprite(0).getHeight(null);

    }
    protected void LoadSpriteSheet(int nb, String path, int num) {
        nbSprites = nb;
        sprites = new ArrayList<>();
        int n = path.length();
        //for Different skins -needs implementation still
        int character = num;

        String start = path.substring(0, n-4);
        String end = path.substring(n-4);
        String tmp = start + character + end;
        try {
            BufferedImage sheet = ImageIO.read(new File(tmp).getAbsoluteFile());
            //size of separate images in sprite sheet
            int img_width = sheet.getWidth(null)/nbSprites;
            int img_height = sheet.getHeight(null);

            for(int i = 0; i < nb; i++){
                BufferedImage tmp_img = sheet.getSubimage(img_width * i,0, img_width, img_height);
                sprites.add(new ImageIcon(tmp_img).getImage());
            }

        } catch (Exception e){
            System.err.println(e);
        }

        currentSprite = 0;
        width = getSprite(0).getWidth(null);
        height = getSprite(0).getHeight(null);
    }

    public void animateLoop(int nbSprites) {
        if(System.currentTimeMillis() - lastFrame > Constants.TIME_BETWEEN_ANIMATIONS) {
            if(animationFrame < nbSprites-1) {
                animationFrame++;
            }
            else {
                animationFrame = 0;
            }
            lastFrame = System.currentTimeMillis();
        }
    }

    public boolean animatedOnce(int nbSprites) {
        animateLoop(nbSprites);
        if(animationFrame == nbSprites-1) {return true;}

        currentSprite = animationFrame;
        return false;
    }

    public int getPosX()   {return posX;}
    public int getPosY()   {return posY;}
    public int getDx()      {return dx;}
    public int getDy()      {return dy;}
    public double getAngle() {return angle;}

    public boolean isLive() {return live;}
    public void setLive(boolean set) {live = set;}
    public boolean isDying() {return dying;}
    public int getwidth() {return width;}
    public int getheight() {return height;}
    public Team getTeam() {return team;}


    public Image getSprite(int currentSprite){return sprites.get(currentSprite); }
    public Rectangle getHitbox() { return new Rectangle(posX, posY , width, height);}
}

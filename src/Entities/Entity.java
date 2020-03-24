package Entities;


import Tools.Constants;

import javax.swing.*;
import java.awt.*;
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

    public Entity(Team pteam, int x, int y, double pangle, String path, int nbSprites){

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

        LoadSprites(nbSprites, path);
    }

    public Entity(Team pteam, int x, int y, double pangle, String path, int nbSprites, int pdx, int pdy){

        team = pteam;
        live = true;
        dying = false;
        posX = x;
        posY = y;
        angle = pangle;

        dangle = 0;
        dx = pdx;
        dy = pdy;

        LoadSprites(nbSprites, path);
    }

    public Entity(int x, int y, String path, int nbSprites){
        posX = x;
        posY = y;

        LoadSprites(nbSprites, path);
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

    public int dyingAnimation(int nbSprites) {
        animateLoop(nbSprites);
        if(animationFrame == nbSprites-1) {live = false;}
        return animationFrame;
    }

    public int getPosX()   {return posX;}
    public int getPosY()   {return posY;}
    public double getAngle() {return angle;}
    public boolean isLive() {return live;}
    public boolean isDying() {return dying && isLive();}
    public int getwidth() {return width;}
    public int getheight() {return height;}
    public Team getTeam() {return team;}


    public Image getSprite(int currentSprite){return sprites.get(currentSprite); }
    public Rectangle getHitbox() { return new Rectangle(posX, posY , width, height);}
}

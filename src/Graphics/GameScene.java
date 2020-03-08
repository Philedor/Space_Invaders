package Graphics;

import Entities.Enemy;
import Entities.Entity;
import Entities.Player;
import Entities.Projectile;
import Tools.Constants;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import static Entities.Enemy.*;


public class GameScene extends JPanel implements ActionListener {

    public static int width;
    public static int height;

    private Image back;

    int DELAY = 15;

    public static List<Enemy> enemies = new ArrayList<>();
    private Player player;


    // Init the game Scene
    public GameScene(int w, int h) {
        width = w;
        height = h;

        back = new ImageIcon("resources/space.jpg").getImage();

        addKeyListener(new InputManager());

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        requestFocus();

        InitEntities();
        //playing background music. No music here yet, also need to figure out stopping music (pause state and such)
        //Audio.playSoundLoop(Constants.BACKGROUND_MUSIC);

        Timer timer = new Timer(DELAY, this);
        timer.start();
    }

    private void InitEntities() {
        player = new Player();

        // Enemies generation
        enemies = new ArrayList<>();

        for (int i = 0; i < Constants.LEVEL1.length; i++) {
            int[] en = Constants.LEVEL1[i];
            enemies.add(new Enemy(en[0], en[1]));
        }
    }


    // Is called by repaint()
    // Use to draw different game states (Menu, In Game, Pause, End Screen...)
    @Override
    public void paintComponent(Graphics graphics1) {
        Graphics2D graphics = (Graphics2D) graphics1;
        super.paintComponent(graphics1);
        // different drawing functions for different game states
        graphics.drawImage(back, 0, 0, this);

        // animating player


        for (Enemy enemy : enemies) {
            graphics.drawImage(enemy.getSprite(enemy.currentSprite), enemy.getPosX(), enemy.getPosY(), this);
        }

        for (Projectile proj : player.getProjectiles()) {
            graphics.rotate(Math.PI/2 - Math.toRadians(proj.getAngle() ),  (double) proj.getwidth() /2 + proj.getPosX(), (double) proj.getwheight()/2 + proj.getPosY());
            graphics.drawImage(proj.getSprite(0), proj.getPosX(), proj.getPosY(), this);
            graphics.rotate(-(Math.PI/2 - Math.toRadians(proj.getAngle()) ),  (double) proj.getwidth() /2 + proj.getPosX(), (double) proj.getwheight()/2 + proj.getPosY());
        }

        for (Projectile proj : Enemy.getProjectiles()) {
            graphics.rotate(Math.PI/2 - Math.toRadians(proj.getAngle() ),  (double) proj.getwidth() /2 + proj.getPosX(), (double) proj.getwheight()/2 + proj.getPosY());
            graphics.drawImage(proj.getSprite(0), proj.getPosX(), proj.getPosY(), this);
            graphics.rotate(-(Math.PI/2 - Math.toRadians(proj.getAngle()) ),  (double) proj.getwidth() /2 + proj.getPosX(), (double) proj.getwheight()/2 + proj.getPosY());
        }

        player.animate();
        graphics.rotate(Math.PI/2 - Math.toRadians(player.getAngle() ),  (double) player.getwidth() /2 + player.getPosX(), (double) player.getwheight()/2 + player.getPosY());
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, player.opacity));
        graphics.drawImage(player.getSprite(player.currentSprite), player.getPosX(), player.getPosY(), this);
        graphics.rotate(-(Math.PI/2 - Math.toRadians(player.getAngle() )),  (double) player.getwidth() /2 + player.getPosX(), (double) player.getwheight()/2 + player.getPosY());

        graphics.dispose();

        Toolkit.getDefaultToolkit().sync();
    }


    /**
     * Update function called automatically whenever an action takes place
     *
     * @param e the event
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        // Update Player
        player.Update();
        if (enemies.size() > 0)
            updateEnemies();
        updateProjectiles();
        ContactCheck();

        // collision checked during update to avoid calling multiple loops to go through each lists every time
        repaint();
    }


    // Apply when enemies reach end of line
    private void Reached_End() {
        for (Enemy enemy : enemies) {
            enemy.InvertDirection();
            enemy.MoveDown();
        }
    }

    private void updateEnemies() {

        // TODO add check if end animation done or replace the condition here
        enemies.removeIf(enemy -> !enemy.isLive());

        if (System.currentTimeMillis() - enemyLastMove > enemyMoveTime){
                enemyLastMove = System.currentTimeMillis();

            //Enemy left = getMostLeftEnemy();
            Enemy right = getMostRightEnemy();

            if (!enemywentdown &&
                    ((getEnemyLeftPos() <= Constants.GAME_MIN_WIDTH ) ||
                    (getEnemyRightPos() >= Constants.GAME_MAX_WIDTH - right.getwidth() ))) {
                Reached_End();
                enemywentdown = true;
            }

            // Actually move the enemies
            else {
                enemywentdown = false;
                for (Enemy e : enemies) {
                    e.Shoot();
                    e.MoveSideways();
                }
            }
        }

    }


    /**
     * Projectile update and collision check
     */
    private void updateProjectiles() {

        // Check player's projectile
        for (Projectile proj: player.getProjectiles()) {
            // if condition to add for explosion animation
            proj.Update();
            ProjectileCollisionCheck(proj, Entity.Team.PLAYER);

        }

        // Check enemy projectiles
        for (Projectile proj : Enemy.getProjectiles()) {
            proj.Update();
            if (proj.isLive()){
                ProjectileCollisionCheck(proj, Entity.Team.ENEMIES);
            }
        }

    }

    public void ContactCheck(){
        if (!player.invincible){
            Rectangle playerHitbox = player.getHitbox();
            for (Enemy enemy : enemies){
                if (playerHitbox.intersects(enemy.getHitbox())){
                    player.damage(enemy.contactdmg);
                    System.out.println("Player hit");
                }
            }
        }
    }

    /**
     * Chack Collision between enemy projectile and player
     * @param projectile the projectile we re checking
     */
    private void ProjectileCollisionCheck(Projectile projectile, Entity.Team team) {
        Rectangle projectilehitbox = projectile.getHitbox();
        if (team == Entity.Team.PLAYER) {
            for (Enemy enemy : enemies) {
                if (projectilehitbox.intersects(enemy.getHitbox())){
                    enemy.damage(projectile.getDmg());
                    projectile.damage(1);
                }
            }
        }
        else if (projectilehitbox.intersects(player.getHitbox())){
                player.damage(projectile.getDmg());
                projectile.damage(1);
        }

    }


    // Get the enemy block left coordinate
    private Enemy getMostLeftEnemy(){
        Enemy enemy = null;
        int minX = Constants.GAME_MAX_WIDTH;
        int tmp;
        for (Enemy e : enemies){
            tmp = e.getPosX();
            if (tmp <= minX){
                minX = tmp;
                enemy = e;
            }
        }
            return enemy;
    }

    // Get the enemy block right coordinate
    private Enemy getMostRightEnemy() {
        Enemy enemy = null;
        int maxX = Constants.GAME_MIN_WIDTH;
        int tmp;
        for (Enemy e : enemies) {
            tmp = e.getPosX();
            if (tmp >= maxX) {
                maxX = tmp;
                enemy = e;
            }
        }
        return enemy;
    }

    private int getEnemyLeftPos(){
        Enemy enemy = getMostLeftEnemy();
        if(enemy != null)
            return enemy.getPosX();
        else return 0;
    }
    private int getEnemyRightPos() {
        Enemy enemy = getMostRightEnemy();
        if (enemy != null)
            return enemy.getPosX();
        else return 0;
    }

    // Key pressed Management redirected to player Input
    private class InputManager extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            player.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) { player.keyReleased(e);}
    }


}

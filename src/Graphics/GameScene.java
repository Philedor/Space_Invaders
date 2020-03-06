package Graphics;

import Entities.Enemy;
import Entities.Entity;
import Entities.Player;
import Entities.Projectile;
import Tools.Constants;
import com.sun.source.tree.ArrayAccessTree;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;


public class GameScene extends JPanel implements ActionListener {

    public static int width;
    public static int height;

    private Image back;

    private Timer timer;
    int DELAY = 15;

    private boolean running = false;
    public static List<Enemy> enemies = new ArrayList<>();
    private Player player;
    private long enemyLastMove;
    private long enemyMoveTime = 1000;
    private boolean enemywentdown = false;


    private Thread thread;

    // Init the game Scene
    public GameScene(int w, int h) {
        width = w;
        height = h;
        running = true;

        back = new ImageIcon("resources/space.jpg").getImage();

        addKeyListener(new InputManager());

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        requestFocus();

        InitEntities();

        timer = new Timer(DELAY, this);
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
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        // different drawing functions for different game states
        graphics.drawImage(back, 0, 0, this);

        // animating player


        for (Enemy enemy : enemies) {
            graphics.drawImage(enemy.getSprite(enemy.currentSprite), enemy.getPosX(), enemy.getPosY(), this);
        }
        for (Projectile proj : player.getProjectiles()) {
            graphics.drawImage(proj.getSprite(0), proj.getPosX(), proj.getPosY(), this);
        }
        player.animate();
        graphics.drawImage(player.getSprite(player.currentSprite), player.getPosX(), player.getPosY(), this);
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
        if (System.currentTimeMillis() - enemyLastMove > enemyMoveTime)
            updateEnemies();
        updateProjectiles();

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
        enemyLastMove = System.currentTimeMillis();

        Enemy left = getMostLeftEnemy();
        Enemy right = getMostRightEnemy();

        if (!enemywentdown &&
                ((getEnemyLeftPos() <= Constants.GAME_MIN_WIDTH ) ||
                (getEnemyRightPos() >= Constants.GAME_MAX_WIDTH ))) {
            Reached_End();
            enemywentdown = true;
        }

        // Actually move the enemies
        else {
            enemywentdown = false;
            for (Enemy e : enemies) {
                e.MoveSideways();
                PlayerCollision(e);     // Check Collision
            }
        }

    }


    private void updateProjectiles() {
        for (Projectile proj: player.getProjectiles()) {
            // if condition to add for explosion animation
            proj.Update();
            if (!proj.isLive()){
                player.getProjectiles().remove(proj);
                ProjectileCollisionCheck(proj);
            }
        }

        /*for(Enemy enemy : enemies) {
            for (Projectile proj : enemy.getProjectiles()) {
                // if condition to add for explosion animation
                proj.Update();
                if (proj.isLive()){
                    player.getProjectiles().remove(proj);
                    ProjectileCollisionCheck(proj);
                }
            }
        }*/
    }

    /**
     * Chack Collision between enemy projetile and player
     * @param projectile the projectile we re checking
     */
    private void ProjectileCollisionCheck(Projectile projectile) {
        Rectangle playerHitbox = player.getHitbox();
        if (playerHitbox.intersects(projectile.getHitbox())){
            projectile.Kill();
            player.damage(projectile.getDmg());
        }
    }

    /**
     * collision check between player and enemies
     * @param e the enemy with which we're checking
     */
    private void PlayerCollision(Enemy e){
        Rectangle playerHitbox = player.getHitbox();
        if (playerHitbox.intersects(e.getHitbox()))
            player.damage(Constants.CONTACT_DAMAGE);
    }

    /**
     * Checking if the player projectiles hit the enemies
     * Call this function in the player update loop to get the projectiles tht he's shooting
     * @param p the projectile we're checking
     */
    // TODO check the collision
    private void AlienCollision(Projectile p){
        Rectangle projectileHitBox = p.getHitbox();
        for (Enemy e : enemies){
            if (projectileHitBox.intersects(e.getHitbox())){
                p.Kill();
                e.damage(p.getDmg());
            }
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

    private int getEnemyLeftPos(){ return getMostLeftEnemy().getPosX();}
    private int getEnemyRightPos(){ return getMostRightEnemy().getPosX();}


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

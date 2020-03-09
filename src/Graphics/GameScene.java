package Graphics;

import Entities.Enemy;
import Entities.Entity;
import Entities.Player;
import Entities.Projectile;
import Tools.Constants;
import Tools.InputManager;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static Entities.Enemy.*;


public class GameScene extends JPanel implements ActionListener {

    public static int width;
    public static int height;

    private Image back;
    private boolean running = true;
    private static boolean pause = false;

    int DELAY = 15;

    public static List<Enemy> enemies = new ArrayList<>();
    public static List<Player> players = new ArrayList<>();

    private int p1score = 0;
    private int p2score = 0;
    int px = 10;
    int p1y = 20;
    int p2y = 40;

    private String p1 = "Player 1 : ";
    private String p2 = "Player 2 : ";

    // Init the game Scene
    public GameScene(int w, int h, int nb_players) {
        width = w;
        height = h;

        back = new ImageIcon("resources/space.jpg").getImage();

        addKeyListener(new InputManager());

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        requestFocus();

        InitEntities(nb_players);
        //playing background music. No music here yet, also need to figure out stopping music (pause state and such)
        //Audio.playSoundLoop(Constants.BACKGROUND_MUSIC);

        Timer timer = new Timer(DELAY, this);
        timer.start();
    }

    private void InitEntities(int nb_players) {
        for (int i = 0; i < nb_players; i ++)
            players.add(new Player(Team.values()[i]));

        // Enemies generation
        enemies = new ArrayList<>();
        InitEnemies();

    }

    private void InitEnemies(){
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
        if (!running || pause)
            graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
        graphics.drawImage(back, 0, 0, this);

        if (!running || pause)
            graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, System.currentTimeMillis()%7 == 0? 0.3f : 0.5f));


        for (Enemy enemy : enemies) {
            graphics.drawImage(enemy.getSprite(enemy.currentSprite), enemy.getPosX(), enemy.getPosY(), this);
        }

        for (Player player : players) {
            for (Projectile proj : player.getProjectiles()) {
                graphics.rotate(Math.PI / 2 - Math.toRadians(proj.getAngle()), (double) proj.getwidth() / 2 + proj.getPosX(), (double) proj.getwheight() / 2 + proj.getPosY());
                graphics.drawImage(proj.getSprite(0), proj.getPosX(), proj.getPosY(), this);
                graphics.rotate(-(Math.PI / 2 - Math.toRadians(proj.getAngle())), (double) proj.getwidth() / 2 + proj.getPosX(), (double) proj.getwheight() / 2 + proj.getPosY());
            }
        }
        for (Projectile proj : Enemy.getProjectiles()) {
            graphics.rotate(Math.PI/2 - Math.toRadians(proj.getAngle() ),  (double) proj.getwidth() /2 + proj.getPosX(), (double) proj.getwheight()/2 + proj.getPosY());
            graphics.drawImage(proj.getSprite(0), proj.getPosX(), proj.getPosY(), this);
            graphics.rotate(-(Math.PI/2 - Math.toRadians(proj.getAngle()) ),  (double) proj.getwidth() /2 + proj.getPosX(), (double) proj.getwheight()/2 + proj.getPosY());
        }

        // animating player
        for (Player player : players) {

            // Update display score
            if (player.getTeam() == Team.PLAYER2){
                p2score = player.score;
            }
            else p1score = player.score;


            player.animate();
            graphics.rotate(Math.PI / 2 - Math.toRadians(player.getAngle()), (double) player.getwidth() / 2 + player.getPosX(), (double) player.getwheight() / 2 + player.getPosY());
            if (running && !pause)
                graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, player.opacity));
            graphics.drawImage(player.getSprite(player.currentSprite), player.getPosX(), player.getPosY(), this);
            graphics.rotate(-(Math.PI / 2 - Math.toRadians(player.getAngle())), (double) player.getwidth() / 2 + player.getPosX(), (double) player.getwheight() / 2 + player.getPosY());
        }

        // Display score
        graphics.setColor(Color.WHITE);
        Font font = new Font("Helvetica", Font.BOLD, 20);
        graphics.setFont(font);        graphics.drawString(p1 + p1score, px, p1y);
        graphics.drawString(p2 + p2score, px, p2y);

        if(pause){
            graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            graphics.setColor(Color.WHITE);
            Font font2 = new Font("Helvetica", Font.BOLD, 80);
            graphics.setFont(font2);
            FontMetrics fm = graphics.getFontMetrics();
            int x = ((getWidth() - fm.stringWidth("PAUSE")) / 2);
            int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
            graphics.drawString("PAUSE", x, y);
        }

        else if (!running){
            graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            graphics.setColor(Color.WHITE);
            Font font3 = new Font("Helvetica", Font.BOLD, 80);
            graphics.setFont(font3);
            FontMetrics fm = graphics.getFontMetrics();
            int x = ((getWidth() - fm.stringWidth("GAME OVER")) / 2);
            int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
            graphics.drawString("GAME OVER", x, y);
        }


        graphics.dispose();

        Toolkit.getDefaultToolkit().sync();
    }


    /**
     * Update function called automatically whenever an action takes place
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (enemies.size() == 0){
            InitEnemies();
            // TODO Next level
        }
        if (getlowerposX() >= Constants.GAME_OVER_Y || players.size() == 0)
            running = false;
        if (running && !pause) {
            players.removeIf(player -> !player.isLive());
            // Update Player
            for (Player player : players)
                player.Update();
            if (enemies.size() > 0)
                updateEnemies();
            updateProjectiles();
            ContactCheck();
        }
        // collision checked during update to avoid calling multiple loops to go through each lists every time
        repaint();

    }


    /**
     * Whenever the enemy block reached a side, makes them go down
      */
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

            // If the enemy is at the end of the line and didn't move down already
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

        Enemy.getProjectiles().removeIf(enemy -> !enemy.isLive());
        // Check player's projectile
        for (Player player : players) {
            player.getProjectiles().removeIf(projectile -> !projectile.isLive());
            for (Projectile proj : player.getProjectiles()) {
                // if condition to add for explosion animation
                proj.Update();
                ProjectileCollisionCheck(proj, player.getTeam());
            }
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
        for (Player player : players) {
            if (!player.invincible){
                Rectangle playerHitbox = player.getHitbox();
                for (Enemy enemy : enemies){
                    if (playerHitbox.intersects(enemy.getHitbox())){
                        player.damage(enemy.contactdmg);
                    }
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
        if (team == Team.ENEMIES){
            for (Player player : players) {
                if (projectilehitbox.intersects(player.getHitbox())) {
                    player.damage(projectile.getDmg());
                    projectile.damage(1);
                }
            }
        }

        else {
            for (Enemy enemy : enemies) {
                if (projectilehitbox.intersects(enemy.getHitbox())) {
                    enemy.damage(projectile.getDmg());
                    projectile.damage(1);
                    if (team ==  players.get(0).getTeam())
                        players.get(0).score += 1;
                    else if (players.size() == 2 && team == players.get(1).getTeam())
                        players.get(1).score += 1;
                }
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

    public int getlowerposX(){
        Enemy enemy = enemies.get(enemies.size()-1);
        return enemy.getPosY();
    }

    public static void setPause(boolean x){
        pause = x;
    }
    public static boolean getPause(){return pause;}




}

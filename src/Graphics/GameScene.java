package Graphics;

import Entities.Enemy;
import Entities.Entity;
import Entities.Player;
import Entities.Projectile;
import Tools.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Formatter;

import static Entities.Enemy.*;


public class GameScene extends JPanel implements ActionListener {

    public static int width;
    public static int height;

    private Image[] back = new Image[2];
    private Audio backsong;
    private int bg_posy = 0;
    private int bg_posy2 = -1000;
    private boolean running = true;
    private static boolean pause = false;

    private Entity[] health = new Entity[2];
    private Entity sdisplay;
    private Entity topHUD;

    int DELAY = 15;

    public static List<Enemy> enemies = new ArrayList<>();
    public static List<Player> players = new ArrayList<>();

    private int p1score = 0;
    private int p2score = 0;
    private StopWatch stopWatch = new StopWatch();
    int px = 30;
    int p1y = 30;
    int p2y = 50;
    int tx = 590;
    int ty = 50;

    private String p1 = "PLAYER 1 : ";
    private String p2 = "PLAYER 2 : ";

    // Init the game Scene
    public GameScene(int w, int h, int nb_players) {
        width = w;
        height = h;

        back[0] = new ImageIcon(Constants.BACKGROUND_IMAGE).getImage();
        back[1] = new ImageIcon(ImageManipulation.flipHorizontally(Constants.BACKGROUND_IMAGE)).getImage();
        backsong = new Audio(Constants.BACKGROUND_MUSIC);

        InitHUD();

        addKeyListener(new InputManager());

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        requestFocus();

        InitEntities(nb_players);
        //playing background music. No music here yet, also need to figure out stopping music (pause state and such)

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

    private void InitHUD(){
        //loading health HUD
        for(int i = 0; i < 2; i++) {
            health[i] = new Entity(0, 928, Constants.HP_DISPLAY, Constants.NB_HP_DISPLAY, i);
        }

        //loading small display
        sdisplay = new Entity(6,937, Constants.SMALL_DISPLAY, Constants.NB_SMALL_DISPLAY, 0);

        //loading top HUD
        stopWatch.start();
        topHUD = new Entity(0, 0, Constants.TOP_HUD, Constants.NB_TOP_HUD, 0);
    }


    // Is called by repaint()
    // Use to draw different game states (Menu, In Game, Pause, End Screen...)
    @Override
    public void paintComponent(Graphics graphics1) {
        Graphics2D graphics = (Graphics2D) graphics1;
        super.paintComponent(graphics1);
        // different drawing functions for different game states
        if (!pause) {
            ScrollBG(graphics);
            stopWatch.resume();
            if(!backsong.isRunning()) {
                backsong.playSoundLoop(0.5f);
            }
        }

        // Flickering
        if (!running || pause) {
            graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0));
            backsong.stopAudio();
        }


        for (Enemy enemy : enemies) {
            graphics.drawImage(enemy.getSprite(enemy.currentSprite), enemy.getPosX(), enemy.getPosY(), this);
            if(enemy.isDying()) {
                if(enemy.animatedOnce(Constants.NB_ENEMY_DEATH_SPRITE)) {
                    enemy.setLive(false);
                }
            }
        }

        drawProjectiles(graphics);
        drawPlayer(graphics);


        // Displays depending on the state
        if(pause) Pause(graphics);
        else if (!running) GameOver(graphics);

        drawHUD(graphics);

        // Display score
        graphics.setColor(new Color(216, 97, 225));
        Font font = new Font("Helvetica", Font.BOLD, 17);
        graphics.setFont(font);
        graphics.drawString(p1 + String.format("%03d", p1score), px, p1y);
        graphics.drawString(p2 + String.format("%03d", p2score), px, p2y);
        // Display time
        String time = stopWatch.toMinAndSecString();
        font = new Font("Helvetica", Font.BOLD, 30);
        graphics.setFont(font);
        graphics.drawString(time, tx, ty);

        graphics.dispose();
        Toolkit.getDefaultToolkit().sync();
    }

    private void Pause(Graphics2D graphics){
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        graphics.setColor(Color.WHITE);
        Font font2 = new Font("Helvetica", Font.BOLD, 80);
        graphics.setFont(font2);
        FontMetrics fm = graphics.getFontMetrics();
        int x = ((getWidth() - fm.stringWidth("PAUSE")) / 2);
        int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
        graphics.drawString("PAUSE", x, y);
        stopWatch.pause();
    }

    private void GameOver(Graphics2D graphics){
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        graphics.setColor(Color.WHITE);
        Font font3 = new Font("Helvetica", Font.BOLD, 80);
        graphics.setFont(font3);
        FontMetrics fm = graphics.getFontMetrics();
        int x = ((getWidth() - fm.stringWidth("GAME OVER")) / 2);
        int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
        graphics.drawString("GAME OVER", x, y);
        backsong.closeAudio();
        stopWatch.stop();
    }

    private void ScrollBG(Graphics2D graphics){
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
        graphics.drawImage(back[1], 0, bg_posy2, this);
        graphics.drawImage(back[0], 0, bg_posy, this);

        //scrolling down backgrounds
        bg_posy = bg_posy + Constants.BACKGROUND_SPEED;
        bg_posy2 = bg_posy2 + Constants.BACKGROUND_SPEED;
        //moving each individual image to the top
        if(bg_posy == 1000) { bg_posy = -1000;}
        if(bg_posy2 == 1000) { bg_posy2 = -1000;}
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    private void drawHUD(Graphics2D graphics){
        //update hp display
        int[] hp= new int[2];

        for (Player player : players) {
            if(player.getHp() >= 0) {
                hp[players.indexOf(player)] = player.getHp();
            }
            else {
                hp[players.indexOf(player)] = 0;
            }
        }

        graphics.drawImage(health[0].getSprite(hp[1]), health[0].getPosX(), health[0].getPosY(), this);
        graphics.drawImage(health[1].getSprite(hp[0]),health[1].getPosX() + 519, health[1].getPosY(), this);

        //updating the small display in the bottom left corner
        if(!players.isEmpty()) {
            //moving right
            if(players.get(0).getDx() > 0){
                sdisplay.currentSprite = 3;
            }
            else if(players.get(0).getDx() < 0) {
                sdisplay.currentSprite = 5;
            }
            else {
                sdisplay.currentSprite = 2;
            }
        } else {
            sdisplay.currentSprite = 0;
            sdisplay.animateLoop(2);
        }
        graphics.drawImage(sdisplay.getSprite(sdisplay.currentSprite), sdisplay.getPosX(), sdisplay.getPosY(), this);
        //drawing topHUD
        graphics.drawImage(topHUD.getSprite(topHUD.currentSprite), topHUD.getPosX(), topHUD.getPosY(), this);
    }

    private void drawPlayer(Graphics2D graphics){
        // animating player
        for (Player player : players) {

            // Update display score
            if (player.getTeam() == Team.PLAYER2){
                p2score = player.score;
            }
            else p1score = player.score;


            if(player.isDying()) {
                if(player.animatedOnce(Constants.NB_PLAYER_DEATH_SPRITE)) {
                    player.setLive(false);
                }
            }
            else {
                player.animateMovement();
            }
            graphics.rotate(Math.PI / 2 - Math.toRadians(player.getAngle()), (double) player.getwidth() / 2 + player.getPosX(), (double) player.getheight() / 2 + player.getPosY());
            if (running && !pause)
                graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, player.opacity));
            graphics.drawImage(player.getSprite(player.currentSprite), player.getPosX(), player.getPosY(), this);
            graphics.rotate(-(Math.PI / 2 - Math.toRadians(player.getAngle())), (double) player.getwidth() / 2 + player.getPosX(), (double) player.getheight() / 2 + player.getPosY());

        }
    }

    private void drawProjectiles(Graphics2D graphics){
        for (Player player : players) {
            for (Projectile proj : player.getProjectiles()) {
                graphics.rotate(Math.PI / 2 - Math.toRadians(proj.getAngle()), (double) proj.getwidth() / 2 + proj.getPosX(), (double) proj.getheight() / 2 + proj.getPosY());
                graphics.drawImage(proj.getSprite(proj.currentSprite), proj.getPosX(), proj.getPosY(), this);
                graphics.rotate(-(Math.PI / 2 - Math.toRadians(proj.getAngle())), (double) proj.getwidth() / 2 + proj.getPosX(), (double) proj.getheight() / 2 + proj.getPosY());
                //death animation for projectile
                if(proj.isDying()) {
                    if(proj.animatedOnce(Constants.NB_PROJECTILE_EXPLOSION_SPRITE)) {
                        proj.setLive(false);
                    }
                }
            }
        }
        for (Projectile proj : Enemy.getProjectiles()) {
            graphics.rotate(Math.PI/2 - Math.toRadians(proj.getAngle() ),  (double) proj.getwidth() /2 + proj.getPosX(), (double) proj.getheight()/2 + proj.getPosY());
            graphics.drawImage(proj.getSprite(proj.currentSprite), proj.getPosX(), proj.getPosY(), this);
            graphics.rotate(-(Math.PI/2 - Math.toRadians(proj.getAngle()) ),  (double) proj.getwidth() /2 + proj.getPosX(), (double) proj.getheight()/2 + proj.getPosY());
            //death animation for projectile
            if(proj.isDying()) {
                if(proj.animatedOnce(Constants.NB_PROJECTILE_EXPLOSION_SPRITE)) {
                    proj.setLive(false);
                }
            }
        }
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
                if(player.isLive())
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
            if (!player.invincible && !player.isDying()){
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
                if (projectilehitbox.intersects(player.getHitbox()) && !projectile.isDying() && !player.isDying()) {
                    player.damage(projectile.getDmg());
                    projectile.damage(1);
                }
            }
        }

        else {
            for (Enemy enemy : enemies) {
                if (projectilehitbox.intersects(enemy.getHitbox()) && !projectile.isDying() && !enemy.isDying()) {
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

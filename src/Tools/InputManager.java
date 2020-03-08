package Tools;

import Entities.Player;
import Graphics.GameScene;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// Key pressed Management redirected to player Input
public class InputManager extends KeyAdapter {

    @Override
    public void keyPressed(KeyEvent e) {
        for(Player player : GameScene.players)
            player.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        for(Player player : GameScene.players)
            player.keyReleased(e);
    }
}
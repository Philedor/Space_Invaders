package Tools;

import Entities.Player;
import Graphics.GameScene;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.VK_P;

// Key pressed Management redirected to player Input
public class InputManager extends KeyAdapter {

    @Override
    public void keyPressed(KeyEvent e) {
        if (GameScene.getState() == "GAME") {
            for (Player player : GameScene.players)
                player.keyPressed(e);
            int key = e.getKeyCode();
            if (key == VK_P) {
                GameScene.setPause(!GameScene.getPause());
            }
        } else if (GameScene.getState() == "MENU") {
                GameScene.menu.keyPressed(e);
            }
        }

    @Override
    public void keyReleased(KeyEvent e) {
        if (GameScene.getState() == "GAME") {
            for (Player player : GameScene.players)
                player.keyReleased(e);
        }else if (GameScene.getState() == "MENU") {
            GameScene.menu.keyReleased(e);
        }
    }
}
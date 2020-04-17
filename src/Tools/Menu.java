package Tools;

import java.awt.event.KeyEvent;
import Graphics.GameScene;

public class Menu {

    private boolean upPressed = false;
    private boolean downPressed = false;

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_S) {
            if ((!downPressed)&&(GameScene.getButton() == "PLAY")) {
                GameScene.setButton("SETTINGS");
            } else if ((!downPressed)&&(GameScene.getButton() == "SETTINGS")) {
                GameScene.setButton("EXIT");
            }
            downPressed = true ;
        } else if (key == KeyEvent.VK_W) {
            if ((!upPressed)&&(GameScene.getButton() == "SETTINGS")) {
                GameScene.setButton("PLAY");
            } else if ((!upPressed)&&(GameScene.getButton() == "EXIT")) {
                GameScene.setButton("SETTINGS");
            }
            upPressed = true ;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode() ;
        if (key == KeyEvent.VK_S) {
            downPressed = false ;
        } else if (key == KeyEvent.VK_W) {
            upPressed = false ;
        }
    }
}

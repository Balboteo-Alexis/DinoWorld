package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean left, right, jump, restart, enter;



    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT  -> left = true;
            case KeyEvent.VK_RIGHT -> right = true;
            case KeyEvent.VK_SPACE -> jump = true;
            case KeyEvent.VK_R     -> restart = true;
            case KeyEvent.VK_ENTER -> enter = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT  -> left = false;
            case KeyEvent.VK_RIGHT -> right = false;
            case KeyEvent.VK_SPACE -> jump = false;
            case KeyEvent.VK_R     -> restart = false;
            case KeyEvent.VK_ENTER -> enter = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}
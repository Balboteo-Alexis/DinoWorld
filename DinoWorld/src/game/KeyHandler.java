package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean left, right, jump;

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_LEFT)  left  = true;
        if (code == KeyEvent.VK_RIGHT) right = true;
        if (code == KeyEvent.VK_UP)    jump  = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_LEFT)  left  = false;
        if (code == KeyEvent.VK_RIGHT) right = false;
        if (code == KeyEvent.VK_UP)    jump  = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}
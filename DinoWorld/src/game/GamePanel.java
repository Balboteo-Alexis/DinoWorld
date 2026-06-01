package game;

import entity.Player;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 500;
    private static final int FPS = 60;

    private Thread gameThread;
    private Player player;
    private KeyHandler keyHandler;

    public GamePanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);

        keyHandler = new KeyHandler();
        player = new Player(keyHandler);

        this.addKeyListener(keyHandler);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1_000_000_000.0 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        player.update();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Cielo
        g2.setColor(new Color(135, 206, 235));
        g2.fillRect(0, 0, WIDTH, HEIGHT);

        // Suelo
        g2.setColor(new Color(34, 139, 34));
        g2.fillRect(0, 400, WIDTH, 100);

        // Dinosaurio
        player.draw(g2);

        g2.dispose();
    }
}
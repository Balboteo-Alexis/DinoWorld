package game;

import entity.Enemy;
import entity.Player;
import level.Level;
import screen.CreditsScreen;
import screen.MenuScreen;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {

    public static final int WIDTH  = 1000;
    public static final int HEIGHT = 500;
    private static final int FPS   = 60;

    private Thread gameThread;
    private KeyHandler keyHandler = new KeyHandler();
    private Camera camera = new Camera();

    private GameState gameState = GameState.MENU;

    private Player player;
    private Level level;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private int currentLevel = 1;
    private static final int TOTAL_LEVELS = 5;

    // Nido (zona final del nivel)
    private int nestX = 4700;
    private int nestY = 340;
    private int nestW = 100;
    private int nestH = 60;

    private MenuScreen menuScreen;
    private CreditsScreen creditsScreen;

    public GamePanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(keyHandler);

        menuScreen   = new MenuScreen(WIDTH, HEIGHT);
        creditsScreen = new CreditsScreen(WIDTH, HEIGHT);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleMouse(e);
            }
        });
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    private void handleMouse(MouseEvent e) {
        switch (gameState) {
            case MENU -> {
                menuScreen.handleClick(e);
                if (menuScreen.startClicked) {
                    menuScreen.startClicked = false;
                    startLevel(1);
                }
                if (menuScreen.creditsClicked) {
                    menuScreen.creditsClicked = false;
                    gameState = GameState.CREDITS;
                }
            }
            case CREDITS -> {
                creditsScreen.handleClick(e);
                if (creditsScreen.backClicked) {
                    creditsScreen.backClicked = false;
                    gameState = GameState.MENU;
                }
            }
            default -> {}
        }
    }

    private void startLevel(int levelNumber) {
        currentLevel = levelNumber;
        level  = new Level(levelNumber);
        player = new Player(100, 300, keyHandler);
        camera = new Camera();
        enemies.clear();
        loadEnemies(levelNumber);
        gameState = GameState.PLAYING;
    }

    private void loadEnemies(int levelNumber) {
        switch (levelNumber) {
            case 1 -> {
                enemies.add(new Enemy(500,  340, 400,  700,  2));
                enemies.add(new Enemy(1000, 340, 800,  1200, 2));
                enemies.add(new Enemy(1800, 340, 1600, 2000, 2));
                enemies.add(new Enemy(2500, 340, 2300, 2700, 2));
                enemies.add(new Enemy(3200, 340, 3000, 3400, 2));
            }
            case 2 -> {
                enemies.add(new Enemy(500,  340, 400,  750,  3));
                enemies.add(new Enemy(1100, 340, 900,  1300, 3));
                enemies.add(new Enemy(1900, 340, 1700, 2100, 3));
                enemies.add(new Enemy(2600, 340, 2400, 2800, 3));
                enemies.add(new Enemy(3300, 340, 3100, 3500, 3));
                enemies.add(new Enemy(4000, 340, 3800, 4200, 3));
            }
            case 3 -> {
                enemies.add(new Enemy(600,  340, 400,  800,  3));
                enemies.add(new Enemy(1200, 340, 1000, 1400, 4));
                enemies.add(new Enemy(1900, 340, 1700, 2100, 3));
                enemies.add(new Enemy(2600, 340, 2400, 2800, 4));
                enemies.add(new Enemy(3300, 340, 3100, 3500, 3));
                enemies.add(new Enemy(4000, 340, 3800, 4200, 4));
            }
            case 4 -> {
                enemies.add(new Enemy(500,  340, 300,  700,  4));
                enemies.add(new Enemy(1100, 340, 900,  1300, 4));
                enemies.add(new Enemy(1800, 340, 1600, 2000, 5));
                enemies.add(new Enemy(2500, 340, 2300, 2700, 4));
                enemies.add(new Enemy(3200, 340, 3000, 3400, 5));
                enemies.add(new Enemy(3900, 340, 3700, 4100, 4));
                enemies.add(new Enemy(4400, 340, 4200, 4600, 5));
            }
            case 5 -> {
                // Boss — un solo enemigo con mucha vida (por definir, de momento más grande)
                enemies.add(new Enemy(2000, 300, 1000, 3500, 3));
            }
        }
    }

    @Override
    public void run() {
        double drawInterval = 1_000_000_000.0 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();

        while (gameThread != null) {
            long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    private void update() {
        if (gameState == GameState.PLAYING) {

            player.update(level);
            camera.update(player);

            for (Enemy enemy : enemies) {
                enemy.update(level);
            }

            for (level.Obstacle obstacle : level.obstacles) {
                obstacle.update();
            }

            for (Enemy enemy : enemies) {
                if (enemy.dead) continue;
                if (enemy.isStompedBy(player)) {
                    enemy.dead   = true;
                    enemy.active = false;
                    player.velocityY = -10;
                } else if (player.getBounds().intersects(enemy.getBounds())) {
                    gameState = GameState.GAME_OVER;
                    return;
                }
            }

            for (level.Obstacle obstacle : level.obstacles) {
                if (player.getBounds().intersects(obstacle.getBounds())) {
                    gameState = GameState.GAME_OVER;
                    return;
                }
            }

            boolean allDead = enemies.stream().allMatch(e -> e.dead);
            java.awt.Rectangle nestBounds = new java.awt.Rectangle(nestX, nestY, nestW, nestH);
            if (allDead && player.getBounds().intersects(nestBounds)) {
                gameState = GameState.LEVEL_COMPLETE;
            }
        }

        if (gameState == GameState.GAME_OVER && keyHandler.restart) {
            startLevel(currentLevel);
        }

        if (gameState == GameState.LEVEL_COMPLETE) {
            if (keyHandler.enter && currentLevel < TOTAL_LEVELS) {
                startLevel(currentLevel + 1);
            }
            if (keyHandler.restart && currentLevel < TOTAL_LEVELS) {
                startLevel(currentLevel);
            }
            if (keyHandler.restart && currentLevel == TOTAL_LEVELS) {
                gameState = GameState.MENU;
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        switch (gameState) {
            case MENU        -> menuScreen.draw(g2);
            case CREDITS     -> creditsScreen.draw(g2);
            case PLAYING,
                 GAME_OVER,
                 LEVEL_COMPLETE -> drawGame(g2);
        }

        g2.dispose();
    }

    private void drawGame(Graphics2D g2) {
        int camX = camera.x;

        // Cielo
        g2.setColor(new Color(135, 206, 235));
        g2.fillRect(0, 0, WIDTH, HEIGHT);

        // Nubes (se mueven lentamente con la cámara — efecto parallax)
        g2.setColor(new Color(255, 255, 255, 80));
        int[] cloudOffsets = {0, 300, 650, 950, 1300, 1700, 2100};
        int[] cloudY       = {50,  80,  40,  90,   60,   75,   45};
        int[] cloudW       = {160, 120, 180, 140,  200,  130,  170};
        int[] cloudH       = {60,   45,  70,  50,   80,   50,   65};
        for (int i = 0; i < cloudOffsets.length; i++) {
            int cx = cloudOffsets[i] - (camX / 4 % 2000);
            g2.fillRect(cx, cloudY[i], cloudW[i], cloudH[i]);
            // Segunda capa para dar volumen
            g2.fillRect(cx + 20, cloudY[i] - 20, cloudW[i] - 40, cloudH[i] - 10);
        }

        // Nivel (plataformas)
        level.draw(g2, camX);

        // Nido
        g2.setColor(new Color(255, 200, 0));
        g2.fillRect(nestX - camX, nestY, nestW, nestH);
        g2.setColor(new Color(200, 140, 0));
        g2.drawRect(nestX - camX, nestY, nestW, nestH);
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 11));
        g2.drawString("NIDO", nestX - camX + 28, nestY + 35);

        // Enemigos
        for (Enemy enemy : enemies) {
            if (!enemy.dead) {
                Graphics2D ge = (Graphics2D) g2.create();
                ge.translate(-camX, 0);
                enemy.draw(ge);
                ge.dispose();
            }
        }

        // Jugador
        Graphics2D gp = (Graphics2D) g2.create();
        gp.translate(-camX, 0);
        player.draw(gp);
        gp.dispose();

        // HUD
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        long alive = enemies.stream().filter(e -> !e.dead).count();
        g2.drawString("Nivel: " + currentLevel, 20, 30);
        g2.drawString("Enemigos: " + alive, 20, 55);

        // Pantalla GAME OVER
        if (gameState == GameState.GAME_OVER) {
            g2.setColor(new Color(0, 0, 0, 160));
            g2.fillRect(0, 0, WIDTH, HEIGHT);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 70));
            drawCentered(g2, "GAME OVER", 220);
            g2.setFont(new Font("Arial", Font.PLAIN, 26));
            drawCentered(g2, "Pulsa R para reintentar", 280);
        }

        // Pantalla NIVEL COMPLETADO
        if (gameState == GameState.LEVEL_COMPLETE) {
            g2.setColor(new Color(0, 0, 0, 160));
            g2.fillRect(0, 0, WIDTH, HEIGHT);
            g2.setColor(Color.YELLOW);
            g2.setFont(new Font("Arial", Font.BOLD, 60));
            drawCentered(g2, "¡NIVEL COMPLETADO!", 200);

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.PLAIN, 26));

            if (currentLevel < TOTAL_LEVELS) {
                drawCentered(g2, "Pulsa ENTER para el siguiente nivel", 270);
                drawCentered(g2, "Pulsa R para repetir", 310);
            } else {
                drawCentered(g2, "¡Has completado el juego!", 270);
                drawCentered(g2, "Pulsa R para volver al menú", 310);
                if (keyHandler.restart) gameState = GameState.MENU;
            }
        }
    }

    private void drawCentered(Graphics2D g2, String text, int y) {
        int x = WIDTH / 2 - g2.getFontMetrics().stringWidth(text) / 2;
        g2.drawString(text, x, y);
    }
}
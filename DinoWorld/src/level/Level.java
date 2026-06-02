package level;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Level {

    public ArrayList<Platform> platforms  = new ArrayList<>();
    public ArrayList<Obstacle> obstacles  = new ArrayList<>();
    public int mapWidth;
    public int mapHeight;
    public int levelNumber;

    public Level(int levelNumber) {
        this.levelNumber = levelNumber;
        this.mapWidth    = 5000;
        this.mapHeight   = 600;
        load(levelNumber);
    }

    private void load(int levelNumber) {
        platforms.clear();
        obstacles.clear();

        // Suelo base — height=100 para llegar hasta abajo de la pantalla
        platforms.add(new Platform(0, 400, mapWidth, 100, new Color(34, 139, 34)));

        switch (levelNumber) {
            case 1 -> { loadLevel1(); loadObstacles1(); }
            case 2 -> { loadLevel2(); loadObstacles2(); }
            case 3 -> { loadLevel3(); loadObstacles3(); }
            case 4 -> { loadLevel4(); loadObstacles4(); }
            case 5 -> { loadLevel5(); loadObstacles5(); }
        }
    }

    // ── PLATAFORMAS ───────────────────────────────────────────────

    private void loadLevel1() {
        platforms.add(new Platform(300,  320, 150, 20, new Color(139, 90, 43)));
        platforms.add(new Platform(600,  260, 150, 20, new Color(139, 90, 43)));
        platforms.add(new Platform(900,  300, 150, 20, new Color(139, 90, 43)));
        platforms.add(new Platform(1300, 240, 200, 20, new Color(139, 90, 43)));
        platforms.add(new Platform(1700, 280, 150, 20, new Color(139, 90, 43)));
        platforms.add(new Platform(2100, 220, 200, 20, new Color(139, 90, 43)));
        platforms.add(new Platform(2600, 300, 150, 20, new Color(139, 90, 43)));
        platforms.add(new Platform(3000, 250, 200, 20, new Color(139, 90, 43)));
        platforms.add(new Platform(3500, 280, 150, 20, new Color(139, 90, 43)));
        platforms.add(new Platform(4700, 340, 100, 60, new Color(255, 200, 0)));
    }

    private void loadLevel2() {
        platforms.add(new Platform(400,  300, 120, 20, new Color(139, 90, 43)));
        platforms.add(new Platform(700,  240, 120, 20, new Color(139, 90, 43)));
        platforms.add(new Platform(1000, 280, 100, 20, new Color(139, 90, 43)));
        platforms.add(new Platform(1400, 200, 150, 20, new Color(139, 90, 43)));
        platforms.add(new Platform(1800, 260, 120, 20, new Color(139, 90, 43)));
        platforms.add(new Platform(2200, 180, 150, 20, new Color(139, 90, 43)));
        platforms.add(new Platform(2700, 240, 120, 20, new Color(139, 90, 43)));
        platforms.add(new Platform(3100, 200, 150, 20, new Color(139, 90, 43)));
        platforms.add(new Platform(3600, 260, 120, 20, new Color(139, 90, 43)));
        platforms.add(new Platform(4200, 180, 150, 20, new Color(139, 90, 43)));
        platforms.add(new Platform(4700, 340, 100, 60, new Color(255, 200, 0)));
    }

    private void loadLevel3() {
        platforms.add(new Platform(350,  280, 100, 20, new Color(139, 90, 43)));
        platforms.add(new Platform(650,  220, 100, 20, new Color(139, 90, 43)));
        platforms.add(new Platform(950,  160, 120, 20, new Color(139, 90, 43)));
        platforms.add(new Platform(1300, 220, 100, 20, new Color(139, 90, 43)));
        platforms.add(new Platform(1650, 160, 100, 20, new Color(139, 90, 43)));
        platforms.add(new Platform(2000, 200, 120, 20, new Color(139, 90, 43)));
        platforms.add(new Platform(2400, 140, 100, 20, new Color(139, 90, 43)));
        platforms.add(new Platform(2800, 200, 120, 20, new Color(139, 90, 43)));
        platforms.add(new Platform(3300, 160, 100, 20, new Color(139, 90, 43)));
        platforms.add(new Platform(3800, 220, 120, 20, new Color(139, 90, 43)));
        platforms.add(new Platform(4300, 160, 100, 20, new Color(139, 90, 43)));
        platforms.add(new Platform(4700, 340, 100, 60, new Color(255, 200, 0)));
    }

    private void loadLevel4() {
        platforms.add(new Platform(300,  260, 80,  20, new Color(139, 90, 43)));
        platforms.add(new Platform(550,  200, 80,  20, new Color(139, 90, 43)));
        platforms.add(new Platform(800,  140, 80,  20, new Color(139, 90, 43)));
        platforms.add(new Platform(1100, 200, 80,  20, new Color(139, 90, 43)));
        platforms.add(new Platform(1400, 140, 80,  20, new Color(139, 90, 43)));
        platforms.add(new Platform(1700, 100, 100, 20, new Color(139, 90, 43)));
        platforms.add(new Platform(2050, 160, 80,  20, new Color(139, 90, 43)));
        platforms.add(new Platform(2350, 100, 80,  20, new Color(139, 90, 43)));
        platforms.add(new Platform(2700, 160, 80,  20, new Color(139, 90, 43)));
        platforms.add(new Platform(3050, 100, 80,  20, new Color(139, 90, 43)));
        platforms.add(new Platform(3400, 140, 80,  20, new Color(139, 90, 43)));
        platforms.add(new Platform(3800, 200, 100, 20, new Color(139, 90, 43)));
        platforms.add(new Platform(4200, 140, 80,  20, new Color(139, 90, 43)));
        platforms.add(new Platform(4700, 340, 100, 60, new Color(255, 200, 0)));
    }

    private void loadLevel5() {
        platforms.add(new Platform(400,  300, 200, 20, new Color(139, 90, 43)));
        platforms.add(new Platform(900,  250, 200, 20, new Color(139, 90, 43)));
        platforms.add(new Platform(1400, 300, 200, 20, new Color(139, 90, 43)));
        platforms.add(new Platform(1900, 250, 200, 20, new Color(139, 90, 43)));
        platforms.add(new Platform(2400, 300, 200, 20, new Color(139, 90, 43)));
        platforms.add(new Platform(4700, 340, 100, 60, new Color(255, 200, 0)));
    }

    // ── OBSTÁCULOS ────────────────────────────────────────────────

    private void loadObstacles1() {
        // 1 rodante + 1 pinchos
        obstacles.add(new Obstacle(Obstacle.Type.ROLLING, 800,  360, 40, 40, 600,  1100, 3));
        obstacles.add(new Obstacle(Obstacle.Type.SPIKE,   1500, 370, 40, 30));
        obstacles.add(new Obstacle(Obstacle.Type.SPIKE,   2300, 370, 40, 30));
    }

    private void loadObstacles2() {
        obstacles.add(new Obstacle(Obstacle.Type.ROLLING, 600,  360, 40, 40, 400,  900,  3));
        obstacles.add(new Obstacle(Obstacle.Type.AERIAL,  1200, 200, 40, 40, 160,  320,  2));
        obstacles.add(new Obstacle(Obstacle.Type.SPIKE,   1800, 370, 40, 30));
        obstacles.add(new Obstacle(Obstacle.Type.ROLLING, 3000, 360, 40, 40, 2800, 3300, 4));
        obstacles.add(new Obstacle(Obstacle.Type.SPIKE,   3800, 370, 40, 30));
    }

    private void loadObstacles3() {
        obstacles.add(new Obstacle(Obstacle.Type.ROLLING, 500,  360, 40, 40, 300,  800,  4));
        obstacles.add(new Obstacle(Obstacle.Type.AERIAL,  1100, 180, 40, 40, 140,  300,  3));
        obstacles.add(new Obstacle(Obstacle.Type.SPIKE,   1600, 370, 40, 30));
        obstacles.add(new Obstacle(Obstacle.Type.SPIKE,   1660, 370, 40, 30));
        obstacles.add(new Obstacle(Obstacle.Type.AERIAL,  2600, 160, 40, 40, 120,  280,  3));
        obstacles.add(new Obstacle(Obstacle.Type.ROLLING, 3500, 360, 40, 40, 3300, 3800, 4));
        obstacles.add(new Obstacle(Obstacle.Type.SPIKE,   4200, 370, 40, 30));
    }

    private void loadObstacles4() {
        obstacles.add(new Obstacle(Obstacle.Type.ROLLING, 400,  360, 40, 40, 200,  700,  5));
        obstacles.add(new Obstacle(Obstacle.Type.AERIAL,  900,  150, 40, 40, 100,  260,  4));
        obstacles.add(new Obstacle(Obstacle.Type.SPIKE,   1200, 370, 40, 30));
        obstacles.add(new Obstacle(Obstacle.Type.SPIKE,   1260, 370, 40, 30));
        obstacles.add(new Obstacle(Obstacle.Type.SPIKE,   1320, 370, 40, 30));
        obstacles.add(new Obstacle(Obstacle.Type.ROLLING, 2200, 360, 40, 40, 2000, 2500, 5));
        obstacles.add(new Obstacle(Obstacle.Type.AERIAL,  3000, 130, 40, 40, 90,   240,  5));
        obstacles.add(new Obstacle(Obstacle.Type.ROLLING, 3800, 360, 40, 40, 3600, 4100, 5));
        obstacles.add(new Obstacle(Obstacle.Type.SPIKE,   4400, 370, 40, 30));
    }

    private void loadObstacles5() {
        obstacles.add(new Obstacle(Obstacle.Type.ROLLING, 800,  360, 50, 50, 500,  1200, 5));
        obstacles.add(new Obstacle(Obstacle.Type.AERIAL,  1500, 180, 50, 50, 120,  300,  4));
        obstacles.add(new Obstacle(Obstacle.Type.ROLLING, 2200, 360, 50, 50, 1800, 2600, 5));
        obstacles.add(new Obstacle(Obstacle.Type.AERIAL,  3000, 160, 50, 50, 100,  300,  5));
        obstacles.add(new Obstacle(Obstacle.Type.SPIKE,   3500, 370, 40, 30));
        obstacles.add(new Obstacle(Obstacle.Type.SPIKE,   3560, 370, 40, 30));
        obstacles.add(new Obstacle(Obstacle.Type.ROLLING, 4000, 360, 50, 50, 3800, 4300, 6));
    }

    // ── DIBUJO ────────────────────────────────────────────────────

    public void draw(Graphics2D g2, int cameraX) {
        for (Platform p : platforms) {
            if (p.x + p.width > cameraX && p.x < cameraX + 1000) {
                Graphics2D gc = (Graphics2D) g2.create();
                gc.translate(-cameraX, 0);
                p.draw(gc);
                gc.dispose();
            }
        }
        for (Obstacle o : obstacles) {
            if (o.x + o.width > cameraX && o.x < cameraX + 1000) {
                Graphics2D gc = (Graphics2D) g2.create();
                gc.translate(-cameraX, 0);
                o.draw(gc);
                gc.dispose();
            }
        }
    }
}
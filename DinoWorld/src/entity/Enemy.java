package entity;

import level.Level;
import level.Platform;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Enemy extends Entity {

    private int speed;
    private int minX, maxX;
    private int direction = 1;
    public boolean dead = false;

    private static final int GRAVITY = 1;
    private int velocityY = 0;
    private static final int GROUND_Y = 360;

    public Enemy(int x, int y, int minX, int maxX, int speed) {
        this.x = x;
        this.y = y;
        this.width = 40;
        this.height = 40;
        this.minX = minX;
        this.maxX = maxX;
        this.speed = speed;
    }

    @Override
    public void update() {
        if (dead) return;

        // Movimiento horizontal patrón fijo
        x += speed * direction;
        if (x >= maxX) direction = -1;
        if (x <= minX) direction = 1;

        // Gravedad
        velocityY += GRAVITY;
        y += velocityY;

        if (y >= GROUND_Y) {
            y = GROUND_Y;
            velocityY = 0;
        }
    }

    public void update(Level level) {
        if (dead) return;

        // Movimiento horizontal patrón fijo
        x += speed * direction;
        if (x >= maxX) direction = -1;
        if (x <= minX) direction = 1;

        // Gravedad
        velocityY += GRAVITY;
        y += velocityY;

        // Colisión con plataformas
        for (Platform platform : level.platforms) {
            if (getBounds().intersects(platform.getBounds())) {
                if (velocityY >= 0 && y + height - velocityY <= platform.y + 10) {
                    y = platform.y - height;
                    velocityY = 0;
                }
            }
        }

        // Suelo base
        if (y >= GROUND_Y) {
            y = GROUND_Y;
            velocityY = 0;
        }
    }

    // Comprueba si el jugador salta encima
    public boolean isStompedBy(Player player) {
        return player.velocityY > 0
                && player.getBounds().intersects(getTopBounds());
    }

    // Zona superior del enemigo para detectar el salto encima
    public Rectangle getTopBounds() {
        return new Rectangle(x + 5, y, width - 10, 10);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x + 5, y + 5, width - 10, height - 5);
    }

    @Override
    public void draw(Graphics2D g2) {
        if (dead) return;
        // Cuerpo
        g2.setColor(new Color(180, 0, 0));
        g2.fillRect(x, y, width, height);
        // Ojos
        g2.setColor(Color.YELLOW);
        g2.fillOval(x + 5,  y + 8, 10, 10);
        g2.fillOval(x + 25, y + 8, 10, 10);
        g2.setColor(Color.BLACK);
        g2.fillOval(x + 8,  y + 11, 5, 5);
        g2.fillOval(x + 28, y + 11, 5, 5);
    }
}
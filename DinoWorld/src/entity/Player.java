package entity;

import game.KeyHandler;
import level.Level;
import level.Platform;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Player extends Entity {

    private KeyHandler keyHandler;

    private int speed = 5;
    public int velocityY = 0;
    public boolean onGround = false;

    private static final int GRAVITY = 1;
    private static final int JUMP_FORCE = -18;
    private static final int GROUND_Y = 340;

    public Player(int x, int y, KeyHandler keyHandler) {
        this.x = x;
        this.y = y;
        this.width = 50;
        this.height = 60;
        this.keyHandler = keyHandler;
    }

    @Override
    public void update() {}

    public void update(Level level) {
        // Movimiento horizontal
        if (keyHandler.left)  x -= speed;
        if (keyHandler.right) x += speed;

        // No salir por la izquierda del mapa
        if (x < 0) x = 0;

        // Salto
        if (keyHandler.jump && onGround) {
            velocityY = JUMP_FORCE;
            onGround = false;
        }

        // Gravedad
        velocityY += GRAVITY;
        y += velocityY;

        // Colisión con plataformas
        onGround = false;
        for (Platform platform : level.platforms) {
            if (getBounds().intersects(platform.getBounds())) {
                // Viene desde arriba
                if (velocityY >= 0 && y + height - velocityY <= platform.y + 10) {
                    y = platform.y - height;
                    velocityY = 0;
                    onGround = true;
                }
                // Viene desde abajo
                else if (velocityY < 0 && y - velocityY >= platform.y + platform.height - 10) {
                    y = platform.y + platform.height;
                    velocityY = 0;
                }
                // Viene desde la izquierda
                else if (x + width - speed <= platform.x) {
                    x = platform.x - width;
                }
                // Viene desde la derecha
                else if (x + speed >= platform.x + platform.width) {
                    x = platform.x + platform.width;
                }
            }
        }

        // Suelo base por si acaso
        if (y >= GROUND_Y) {
            y = GROUND_Y;
            velocityY = 0;
            onGround = true;
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x + 5, y + 5, width - 10, height - 5);
    }

    @Override
    public void draw(Graphics2D g2) {
        // Cuerpo
        g2.setColor(new Color(0, 180, 0));
        g2.fillRect(x, y, width, height);
        // Ojo
        g2.setColor(Color.WHITE);
        g2.fillOval(x + 30, y + 10, 14, 14);
        g2.setColor(Color.BLACK);
        g2.fillOval(x + 34, y + 13, 7, 7);
    }
}

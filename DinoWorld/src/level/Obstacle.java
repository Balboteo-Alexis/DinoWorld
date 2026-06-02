package level;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Obstacle {

    public enum Type {
        SPIKE,      // terrestre estático, mata al tocar
        ROLLING,    // rueda por el suelo de lado a lado
        AERIAL      // flota y se mueve arriba/abajo
    }

    public int x, y, width, height;
    public Type type;
    public boolean active = true;

    private int minVal, maxVal, speed, direction = 1;

    // SPIKE
    public Obstacle(Type type, int x, int y, int width, int height) {
        this.type   = type;
        this.x      = x;
        this.y      = y;
        this.width  = width;
        this.height = height;
    }

    // ROLLING / AERIAL
    public Obstacle(Type type, int x, int y, int width, int height,
                    int min, int max, int speed) {
        this(type, x, y, width, height);
        this.minVal = min;
        this.maxVal = max;
        this.speed  = speed;
    }

    public void update() {
        if (type == Type.ROLLING) {
            x += speed * direction;
            if (x >= maxVal) direction = -1;
            if (x <= minVal) direction =  1;
        }
        if (type == Type.AERIAL) {
            y += speed * direction;
            if (y >= maxVal) direction = -1;
            if (y <= minVal) direction =  1;
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x + 2, y + 2, width - 4, height - 4);
    }

    public void draw(Graphics2D g2) {
        switch (type) {
            case SPIKE -> {
                // Triángulo dibujado como rectángulo oscuro con punta
                g2.setColor(new Color(60, 60, 60));
                int[] xs = { x, x + width / 2, x + width };
                int[] ys = { y + height, y, y + height };
                g2.fillPolygon(xs, ys, 3);
                g2.setColor(Color.DARK_GRAY);
                g2.drawPolygon(xs, ys, 3);
            }
            case ROLLING -> {
                // Bola rojiza que rueda
                g2.setColor(new Color(160, 40, 40));
                g2.fillOval(x, y, width, height);
                g2.setColor(new Color(200, 80, 80));
                g2.drawOval(x, y, width, height);
                // Cruz para simular rotación
                g2.setColor(new Color(120, 20, 20));
                g2.drawLine(x + width/2, y + 4, x + width/2, y + height - 4);
                g2.drawLine(x + 4, y + height/2, x + width - 4, y + height/2);
            }
            case AERIAL -> {
                // Bloque azul flotante con bordes
                g2.setColor(new Color(40, 80, 180));
                g2.fillRect(x, y, width, height);
                g2.setColor(new Color(80, 120, 220));
                g2.drawRect(x, y, width, height);
                g2.setColor(new Color(150, 180, 255));
                g2.drawRect(x + 4, y + 4, width - 8, height - 8);
            }
        }
    }
}
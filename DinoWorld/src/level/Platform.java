package level;

import entity.Entity;
import java.awt.Color;
import java.awt.Graphics2D;

public class Platform extends Entity {

    private Color color;

    public Platform(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    @Override
    public void update() {
        // Las plataformas son estáticas, no hacen nada
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(color);
        g2.fillRect(x, y, width, height);
        g2.setColor(color.darker());
        g2.drawRect(x, y, width, height);
    }
}
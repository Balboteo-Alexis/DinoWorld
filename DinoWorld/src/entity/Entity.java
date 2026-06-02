package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class Entity {

    public int x, y;
    public int width, height;
    public boolean active = true;

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public abstract void update();
    public abstract void draw(Graphics2D g2);
}
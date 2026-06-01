package entity;

import game.KeyHandler;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player {

    // Posición
    public int x, y;

    // Tamaño
    public int width = 100;
    public int height = 60;

    private int speed = 5;
    private int velocityY = 0;
    private boolean onGround = false;

    private static final int GROUND_Y = 340;
    private static final int GRAVITY = 1;
    private static final int JUMP_FORCE = -18;

    private KeyHandler keyHandler;
    private BufferedImage image;

    public Player(KeyHandler keyHandler) {
        this.keyHandler = keyHandler;
        x = 100;
        y = GROUND_Y;
        loadImage();
    }

    private void loadImage() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/player/idle (1).png"));
        } catch (IOException e) {
            System.out.println("No se pudo cargar la imagen del dinosaurio");
            e.printStackTrace();
        }
    }


    public void update() {
        // Movimiento horizontal
        if (keyHandler.left)  x -= speed;
        if (keyHandler.right) x += speed;

        // Salto
        if (keyHandler.jump && onGround) {
            velocityY = JUMP_FORCE;
            onGround = false;
        }

        // Gravedad
        velocityY += GRAVITY;
        y += velocityY;

        // Colisión con el suelo
        if (y >= GROUND_Y) {
            y = GROUND_Y;
            velocityY = 0;
            onGround = true;
        }
    }

    public void draw(Graphics2D g2) {
        if (image != null) {
            g2.drawImage(image, x, y, width, height, null);
        }
    }
}
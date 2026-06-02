package entity;

import game.KeyHandler;
import level.Level;
import level.Platform;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {

    private KeyHandler keyHandler;

    public int velocityY = 0;
    public boolean onGround = false;

    private int speed = 5;
    private static final int GRAVITY    = 1;
    private static final int JUMP_FORCE = -18;
    private static final int GROUND_Y   = 360;

    // ── Animación ─────────────────────────────────────────────────
    private enum AnimState { IDLE, WALK, RUN, JUMP, DEAD }
    private AnimState animState     = AnimState.IDLE;
    private AnimState lastAnimState = AnimState.IDLE; // ← añade esta línea

    private BufferedImage[] idleFrames;
    private BufferedImage[] walkFrames;
    private BufferedImage[] runFrames;
    private BufferedImage[] jumpFrames;
    private BufferedImage[] deadFrames;

    private int currentFrame  = 0;
    private int frameCounter  = 0;
    private int frameDelay    = 5; // ticks entre frames

    public boolean dead = false;
    private boolean facingLeft = false;

    public Player(int x, int y, KeyHandler keyHandler) {
        this.x = x;
        this.y = y;
        this.width  = 60;
        this.height = 60;
        this.keyHandler = keyHandler;

        idleFrames = loadFrames("Idle", 10);
        walkFrames = loadFrames("Walk", 10);
        runFrames  = loadFrames("Run",  8);
        jumpFrames = loadFrames("Jump", 12);
        deadFrames = loadFrames("Dead", 8);
    }

    private BufferedImage[] loadFrames(String name, int count) {
        BufferedImage[] frames = new BufferedImage[count];
        for (int i = 1; i <= count; i++) {
            try {
                String path = "src/res/player/" + name + " (" + i + ").png";
                frames[i - 1] = ImageIO.read(new java.io.File(path));
            } catch (IOException e) {
                frames[i - 1] = null;
            }
        }
        return frames;
    }

    @Override
    public void update() {}

    public void update(Level level) {
        if (dead) {
            animState = AnimState.DEAD;
            updateAnimation();
            return;
        }

        boolean moving = false;

        if (keyHandler.left) {
            x -= speed;
            facingLeft = true;
            moving = true;
        }
        if (keyHandler.right) {
            x += speed;
            facingLeft = false;
            moving = true;
        }
        if (x < 0) x = 0;

        if (keyHandler.jump && onGround) {
            velocityY = JUMP_FORCE;
            onGround  = false;
        }

        velocityY += GRAVITY;
        y += velocityY;

        onGround = false;
        for (Platform platform : level.platforms) {
            if (getBounds().intersects(platform.getBounds())) {
                if (velocityY >= 0 && y + height - velocityY <= platform.y + 10) {
                    y = platform.y - height;
                    velocityY = 0;
                    onGround  = true;
                } else if (velocityY < 0 && y - velocityY >= platform.y + platform.height - 10) {
                    y = platform.y + platform.height;
                    velocityY = 0;
                } else if (x + width - speed <= platform.x) {
                    x = platform.x - width;
                } else if (x + speed >= platform.x + platform.width) {
                    x = platform.x + platform.width;
                }
            }
        }

        if (y >= GROUND_Y) {
            y = GROUND_Y;
            velocityY = 0;
            onGround  = true;
        }

        // Elegir animación
        if (!onGround) {
            animState = AnimState.JUMP;
        } else if (moving && (keyHandler.left && keyHandler.right)) {
            animState = AnimState.IDLE;
        } else if (moving) {
            // Si va rápido (shift futuro) → RUN, si no → WALK
            animState = AnimState.WALK;
        } else {
            animState = AnimState.IDLE;
        }

        updateAnimation();
    }

    private void updateAnimation() {
        // Si cambia la animación, resetea el frame
        if (animState != lastAnimState) {
            currentFrame  = 0;
            frameCounter  = 0;
            lastAnimState = animState;
        }

        frameCounter++;
        int delay = (animState == AnimState.RUN) ? 3 : frameDelay;
        if (frameCounter >= delay) {
            frameCounter = 0;
            BufferedImage[] frames = getFrames();
            if (frames != null) {
                currentFrame = (currentFrame + 1) % frames.length;
            }
        }
    }

    private BufferedImage[] getFrames() {
        return switch (animState) {
            case IDLE -> idleFrames;
            case WALK -> walkFrames;
            case RUN  -> runFrames;
            case JUMP -> jumpFrames;
            case DEAD -> deadFrames;
        };
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x + 8, y + 5, width - 16, height - 5);
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage[] frames = getFrames();
        BufferedImage img = (frames != null && frames[currentFrame] != null)
                ? frames[currentFrame] : null;

        if (img != null) {
            if (facingLeft) {
                // Voltear horizontalmente
                g2.drawImage(img, x + width, y, -width, height, null);
            } else {
                g2.drawImage(img, x, y, width, height, null);
            }
        } else {
            // Fallback
            g2.setColor(new Color(0, 180, 0));
            g2.fillRect(x, y, width, height);
        }
    }
}
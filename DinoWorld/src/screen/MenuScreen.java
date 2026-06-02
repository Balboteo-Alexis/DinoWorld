package screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class MenuScreen {

    private int screenWidth, screenHeight;

    // Botones
    private int btnX, btnWidth, btnHeight;
    private int btnStartY, btnCreditsY;

    public boolean startClicked = false;
    public boolean creditsClicked = false;

    public MenuScreen(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        btnWidth   = 260;
        btnHeight  = 55;
        btnX       = screenWidth / 2 - btnWidth / 2;
        btnStartY  = 280;
        btnCreditsY = 360;
    }

    public void draw(Graphics2D g2) {
        // Fondo
        g2.setColor(new Color(20, 20, 40));
        g2.fillRect(0, 0, screenWidth, screenHeight);

        // Logo / título
        g2.setColor(new Color(0, 220, 80));
        g2.setFont(new Font("Arial", Font.BOLD, 80));
        String title = "DinoWorld";
        int titleX = screenWidth / 2 - g2.getFontMetrics().stringWidth(title) / 2;
        g2.drawString(title, titleX, 180);

        // Subtítulo
        g2.setColor(new Color(180, 180, 180));
        g2.setFont(new Font("Arial", Font.PLAIN, 20));
        String sub = "Un juego de plataformas";
        int subX = screenWidth / 2 - g2.getFontMetrics().stringWidth(sub) / 2;
        g2.drawString(sub, subX, 220);

        // Botón Iniciar
        drawButton(g2, btnX, btnStartY, btnWidth, btnHeight,
                "Iniciar Partida", new Color(0, 160, 60));

        // Botón Créditos
        drawButton(g2, btnX, btnCreditsY, btnWidth, btnHeight,
                "Créditos", new Color(60, 60, 160));
    }

    private void drawButton(Graphics2D g2, int x, int y, int w, int h,
                            String text, Color color) {
        g2.setColor(color);
        g2.fillRoundRect(x, y, w, h, 20, 20);
        g2.setColor(color.brighter());
        g2.drawRoundRect(x, y, w, h, 20, 20);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 24));
        int textX = x + w / 2 - g2.getFontMetrics().stringWidth(text) / 2;
        int textY = y + h / 2 + 8;
        g2.drawString(text, textX, textY);
    }

    public void handleClick(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if (mx >= btnX && mx <= btnX + btnWidth) {
            if (my >= btnStartY && my <= btnStartY + btnHeight) {
                startClicked = true;
            }
            if (my >= btnCreditsY && my <= btnCreditsY + btnHeight) {
                creditsClicked = true;
            }
        }
    }
}
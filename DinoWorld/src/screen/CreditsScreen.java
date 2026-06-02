package screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class CreditsScreen {

    private int screenWidth, screenHeight;

    private int btnX, btnY, btnWidth, btnHeight;
    public boolean backClicked = false;

    public CreditsScreen(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        btnWidth  = 200;
        btnHeight = 50;
        btnX = screenWidth / 2 - btnWidth / 2;
        btnY = 420;
    }

    public void draw(Graphics2D g2) {
        // Fondo
        g2.setColor(new Color(20, 20, 40));
        g2.fillRect(0, 0, screenWidth, screenHeight);

        // Título
        g2.setColor(new Color(0, 220, 80));
        g2.setFont(new Font("Arial", Font.BOLD, 50));
        String title = "Créditos";
        int titleX = screenWidth / 2 - g2.getFontMetrics().stringWidth(title) / 2;
        g2.drawString(title, titleX, 100);

        // Contenido
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 24));
        drawCentered(g2, "Desarrollo", 180);

        g2.setFont(new Font("Arial", Font.PLAIN, 20));
        drawCentered(g2, "Balboteo-Alexis", 215);

        g2.setFont(new Font("Arial", Font.BOLD, 24));
        drawCentered(g2, "Agradecimientos", 275);

        g2.setFont(new Font("Arial", Font.PLAIN, 20));
        drawCentered(g2, "A todos los que han apoyado el proyecto", 310);
        drawCentered(g2, "¡Gracias por jugar!", 345);

        // Botón volver
        g2.setColor(new Color(100, 100, 100));
        g2.fillRoundRect(btnX, btnY, btnWidth, btnHeight, 20, 20);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 22));
        drawCentered(g2, "← Volver", btnY + 33);
    }

    private void drawCentered(Graphics2D g2, String text, int y) {
        int x = screenWidth / 2 - g2.getFontMetrics().stringWidth(text) / 2;
        g2.drawString(text, x, y);
    }

    public void handleClick(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
        if (mx >= btnX && mx <= btnX + btnWidth &&
                my >= btnY && my <= btnY + btnHeight) {
            backClicked = true;
        }
    }
}
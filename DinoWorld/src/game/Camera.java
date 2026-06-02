package game;

import entity.Player;

public class Camera {

    public int x = 0;

    private static final int SCREEN_WIDTH = 1000;

    public void update(Player player) {
        // El jugador siempre centrado en pantalla
        x = player.x - SCREEN_WIDTH / 2 + player.width / 2;

        // No retroceder más allá del inicio del mapa
        if (x < 0) x = 0;
    }
}
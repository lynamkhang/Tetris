package tetris;

import java.awt.*;


public class Shape {

    private Color color;
    private Board board;
    private int x = 4, y = 0;
    private int normal = 600;
    private int fast = 50;
    private int deplayTimeForMovement = normal;
    private long beginTime;
    private int dX = 0;
    private boolean collision = false;
    private int[][] coordinates;

    public Shape(int[][] coordinates, Board board, Color color) {
        this.coordinates = coordinates;
        this.board = board;
    }

    public void update() {

        // check collision
        if (collision) {
            for (int row = 0; row < coordinates.length; row++) {
                for (int col = 0; col < coordinates[0].length; col++) {
                    board.getBoard()[y + row][x + col] =color;
                }
            }
            return;
        }

        // check moving horizontal
        if (!(x + dX + coordinates[0].length > 10) && !(x + dX < 0)) {
            x += dX;
        }
        dX = 0;

        if (System.currentTimeMillis() - beginTime > deplayTimeForMovement) {
            if (!(y + 1 + coordinates.length > Board.boardHeight)) {
                y++;
            }
            else {
                collision = true;
            }

            beginTime = System.currentTimeMillis();
        }
    }

    public void render(Graphics g) {
        for (int row = 0; row < coordinates.length; row++) {
            for (int col = 0; col < coordinates[0].length; col++) {
                if (coordinates[row][col] != 0) {
                    g.setColor(Color.RED);
                    g.fillRect(col * 30 + x * 30, row * 30 + y * 30, Board.blockSize, Board.blockSize);
                }
            }
        }
    }

    public void speedUp() {
        deplayTimeForMovement = fast;
    }

    public void speedDown() {
        deplayTimeForMovement = normal;
    }

    public void moveRight() {
        dX = 1;
    }

    public void moveLeft() {
        dX = -1;
    }
}

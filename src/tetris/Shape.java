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
        this.color = color;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void reset() {
        this.x = 4;
        this.y = 0;
        collision = false;
    }

    public void update() {

        // check collision
        if (collision) {
            for (int row = 0; row < coordinates.length; row++) {
                for (int col = 0; col < coordinates[0].length; col++) {
                    if (coordinates[row][col] != 0) {
                        board.getBoard()[y + row][x + col] = color;
                    }
                }
            }
            checkLine();
            board.setCurShape();
            return;
        }

        // check moving horizontal
        boolean moveX = true;
        if (!(x + dX + coordinates[0].length > 10) && !(x + dX < 0)) {
            for (int row = 0; row < coordinates.length; row++) {
                for (int col = 0; col < coordinates[row].length; col++) {
                    if (coordinates[row][col] != 0) {
                        if (board.getBoard()[y + row][x + dX + col] != null) {
                            moveX = false;
                        }
                    }
                }
            }
            if (moveX) {
                x += dX;
            }
        }
        dX = 0;

        if (System.currentTimeMillis() - beginTime > deplayTimeForMovement) {
            // vertical movement
            if (!(y + 1 + coordinates.length > Board.boardHeight)) {
                for (int row = 0; row < coordinates.length; row++) {
                    for (int col = 0; col < coordinates[row].length; col++) {
                        if (coordinates[row][col] != 0) {
                            if (board.getBoard()[y + 1 + row][x + dX + col] != null) {
                                collision = true;
                            }
                        }
                    }
                }

                if (!collision) {
                    y++;
                }
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
                    g.setColor(color);
                    g.fillRect(col * 30 + x * 30, row * 30 + y * 30, Board.blockSize, Board.blockSize);
                }
            }
        }
    }

    private void checkLine() {
        int size = board.getBoard().length - 1;

        for (int i = board.getBoard().length - 1; i > 0; i--) {
            int count = 0;
            for (int j = 0; j < board.getBoard()[0].length; j++) {
                if (board.getBoard()[i][j] != null) {
                    count++;
                }

                board.getBoard()[size][j] = board.getBoard()[i][j];
            }
            if (count < board.getBoard()[0].length) {
                size--;
            }
        }
    }

    public void rotateShape() {

        int[][] rotatedShape = null;

        rotatedShape = transposeMatrix(coordinates);

        rotatedShape = reverseRows(rotatedShape);

        if ((x + rotatedShape[0].length > 10) || (y + rotatedShape.length > 20)) {
            return;
        }

        for (int row = 0; row < rotatedShape.length; row++) {
            for (int col = 0; col < rotatedShape[row].length; col++) {
                if (rotatedShape[row][col] != 0) {
                    if (board.getBoard()[y + row][x + col] != null) {
                        return;
                    }
                }
            }
        }
        coordinates = rotatedShape;
    }

    private int[][] transposeMatrix(int[][] matrix) {
        int[][] temp = new int[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                temp[j][i] = matrix[i][j];
            }
        }
        return temp;
    }

    private int[][] reverseRows(int[][] matrix) {

        int middle = matrix.length / 2;

        for (int i = 0; i < middle; i++) {
            int[] temp = matrix[i];

            matrix[i] = matrix[matrix.length - i - 1];
            matrix[matrix.length - i - 1] = temp;
        }

        return matrix;

    }

    public int[][] getCoordinates() {
        return  coordinates;
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
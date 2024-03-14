package tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Board extends JPanel implements KeyListener {

    private  static int fps = 60;
    private static int deplay = fps / 1000;
    public static final int boardWidth = 10, boardHeight = 20;
    public static final  int blockSize = 30;
    private Timer looper;
    private Color[][] board = new Color[boardHeight][boardWidth];
    private Shape[] shapes = new Shape[7];
    private Color[] colors = {Color.decode("#ed1c24"), Color.decode("#ff7f27"), Color.decode("#fff200"),
            Color.decode("#22b14c"), Color.decode("#00a2e8"), Color.decode("#a349a4"), Color.decode("#3f48cc")};
    private Shape curShape;

    public Board() {

        shapes[0] = new Shape(new int[][]{
                {1, 1, 1, 1} // I shape;
        }, this, colors[0]);

        shapes[1] = new Shape(new int[][]{
                {1, 1, 1},
                {0, 1, 0}, // T shape;
        }, this, colors[1]);

        shapes[2] = new Shape(new int[][]{
                {1, 1, 1},
                {1, 0, 0}, // L shape;
        }, this, colors[2]);

        shapes[3] = new Shape(new int[][]{
                {1, 1, 1},
                {0, 0, 1}, // J shape;
        }, this, colors[3]);

        shapes[4] = new Shape(new int[][]{
                {0, 1, 1},
                {1, 1, 0}, // S shape;
        }, this, colors[4]);

        shapes[5] = new Shape(new int[][]{
                {1, 1, 0},
                {0, 1, 1}, // Z shape;
        }, this, colors[5]);

        shapes[6] = new Shape(new int[][]{
                {1, 1},
                {1, 1}, // O shape;
        }, this, colors[6]);

        curShape = shapes[0];

        looper = new Timer(deplay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
                repaint();
            }
        });
        looper.start();
    }

    private void update() {
        curShape.update();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        curShape.render(g);

        //draw board
        g.setColor(Color.WHITE);
        for (int row = 0; row <= boardHeight; row++) {
              g.drawLine(0, row * blockSize, blockSize * boardWidth, row * blockSize);
        }

        for (int col = 0; col <= boardWidth; col++) {
            g.drawLine(col * blockSize, 0, blockSize * col, boardHeight * blockSize);
        }

    }

    public Color[][] getBoard() {
        return board;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            curShape.speedUp();
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            curShape.moveRight();
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            curShape.moveLeft();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            curShape.speedDown();
        }
    }
}
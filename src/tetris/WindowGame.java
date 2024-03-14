package tetris;

import javax.swing.*;

public class WindowGame extends JFrame {
    public static final int WIDTH = 480, HEIGHT = 640;

    private Board board;
    private JFrame window;

    public WindowGame() {
        window = new JFrame("Tetris");
        window.setSize(WIDTH, HEIGHT);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setResizable(false);

        board = new Board();
        window.add(board);
        window.addKeyListener(board);


        window.setVisible(true);

    }

    public static void main(String[] args) {
        new WindowGame();
    }
}


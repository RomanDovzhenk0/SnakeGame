package SnakeGame;

import javax.swing.*;

public class MainWindow extends JFrame {
    public MainWindow() {
        setTitle("Snaaake");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(320, 345);
        setLocation(400, 400);
        add(new GameField());
        setVisible(true);
        setResizable(false);
    }
}
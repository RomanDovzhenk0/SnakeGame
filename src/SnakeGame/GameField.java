package SnakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Timer;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;

public class GameField extends JPanel implements ActionListener {
    private Image snake;
    private Image snakeHead;
    private Image apple;
    private int appleX;
    private int appleY;
    private int snakeLenght = 3;
    private final int SIZE = 320;
    private final int DOT_SIZE = 16;
    private final int FIELD_LENGHT = SIZE / DOT_SIZE;
    private int[] x = new int[FIELD_LENGHT * FIELD_LENGHT];
    private int[] y = new int[FIELD_LENGHT * FIELD_LENGHT];
    private String direction = "right";
    private boolean inGame = true;
    private javax.swing.Timer timer;

    public GameField() {
        setBackground(Color.BLACK);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

    public void loadImages() {
        ImageIcon iia = new ImageIcon("src\\SnakeGame\\apple.png");
        ImageIcon iis = new ImageIcon("src\\SnakeGame\\snake.png");
        ImageIcon iish = new ImageIcon("src\\SnakeGame\\snakehead.png");
        apple = iia.getImage();
        snake = iis.getImage();
        snakeHead = iish.getImage();
    }

    public void initGame() {
        for (int i = 0; i < snakeLenght; i++) {
            x[i] = 48 - i * DOT_SIZE;
            y[i] = 48;
        }
        timer = new javax.swing.Timer(250, this);
        timer.start();
        createApple();
    }

    public void createApple() {
        appleX = new Random().nextInt(FIELD_LENGHT - 1) * DOT_SIZE;
        appleY = new Random().nextInt(FIELD_LENGHT - 1) * DOT_SIZE;
    }

    public void checkApple(){
        if(x[0] == appleX && y[0] == appleY){
            snakeLenght++;
            createApple();
        }
    }

    public void move() {
        for (int i = snakeLenght; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case "right" -> x[0] += DOT_SIZE;
            case "left" -> x[0] -= DOT_SIZE;
            case "bottom" -> y[0] += DOT_SIZE;
            case "up" -> y[0] -= DOT_SIZE;
        }
    }

    public void checkCollisions(){
        for (int i = snakeLenght; i > 0 ; i--) {
            if(i > 4 && x[0] == x[i - 1] && y[0] == y[i - 1]){
                inGame = false;
            }
        }
        if(x[0] > SIZE - 2 * DOT_SIZE){
            inGame = false;
        }
        if(x[0] < 0){
            inGame = false;
        }
        if(y[0] > SIZE - 2 * DOT_SIZE){
            inGame = false;
        }
        if(y[0] < 0){
            inGame = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(inGame) {
            checkApple();
            checkCollisions();
            move();
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(inGame) {
            g.drawImage(apple, appleX, appleY, this);
            g.drawImage(snakeHead, x[0], y[0], this);
            for (int i = 1; i < snakeLenght; i++) {
                g.drawImage(snake, x[i], y[i], this);
            }
        }
    }

    class FieldKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_A && direction != "right") {
                direction = "left";
            } else if (key == KeyEvent.VK_D && direction != "left") {
                direction = "right";
            } else if (key == KeyEvent.VK_W && direction != "bottom") {
                direction = "up";
            } else if (key == KeyEvent.VK_S && direction != "up") {
                direction = "bottom";
            }
        }
    }
}

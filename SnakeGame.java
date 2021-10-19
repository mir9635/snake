package com.javarush.games.snake;

import com.javarush.engine.cell.*;

public class SnakeGame extends Game {
    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;

    private static final  int GOAL = 28;

    private Snake snake;
    private int turnDelay;

    private Apple apple;
    private boolean isGameStopped;
    private int score;




    private void createGame(){
        score = 0;
        setScore(score);
        snake = new Snake(WIDTH/2, HEIGHT/2);
        createNewApple();
        isGameStopped = false;
        drawScene();
        turnDelay = 300;
        setTurnTimer(turnDelay);


    }

    private void drawScene(){
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                setCellValueEx(x, y, Color.DARKSEAGREEN, "");
            }
        }
        snake.draw(this);
        apple.draw(this);
    }

    private void createNewApple() {

        apple = new Apple(getRandomNumber(WIDTH),getRandomNumber(HEIGHT));
        while (snake.checkCollision(apple)){
            apple = new Apple(getRandomNumber(WIDTH),getRandomNumber(HEIGHT));
        }
    }

    private void gameOver() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.WHEAT, "GAME OVER", Color.BLACK, 24);
    }

    private void win() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.WHEAT, "YOU WIN", Color.BLACK, 24);
    }

    @Override
    public void initialize() {
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    @Override
    public void onTurn(int step) {
        if (!apple.isAlive) {
            createNewApple();
            score+=5;
            setScore(score);
            turnDelay-=10;
            setTurnTimer(turnDelay);
        }
        snake.move(apple);
        if (!snake.isAlive) gameOver();
        if (snake.getLength() > GOAL) win();
        drawScene();
    }

    @Override
    public void onKeyPress(Key key) {
        switch (key){
            case LEFT: snake.setDirection(Direction.LEFT); break;
            case RIGHT: snake.setDirection(Direction.RIGHT); break;
            case UP: snake.setDirection(Direction.UP); break;
            case DOWN: snake.setDirection(Direction.DOWN); break;
            case SPACE: if (isGameStopped) createGame();
        }
    }
}

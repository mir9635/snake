package com.javarush.games.snake;

import com.javarush.engine.cell.*;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private List<GameObject> snakeParts = new ArrayList<>();
    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";
    public boolean isAlive = true;
    private Direction direction = Direction.LEFT;

    public Snake(int x, int y) {
        snakeParts.add(new GameObject(x, y));
        snakeParts.add(new GameObject(x + 1, y));
        snakeParts.add(new GameObject(x + 2, y));
    }

    public void draw(Game game) {
        Color color = isAlive ? Color.BLACK : Color.RED;
        for (int t = 0; t < snakeParts.size(); t++) {

            if (t == 0) game.setCellValueEx(snakeParts.get(t).x, snakeParts.get(t).y, Color.NONE, HEAD_SIGN, color, 75);
            else game.setCellValueEx(snakeParts.get(t).x, snakeParts.get(t).y, Color.NONE, BODY_SIGN, color, 75);
        }
    }


    public void move(Apple apple) {
        GameObject gameObject = createNewHead();
        int gameObjectX = gameObject.x;
        int gameObjectY = gameObject.y;

        if (checkCollision(gameObject)) {
            isAlive = false;
            return;
        }

        int appleX = apple.x;
        int appleY = apple.y;

        if (gameObjectX < 0 || gameObjectX > SnakeGame.WIDTH - 1 || gameObjectY < 0 || gameObjectY > SnakeGame.HEIGHT - 1) {
            isAlive = false;
            return;
        }
        snakeParts.add(0, gameObject);

        if (appleX == gameObjectX && appleY == gameObjectY) apple.isAlive = false;
        else removeTail();
    }

    public GameObject createNewHead() {
        GameObject gameObject;
        int x = snakeParts.get(0).x;
        int y = snakeParts.get(0).y;

        switch (direction) {
            case LEFT:
                gameObject = new GameObject(x - 1, y);
                break;
            case DOWN:
                gameObject = new GameObject(x, y + 1);
                break;
            case RIGHT:
                gameObject = new GameObject(x + 1, y);
                break;
            case UP:
                gameObject = new GameObject(x, y - 1);
                break;
            default:
                gameObject = new GameObject(x, y);
                break;
        }
        return gameObject;
    }

    public void removeTail() {
        snakeParts.remove(snakeParts.size() - 1);
    }

    public void setDirection(Direction direction) {
        switch (this.direction) {
            case UP:
                if (Direction.DOWN == direction || snakeParts.get(0).y == snakeParts.get(1).y) return;
                break;
            case LEFT:
                if (Direction.RIGHT == direction || snakeParts.get(0).x == snakeParts.get(1).x) return;
                break;
            case DOWN:
                if (Direction.UP == direction || snakeParts.get(0).y == snakeParts.get(1).y) return;
                break;
            case RIGHT:
                if (Direction.LEFT == direction || snakeParts.get(0).x == snakeParts.get(1).x) return;
                break;
        }
        this.direction = direction;

    }

    public boolean checkCollision(GameObject gameObject) {
        for (GameObject snakePart : snakeParts) {
            if (snakePart.x == gameObject.x && snakePart.y == gameObject.y) return true;
        }
        return false;
    }

    public int getLength() {
        return snakeParts.size();
    }
}

import javax.swing.*;
import java.util.Timer;
/**
 * Created by Kevin on 4/5/2015.
 */
public class PlaySnake {
    protected static Snake snake;
    protected static Kibble kibble;
    protected static int gameScore = 0;
    protected static Timer timer;
    protected static long clockInterval = 500;
    protected static DrawSnakeGamePanel snakePanel;
    static long beginTime = System.currentTimeMillis();

    static final int BEFORE_GAME = 1;
    static final int DURING_GAME = 2;
    static final int GAME_OVER = 3;
    static final int GAME_WON = 4;   //The values are not important. The important thing is to use the constants
    //instead of the values so you are clear what you are setting. Easy to forget what number is Game over vs. game won
    //Using constant names instead makes it easier to keep it straight. Refer to these variables
    //using statements such as SnakeGame.GAME_OVER

    private static int gameStage = BEFORE_GAME;  //use this to figure out what should be happening.
    //Other classes like Snake and DrawSnakeGamePanel will need to query this, and change it's value

    public PlaySnake(int score) {
        this.gameScore = score;
        this.snakePanel = new DrawSnakeGamePanel(snake, kibble, score);
        this.snake = new Snake(snakePanel.xSquares, snakePanel.ySquares, snakePanel.squareSize);
        this.kibble = new Kibble(snake, snakePanel);
        this.timer = new Timer();
        //this.snakePanel = gameBoard.createAndShowGUI(snake, kibble, score);
        this.snakePanel = snakePanel.createAndShowGUI(snake, kibble, score);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                runSnake();
            }
        });
    }

    public static void runSnake() {
        GameClock clockTick = new GameClock(snake, kibble, gameScore, snakePanel, timer, clockInterval);
        timer.scheduleAtFixedRate(clockTick, 0, clockInterval);
    }

    public static void setGameStage(int gameStage) {
        PlaySnake.gameStage = gameStage;
    }

    public static int getGameStage() {
        return gameStage;
    }

    public static boolean gameEnded() {
        if (gameStage == GAME_OVER || gameStage == GAME_WON) {
            return true;
        }
        return false;
    }
}
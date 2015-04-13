import javax.swing.*;
import java.util.ArrayList;
import java.util.Timer;
/**
 * Created by Kevin on 4/5/2015.
 */
public class PlaySnake {
    protected static Snake snake;
    protected static Kibble kibble;
    protected static int gameScore = 0;
    protected static Timer timer = new Timer();
    //protected static GameBoard gameBoard;

    public static final long SLOW = 1000;
    public static final long MEDIUM = 500;
    public static final long FAST = 250;

    public static String speed = "Medium";
    protected static long clockInterval = MEDIUM;

    protected static DrawSnakeGamePanel gamePanel;
    public static long beginTime = System.currentTimeMillis();
    public static boolean resetGame = false;

    public static ArrayList<Blocks> blockList = new ArrayList<Blocks>();
    public static ArrayList<Snake> snakeList = new ArrayList<Snake>();
    public static ArrayList<Kibble> kibbleList = new ArrayList<Kibble>();


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
        this.gamePanel = new DrawSnakeGamePanel(snake, kibble, score);
        this.snake = new Snake(gamePanel.xSquares, gamePanel.ySquares, gamePanel.squareSize, gamePanel);
        this.snakeList.add(snake);
        this.kibble = new Kibble();
        this.kibbleList.add(kibble);
        this.gamePanel = gamePanel.createAndShowGUI(snake, kibble, score);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                runSnake();
            }
        });
    }

    public static void runSnake() {
        GameClock clockTick = new GameClock(snake, kibble, gameScore, gamePanel, timer, clockInterval);
        timer.scheduleAtFixedRate(clockTick, 0, clockInterval);
        if (gameStage == GAME_OVER){
            return;
        }
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
    /*public void setBlocksCreated(boolean newSetting){
        GameControls.blocksCreated = newSetting;
    }*/

    /*public boolean getBlocksCreated(){
        return blocksCreated;
    }*/

    public static void changeSpeed(){
        if(speed.equals("Slow")){
            clockInterval = MEDIUM;
            speed = "Medium";
        }
        else if(speed.equals("Medium")){
            clockInterval = FAST;
            speed = "Fast";
        }
        else if(speed.equals("Fast")){
            clockInterval = SLOW;
            speed = "Slow";
        }
    }
}
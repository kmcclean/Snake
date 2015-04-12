/*import javax.swing.*;
/**
 * Created by Kevin on 4/5/2015.
 */
/*
public class GameBoard extends JPanel{
    public static final int SMALL_X = 201;
    public static final int SMALL_Y = 201;

    public static final int MEDIUM_X = 501;
    public static final int MEDIUM_Y = 501;

    public static final int LARGE_X = 801;
    public static final int LARGE_Y = 801;

    public static String currentSize = "medium";
    public static int xPixelMaxDimension = MEDIUM_X;  //Pixels in window. 501 to have 50-pixel squares plus 1 to draw a border on last square
    public static int yPixelMaxDimension = MEDIUM_Y;

    public final static int squareSize = 50;

    public static int xSquares = xPixelMaxDimension / squareSize;
    public static int ySquares = yPixelMaxDimension / squareSize;

    static JFrame snakeFrame;
    static DrawSnakeGamePanel gamePanel;
    //Framework for this class adapted from the Java Swing Tutorial, FrameDemo and Custom Painting Demo. You should find them useful too.
    //http://docs.oracle.com/javase/tutorial/displayCode.html?code=http://docs.oracle.com/javase/tutorial/uiswing/examples/components/FrameDemoProject/src/components/FrameDemo.java
    //http://docs.oracle.com/javase/tutorial/uiswing/painting/step2.html

    protected static DrawSnakeGamePanel createAndShowGUI(Snake snake, Kibble kibble, int score) {
        //Create and set up the window.
        snakeFrame = new JFrame();
        snakeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        snakeFrame.setSize(xPixelMaxDimension, yPixelMaxDimension);
        snakeFrame.setUndecorated(true); //hide title bar
        snakeFrame.setVisible(true);
        snakeFrame.setResizable(false);

        gamePanel = new DrawSnakeGamePanel(snake, kibble, score);
        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow(); //required to give this component the focus so it can generate KeyEvents

        snakeFrame.add(gamePanel);
        gamePanel.addKeyListener(new GameControls(snake, kibble, gamePanel));

        snakeFrame.setVisible(true);
        return gamePanel;
    }

    public static DrawSnakeGamePanel getGamePanel() {
        return gamePanel;
    }

    public static int getxSquares() {
        return xSquares;
    }

    public static int getySquares() {
        return ySquares;
    }

    public static int getSquareSize() {
        return squareSize;
    }

    public void changeSize(){
        if (currentSize.equals("small")) {
            xPixelMaxDimension = MEDIUM_X;
            yPixelMaxDimension = MEDIUM_Y;
            currentSize = "medium";
        }
        else if (currentSize.equals("medium")) {
            xPixelMaxDimension = LARGE_X;
            yPixelMaxDimension = LARGE_Y;
            currentSize = "large";
        }
        else if(currentSize.equals("large")) {
            xPixelMaxDimension = SMALL_X;
            yPixelMaxDimension = SMALL_Y;
            currentSize = "small";
        }
    }
}
*/
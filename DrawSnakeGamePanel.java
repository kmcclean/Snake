import java.awt.*;
import java.util.LinkedList;
import javax.swing.*;

/** This class responsible for displaying the graphics, so the snake, grid, kibble, instruction text and high score
 *
 * @author Clara
 *
 */
public class DrawSnakeGamePanel extends JPanel {

	private static int gameStage = PlaySnake.getGameStage();
	static JFrame snakeFrame;
	static DrawSnakeGamePanel gamePanel;
	private Snake snake;
	private Kibble kibble;
	private int score;


	public static final int SMALL_X = 251;
	public static final int SMALL_Y = 251;

	public static final int MEDIUM_X = 501;
	public static final int MEDIUM_Y = 501;

	public static final int LARGE_X = 751;
	public static final int LARGE_Y = 751;

	public static String currentSize = "medium";
	public static int xPixelMaxDimension = MEDIUM_X;  //Pixels in window. 501 to have 50-pixel squares plus 1 to draw a border on last square
	public static int yPixelMaxDimension = MEDIUM_Y;

	public final static int squareSize = 50;

	public static int xSquares = xPixelMaxDimension / squareSize;
	public static int ySquares = yPixelMaxDimension / squareSize;


	DrawSnakeGamePanel(Snake s, Kibble k, int sc) {
		this.snake = s;
		this.kibble = k;
		this.score = sc;
	}

	public Dimension getPreferredSize() {
		return new Dimension(xPixelMaxDimension, yPixelMaxDimension);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

        /* Where are we at in the game? 4 phases.. 
         * 1. Before game starts
         * 2. During game
         * 3. Game lost aka game over
         * 4. or, game won
         */

		gameStage = PlaySnake.getGameStage();

		switch (gameStage) {
			case PlaySnake.BEFORE_GAME: {
				displayInstructions(g);
				break;
			}
			case PlaySnake.DURING_GAME: {
				displayGame(g);
				break;
			}
			case PlaySnake.GAME_OVER: {
				displayGameOver(g);
				break;
			}
			case PlaySnake.GAME_WON: {
				displayGameWon(g);
				break;
			}
		}
	}

	private void displayGameWon(Graphics g) {
		snakeFrame.setSize(501, 501);
		// TODO Replace this with something really special!
		g.clearRect(100, 100, 350, 350);
		g.drawString("YOU WON SNAKE!!!", 150, 150);

	}

	protected void displayGameOver(Graphics g) {
		snakeFrame.setSize(501, 501);
		g.clearRect(100, 100, 350, 350);
		g.drawString("Game Over. Press Enter to Continue", 150, 150);

		String textScore = SnakeGame.getStringScore();
		String textHighScore = SnakeGame.getStringHighScore();
		String newHighScore = SnakeGame.newHighScore();

		g.drawString("SCORE = " + textScore, 150, 250);

		g.drawString("HIGH SCORE = " + textHighScore, 150, 300);
		g.drawString(newHighScore, 150, 400);

		g.drawString("press ENTER to play again", 150, 350);
		g.drawString("Press q to quit the game", 150, 400);
	}

	private void displayGame(Graphics g) {
		displayGameGrid(g);
		displaySnake(g);
		displayKibble(g);
		displayBlocks(g);
	}

	private void displayGameGrid(Graphics g) {

		int maxX = xPixelMaxDimension;
		int maxY = yPixelMaxDimension;
		//int squareSize = squareSizeinPixels;

		g.clearRect(0, 0, maxX, maxY);
		g.setColor(Color.RED);

		//Draw grid - horizontal lines
		for (int y = 0; y <= maxY; y += squareSize) {
			g.drawLine(0, y, maxX, y);
		}
		//Draw grid - vertical lines
		for (int x = 0; x <= maxX; x += squareSize) {
			g.drawLine(x, 0, x, maxY);
		}
	}

	private void displayKibble(Graphics g) {

		//Draw the kibble in green
		g.setColor(Color.GREEN);

		int x = kibble.getKibbleX() * squareSize;
		int y = kibble.getKibbleY() * squareSize;

		g.fillRect(x + 1, y + 1, squareSize - 1, squareSize - 1);
	}

	private void displayBlocks(Graphics g) {
		g.setColor(Color.RED);

		for (Blocks block : PlaySnake.blockList) {
			int x = block.getBlockX() * squareSize;
			int y = block.getBlockY() * squareSize;

			g.fillRect(x + 1, y + 1, squareSize - 1, squareSize - 1);
		}

	}

	private void displaySnake(Graphics g) {

		LinkedList<Point> coordinates = snake.segmentsToDraw();

		//Draw head in grey
		g.setColor(Color.LIGHT_GRAY);
		Point head = coordinates.pop();
		g.fillRect((int) head.getX(), (int) head.getY(), squareSize, squareSize);

		//Draw rest of snake in black
		g.setColor(Color.DARK_GRAY);
		for (Point p : coordinates) {
			g.fillRect((int) p.getX(), (int) p.getY(), squareSize, squareSize);
		}

	}

	public void displayInstructions(Graphics g) {
		g.drawString("Press ENTER to begin!", 100, 100);
		g.drawString("Press 'q' to quit the game", 100, 115);
		g.drawString("Press 's' to change the size", 100, 160);
		g.drawString("Current size: " + currentSize, 100, 175);
		g.drawString("Press 'w' to turn on warp walls", 100, 220);
		g.drawString("Warp Walls: " + Snake.warpWallsOn, 100, 235);
		g.drawString("Press the space bar to change the speed.", 100, 280);
		g.drawString("Speed: " + PlaySnake.speed, 100, 295);
		g.drawString("Press 'b' to turn blocks off and on", 100, 340);
		g.drawString("Blocks: " + Blocks.blockString, 100, 355);
		g.drawString("Press 't' to toggle teleporting kibble", 100, 400);
		g.drawString("Telporting kibble: " + kibble.teleportingString, 100, 415);
	}


	//Framework for this class adapted from the Java Swing Tutorial, FrameDemo and Custom Painting Demo. You should find them useful too.
	//http://docs.oracle.com/javase/tutorial/displayCode.html?code=http://docs.oracle.com/javase/tutorial/uiswing/examples/components/FrameDemoProject/src/components/FrameDemo.java
	//http://docs.oracle.com/javase/tutorial/uiswing/painting/step2.html
	protected DrawSnakeGamePanel createAndShowGUI(Snake snake, Kibble kibble, int score) {
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

	public void changeSize(Snake snake) {
		if (currentSize.equals("small")) {
			xPixelMaxDimension = MEDIUM_X;
			yPixelMaxDimension = MEDIUM_Y;
			currentSize = "medium";
		} else if (currentSize.equals("medium")) {
			xPixelMaxDimension = LARGE_X;
			yPixelMaxDimension = LARGE_Y;
			currentSize = "large";
		} else if (currentSize.equals("large")) {
			xPixelMaxDimension = SMALL_X;
			yPixelMaxDimension = SMALL_Y;
			currentSize = "small";
		}
		setXSquares(xPixelMaxDimension);
		setYSquares(yPixelMaxDimension);
		snake.setSnakeParameters(xSquares, ySquares, squareSize);
		gamePanel.repaint();
	}

	public void setFrame() {
		snakeFrame.setSize(xPixelMaxDimension, yPixelMaxDimension);
	}

	public static int getXSquares() {
		return xSquares;
	}

	public static int getYSquares() {
		return ySquares;
	}

	public static int getSquareSize() {
		return squareSize;
	}


	public static void setXSquares(int xPixelMaxDimension) {
		DrawSnakeGamePanel.xSquares = xPixelMaxDimension / squareSize;
	}

	public static void setYSquares(int yPixelMaxDimension) {
		DrawSnakeGamePanel.ySquares = yPixelMaxDimension / squareSize;
	}
}
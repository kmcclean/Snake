import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;

public class GameControls implements KeyListener{

	public static final long SLOW = 1000;
	public static final long MEDIUM = 500;
	public static final long FAST = 250;
	public static String speed = "Medium";
	protected static long clockInterval = MEDIUM;
	protected static Timer timer = new Timer();

	Snake snake;
	Kibble kibble;
	DrawSnakeGamePanel gamePanel;

	//'Enter' key.
	private static final int PLAY_GAME_BUTTON = 10;

	GameControls(Snake s, Kibble k, DrawSnakeGamePanel gP){
		this.snake = s;
		this.kibble = k;
		this.gamePanel = gP;
	}
	
	public void keyPressed(KeyEvent ev) {
		//keyPressed events are for catching events like function keys, enter, arrow keys
		//We want to listen for arrow keys to move snake
		//Has to id if user pressed arrow key, and if so, send info to Snake object

		//is game running? No? tell the game to draw grid, start, etc.

		//Get the component which generated this event
		//Hopefully, a DrawSnakeGamePanel object.
		//It would be a good idea to catch a ClassCastException here. 

		char buttonPress = ev.getKeyChar();
		DrawSnakeGamePanel panel = (DrawSnakeGamePanel) ev.getComponent();

		//this allows the game to start if the player presses the 'Enter' key.
		if ((int) buttonPress == PLAY_GAME_BUTTON && PlaySnake.getGameStage() == PlaySnake.BEFORE_GAME) {
			//Start the game
			PlaySnake.setGameStage(PlaySnake.DURING_GAME);
			gamePanel.snakeFrame.setSize(gamePanel.xPixelMaxDimension, gamePanel.yPixelMaxDimension);
			snake.startSnake(gamePanel);
			kibble.placeKibble(snake, gamePanel);
			if (Blocks.wantsBlocks) {
				PlaySnake.blockList.clear();
				int numberOfBlocks = (int)((gamePanel.xSquares * gamePanel.ySquares)/20);
				for (int i = 1; i <= numberOfBlocks; i++) {
					Blocks block = new Blocks(snake, gamePanel, kibble);
					PlaySnake.blockList.add(block);
				}

			}
			PlaySnake.runSnake();
			panel.repaint();
			return;
		}

		//allows the player to restart if they have lost at snake.
		if ((int) buttonPress == PLAY_GAME_BUTTON && PlaySnake.getGameStage() == PlaySnake.GAME_OVER) {
			gamePanel.snakeFrame.setSize(gamePanel.xPixelMaxDimension, gamePanel.yPixelMaxDimension);
			snake.reset(gamePanel);
			SnakeGame.resetScore();
			kibble.placeKibble(snake, gamePanel);
			for (Blocks b : PlaySnake.blockList) {
				b.moveBlock(snake, gamePanel, kibble);
			}
			//Need to start the timer and start the game again
			PlaySnake.setGameStage(PlaySnake.DURING_GAME);
			PlaySnake.runSnake();
			panel.repaint();
			return;
		}


		if (ev.getKeyCode() == KeyEvent.VK_DOWN) {
			//System.out.println("snake down");
			snake.snakeDown();
		}
		if (ev.getKeyCode() == KeyEvent.VK_UP) {
			//System.out.println("snake up");
			snake.snakeUp();
		}
		if (ev.getKeyCode() == KeyEvent.VK_LEFT) {
			//System.out.println("snake left");
			snake.snakeLeft();
		}
		if (ev.getKeyCode() == KeyEvent.VK_RIGHT) {
			//System.out.println("snake right");
			snake.snakeRight();
		}

	}


	@Override
	public void keyReleased(KeyEvent ev) {
		//We don't care about keyReleased events, but are required to implement this method anyway.		
	}


	@Override
	public void keyTyped(KeyEvent ev) {
		//keyTyped events are for user typing letters on the keyboard, anything that makes a character display on the screen
		char keyPressed = ev.getKeyChar();
		char q = 'q';
		char w = 'w';
		char b = 'b';
		char s = 's';
		char t = 't';
		if( keyPressed == q){
			System.exit(0);    //quit if user presses the q key.
		}

		//turns warp walls on and off.
		if(keyPressed == w && PlaySnake.getGameStage() == PlaySnake.BEFORE_GAME){
			if(Snake.warpOn){
				Snake.warpOn = false;
				Snake.warpWallsOn = "Off";
			}
			else{
				Snake.warpOn = true;
				Snake.warpWallsOn = "On";
			}
			gamePanel.repaint();
		}

		//turns blocks on and off.
		if (keyPressed == b){
			if (Blocks.wantsBlocks){
				Blocks.wantsBlocks = false;
				Blocks.blockString = "Off";
			}
			else if (!Blocks.wantsBlocks){
				Blocks.wantsBlocks = true;
				Blocks.blockString = "On";
			}
			gamePanel.repaint();
		}

		//changes the size of the board being used.
		if (keyPressed == s && PlaySnake.getGameStage() == PlaySnake.BEFORE_GAME){
			gamePanel.changeSize(snake);
		}

		//turns teleporting kibble on and off.
		if (keyPressed == t && PlaySnake.getGameStage() == PlaySnake.BEFORE_GAME){
			if(kibble.teleportingKibble){
				kibble.teleportingKibble = false;
				kibble.teleportingString = "Off";
			}
			else {
				kibble.teleportingKibble = true;
				kibble.teleportingString = "On";
			}
		}

		//changes the speed at which the snake moves.
		if((int)keyPressed == 32 && PlaySnake.getGameStage() == PlaySnake.BEFORE_GAME){
			PlaySnake.changeSpeed();
		}

		gamePanel.repaint();

	}
}

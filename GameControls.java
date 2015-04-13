import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameControls implements KeyListener{
	
	Snake snake;
	Kibble kibble;
	DrawSnakeGamePanel gamePanel;
	private static final int START_BUTTON = 10;


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

		char start = ev.getKeyChar();
		DrawSnakeGamePanel panel = (DrawSnakeGamePanel) ev.getComponent();

		if ((int) start == START_BUTTON && PlaySnake.getGameStage() == PlaySnake.BEFORE_GAME) {
			//Start the game
			PlaySnake.setGameStage(PlaySnake.DURING_GAME);
			gamePanel.setFrame();
			snake.startSnake(gamePanel);
			kibble.placeKibble(snake, gamePanel);
			if (Blocks.wantsBlocks) {
				PlaySnake.blockList.clear();
				for (int i = 1; i <= 5; i++) {
					Blocks block = new Blocks(snake, gamePanel, kibble);
					PlaySnake.blockList.add(block);
				}

			}

			PlaySnake.runSnake();
			panel.repaint();
			return;
		}


		if (PlaySnake.getGameStage() == PlaySnake.GAME_OVER) {
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
		if(keyPressed == w && PlaySnake.getGameStage() == PlaySnake.BEFORE_GAME){
			if(Snake.warpOn){
				Snake.warpOn = false;
				Snake.warpWallsOn = "Off";
			}
			else{
				Snake.warpOn = true;
				Snake.warpWallsOn = "On";
			}
		}
		if (keyPressed == b){
			if (Blocks.wantsBlocks){
				Blocks.wantsBlocks = false;
				Blocks.blockString = "Off";
			}
			else if (!Blocks.wantsBlocks){
				Blocks.wantsBlocks = true;
				Blocks.blockString = "On";
			}
		}
		if (keyPressed == s && PlaySnake.getGameStage() == PlaySnake.BEFORE_GAME){
			gamePanel.changeSize(snake);
		}

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

		if((int)keyPressed == 32 && PlaySnake.getGameStage() == PlaySnake.BEFORE_GAME){
			PlaySnake.changeSpeed();
		}
	}

}

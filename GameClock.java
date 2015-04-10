import java.util.Timer;
import java.util.TimerTask;

public class GameClock extends TimerTask {

	Snake snake;
	Kibble kibble;
	int score;
	DrawSnakeGamePanel gamePanel;
	Timer timer;
	long clockInterval;
	int count = 0;

	public GameClock(Snake snake, Kibble kibble, int score, DrawSnakeGamePanel gamePanel, Timer timer, long clock){
		this.snake = snake;
		this.kibble = kibble;
		this.score = score;
		this.gamePanel = gamePanel;
		this.timer = timer;
		this.clockInterval = clock;
	}
	
	@Override
	public void run() {
		// This method will be called every clock tick
		count++;
		int stage = PlaySnake.getGameStage();
		switch (stage) {
			case PlaySnake.BEFORE_GAME: {
				//don't do anything, waiting for user to press a key to start
				break;
			}
			case PlaySnake.DURING_GAME: {
				snake.moveSnake();
				long endTime = System.currentTimeMillis();
				System.out.println("Time between turns: " + (endTime - PlaySnake.beginTime));
				PlaySnake.beginTime = System.currentTimeMillis();

				if (snake.didEatKibble(kibble) == true) {
					//tell kibble to update
					kibble.moveKibble(snake, gamePanel);
					SnakeGame.increaseScore();
				}
				break;
			}
			case PlaySnake.GAME_OVER: {
				this.cancel();		//Stop the Timer
				break;	
			}
			case PlaySnake.GAME_WON: {
				this.cancel();   //stop timer
				break;
			}
		}
		gamePanel.repaint();		//In every circumstance, must update screen
	}
}

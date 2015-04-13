import java.util.Timer;
import java.util.TimerTask;

public class GameClock extends TimerTask {

	Snake snake;
	Kibble kibble;
	int score;
	DrawSnakeGamePanel gamePanel;
	Timer timer;
	long clockInterval;


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

		int stage = PlaySnake.getGameStage();
		switch (stage) {
			case PlaySnake.BEFORE_GAME: {
				System.out.println("Snake max screen size before game: " + snake.maxX +", " + snake.maxY);
				System.out.println("Game max sizes before game: " + gamePanel.xSquares + ", " + gamePanel.ySquares);

				break;
			}
			case PlaySnake.DURING_GAME: {
				System.out.println("Snake max screen size during game: " + snake.maxX +", " + snake.maxY);
				System.out.println("Game max sizes during game: " + gamePanel.xPixelMaxDimension + ", " + gamePanel.yPixelMaxDimension);
				if(kibble.teleportingKibble) {
					Kibble.kibbleTeleportCounter++;
					if (Kibble.kibbleTeleportCounter >= 5) {
						kibble.placeKibble(snake, gamePanel);
						Kibble.kibbleTeleportCounter = 0;
					}
				}
				snake.moveSnake();


				/*long endTime = System.currentTimeMillis();
				System.out.println("Time between turns: " + (endTime - PlaySnake.beginTime));
				PlaySnake.beginTime = System.currentTimeMillis();*/


				if (snake.didEatKibble(kibble) == true) {
					//tell kibble to update
					kibble.placeKibble(snake, gamePanel);
					SnakeGame.increaseScore();
				}
				break;
			}
			case PlaySnake.GAME_OVER: {

				this.cancel();		//Stop the Timer
				break;	
			}
			case PlaySnake.GAME_WON: {
				PlaySnake.resetGame = true;
				this.cancel();   //stop timer
				break;
			}
		}
		gamePanel.repaint();		//In every circumstance, must update screen
	}
}

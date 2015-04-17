import java.util.Timer;
import java.util.TimerTask;

public class GameClock extends TimerTask {

	Snake snake;
	Kibble kibble;
	int score;
	DrawSnakeGamePanel gamePanel;
	Timer timer;
	long clockInterval;

	/*public GameClock(){

	}*/

	public void setupGameClock(Snake snake, Kibble kibble, int score, DrawSnakeGamePanel gamePanel, Timer timer, long clock){
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
		long endTime= System.currentTimeMillis();
		System.out.println("Time between turns is: " + (endTime-PlaySnake.beginTime));
		PlaySnake.beginTime = endTime;

		int stage = PlaySnake.getGameStage();
		switch (stage) {
			case PlaySnake.BEFORE_GAME: {
				PlaySnake.stopClock();
				break;
			}
			case PlaySnake.DURING_GAME: {
				//runs the teleporter if it is turned on.
				if(kibble.teleportingKibble) {
					Kibble.kibbleTeleportCounter++;
					if (Kibble.kibbleTeleportCounter >= 5) {
						kibble.placeKibble(snake, gamePanel);
						Kibble.kibbleTeleportCounter = 0;
					}
				}

				//moves the snake.
				if(!snake.moveSnake()){
					return;
				}
				//checks to see if the kibble was eaten. the game then checks the score to see if the game was won...
				//...and ends the game if it was. Otherwise it continues the game.
				if (snake.didEatKibble(kibble) == true) {
					SnakeGame.increaseScore();
					if(snake.checkForVictory()){
						PlaySnake.setGameStage(PlaySnake.GAME_WON);
						return;
					}
					kibble.placeKibble(snake, gamePanel);
				}
				break;
			}


			case PlaySnake.GAME_OVER: {
				PlaySnake.timer.purge();
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

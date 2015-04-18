import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Timer;
import java.util.TimerTask;

public class GameClock extends TimerTask {

	Snake snake;
	Kibble kibble;
	int score;
	DrawSnakeGamePanel gamePanel;
	Timer timer;
	long clockInterval;
	InputStream inputStream;


	//these files were taken from the SoundBible webpage. Each of these was converted from a wav file to an aif file.
	//http://soundbible.com/2084-Glass-Ping.html
	final String GLASS_PING = "KIBBLE_EATEN.aif";
	//http://soundbible.com/1881-Sports-Crowd.html
	final String LOUD_CHEERING = "NEAR_VICTORY.aif";
	//http://soundbible.com/219-Diamond-Back-Rattle-Snake.html
	final String GAME_OVER = "GAME_OVER.aif";
	AudioStream audioStream;
	String fullPath;
	Path path;
	//AISnake aiSnake;

	public void setupGameClock(Snake snake, Kibble kibble, int score, DrawSnakeGamePanel gamePanel, Timer timer, long clock){
		this.snake = snake;
		//this.aiSnake = ais;
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
				break;
			}
			case PlaySnake.DURING_GAME: {


				//runs the teleporter if it is turned on.
				if(kibble.teleportingKibble) {
					Kibble.kibbleTeleportCounter++;
					if (Kibble.kibbleTeleportCounter >= Kibble.kibbleTeleportNumber) {
						kibble.placeKibble(snake, gamePanel);
						kibble.changeKibbleTeleportNumber();
					}
				}

				//moves the snake.
				if(!snake.moveSnake()){
					return;
				}
				//aiSnake.moveAISnake(kibble);
				//checks to see if the kibble was eaten. the game then checks the score to see if the game was won...
				//...and ends the game if it was. Otherwise it continues the game.
				if (snake.didEatKibble(kibble) == true) {
					SnakeGame.increaseScore();
					if (snake.checkForVictory()) {
						PlaySnake.setGameStage(PlaySnake.GAME_WON);
						return;
					}

					kibble.placeKibble(snake, gamePanel);

					//this controls the cheering. It becomes more raucous as the play gets over 90% of the board filled.
					if (snake.getSnakeSize() < ((snake.getMaxX() * snake.getMaxY())*.90)) {
						try {
							path = Paths.get(GLASS_PING);
							fullPath = path.toAbsolutePath().toString();
							inputStream = new FileInputStream(fullPath);
							audioStream = new AudioStream(inputStream);
							AudioPlayer.player.start(audioStream);
						} catch (Exception e) {
						}
					}
					else{
						try {
							path = Paths.get(LOUD_CHEERING);
							fullPath = path.toAbsolutePath().toString();
							inputStream = new FileInputStream(fullPath);
							audioStream = new AudioStream(inputStream);
							AudioPlayer.player.start(audioStream);
						} catch (Exception e) {
						}
					}

				}
				break;
			}

			//plays the game over sound if you lose.
			case PlaySnake.GAME_OVER: {
				try {
					path = Paths.get(GAME_OVER);
					fullPath = path.toAbsolutePath().toString();
					inputStream = new FileInputStream(fullPath);
					audioStream = new AudioStream(inputStream);
					AudioPlayer.player.start(audioStream);
				} catch (Exception e) {
				}
					break;
			}

			//just in case you win.
			case PlaySnake.GAME_WON: {
				break;
			}
		}
		gamePanel.repaint();		//In every circumstance, must update screen
	}
}

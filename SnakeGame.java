public class SnakeGame {

	protected static int score;
	protected static int highScore = 0;
	protected static final int INCREMENT = 1;

	//runs a new game of snake.
	public static void main(String[] args) {
		score = 0;
		PlaySnake newGame = new PlaySnake(score);
	}

	//creates a string version of the score.
	public static String getStringScore() {
		return Integer.toString(score);
	}

	//sets a new high score if one is reached.
	public static String newHighScore() {

		if (score > highScore) {
			highScore = score;
			return "New High Score!!";
		} else {
			return "";
		}
	}

	//gets the high score.
	public static String getStringHighScore() {
		return Integer.toString(highScore);
	}

	//resets the score.
	public static void resetScore(){
		score = 0;
	}

	//increases the score if the snake eats some kibble.
	public static void increaseScore() {
		score = score + INCREMENT;
	}
}
public class SnakeGame {

	protected static int score;
	protected static int highScore = 0;
	protected static int increment = 1;

	public static void main(String[] args) {
		score = 0;
		PlaySnake newGame = new PlaySnake(score);
	}


	public static String getStringScore() {
		return Integer.toString(score);
	}

	public static String newHighScore() {

		if (score > highScore) {
			highScore = score;
			return "New High Score!!";
		} else {
			return "";
		}
	}
	public static String getStringHighScore() {
		return Integer.toString(highScore);
	}


	public static void resetScore(){
		score = 0;
	}


	public static void increaseScore() {
		score = score + increment;
	}
}
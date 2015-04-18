import java.util.Random;

public class Kibble {

	/**
	 * Identifies a random square to display a kibble
	 * Any square is ok, so long as it doesn't have any snake segments in it.
	 */
	private int kibbleX; //This is the square number (not pixel)
	private int kibbleY;  //This is the square number (not pixel)
	public static int kibbleTeleportCounter = 0;
	public static int kibbleTeleportNumber = 5; //this is the number that the teleport is counting to (changes after each move).
	private int kibbleSquares[][];

	//these are used when the player chooses to have the kibble's teleporting ability (i.e. rerandomize its location) turned on.
	public static boolean teleportingKibble = false;
	public static String teleportingString = "Off";

	//this places the kibble on a random square within the game panel. It keeps trying until it puts it in a spot not occupied by either the snake or a block.
	//mainly the version that originally existed in the original conception.
	protected void placeKibble(Snake s, DrawSnakeGamePanel snakePanel) {

		Random rng = new Random();
		boolean kibbleInSnake = true;
		boolean kibbleOnBlock = true;
		int count = 1;
		while (kibbleInSnake || kibbleOnBlock) {
			//Generate random kibble location
			kibbleOnBlock = false;
			kibbleX = rng.nextInt(snakePanel.xSquares);
			kibbleY = rng.nextInt(snakePanel.ySquares);
			kibbleInSnake = s.isSnakeSegment(kibbleX, kibbleY);
			for (Blocks b : PlaySnake.blockList) {
				kibbleOnBlock = b.isBlocksSquare(kibbleX, kibbleY);
				if (kibbleOnBlock) {
					kibbleOnBlock = true;
					break;
				}
			}
		}
		kibbleSquares = new int[kibbleX][kibbleY];
		kibbleTeleportCounter = 0;
	}

	//this checks if the kibble is on a specific square. Used by the block to see if the space is open.
	public boolean isKibbleSquare(int tryX, int tryY) {
		if (getKibbleX() == tryX && getKibbleY() == tryY) {
			return true;
		}
		return false;
	}

	//this gets the x location of the kibble.
	public int getKibbleX() {
		return kibbleX;
	}

	//this gets the y location of the kibble.
	public int getKibbleY() {
		return kibbleY;
	}

	//randomize how long the kibble stays in one location.
	public int changeKibbleTeleportNumber() {
		Random random = new Random();
		kibbleTeleportNumber = random.nextInt(10);
		return  kibbleTeleportNumber;
	}
}

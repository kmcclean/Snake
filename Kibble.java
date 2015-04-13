import java.util.Random;

public class Kibble {

	/** Identifies a random square to display a kibble
	 * Any square is ok, so long as it doesn't have any snake segments in it. 
	 * 
	 */
	private int kibbleX; //This is the square number (not pixel)
	private int kibbleY;  //This is the square number (not pixel)
	public static int kibbleTeleportCounter = 0;
	private int kibbleSquares[][];

	public static boolean teleportingKibble = false;
	public static String teleportingString = "Off";
	
	public Kibble(){
		//Kibble needs to know where the snake is, so it does not create a kibble in the snake
		//Pick a random location for kibble, check if it is in the snake
		//If in snake, try again
		//placeKibble(s, sp);
	}
	
	protected void placeKibble(Snake s, DrawSnakeGamePanel snakePanel){
		
		Random rng = new Random();
		boolean kibbleInSnake = true;
		boolean kibbleOnBlock = true;
		while (kibbleInSnake || kibbleOnBlock) {
			//Generate random kibble location
			kibbleOnBlock = false;
			kibbleX = rng.nextInt(snakePanel.xSquares);
			kibbleY = rng.nextInt(snakePanel.ySquares);
			kibbleInSnake = s.isSnakeSegment(kibbleX, kibbleY);
			for (Blocks b:PlaySnake.blockList){
				kibbleOnBlock = b.isBlocksSquare(kibbleX, kibbleY);
				if (kibbleOnBlock){
					kibbleOnBlock = true;
					break;
				}
			}
		}
		kibbleSquares = new int[kibbleX][kibbleY];
		kibbleTeleportCounter = 0;
	}

	public boolean isKibbleSquare(int tryX, int tryY) {
		if (getKibbleX() == tryX && getKibbleY() == tryY) {
			return true;
		}
		return false;
	}


	public int getKibbleX() {
		return kibbleX;
	}

	public int getKibbleY() {
		return kibbleY;
	}


	
}

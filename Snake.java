import java.awt.Point;
import java.util.LinkedList;

public class Snake {

	final int DIRECTION_UP = 0;
	final int DIRECTION_DOWN = 1;
	final int DIRECTION_LEFT = 2;
	final int DIRECTION_RIGHT = 3;  //These are completely arbitrary numbers. 

	protected static boolean warpOn = false;
	public static String warpWallsOn = "Off";

	private int snakeSquares[][];  //represents all of the squares on the screen
	//NOT pixels!
	//A 0 means there is no part of the snake in this square
	//A non-zero number means part of the snake is in the square
	//The head of the snake is 1, rest of segments are numbered in order

	private int currentHeading;  //Direction snake is going in, ot direction user is telling snake to go
	private int lastHeading;    //Last confirmed movement of snake. See moveSnake method
	
	private int snakeSize;   //size of snake - how many segments?

	private int growthIncrement = 2; //how many squares the snake grows after it eats a kibble

	private int justAteMustGrowThisMuch = 0;

	public static int maxX, maxY, squareSize;
	public int snakeHeadX, snakeHeadY; //store coordinates of head - first segment

	public Snake(int maxX, int maxY, int squareSize, DrawSnakeGamePanel gamePanel){
		setSnakeParameters(maxX, maxY, squareSize);

		//Create and fill snakeSquares with 0s
		fillSquares(maxX, maxY);
		startSnake(gamePanel);
	}

	protected void startSnake(DrawSnakeGamePanel gamePanel){//int maxX, int maxY, int squareSize){
		setSnakeParameters(maxX, maxY, squareSize);

		//Create and fill snakeSquares with 0s
		fillSquares(this.maxX, this.maxY);
		//snake starts as 3 horizontal squares in the center of the screen, moving left

		int screenXCenter = (int) maxX/2;  //Cast just in case we have an odd number
		int screenYCenter = (int) maxY/2;  //Cast just in case we have an odd number

		snakeSquares[screenXCenter][screenYCenter] = 1;
		snakeSquares[screenXCenter+1][screenYCenter] = 2;
		snakeSquares[screenXCenter+2][screenYCenter] = 3;

		snakeHeadX = screenXCenter;
		snakeHeadY = screenYCenter;

		snakeSize = 3;

		currentHeading = DIRECTION_LEFT;
		lastHeading = DIRECTION_LEFT;
		
		justAteMustGrowThisMuch = 0;
	}

	private void fillSnakeSquaresWithZeros() {
		for (int x = 0; x < this.maxX; x++){
			for (int y = 0 ; y < this.maxY ; y++) {
				snakeSquares[x][y] = 0;
			}
		}
	}

	public LinkedList<Point> segmentsToDraw(){
		//Return a list of the actual x and y coordinates of the top left of each snake segment
		//Useful for the Panel class to draw the snake
		LinkedList<Point> segmentCoordinates = new LinkedList<Point>();
		for (int segment = 1 ; segment <= snakeSize ; segment++ ) {
			//search array for each segment number
			for (int x = 0 ; x < maxX ; x++) {
				for (int y = 0 ; y < maxY ; y++) {
					if (snakeSquares[x][y] == segment){
						//make a Point for this segment's coordinates and add to list
						Point p = new Point(x * squareSize , y * squareSize);
						segmentCoordinates.add(p);
					}
				}
			}
		}
		return segmentCoordinates;

	}

	public void snakeUp(){
		if (currentHeading == DIRECTION_UP || currentHeading == DIRECTION_DOWN) { return; }
		currentHeading = DIRECTION_UP;
	}
	public void snakeDown(){
		if (currentHeading == DIRECTION_DOWN || currentHeading == DIRECTION_UP) { return; }
		currentHeading = DIRECTION_DOWN;
	}
	public void snakeLeft(){
		if (currentHeading == DIRECTION_LEFT || currentHeading == DIRECTION_RIGHT) { return; }
		currentHeading = DIRECTION_LEFT;
	}
	public void snakeRight(){
		if (currentHeading == DIRECTION_RIGHT || currentHeading == DIRECTION_LEFT) { return; }
		currentHeading = DIRECTION_RIGHT;
	}

	protected boolean moveSnake(){

		//Called every clock tick
		//Must check that the direction snake is being sent in is not contrary to current heading
		//So if current heading is down, and snake is being sent up, then should ignore.
		//Without this code, if the snake is heading up, and the user presses left then down quickly, the snake will back into itself.
		if (currentHeading == DIRECTION_DOWN && lastHeading == DIRECTION_UP) {
			currentHeading = DIRECTION_UP; //keep going the same way
		}
		if (currentHeading == DIRECTION_UP && lastHeading == DIRECTION_DOWN) {
			currentHeading = DIRECTION_DOWN; //keep going the same way
		}
		if (currentHeading == DIRECTION_LEFT && lastHeading == DIRECTION_RIGHT) {
			currentHeading = DIRECTION_RIGHT; //keep going the same way
		}
		if (currentHeading == DIRECTION_RIGHT && lastHeading == DIRECTION_LEFT) {
			currentHeading = DIRECTION_LEFT; //keep going the same way
		}


		//Use snakeSquares array, and current heading, to move snake
		//Put a 1 in new snake head square
		//increase all other snake segments by 1
		//set tail to 0 if snake did not just eat
		//Otherwise leave tail as is until snake has grown the correct amount 
		//Find the head of the snake - snakeHeadX and snakeHeadY
		//Increase all snake segments by 1
		//All non-zero elements of array represent a snake segment

		for (int x = 0 ; x < maxX ; x++) {
			for (int y = 0 ; y < maxY ; y++){
				if (snakeSquares[x][y] != 0) {
					snakeSquares[x][y]++;
				}
			}
		}

		//now identify where to add new snake head
		if (currentHeading == DIRECTION_UP) {		
			//Subtract 1 from Y coordinate so head is one square up
			snakeHeadY-- ;
		}
		if (currentHeading == DIRECTION_DOWN) {		
			//Add 1 to Y coordinate so head is 1 square down
			snakeHeadY++ ;
		}
		if (currentHeading == DIRECTION_LEFT) {		
			//Subtract 1 from X coordinate so head is 1 square to the left
			snakeHeadX -- ;
		}
		if (currentHeading == DIRECTION_RIGHT) {		
			//Add 1 to X coordinate so head is 1 square to the right
			snakeHeadX ++ ;
		}

		if (warpOn){
			warpWalls();
		}

		if(isGameOver()){
			return false;
		}
		//Otherwise, game is still on. Add new head
		snakeSquares[snakeHeadX][snakeHeadY] = 1; 

		//If snake did not just eat, then remove tail segment
		//to keep snake the same length.
		//find highest number, which should now be the same as snakeSize+1, and set to 0
		
		if (justAteMustGrowThisMuch == 0) {
			for (int x = 0 ; x < maxX ; x++) {
				for (int y = 0 ; y < maxY ; y++){
					if (snakeSquares[x][y] == snakeSize+1) {
						snakeSquares[x][y] = 0;
					}
				}
			}
		}
		else {
			//Snake has just eaten. leave tail as is.  Decrease justAte... variable by 1.
			justAteMustGrowThisMuch -- ;
			snakeSize ++;
		}
		lastHeading = currentHeading; //Update last confirmed heading
		return true;

	}

	//determines whether or not a given square is occupied by the snake.
	public boolean isSnakeSegment(int tryX, int tryY) {
		if (snakeSquares[tryX][tryY] == 0) {
			return false;
		}
		return true;
	}

	//checks to see if the snake did eat some kibble.
	public boolean didEatKibble(Kibble kibble) {
		//Is this kibble in the snake? It should be in the same square as the snake's head
		if (kibble.getKibbleX() == snakeHeadX && kibble.getKibbleY() == snakeHeadY){
			justAteMustGrowThisMuch += growthIncrement;
			return true;
		}
		return false;
	}

	//checks to see if the snake ran into its own tail.
	public boolean didEatTail() {
		if (snakeSquares[snakeHeadX][snakeHeadY] != 0) {
			PlaySnake.setGameStage(PlaySnake.GAME_OVER);
			return true;
		}
		else{
			return false;
		}
	}

	//checks to see if the snake ran into a block.
	public boolean didHitBlock(){
		for (Blocks b: PlaySnake.blockList) {
			if (b.getBlockX() == snakeHeadX && b.getBlockY() == snakeHeadY) {
				return true;
			}
		}
		return false;
	}

	//puts the snake into a string.
	public String toString(){
		String textsnake = "";
		//This looks the wrong way around. Actually need to do it this way or snake is drawn flipped 90 degrees. 
		for (int y = 0 ; y < maxY ; y++) {
			for (int x = 0 ; x < maxX ; x++){
				textsnake = textsnake + snakeSquares[x][y];
			}
			textsnake += "\n";
		}
		return textsnake;
	}

	//resets the game to its starting point.
	public void reset(DrawSnakeGamePanel gamePanel) {
		fillSnakeSquaresWithZeros();
		startSnake(gamePanel);
	}

	//this checks to see if the game has been lost.
	public boolean isGameOver() {
		if (didHitWall()|| didEatTail() || didHitBlock()){
			PlaySnake.setGameStage(PlaySnake.GAME_OVER);
			return true;
		}
		return false;
	}

	//this is the setting for warp walls.
	public void warpWalls() {
		if (snakeHeadX >= maxX) {
			snakeHeadX = 0;
		} else if (snakeHeadX < 0) {
			snakeHeadX = maxX - 1;
		} else if (snakeHeadY >= maxY) {
			snakeHeadY = 0;
		} else if (snakeHeadY < 0) {
			snakeHeadY = maxY - 1;
		}
	}

	//this is the setting for hard walls. This is used when warp walls are turned off.
	public boolean didHitWall() {
		if ((snakeHeadX >= maxX || snakeHeadX < 0 || snakeHeadY >= maxY || snakeHeadY < 0) && (!warpOn)) {
			return true;
		}
		else{
			return false;
		}
	}

	//checks to see if the game has been won.
	public boolean checkForVictory(){
		if(snakeSize >= ((maxX * maxY) - PlaySnake.blockList.size())){
			PlaySnake.setGameStage(PlaySnake.GAME_WON);
			return true;
		}
		return false;
	}

	//sets the parameters of the snake's movement. Used so that match that of the game panel.
	public void setSnakeParameters(int newX, int newY, int squareSize) {
		this.maxX = newX;
		this.maxY = newY;
		this.squareSize = squareSize;
	}

	//fills all the squares that the snakeOccupies with 1 and the rest with 0s.
	public void fillSquares (int maxX, int maxY){
		this.snakeSquares = new int [maxX][maxY];
		fillSnakeSquaresWithZeros();
	}

	public int getSnakeSize() {return snakeSize;
	}

	public static int getMaxX() {
		return maxX;
	}

	public static int getMaxY() {
		return maxY;
	}
}
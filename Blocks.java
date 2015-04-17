import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Kevin on 4/10/2015.
 */
public class Blocks {
    protected static boolean wantsBlocks = false;
    protected static String blockString = "Off";

    private int blockX;
    private int blockY;
    private int blockSquares[][];

    //creates a new block.
    public Blocks(Snake s, DrawSnakeGamePanel snakePanel, Kibble k) {
        Random rng = new Random();
        boolean snakeLocation = true;
        while (snakeLocation) {
            this.blockX = rng.nextInt(snakePanel.xSquares);
            this.blockY = rng.nextInt(snakePanel.ySquares);
            snakeLocation = s.isSnakeSegment(blockX, blockY);
        }
        this.blockSquares = new int[blockX][blockY];
    }

    //this moves the block after each game of snake.
    protected void moveBlock(Snake s, DrawSnakeGamePanel snakePanel, Kibble k){
        Random rng = new Random();
        boolean blockInSnake = true;
        boolean blockOnKibble = true;

        //makes sure that the block doesn't land on the same
        while (blockInSnake || blockOnKibble) {
            blockOnKibble = false;
            this.blockX = rng.nextInt(snakePanel.xSquares);
            this.blockY = rng.nextInt(snakePanel.ySquares);
            blockInSnake = s.isSnakeSegment(this.blockX, this.blockY);
            k.isKibbleSquare(blockX, blockY);
        }
        this.blockSquares= new int[blockX][blockY];
    }

    //checks to see if the block is on a specific square.
    public boolean isBlocksSquare(int tryX, int tryY){
        if (getBlockX() == tryX && getBlockY() == tryY){
            return true;
        }
        return false;
    }

    //gts the X coordinate of the block.
    public int getBlockY() {
        return this.blockY;
    }

    //gets the Y coordinate of the block.
    public int getBlockX() {
        return this.blockX;
    }
}

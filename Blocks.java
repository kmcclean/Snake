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


    public Blocks(Snake s, DrawSnakeGamePanel snakePanel, Kibble k) {
        Random rng = new Random();
        boolean snakeLocation = true;
        boolean kibbleLocation = true;
        while (snakeLocation) {// || kibbleLocation) {
            this.blockX = rng.nextInt(snakePanel.xSquares);
            this.blockY = rng.nextInt(snakePanel.ySquares);
            snakeLocation = s.isSnakeSegment(blockX, blockY);
//            kibbleLocation = k.isKibbleSquare(blockX, blockY);
        }
        this.blockSquares = new int[blockX][blockY];
    }
    protected void moveBlock(Snake s, DrawSnakeGamePanel snakePanel, Kibble k){

        Random rng = new Random();
        boolean kibbleInSnake = true;
        boolean blockOnKibble = true;
        while (kibbleInSnake || blockOnKibble) {
            blockOnKibble = false;
            this.blockX = rng.nextInt(snakePanel.xSquares);
            this.blockY = rng.nextInt(snakePanel.ySquares);
            kibbleInSnake = s.isSnakeSegment(this.blockX, this.blockY);
            k.isKibbleSquare(blockX, blockY);
        }
        this.blockSquares= new int[blockX][blockY];
    }

    public boolean isBlocksSquare(int tryX, int tryY){
        if (getBlockX() == tryX && getBlockY() == tryY){
            return true;
        }
        return false;
    }

    public int getBlockY() {
        return blockY;
    }

    public int getBlockX() {
        return blockX;
    }
}

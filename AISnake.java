/*import java.util.*;

/**
 * Created by Kevin on 4/17/2015.
 */
/*public class AISnake {

    final int DIRECTION_UP = 0;
    final int DIRECTION_DOWN = 1;
    final int DIRECTION_LEFT = 2;
    final int DIRECTION_RIGHT = 3;

    private int justAteMustGrowThisMuch = 0;

    public static int maxX, maxY, squareSize;
    public int snakeHeadX, snakeHeadY;
    LinkedList<ArrayList> snakeBody;
    int aiSquares[][];
    ArrayList<Integer> snakeHead;
    int snakeSize;

    public AISnake(int maxX, int maxY, int squareSize, DrawSnakeGamePanel gamePanel) {
        setAISnakeParameters(maxX, maxY, squareSize);
        this.snakeBody = new LinkedList<ArrayList>();
        this.snakeHead = new ArrayList<Integer>();
    }

    protected void startAISnake(DrawSnakeGamePanel gamePanel) {//int maxX, int maxY, int squareSize){
        setAISnakeParameters(maxX, maxY, squareSize);

        //Create and fill snakeSquares with 0s
        //fillAISquares(this.maxX, this.maxY);
        //snake starts as 3 horizontal squares in the center of the screen, moving left

        int screenXCenter = (int) maxX / 2;  //Cast just in case we have an odd number
        int screenYCenter = (int) maxY / 2;  //Cast just in case we have an odd number
        aiSquares[] = new int [maxX][maxY];
        aiSquares[maxX][maxX] = 2;
        aiSquares[maxX][maxX] = 3;
        System.out.println(aiSquares.toString());

        snakeHead = snakeBody.get(0);

        snakeHeadX = snakeHead.get(0);
        snakeHeadY = snakeHead.get(1);
        snakeSize = 3;

        justAteMustGrowThisMuch = 0;
    }

    protected boolean moveAISnake(Kibble kibble) {

        int kibbleX = kibble.getKibbleX();
        int kibbleY = kibble.getKibbleY();

        int xDistance = Math.abs(snakeHeadX - kibbleX);
        int yDistance = Math.abs(snakeHeadY - kibbleY);

        if(xDistance >= yDistance) {
            if (snakeHeadX > kibbleX) {
                snakeHeadX--;
            } else {
                snakeHeadX++;
            }

        }
        else {
            if (snakeHeadY > kibbleY) {
                snakeHeadY--;
            } else {
                snakeHeadY++;
            }
        }
        //aiSquares.add(snakeHeadX);
        //aiSquares.add(snakeHeadY);
        ///snakeBody.push(aiSquares);
        while(snakeBody.size() > snakeSize){
            snakeBody.remove();
        }




/*
        for (int x = 0 ; x < maxX ; x++) {
            for (int y = 0 ; y < maxY ; y++){
                if (snakeSquares[x][y] != 0) {
                    snakeSquares[x][y]++;
                }
            }
        }

        //now identify where to add new snake head

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


    public void setAISnakeParameters(int newX, int newY, int squareSize) {
        this.maxX = newX;
        this.maxY = newY;
        this.squareSize = squareSize;
    }

}
*/
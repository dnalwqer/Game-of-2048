
public class Driver {
	public static int UP = 1;
	public static int LEFT = 2;
	public static int DOWN = 3;
	public static int RIGHT = 4;
	
	
	public static void main(String []args) throws CloneNotSupportedException, InterruptedException {
		playGame();
//		playMultiGame(); //run for 20 times and get the accuracy
	}
	
    public static void playGame() throws CloneNotSupportedException, InterruptedException {
        
        Board game = new Board();
        MinimaxAI ai = new MinimaxAI();
        int next = ai.getMove(game);
        printBoard(game, next);

        Status result = new Status(Status.CONTINUE);
        while(result.status == Status.CONTINUE || result.status == Status.INVALID) {
        	result = game.doMove(next);
            printBoard(game, next);
            if(result.status == Status.CONTINUE || result.status == Status.INVALID) {
                next = ai.getMove(game);
            }
//            Thread.sleep(300);
        } 
        
        if (result.status == Status.WIN) {
        	System.out.println("WIN");
        }
    }
    
    public static void playMultiGame() throws CloneNotSupportedException, InterruptedException {
       
        int success = 0;
        int total = 20;
        for (int i = 0; i < total; i++) {
        	 Board game = new Board();
             MinimaxAI ai = new MinimaxAI();
             int next = ai.getMove(game);
             printBoard(game, next);
             Status result = new Status(Status.CONTINUE);
             while(result.status == Status.CONTINUE || result.status == Status.INVALID) {
             	 result = game.doMove(next);
                 printBoard(game, next);
                 if(result.status == Status.CONTINUE || result.status == Status.INVALID) {
                     next = ai.getMove(game);
                 }
             } 
             
             if (result.status == Status.WIN) {
            	success++;
             	System.out.println("WIN");
             }
        }
        System.out.println("Accuracy: " + (float)success / total);
    }
    public static void printBoard(Board board, int next) {
        System.out.println("******************************");
        System.out.println("Score:\t" + String.valueOf(board.getScore()));
        System.out.println();
        System.out.print("Current:\t");
        if (next == UP) {
        	System.out.println("UP");
        }
        else if (next == DOWN) {
        	System.out.println("DOWN");
        }
        else if (next == LEFT) {
        	System.out.println("LEFT");
        }
        else if (next == RIGHT) {
        	System.out.println("RIGHT");
        }
        System.out.println();
        
        int [][]b = board.board;
        for(int i = 0; i < b.length; i++) {
            for(int j = 0; j < b[0].length; j++) {
                System.out.print(b[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println("******************************");
    }
}

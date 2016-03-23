
import java.util.List;


public class MinimaxAI{
    public static int MAXDEPTH = 7;
	public static int UP = 1;
	public static int LEFT = 2;
	public static int DOWN = 3;
	public static int RIGHT = 4;
	public int []weight = {1, 2, 3, 4, 8, 7, 6, 5, 9, 10, 11, 12, 16, 15, 14, 13};
    
    class Entry {
    	int score;
    	int direction;
    	
    	public Entry(int score, int direction) {
    		this.score = score;
    		this.direction = direction;
    	}
    }

    public int getMove(Board board) throws CloneNotSupportedException {
    	return miniMaxIDS(board);
    }
    
    public int miniMaxIDS(Board board) throws CloneNotSupportedException {
    	int val = Integer.MIN_VALUE;
        int bestDirection = 0;

        for (int i = 1; i <= 4; i++) {
        	Board newboard = (Board) board.clone();
        	newboard.move(i);
        	if (newboard.isSame(board.board, newboard.board)) {
        		continue;
        	}
        	Entry curVal = minValue(newboard, 1);
        	if (curVal.score > val) {
        		val = curVal.score;
        		bestDirection = i;
        	}
        }
        return bestDirection;
    }
    
    //return the min value of the state
    public Entry minValue(Board board, int depth) throws CloneNotSupportedException{
    	int score;
    	int [][]b = board.board;
    	if (cutOffTest(board, depth)) {
    		score = utility(board);
    		Entry entry = new Entry(score, 0); 
    		return entry;
    	}
    	
    	int min = Integer.MAX_VALUE;
    	
    	List<Integer> list = board.getListOfPosition();
    	if (list.size() == 0) {
    		score = 0;
    		min = Integer.MIN_VALUE;
    	}
    	for (int item : list) {
    		int row = item / b[0].length;
    		int col = item % b[0].length;
    		board.board[row][col] = 2;
    		Entry cur = maxValue(board, depth+1);
    		min = Math.min(min, cur.score);
    		board.board[row][col] = 0;
    	}
    	Entry res = new Entry(min, 0);
    	return res;
    }
    
    //return the max value of the state
    public Entry maxValue(Board board, int depth) throws CloneNotSupportedException{
    	int score;
    	if (cutOffTest(board, depth)) {
    		score = utility(board);
    		Entry entry = new Entry(score, 0); 
    		return entry;
    	}
    	
    	int max = Integer.MIN_VALUE;
    	int direction = 0;
    	
    	for (int i = 1; i <= 4; i++) {
    		Board newboard = (Board)board.clone();
    		
    		newboard.move(i);
    		Entry entry = minValue(newboard, depth+1);
    		if (entry.score > max) {
    			max = entry.score;
    			direction = i;
    		}
    	}
    	
    	Entry res = new Entry(max, direction);
    	return res;
    }

    //determine whether the game is finished
    public boolean cutOffTest(Board board, int depth) throws CloneNotSupportedException {
        if(board.isTerminal() || depth == MAXDEPTH) return true;
        return false;
    }
    
    //return the result of the current state
    public int utility(Board board) {
    	int sum = getResult(board.getScore(), board.getListOfPosition().size(), score(board));
    	int [][]b = board.board;
    	for (int i = 0; i < b.length; i++) {
    		for (int j = 0; j < b[0].length; j++) {
    			sum += weight[i * b.length + j] * b[i][j];
    		}
    	}
    	return sum;
    }

    private static int getResult(int score, int numberEmpty, int score2) {
        int res = (int) (score + Math.log(score) * numberEmpty - score2);
        return Math.max(res, score);
    }
    
    
    public int score(Board board) {
    	int score = 0;
    	int [][]b = board.board;
    	for (int i = 0; i < b.length; i++) {
    		for (int j = 0; j < b[0].length; j++) {
    			int num = 0;
    			int sum = 0;
    			if (b[i][j] == 0) continue;
    			for (int m = i - 1; m <= i + 1; m++) {
    				if (m < 0 || m > b.length - 1) continue;
    				for (int n = j - 1; n <= j + 1; n++) {
    					if (n < 0 || n > b[0].length - 1) continue;
    					if (b[m][n] > 0) {
    						num++;
    						sum += Math.abs(b[m][n] - b[i][j]);
    					}
    				}
    			}
    			score += sum * 1.0 / num;
    		}
    	}
    	return score;
    }
}
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Board implements Cloneable {
	public static int size = 4;
	public static int UP = 1;
	public static int LEFT = 2;
	public static int DOWN = 3;
	public static int RIGHT = 4;
	
	public int score;
	
	public int [][]board;
	public Board() {
		board = new int[size][size];
		addNumber();
		addNumber();
	}
	
	
	public boolean addNumber() {
		List<Integer> list = getListOfPosition();
		int num = list.size();
		if (num == 0) {
			return false;
		}
		
		Random random = new Random(System.currentTimeMillis());
		int index = random.nextInt(num);
		int res = list.get(index);
		int row = res / board.length;
		int col = res % board.length;
		board[row][col] = 2;
		return true;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public boolean success() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j] >= 2048) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isTerminal() throws CloneNotSupportedException {
		if (success()) return true;
		else {
			List<Integer> list = this.getListOfPosition();
			if (list.size() == 0) {
				Board newboard = (Board) clone();
				
				newboard.move(UP);
				if (!isSame(board, newboard.board)) return false;
				newboard.move(DOWN);
				if (!isSame(board, newboard.board)) return false;
				newboard.move(LEFT);
				if (!isSame(board, newboard.board)) return false;
				newboard.move(RIGHT);
				if (!isSame(board, newboard.board)) return false;
				return true;
			}
		}
		return false;
	}
	
	
	public boolean isSame(int [][]board, int [][]newboard) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j] != newboard[i][j]) {
					return false;
				}
			}
		}
		return true;
	}
	
    public int[][] getBoard() {
    	int [][]newboard = new int[board.length][board[0].length];
    	for (int i = 0; i < newboard.length; i++) {
    		for (int j = 0; j < newboard[0].length; j++) {
    			newboard[i][j] = board[i][j];
    		}
    	}
    	return newboard;
    }

	
    public Status doMove(int direction) throws CloneNotSupportedException {
        int result = Status.CONTINUE;
        int[][] board = getBoard();
        int point = move(direction);
        int[][] newBoard = getBoard();
        
        boolean flag = false;
        if(!isSame(board, newBoard)) {
            addNumber();
            flag = true;
        }
        
        if(point == 0 && !flag) {
            if(isTerminal()) result = Status.LOSS;
            else result = Status.INVALID;
        }
        else {
            if(point >= 2048) result = Status.WIN;
            else if(isTerminal()) result = Status.INVALID;
        }
        
        return new Status(result);
    }
	
    public Object clone() throws CloneNotSupportedException {
        Board newboard = (Board)super.clone();
        newboard.board = cloneBoard(board);
        return newboard;
    }
    
    private int[][] cloneBoard(int[][] board) { 
        int[][] newboard = new int[board.length][];
        for(int i = 0; i < board.length;++i) {
            newboard[i] = board[i].clone();
        }
        return newboard;
    }
    
	public int move(int direction) {
		int res = 0;
		if (direction == LEFT) {
			for (int i = 0; i < board.length; i++) {
				for (int j = 1; j < board[0].length; j++) {
					int prev = j - 1;
					while (prev > 0 && board[i][prev] == 0) {
						prev--;
					}
					if (board[i][prev] == 0) {
						board[i][prev] = board[i][j];
						board[i][j] = 0;
					}
					else if (board[i][prev] == board[i][j]) {
						board[i][prev] = 2 * board[i][j];
						res += 2 * board[i][j];
						board[i][j] = 0;
					}
					else if (board[i][prev] != board[i][j]) {
						board[i][prev+1] = board[i][j];
						if (prev+1 != j) {
							board[i][j] = 0;
						}
					}
				}
			}
		}
		else if (direction == RIGHT) {
			for (int i = 0; i < board.length; i++) {
				for (int j = board[0].length - 2; j >= 0; j--) {
					int next = j + 1;
					while (next < board[0].length - 1 && board[i][next] == 0) {
						next++;
					}
					if (board[i][next] == 0) {
						board[i][next] = board[i][j];
						board[i][j] = 0;
					}
					else if (board[i][next] == board[i][j]) {
						board[i][next] = 2 * board[i][j];
						res += 2 * board[i][j];
						board[i][j] = 0;
					}
					else if (board[i][next] != board[i][j]) {
						board[i][next-1] = board[i][j];
						if (next-1 != j) {
							board[i][j] = 0;
						}
					}
				}
			}
		}
		else if (direction == UP) {
			for (int j = 0; j < board[0].length; j++) {
				for (int i = 1; i < board.length; i++) {
					int prev = i - 1;
					while (prev > 0 && board[prev][j] == 0) {
						prev--;
					}
					if (board[prev][j] == 0) {
						board[prev][j] = board[i][j];
						board[i][j] = 0;
					}
					else if (board[prev][j] == board[i][j]) {
						board[prev][j] = 2 * board[i][j];
						res += 2 * board[i][j];
						board[i][j] = 0;
					}
					else if (board[prev][j] != board[i][j]) {
						board[prev+1][j] = board[i][j];
						if (prev+1 != i) {
							board[i][j] = 0;
						}
					}
				}
			}
		}
		else if (direction == DOWN) {
			for (int j = 0; j < board[0].length; j++) {
				for (int i = board.length - 2; i >= 0; i--) {
					int next = i + 1;
					while (next < board.length - 1 && board[next][j] == 0) {
						next++;
					}
					if (board[next][j] == 0) {
						board[next][j] = board[i][j];
						board[i][j] = 0;
					}
					else if (board[next][j] == board[i][j]) {
						board[next][j] = 2 * board[i][j];
						res += 2 * board[i][j];
						board[i][j] = 0;
					}
					else if (board[next][j] != board[i][j]) {
						board[next-1][j] = board[i][j];
						if (next-1 != i) {
							board[i][j] = 0;
						}
					}
				}
			}
		}
		score += res;
		return res;
	}
	
	public List<Integer> getListOfPosition() {
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j] == 0) {
					int index = i * board.length + j;
					list.add(index);
				}
			}
		}
		return list;
	}
}

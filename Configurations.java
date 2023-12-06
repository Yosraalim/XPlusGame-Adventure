/*
 * CS2210 - Assignment 2
 * Yosra Alim
 * Oct, 18th 2023
 * 
 */



public class Configurations{
	private int board_size;
    private int lengthToWin;
    private int max_levels;
    private char[][] board;

    
	public Configurations(int board_size, int lengthToWin, int max_levels){
    	this.board_size = board_size;
        this.lengthToWin = lengthToWin;
        this.max_levels = max_levels;
        this.board = new char[board_size][board_size];
        
        for (int i = 0; i < this.board_size; i++) {
            for (int j = 0; j < this.board_size; j++) {
                board[i][j] = ' ';
            }
        }
    }
    
	
    public HashDictionary createDictionary() {

        int dictionarySize = 10000; 
        return new HashDictionary(dictionarySize);
    }
    
    
    public int repeatedConfiguration(HashDictionary hashTable) {

        String config = stringifyBoard();
        int score = hashTable.get(config);

        return score;
    }
    
    
    public void addConfiguration(HashDictionary hashTable, int score) {
		
        
        String config = stringifyBoard();
        int got_score = hashTable.get(config);
        
        if (got_score == -1) {
        	Data record = new Data(config, score);
        	hashTable.put(record);
        } else {
        	// we need to throw an exception saying the record exists 
        }
    }
    

    public void savePlay(int row, int col, char symbol) {
        if (isValidMove(row, col)) {
            board[row][col] = symbol;
        }
    }
    
    
    public boolean squareIsEmpty(int row, int col) {
        if (isValidMove(row, col)) {
            return board[row][col] == ' ';
        } else {
            return false;
        }
    }
    
    
    public boolean wins(char symbol) {
        for (int i = 0; i < board_size; i++) {
            for (int j = 0; j < board_size; j++) {
                if (board[i][j] == symbol) {
                    // check +-shape
                    if (isPlusOfSizeK(i, j, lengthToWin)) {
                        return true;
                    }

                    // check X-shape
                    if (isXOfSizeK(i, j, lengthToWin)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    
    public boolean isDraw() {
    	return isBoardFull() && !(wins('X') || wins('O'));
    }
    
    
    public int evalBoard() {


        if (wins('O')) {
            return 3; // computer wins
        } else if (wins('X')) {
            return 0; // human wins
        } else if (isDraw()) {
            return 2; // game is a draw
        } else {
            return 1; // game is still undecided
        }
    }

    
    
    
    private boolean isPlusOfSizeK(int row, int col, int k) {
    	if (hasPlusShape(row, col)) {
       		char symbol = board[row][col];

            // check the size of the + shape
            int size = 1; // counting the center square

            // check in each direction independently
            for (int step = 1; step <= k; step++) {
                if (checkSquare(row - step, col, symbol)) {
                    size++; 
                } else {
                    break;
                }
            }
            for (int step = 1; step <= k; step++) {
                if (checkSquare(row, col + step, symbol)) {
                    size++; 
                } else {
                    break;
                }
            }
            for (int step = 1; step <= k; step++) {
                if (checkSquare(row + step, col, symbol)) {
                    size++; 
                } else {
                    break;
                }
            }
            for (int step = 1; step <= k; step++) {
                if (checkSquare(row, col - step, symbol)) {
                    size++; 
                } else {
                    break;
                }
            }

            return size >= k;
        }

        return false; // the center is not part of an + shape or size less than k
    }
    
    
    private boolean isXOfSizeK(int row, int col, int k) {
    	if (hasXShape(row, col)) {
       		char symbol = board[row][col];

            // check the size of the X shape
            int size = 1; // Counting the center square

            // check in each direction independently
            for (int step = 1; step <= k; step++) {
                if (checkSquare(row - step, col - step, symbol)) {
                    size++; 
                } else {
                    break;
                }
            }
            for (int step = 1; step <= k; step++) {
                if (checkSquare(row - step, col + step, symbol)) {
                    size++; 
                } else {
                    break;
                }
            }
            for (int step = 1; step <= k; step++) {
                if (checkSquare(row + step, col - step, symbol)) {
                    size++; 
                } else {
                    break;
                }
            }
            for (int step = 1; step <= k; step++) {
                if (checkSquare(row + step, col + step, symbol)) {
                    size++; 
                } else {
                    break;
                }
            }

            return size >= k;
        }

        return false; // the center is not part of an X shape or size less than k
    }
    
    
    private boolean hasPlusShape(int row, int col) {
        char symbol = board[row][col];

        // check top, right, bottom, and left squares
        return checkSquare(row - 1, col, symbol) &&
               checkSquare(row, col + 1, symbol) &&
               checkSquare(row + 1, col, symbol) &&
               checkSquare(row, col - 1, symbol);
    }
    
    
    private boolean hasXShape(int row, int col) {
        char symbol = board[row][col];

        // check top-left, top-right, bottom-left, and bottom-right squares
        return checkSquare(row - 1, col - 1, symbol) &&
               checkSquare(row - 1, col + 1, symbol) &&
               checkSquare(row + 1, col - 1, symbol) &&
               checkSquare(row + 1, col + 1, symbol);
    }

    
    // helper method to check if a square has the specified symbol
    private boolean checkSquare(int row, int col, char symbol) {
        return isValidMove(row, col) && board[row][col] == symbol;
    }
    
    

    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < board_size && col >= 0 && col < board_size;
    }
    
    
    private boolean isBoardFull() {
        for (int i = 0; i < board_size; i++) {
            for (int j = 0; j < board_size; j++) {
                if (board[i][j] == ' ') {
                    return false; // found an empty square
                }
            }
        }
        return true; // no empty squares found
    }
    
    
    
    private String stringifyBoard() {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[i].length; j++) {
                result.append(board[i][j]);
            }
        }

        return result.toString();
    }
    

}


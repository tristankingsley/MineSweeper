package Project2;


import java.util.Random;

public class MineSweeperGame {
	private Cell[][] board;
	private GameStatus status;

	public MineSweeperGame() {
		status = GameStatus.NotOverYet;
		board = new Cell[10][10];
		setEmpty();
		layMines (7);

		//for(int row = )
		
	}

	private void setEmpty() {
		for (int r = 0; r < 10; r++)
			for (int c = 0; c < 10; c++)
				board[r][c] = new Cell(false, false);  // totally clear.
	}

	public Cell getCell(int row, int col) {
		return board[row][col];
	}

	public void select(int row, int col) {
		board[row][col].setExposed(true);
        proximity(row,col);

		if (board[row][col].isMine())   // did I lose
			status = GameStatus.Lost;
		else if(!allFound()){
		    status = GameStatus.NotOverYet;
            }
		else {
			status = GameStatus.WON;    // did I win
		}
	}

	public boolean allFound(){
	    for (int r = 0; r < 10; r++)
	        for (int c = 0; c < 10; c++)
	            if(!board[r][c].isExposed() && !board[r][c].isMine())
	                return false;
        return true;
    }

    public void proximity(int row, int col){
        int boundright = 1;
        int boundleft = 1;
        int boundup = 1;
        int bounddown = 1;

        int counter = 0;

        if(row == 0){
            boundup = 0;
            bounddown = 1;
        }
        else if (row == 9){
            boundup = 1;
            bounddown = 0;
        }
        else{
            boundup = 1;
            bounddown = 1;
        }

        if(col == 0){
            boundleft = 0;
            boundright = 1;
        }
        else if (col == 9){
            boundleft = 1;
            boundright = 0;
        }
        else{
            boundleft = 1;
            boundright = 1;
        }

	    for (int r = row - boundup; r <= row + bounddown; r++)
            for (int c = col - boundleft; c <= col + boundright; c++)
                if(board[r][c].isMine()) {
                    counter++;
                }

        if (!board[row][col].isMine() && counter != 0) {
            board[row][col].setProx("" + counter);
        }
        else {
            board[row][col].setProx("");

            for (int r = row - boundup; r <= row + bounddown; r++)
                for (int c = col - boundleft; c <= col + boundright; c++) {

                    board[r][c].setExposed(true);

                }
        }
    }

	public GameStatus getGameStatus() {
		return status;
	}

	public void reset() {
		status = GameStatus.NotOverYet;
		setEmpty();
		layMines (10);
	}

	private void layMines(int mineCount) {
		int i = 0;		// ensure all mines are set in place

		Random random = new Random();
		while (i < mineCount) {			// perhaps the loop will never end :)
			int c = random.nextInt(10);
			int r = random.nextInt(10);

			if (!board[r][c].isMine()) {
				board[r][c].setMine(true);
				i++;
			}
		}
	}
}




	//  a non-recursive from .... it would be much easier to use recursion



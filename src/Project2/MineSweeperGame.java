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
		setBounds();

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
		Cell iCell = board[row][col];
		if(!iCell.isFlagged() && !iCell.isExposed()) {
			iCell.setExposed(true);

				if(calcCounter(row, col) == 0) {
					for (int r = row - iCell.boundup; r <= row + iCell.bounddown; r++)
						for (int c = col - iCell.boundleft; c <= col + iCell.boundright; c++) {
							if (!board[r][c].isMine())
								select(r,c);
						}
				}

			if (board[row][col].isMine())   // did I lose
				status = GameStatus.Lost;
			else if (!allFound()) {
				status = GameStatus.NotOverYet;
			} else {
				status = GameStatus.WON;    // did I win
			}
		}
	}

	public boolean allFound(){
		for (int r = 0; r < 10; r++)
			for (int c = 0; c < 10; c++)
				if(!board[r][c].isExposed() && !board[r][c].isMine())
					return false;
		return true;
	}

	public void setBounds(){
		for (int r = 0; r < 10; r++)
			for(int c = 0; c < 10; c++){
				if(r == 0){
					board[r][c].boundup = 0;
				}
				else if (r == 9) {
					board[r][c].bounddown = 0;
				}
				if(c == 0){
					board[r][c].boundleft = 0;
				}
				else if (c == 9){
					board[r][c].boundright = 0;
				}

				if(calcCounter(r,c) == 0 || board[r][c].isMine())
					board[r][c].setProx("");
				else
					board[r][c].setProx("" + calcCounter(r, c));

			}
	}

	public int calcCounter(int row, int col){
		int count = 0;

		Cell iCell = board[row][col];

		for (int r = row - iCell.boundup; r <= row + iCell.bounddown; r++)
			for (int c = col - iCell.boundleft; c <= col + iCell.boundright; c++)
				if(board[r][c].isMine())
					count++;

		return count;
	}

	public GameStatus getGameStatus() {
		return status;
	}

	public void reset() {
		status = GameStatus.NotOverYet;
		setEmpty();
		layMines (10);
		setBounds();
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
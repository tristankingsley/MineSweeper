package Project2;


import javax.swing.*;
import java.util.Random;

public class MineSweeperGame {
	private Cell[][] board;
	private GameStatus status;
	private String mineCount2;
	private int mineCount;
	private String theRow;
	private int daRow;
	private String theCol;
	private int daCol;

	public int getMineCount() {
		return mineCount;
	}

	public int getDaRow() {
		return daRow;
	}

	public int getDaCol() {
		return daCol;
	}

	public MineSweeperGame() {
		resize();
		status = GameStatus.NotOverYet;
		board = new Cell[daRow][daCol];
		setEmpty();
		layMines (mineCount);
		setBounds();

	}

	private void setEmpty() {
		for (int r = 0; r < daRow; r++)
			for (int c = 0; c < daCol; c++)
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
		for (int r = 0; r < daRow; r++)
			for (int c = 0; c < daCol; c++)
				if(!board[r][c].isExposed() && !board[r][c].isMine())
					return false;
		return true;
	}

	public void setBounds(){
		for (int r = 0; r < daRow; r++)
			for(int c = 0; c < daCol; c++){
				if(r == 0){
					board[r][c].boundup = 0;
				}
				else if (r == (daRow - 1)) {
					board[r][c].bounddown = 0;
				}
				if(c == 0){
					board[r][c].boundleft = 0;
				}
				else if (c == (daCol - 1)){
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
		resize();
		status = GameStatus.NotOverYet;
		setEmpty();
		layMines (mineCount);
		setBounds();
	}

	private void layMines(int mineCount) {
		int i = 0;		// ensure all mines are set in place

		Random random = new Random();
		while (i < mineCount) {			// perhaps the loop will never end :)
			int c = random.nextInt(daCol);
			int r = random.nextInt(daRow);

			if (!board[r][c].isMine()) {
				board[r][c].setMine(true);
				i++;
			}
		}
	}

	private void resize() {
		theRow = JOptionPane.showInputDialog(null, "How many rows would you like?");
		theCol = JOptionPane.showInputDialog(null, "How many columns would you like?");
		mineCount2 = JOptionPane.showInputDialog(null, "How many mines would you like?");

		if (theRow.length() > 0) {
			daRow = Integer.parseInt(theRow);
		} else if (theRow.equals("")){
			daRow = 10;
		} else {
			daRow = 10;
		}

		if (theCol.length() > 0){
			daCol = Integer.parseInt(theCol);
		} else if ( theCol.equals("")){
			daCol = 10;
		} else {
			daCol = 10;
		}

		if (mineCount2.length() > 0){
			mineCount = Integer.parseInt(mineCount2);
		} else if (mineCount2.equals("")){
			mineCount = 10;
		} else {
			mineCount = 10;
		}
	}
}
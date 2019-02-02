package Project2;


import javax.swing.*;
import java.util.Random;

public class MineSweeperGame {
	private Cell[][] board;
	private GameStatus status;
	private String mineCount2;
	private int mineCount;
	private String daRows;
	private int theRow;
	private String daCols;
	private int theCol;

	//Getter's for private variables
	public String getMineCount2() {
		return mineCount2;
	}

	public int getMineCount() {
		return mineCount;
	}

	public String getDaRows() {
		return daRows;
	}

	public int getTheRow() {
		return theRow;
	}

	public String getDaCols() {
		return daCols;
	}

	public int getTheCol() {
		return theCol;
	}

	public MineSweeperGame() {
		resize();
		status = GameStatus.NotOverYet;
		board = new Cell[theRow][theCol];
		setEmpty();
		layMines (mineCount);
		setBounds();

	}

	private void setEmpty() {
		for (int r = 0; r < theRow; r++)
			for (int c = 0; c < theCol; c++)
				board[r][c] = new Cell(false, false);  // totally clear.
	}

	public Cell getCell(int row, int col) {
		return board[row][col];
	}

	public void select(int row, int col) {
		if(!board[row][col].isFlagged()) {
			board[row][col].setExposed(true);
			proximity(row, col);

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
		for (int r = 0; r < theRow; r++)
			for (int c = 0; c < theCol; c++)
				if(!board[r][c].isExposed() && !board[r][c].isMine())
					return false;
		return true;
	}

	public void setBounds(){
		for (int r = 0; r < theRow; r++)
			for(int c = 0; c < theCol; c++){
				if(r == 0){
					board[r][c].boundup = 0;
				}
				else if (r == (theRow - 1)) {
					board[r][c].bounddown = 0;
				}
				if(c == 0){
					board[r][c].boundleft = 0;
				}
				else if (c == (theCol - 1)){
					board[r][c].boundright = 0;
				}
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

	public void proximity(int row, int col){
		Cell iCell = board[row][col];



		if (!iCell.isMine() && calcCounter(row, col) != 0) {
			iCell.setProx("" + calcCounter(row, col));
		}
		else {
			board[row][col].setProx("");

			for (int r = row - iCell.boundup; r <= row + iCell.bounddown; r++)
				for (int c = col - iCell.boundleft; c <= col + iCell.boundright; c++) {
					if(calcCounter(r,c) == 0)
						board[r][c].setProx("");
					else
						board[r][c].setProx("" + calcCounter(r, c));

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
		setBounds();
	}

	private void layMines(int mineCount) {
		int i = 0;		// ensure all mines are set in place

		Random random = new Random();
		while (i < mineCount) {			// perhaps the loop will never end :)
			int c = random.nextInt(theCol);
			int r = random.nextInt(theRow);

			if (!board[r][c].isMine()) {
				board[r][c].setMine(true);
				i++;
			}
		}
	}

	public void resize(){
		daRows = JOptionPane.showInputDialog(null, "How many rows would you like?");
		daCols = JOptionPane.showInputDialog(null, "How many columns would you like?");
		mineCount2 = JOptionPane.showInputDialog(null, "How many mines would you like?");

		//If text is null, theRow is set to 10
		if (daRows.length() > 0) {
			theRow = Integer.parseInt(daRows);
		} else {
			theRow = 10;
		}

		//If text is null, theCols is set to 10
		if (daCols.length() > 0){
			theCol = Integer.parseInt(daCols);
		} else {
			theCol = 10;
		}

		//If mineCount2 is empty, it is set to 7
		if (mineCount2.length() > 0 ){
			mineCount = Integer.parseInt(mineCount2);
		} else {
			mineCount = 7;
		}
	}
}




//  a non-recursive from .... it would be much easier to use recursion
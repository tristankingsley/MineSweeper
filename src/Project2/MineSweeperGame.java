package Project2;


import javax.swing.*;
import java.util.Random;


/**********************************************************************
 This class creates the minesweeper game object contains the logic to
 follow the rules of the MineSweeper game

 @author Tristan Kingsley and Trevor Spitzley
 @Version February 2019
 *********************************************************************/

public class MineSweeperGame {

	/** A 2-Dimensional array representing a minesweeper board**/
	private Cell[][] board;

	/** An instance of the GameStatus class **/
	private GameStatus status;

	/** The strings that the user enters to specify game **/
	private String inputMineCount, inputRow, inputCol;

	/** Integer values of user inputs above **/
	private int mineCount, boardRow, boardCol;

	/** Integers values of amounts of wins and losses **/
	private int numWins = 0;
	private int numLosses = 0;

	/******************************************************************
	 @return integer value of amount of mines
	 *****************************************************************/
	public int getMineCount() {
		return mineCount;
	}

	/******************************************************************
	 @return integer value of length board
	 *****************************************************************/
	public int getBoardRow() {
		return boardRow;
	}

	/******************************************************************
	 @return integer value of width of board
	 *****************************************************************/
	public int getBoardCol() {
		return boardCol;
	}

	/******************************************************************
	 @return integer value of how many times user has won
	 *****************************************************************/
	public int getNumWins() {
		return numWins;
	}

	/******************************************************************
	 @return integer value of how many times user has lost
	 *****************************************************************/
	public int getNumLosses() {
		return numLosses;
	}

	/******************************************************************
	 @return current GameStatus value
	 *****************************************************************/
	public GameStatus getGameStatus() {
		return status;
	}

	/******************************************************************
	 @param row row of specified cell
	 @param col column of specified cell
	 @return a specific cell from the board 2-D array
	 *****************************************************************/
	public Cell getCell(int row, int col) {
		return board[row][col];
	}

	/******************************************************************
	 Constructor creates a MineSweeperGame object by getting values
	 from the resize method, sets the game status to not over,
	 instantiates a new 2-D array of cells to represent the board,
	 and calls the setEmpty and setBounds methods
	 *****************************************************************/

	public MineSweeperGame() {
		resize();
		status = GameStatus.NotOverYet;
		board = new Cell[boardRow][boardCol];
		setEmpty();
		setBounds();

	}

	/******************************************************************
	 Private helper method that sets all cells in the board to
	 unexposed and with no mines.
	 *****************************************************************/

	private void setEmpty() {

		//loop that goes through the whole board
		for (int r = 0; r < boardRow; r++)
			for (int c = 0; c < boardCol; c++)
				board[r][c] = new Cell(false, false);
	}

	/******************************************************************
	 Private helper method that exposes the surrounding 8 cells around
	 a specified cell. Used in nonrecursive select
	 @param row row of specified cell
	 @param col column of specified cell
	 *****************************************************************/
	private void select8(int row, int col){
		Cell iCell = board[row][col];

		//loop that goes through cells surrounding a specified one
		for (int r = row - iCell.boundup;
			 r <= row + iCell.bounddown; r++)
					for (int c = col - iCell.boundleft;
						 c <= col + iCell.boundright; c++)

						//can't expose flagged cells
						if(!board[r][c].isFlagged())
							board[r][c].setExposed(true);
	}

	/******************************************************************
	 Private helper that checks a cell to see if it has a neighbor that
	 has been exposed with a 0 count
	 @param row row of specified cell
	 @param col column of specified cell
	 @return boolean to tell if the cell has an exposed 0count neighbor
	 *****************************************************************/
	private boolean exposedNeighbor(int row, int col){
		Cell iCell = board[row][col];

		//amount of cells neighboring that are exposed and 0 count
		int check = 0;

		for (int r = row - iCell.boundup; r <= row + iCell.bounddown; r++)
			for (int c = col - iCell.boundleft; c <= col + iCell.boundright; c++)
				if(board[r][c].isExposed() && calcCounter(r, c) == 0)
					check++;

		//returns true if any cell fits the criteria
		if(check == 0)
			return false;
		else
			return true;

	}

	/******************************************************************
	 Private helper method that checks the whole board to see if all
	 but one cell has been selected.
	 Used to trigger lay mines after first select.
	 *****************************************************************/
	private boolean allHidden(){
	    int counter = 0;

        for (int r = 0; r < boardRow; r++)
            for (int c = 0; c < boardCol; c++){

                if (!board[r][c].isExposed())
                    counter++;
                }

        //Checks to see if it's the first selected cell
        if(counter == ((boardRow * boardCol) - 1))
	        return true;
	    else
	        return false;
    }

	/******************************************************************
	 Public method that exposes a cell. If the cell's proximity count
	 is 0, the method will continue to run until no neighboring cells
	 have 0 counts and the cells surrounding them are exposed as well.
	 *****************************************************************/
	public void select(int row, int col) {
		Cell iCell = board[row][col];

		if (!iCell.isFlagged() && !iCell.isExposed() && !iCell.isMine()) {
			iCell.setExposed(true);

            if(allHidden())
                layMines(mineCount);


			/**Recursive**/
//			if (calcCounter(row, col) == 0) {
//				for (int r = row - iCell.boundup; r <= row + iCell.bounddown; r++)
//					for (int c = col - iCell.boundleft; c <= col + iCell.boundright; c++) {
//						if (!board[r][c].isMine())
//							select(r,c);
//					}
//			}
			if(calcCounter(row, col) == 0) {
				/**NonRecursive**/

				for (int n = 0; n < boardRow; n++) {
					for (int i = 0; i < boardRow; i++)
						for (int g = 0; g < boardCol; g++)
							if (exposedNeighbor(i, g) && calcCounter(i, g) == 0 && !board[i][g].isFlagged()) {
								select8(i, g);

							}
				}
			}
		}

		//Checks to see if game has been won, lost or still being played

		if (board[row][col].isMine()) {
			status = GameStatus.Lost;
			numLosses++;
		} else if (!allFound())
			status = GameStatus.NotOverYet;
		else {
			status = GameStatus.WON;
			numWins++;
		}
	}

	public boolean allFound(){
		for (int r = 0; r < boardRow; r++)
			for (int c = 0; c < boardCol; c++)
				if(!board[r][c].isExposed() && !board[r][c].isMine())
					return false;
		return true;
	}

	/******************************************************************
	 Private helper method that sets boundaries for each cell so
	 the indexes being searched in other methods don't go past the
	 bounds of the array
	 *****************************************************************/
	private void setBounds(){
		for (int r = 0; r < boardRow; r++)
			for(int c = 0; c < boardCol; c++){
				if(r == 0){
					board[r][c].boundup = 0;
				}
				else if (r == (boardRow - 1)) {
					board[r][c].bounddown = 0;
				}
				if(c == 0){
					board[r][c].boundleft = 0;
				}
				else if (c == (boardCol - 1)){
					board[r][c].boundright = 0;
				}

			}
	}

	/******************************************************************
	  Public method that counts the mines around a specific cell
	 @param row row of specified cell
	 @param col column of specified cell
	 @return int value of mines around specified cell
	 *****************************************************************/
	public int calcCounter(int row, int col){
		int count = 0;

		Cell iCell = board[row][col];

		for (int r = row - iCell.boundup; r <= row + iCell.bounddown; r++)
			for (int c = col - iCell.boundleft; c <= col + iCell.boundright; c++)
				if(board[r][c].isMine())
					count++;

		return count;
	}

	/******************************************************************
	 Public method that resets the game.
	 *****************************************************************/
	public void reset() {
		status = GameStatus.NotOverYet;
		board = new Cell[boardRow][boardCol];
		setEmpty();
		setBounds();
	}

	/******************************************************************
	 Private helper method that sets random cells as mines on the board
	 @param mineCount int value of mines to be set
	 *****************************************************************/
	private void layMines(int mineCount) {

		//for out laying loop
		int i = 0;

		Random random = new Random();

		while (i < mineCount) {
			int c = random.nextInt(boardCol);
			int r = random.nextInt(boardRow);

			//only increments i if it sets a mine
			if (!board[r][c].isExposed() && !board[r][c].isMine()) {
				board[r][c].setMine(true);
				i++;
			}
		}
	}

	/******************************************************************
	 Public method that asks the user for the size of the board and
	 how many mines. Also checks all inputs and defaults to 10 if
	 inputs are unacceptable.
	 *****************************************************************/
	public void resize() {

		inputRow = JOptionPane.showInputDialog(null, "How many rows would you like?");

		//exits on cancel
		if(inputRow == null){
			System.exit(0);
		}

		inputCol = JOptionPane.showInputDialog(null, "How many columns would you like?");

		//exits on cancel
		if(inputCol == null)
			System.exit(0);

		inputMineCount = JOptionPane.showInputDialog(null, "How many mines would you like?");

		//exits on cancel
		if(inputMineCount == null)
			System.exit(0);

		//checks to see if input is within boundaries
		try {
			if (inputRow.length() > 0) {
				boardRow = Integer.parseInt(inputRow);
				if(boardRow > 30 || boardRow < 3)
					throw new IllegalArgumentException();
			}
			else
				throw new IllegalArgumentException();
		}
		catch (IllegalArgumentException e) {

			//defaults to 10
			boardRow = 10;
		}

		//checks to see if input is within boundaries
		try {
			if (inputCol.length() > 0) {
				boardCol = Integer.parseInt(inputCol);
				if (boardCol > 30 || boardCol < 3)
					throw new IllegalArgumentException();
			}
			else
				throw new IllegalArgumentException();
		}
		catch(IllegalArgumentException e){

			//defaults to 10
			boardCol = 10;
		}

		//checks if minecount is within the game's capabilities
		try {
			if (inputMineCount.length() > 0) {
				mineCount = Integer.parseInt(inputMineCount);
				if (mineCount > (boardRow * boardCol) - 1 || mineCount < 1)
					throw new IllegalArgumentException();
			}else
				throw new IllegalArgumentException();
		}
		catch(IllegalArgumentException e){

			//defaults to 10
			mineCount = 10;
		}
	}
}
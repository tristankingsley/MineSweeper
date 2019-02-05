package Project2;


import javax.swing.*;
import java.util.Random;

public class MineSweeperGame {
	private Cell[][] board;
	private GameStatus status;
	private String inputMineCount;
	private int mineCount;
	private String inputRow;
	private int boardRow;
	private String inputCol;
	private int boardCol;

	public int getMineCount() {
		return mineCount;
	}

	public int getBoardRow() {
		return boardRow;
	}

	public int getBoardCol() {
		return boardCol;
	}

	public MineSweeperGame() {
		resize();
		status = GameStatus.NotOverYet;
		board = new Cell[boardRow][boardCol];
		setEmpty();
		layMines (mineCount);
		setBounds();

	}

	private void setEmpty() {
		for (int r = 0; r < boardRow; r++)
			for (int c = 0; c < boardCol; c++)
				board[r][c] = new Cell(false, false);  // totally clear.
	}

	public Cell getCell(int row, int col) {
		return board[row][col];
	}

	public void select(int row, int col) {
		Cell iCell = board[row][col];
		if(!iCell.isFlagged() && !iCell.isExposed()) {
			iCell.setExposed(true);

			if (calcCounter(row, col) == 0) {
					for (int r = row - iCell.boundup; r <= row + iCell.bounddown; r++)
						for (int c = col - iCell.boundleft; c <= col + iCell.boundright; c++) {
							if (!board[r][c].isMine())
								select(r,c);
						}
				}
		}

		if (board[row][col].isMine())   // did I lose
			status = GameStatus.Lost;
		else if (!allFound())
			status = GameStatus.NotOverYet;
		else
			status = GameStatus.WON;    // did I win
	}

	public boolean allFound(){
		for (int r = 0; r < boardRow; r++)
			for (int c = 0; c < boardCol; c++)
				if(!board[r][c].isExposed() && !board[r][c].isMine())
					return false;
		return true;
	}

	public void setBounds(){
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
		board = new Cell[boardRow][boardCol];
		setEmpty();
		layMines (mineCount);
		setBounds();
	}

	private void layMines(int mineCount) {
		int i = 0;		// ensure all mines are set in place

		Random random = new Random();
		while (i < mineCount) {			// perhaps the loop will never end :)
			int c = random.nextInt(boardCol);
			int r = random.nextInt(boardRow);

			if (!board[r][c].isMine()) {
				board[r][c].setMine(true);
				i++;
			}
		}
	}

	public void resize() {
		inputRow = JOptionPane.showInputDialog(null, "How many rows would you like?");

		if(inputRow == null){
			System.exit(0);
		}

		inputCol = JOptionPane.showInputDialog(null, "How many columns would you like?");

		if(inputCol == null)
			System.exit(0);

		inputMineCount = JOptionPane.showInputDialog(null, "How many mines would you like?");

		if(inputMineCount == null)
			System.exit(0);


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
			boardRow = 10;
		}

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
				boardCol = 10;
			}


			try {
				if (inputMineCount.length() > 0) {
					mineCount = Integer.parseInt(inputMineCount);
					if (mineCount > (boardRow * boardCol) - 1 || mineCount < 1)
						throw new IllegalArgumentException();
				}else
					throw new IllegalArgumentException();
			}
			catch(IllegalArgumentException e){
				mineCount = 10;
			}
		}

	}

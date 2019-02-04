package Project2;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MineSweeperPanel extends JPanel {

	private JMenuBar menus;
	private JMenu file;
	private JMenuItem newGameItem;
	private JMenuItem resizeItem;
	private JMenuItem quitItem;


	private JButton[][] board;
	private JButton butQuit;
	private Cell iCell;

	private MineSweeperGame game;  // model

	public MineSweeperPanel() {


		JPanel bottom = new JPanel();
		JPanel center = new JPanel();

		// create game, listeners
		ButtonListener listener = new ButtonListener();

		MyMouseListener mouse = new MyMouseListener();

		game = new MineSweeperGame();

		// create the board
		center.setLayout(new GridLayout(game.getBoardRow(), game.getBoardCol()));
		board = new JButton[game.getBoardRow()][game.getBoardCol()];

		for (int row = 0; row < game.getBoardRow(); row++)
			for (int col = 0; col < game.getBoardCol(); col++) {
				board[row][col] = new JButton("");
				board[row][col].addActionListener(listener);
				board[row][col].addMouseListener(mouse);
				center.add(board[row][col]);
			}

		displayBoard();

		bottom.setLayout(new GridLayout(3, 2));

		// add all to contentPane
		add(new JLabel("!!!!!!  Mine Sweeper  !!!!"), BorderLayout.NORTH);
		add(center, BorderLayout.CENTER);
		add(bottom, BorderLayout.SOUTH);

	}

	public JMenuBar addMenuBar(){
		menus = new JMenuBar();

		file = new JMenu("File");
		menus.add(file);

		newGameItem = new JMenuItem("New Game");
		file.add(newGameItem);
		newGameItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.reset();
				displayBoard();
			}
		});

		resizeItem = new JMenuItem("Board Size");
		file.add(resizeItem);
		resizeItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.reset();
				resizeBoard();
			}
		});

		quitItem = new JMenuItem("Quit");
		file.add(quitItem);
		quitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		return menus;
	}


	private void displayBoard() {

		for (int r = 0; r < game.getBoardRow(); r++)
			for (int c = 0; c < game.getBoardCol(); c++) {
				iCell = game.getCell(r, c);

				if (iCell.isMine() && !iCell.isFlagged())
					board[r][c].setText("!");
				if (!iCell.isMine() && !iCell.isFlagged())
					board[r][c].setText("");
				if (iCell.isExposed()) {
					board[r][c].setEnabled(false);
					board[r][c].setText(iCell.getProx());
				}
				else
					board[r][c].setEnabled(true);
			}
	}

	private void resizeBoard() {
		for (int row = 0; row < board.length; row++)
			for (int col = 0; col < board[row].length; col++) {
				if (row > game.getBoardRow() || col > game.getBoardCol()) {
					this.remove(board[row][col]);
					board[row][col].remove(this);
				}

			}
	}

	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			for (int r = 0; r < game.getBoardRow(); r++)
				for (int c = 0; c < game.getBoardCol(); c++)
					if (board[r][c] == e.getSource())
						game.select(r, c);

			displayBoard();
								
			if (game.getGameStatus() == GameStatus.Lost) {
				displayBoard();
				JOptionPane.showMessageDialog(null, "You Lose \n The game will reset");
				game.reset();
				displayBoard();

			}

			if (game.getGameStatus() == GameStatus.WON) {
				JOptionPane.showMessageDialog(null, "You Win: all mines have been found!\n The game will reset");
				game.reset();
				displayBoard();
			}

		}

	}

	private class MyMouseListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			for (int r = 0; r < game.getBoardRow(); r++) {
				for (int c = 0; c < game.getBoardCol(); c++) {
					if (e.getSource() == board[r][c] && e.getButton() == MouseEvent.BUTTON3) {
						if (!game.getCell(r, c).isFlagged()) {
							board[r][c].setText("F");
							game.getCell(r, c).setFlagged(true);
						} else if (game.getCell(r, c).isFlagged() && game.getCell(r,c).isMine()) {
							board[r][c].setText("!");
							game.getCell(r, c).setFlagged(false);
						} else if (game.getCell(r,c).isFlagged() && !game.getCell(r,c).isMine()){
							board[r][c].setText("");
							game.getCell(r, c).setFlagged(false);
						}
					}
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {

		}
	}

}




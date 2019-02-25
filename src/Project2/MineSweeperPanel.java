package Project2;


import javax.swing.*;
import javax.swing.border.Border;
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
	private JButton quitButton;
	private JLabel wins;
	private JLabel losses;
	private JLabel title;


	private JButton[][] board;
	private JButton butQuit;
	private Cell iCell;

	private MineSweeperGame game;  // model

	public MineSweeperPanel() {

		JPanel bottom = new JPanel();
		JPanel center = new JPanel();
		JPanel top = new JPanel();



		// create game, listeners
		ButtonListener listener = new ButtonListener();

		MyMouseListener mouse = new MyMouseListener();

		game = new MineSweeperGame();

		// create the board
		center.setLayout(new GridLayout(game.getBoardRow(), game.getBoardCol()));
		board = new JButton[game.getBoardRow()][game.getBoardCol()];

		int overall = game.getBoardRow() * game.getBoardCol();

		for (int row = 0; row < game.getBoardRow(); row++)
			for (int col = 0; col < game.getBoardCol(); col++) {
				board[row][col] = new JButton("");
				board[row][col].setMargin(new Insets(0,0,0,0));
				board[row][col].setFont(new Font("Ariel", Font.PLAIN, 15));
				board[row][col].addActionListener(listener);
				board[row][col].addMouseListener(mouse);
				center.add(board[row][col]);
			}

		wins = new JLabel("WINS: ");
		losses = new JLabel("LOSSES: ");
		title = new JLabel("!!!!!!  Mine Sweeper  !!!!");

		wins.setFont(new Font("Ariel",Font.PLAIN,20));
		losses.setFont(new Font("Ariel", Font.PLAIN, 20));
		title.setFont(new Font("Ariel", Font.PLAIN, 20));

		quitButton = new JButton("Quit");
		quitButton.addActionListener(listener);

		displayBoard();

		bottom.setLayout(new GridBagLayout());
		GridBagConstraints position = new GridBagConstraints();
		position.gridx = 0;
		position.gridy = 0;
		position.insets = new Insets(0,15,0,15);

		bottom.add(wins, position);

		position.gridx = 1;
		bottom.add(losses, position);

		position.gridx = 2;
		bottom.add(quitButton, position);

		// add all to contentPane
		top.add(title);
		add(top, BorderLayout.PAGE_START);
		top.setPreferredSize(new Dimension(800,30));
		add(center, BorderLayout.CENTER);
		center.setPreferredSize(new Dimension(800,500));
		add(bottom, BorderLayout.PAGE_END);
		bottom.setPreferredSize(new Dimension(800,30));


	}

//	public JMenuBar addMenuBar(){
//		menus = new JMenuBar();
//
//		file = new JMenu("File");
//		menus.add(file);
//
//		newGameItem = new JMenuItem("New Game");
//		file.add(newGameItem);
//		newGameItem.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				game.reset();
//				displayBoard();
//			}
//		});
//
//		resizeItem = new JMenuItem("Board Size");
//		file.add(resizeItem);
//		resizeItem.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				game.resize();
//				game.reset();
//				displayBoard();
//			}
//		});
//
//		quitItem = new JMenuItem("Quit");
//		file.add(quitItem);
//		quitItem.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				System.exit(0);
//			}
//		});
//
//		return menus;
//	}


	private void displayBoard() {

		wins.setText("WINS: " + game.getNumWins());
		losses.setText("LOSSES: " + game.getNumLosses());

//		for(int r = 0; r < game.getBoardRow(); r++) {
//			for (int c = 0; c < game.getBoardCol(); c++)
//				board[r][c].setVisible(false);
//		}

		for (int r = 0; r < game.getBoardRow(); r++)
			for (int c = 0; c < game.getBoardCol(); c++) {
				iCell = game.getCell(r, c);

				board[r][c].setVisible(true);

				if (iCell.isMine())
					board[r][c].setText("!");
				if (!iCell.isMine() && !iCell.isFlagged())
					board[r][c].setText("");
				if (iCell.isExposed() && !iCell.isMine()) {
					board[r][c].setEnabled(false);
					if(game.calcCounter(r,c) != 0)
						board[r][c].setText("" + game.calcCounter(r,c));
					else
						board[r][c].setText("");
				} else
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




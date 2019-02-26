package Project2;


import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
/**********************************************************************
 This class extends the JPanel, and also creates cells, JButtons,
 JLabels, JMenuItems, a JMenu, and a JMenuBar. Then adds those to
 a JPanel to create a usable interface to work with the
 MineSweeperGame class to make a fully-functional GUI.

 @author Tristan Kingsley and Trevor Spitzley
 @Version February 2019
 *********************************************************************/

public class MineSweeperPanel extends JPanel {

	/** A JMenuBar to contain a JMenu */
	private JMenuBar menus;

	/** A JMenu to contain JMenuItems */
	private JMenu file;

	/** A JMenuItem to for new game, board size, and quit game */
	private JMenuItem newGameItem, resizeItem, quitItem;

	/** A JButton to quit the game from main GUI panel */
	private JButton quitButton;

	/** A JLabel to show number of overall wins/losses, and title label */
	private JLabel wins, losses, title;

	/** A JPanel divided into three respective sections */
	private JPanel center, bottom, top;

	/** An Array of JButtons to be used as the game board */
	private JButton[][] board;

	/** A Cell object from the cell class to represent each button */
	private Cell iCell;

	/** A game object from the MineSweeperGame class for game logic */
	private MineSweeperGame game;  // model

	/** A constructor for our MineSweeperPanel for our GUI */
	public MineSweeperPanel() {

		//Creates three JPanel objects
		bottom = new JPanel();
		center = new JPanel();
		top = new JPanel();

		// create game, listeners
		ButtonListener listener = new ButtonListener();
		MyMouseListener mouse = new MyMouseListener();
		game = new MineSweeperGame();

		// create the board
		center.setLayout(new GridLayout(game.getBoardRow(), game.getBoardCol()));
		board = new JButton[game.getBoardRow()][game.getBoardCol()];

		// Adds buttons to the board based on user-inputted sizes
		for (int row = 0; row < game.getBoardRow(); row++)
			for (int col = 0; col < game.getBoardCol(); col++) {
				// Sets font, text size, and adds listeners
				board[row][col] = new JButton("");
				board[row][col].setMargin(new Insets(0,0,0,0));
				board[row][col].setFont(new Font("Ariel", Font.PLAIN, 15));
				board[row][col].addActionListener(listener);
				board[row][col].addMouseListener(mouse);
				// Adds Array of JButtons to center panel
				center.add(board[row][col]);
			}

		// JLabels to show wins, losses, and game title
		wins = new JLabel("WINS: ");
		losses = new JLabel("LOSSES: ");
		title = new JLabel("!!!!!!  Mine Sweeper  !!!!");

		// Sets font and size of JLabels
		wins.setFont(new Font("Ariel",Font.PLAIN,20));
		losses.setFont(new Font("Ariel", Font.PLAIN, 20));
		title.setFont(new Font("Ariel", Font.PLAIN, 20));

		//Sets text and adds listening to quit button
		quitButton = new JButton("Quit");
		quitButton.addActionListener(listener);

		// Updates wins, losses, and board of JButtons
		displayBoard();

		// Creates a GridBagLayout for the bottom panel with constraints
		bottom.setLayout(new GridBagLayout());
		GridBagConstraints position = new GridBagConstraints();

		// Sets position, as well as insets
		position.gridx = 0;
		position.gridy = 0;
		position.insets = new Insets(0,15,0,15);

		// Adds JLabel for wins to the bottom panel
		bottom.add(wins, position);

		// Sets position and adds JLabel for losses
		position.gridx = 1;
		bottom.add(losses, position);

		// Sets position and adds JLabel for quit button
		position.gridx = 2;
		bottom.add(quitButton, position);

		// adds all to contentPane
		top.add(title);
		add(top, BorderLayout.PAGE_START);
		top.setPreferredSize(new Dimension(800,30));
		add(center, BorderLayout.CENTER);
		center.setPreferredSize(new Dimension(800,500));
		add(bottom, BorderLayout.PAGE_END);
		bottom.setPreferredSize(new Dimension(800,30));


	}

	/**********************************************************************
 	* A method to return a JMenuBar which has a JMenu that contains
 	* three JMenuItems
 	*
 	* @return The JMenuBar you have created with functioning JMenuItems
	**********************************************************************/
	public JMenuBar addMenuBar(){

		// Creates a ButtonListener object
		ButtonListener listener = new ButtonListener();

		// Instantiates the JMenuBar
		menus = new JMenuBar();

		// Instantiates the JMenu and adds to JMenuBar
		file = new JMenu("File");
		menus.add(file);

		// Adds JMenuItem, sets text, and adds Listener
		newGameItem = new JMenuItem("New Game");
		file.add(newGameItem);
		newGameItem.addActionListener(listener);

		// Adds JMenuItem, sets text, and adds Listener
		resizeItem = new JMenuItem("Board Size");
		file.add(resizeItem);
		resizeItem.addActionListener(listener);

		// Adds JMenuItem, sets text, and adds Listener
		quitItem = new JMenuItem("Quit");
		file.add(quitItem);
		quitItem.addActionListener(listener);

		// Returns the JMenu
		return menus;
	}


	/**********************************************************************
	 * A method to update the board in order to display an accurate
	 * number of wins, number of losses, as well as the text for mines,
	 * bombs, and exposed cells
	 *********************************************************************/

	private void displayBoard() {

		// Sets texts to display current number of wins and losses
		wins.setText("WINS: " + game.getNumWins());
		losses.setText("LOSSES: " + game.getNumLosses());

		// Loops to set text for buttons
		for (int r = 0; r < game.getBoardRow(); r++)
			for (int c = 0; c < game.getBoardCol(); c++) {
				// Creates cell object to use with board Array
				iCell = game.getCell(r, c);

				board[r][c].setVisible(true);

				// Sets text to '!' if cell is a mine
				if (iCell.isMine())
					board[r][c].setText("!");
				// Sets text to blank if not a mine and not flagged
				if (!iCell.isMine() && !iCell.isFlagged())
					board[r][c].setText("");
				// Disables cell when clicked if it is not a mine/exposed
				if (iCell.isExposed() && !iCell.isMine()) {
					board[r][c].setEnabled(false);
					// Calculates the surrounding number of mines
					if(game.calcCounter(r,c) != 0)
						board[r][c].setText("" + game.calcCounter(r,c));
					else
						board[r][c].setText("");
				} else
					board[r][c].setEnabled(true);
			}
	}

	/**********************************************************************
	* A private method used to change the dimensions of the game board
	* and update the the JButton sizes respectively to fit the frame
	**********************************************************************/
	private void resizeBoard() {
		// Clears panel of current JPanel to make room for new-sized ones
		remove(center);
		remove(bottom);
		remove(top);

		// Creates three new JPanel objects
		bottom = new JPanel();
		center = new JPanel();
		top = new JPanel();

		// create game, listeners
		ButtonListener listener = new ButtonListener();
		MyMouseListener mouse = new MyMouseListener();
		game = new MineSweeperGame();

		// create the board
		center.setLayout(new GridLayout(game.getBoardRow(), game.getBoardCol()));
		board = new JButton[game.getBoardRow()][game.getBoardCol()];

		// Adds buttons to the board based on user-inputted sizes
		for (int row = 0; row < game.getBoardRow(); row++)
			for (int col = 0; col < game.getBoardCol(); col++) {
				// Sets font, text size, and adds listeners
				board[row][col] = new JButton("");
				board[row][col].setMargin(new Insets(0,0,0,0));
				board[row][col].setFont(new Font("Ariel", Font.PLAIN, 15));
				board[row][col].addActionListener(listener);
				board[row][col].addMouseListener(mouse);
				// Adds Array of JButtons to center panel
				center.add(board[row][col]);
			}

		// JLabels to show wins, losses, and game title
		wins = new JLabel("WINS: ");
		losses = new JLabel("LOSSES: ");
		title = new JLabel("!!!!!!  Mine Sweeper  !!!!");

		// Sets font and size of JLabels
		wins.setFont(new Font("Ariel",Font.PLAIN,20));
		losses.setFont(new Font("Ariel", Font.PLAIN, 20));
		title.setFont(new Font("Ariel", Font.PLAIN, 20));

		//Sets text and adds listening to quit button
		quitButton = new JButton("Quit");
		quitButton.addActionListener(listener);

		// Updates wins, losses, and board of JButtons
		displayBoard();

		// Creates a GridBagLayout for the bottom panel with constraints
		bottom.setLayout(new GridBagLayout());
		GridBagConstraints position = new GridBagConstraints();

		// Sets position, as well as insets
		position.gridx = 0;
		position.gridy = 0;
		position.insets = new Insets(0,15,0,15);

		// Adds JLabel for wins to the bottom panel
		bottom.add(wins, position);

		// Sets position and adds JLabel for losses
		position.gridx = 1;
		bottom.add(losses, position);

		// Sets position and adds JLabel for quit button
		position.gridx = 2;
		bottom.add(quitButton, position);

		// adds all to contentPane
		top.add(title);
		add(top, BorderLayout.PAGE_START);
		top.setPreferredSize(new Dimension(800,30));
		add(center, BorderLayout.CENTER);
		center.setPreferredSize(new Dimension(800,500));
		add(bottom, BorderLayout.PAGE_END);
		bottom.setPreferredSize(new Dimension(800,30));

		// Validates new sizes and updates GUI
		revalidate();
	}

	/**********************************************************************
	 * A private ButtonListener class that implements an ActionListener
	 * to run the select method from our game class when clicking a cell
	**********************************************************************/
	private class ButtonListener implements ActionListener {


		public void actionPerformed(ActionEvent e) {

			// Runs the select method if the respective cell is clicked
			for (int r = 0; r < game.getBoardRow(); r++)
				for (int c = 0; c < game.getBoardCol(); c++)
					if (board[r][c] == e.getSource())
						game.select(r, c);

			// Updates board to account for exposed cells
			displayBoard();

			// After clicking, checks game status to see if you've lost
			if (game.getGameStatus() == GameStatus.Lost) {
				displayBoard();
				JOptionPane.showMessageDialog(null,
						"You Lose \n The game will reset");
				game.reset();
				displayBoard();

			}

			// After clicking, checks game status to see if you've won
			if (game.getGameStatus() == GameStatus.WON) {
				JOptionPane.showMessageDialog(null,
						"You Win: all mines have been found!" +
								"\n The game will reset");
				game.reset();
				displayBoard();
			}

			// If statement for if the quit button is pressed
			if (e.getSource() == quitButton){
				System.exit(0);
			}

			// If statement for if the New Game button is pressed
			if(e.getSource() == newGameItem){
				game.reset();
				displayBoard();
			}

			// If statement for if the quit button is pressed
			if(e.getSource() == quitItem){
				System.exit(0);
			}

			// If statement for if the board size button is pressed
			if(e.getSource() == resizeItem){
				resizeBoard();
			}

		}

	}

	/**********************************************************************
	 * A private MyMouseListener class that implements a MouseListener
	 *********************************************************************/
	private class MyMouseListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		/******************************************************************
		 * Public method with no return that sets the text of the clicked
		 * cell if it is being flagged or not flagged. Also changes back
		 * to original text if flag is being removed
		 *****************************************************************/
		@Override
		public void mouseReleased(MouseEvent e) {
			// Scans board for clicked cell
			for (int r = 0; r < game.getBoardRow(); r++) {
				for (int c = 0; c < game.getBoardCol(); c++) {
					// Checks if the cell was clicked with the right clicker
					if (e.getSource() == board[r][c] && e.getButton() == MouseEvent.BUTTON3) {
						// If not flagged, set text to 'F', boolean to true
						if (!game.getCell(r, c).isFlagged()) {
							board[r][c].setText("F");
							game.getCell(r, c).setFlagged(true);
						}// If flagged and a mine, sets text to '!' and false boolean
						else if (game.getCell(r, c).isFlagged() && game.getCell(r,c).isMine()) {
							board[r][c].setText("!");
							game.getCell(r, c).setFlagged(false);
						}// If flagged and not a mine, sets blank text and false boolean
						else if (game.getCell(r,c).isFlagged() && !game.getCell(r,c).isMine()){
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




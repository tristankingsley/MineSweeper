package Project2;


import javax.swing.*;
import java.awt.*;
/**********************************************************************
 This class creates JFrame of our MineSweeper project and adds our
 panel to its display.

 @author Tristan Kingsley and Trevor Spitzley
 @Version February 2019
 *********************************************************************/
public class MineSweeperGUI {

	public static void main (String[] args)
	{
		JFrame frame = new JFrame ("Mine Sweeper!");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		MineSweeperPanel panel = new MineSweeperPanel();
		frame.setJMenuBar(panel.addMenuBar());
		frame.getContentPane().add(panel);

		//size set bigger to accommodate larger boards
		frame.setSize(900, 700);
		frame.setVisible(true);
		frame.setLocation(150,30);
	}
}


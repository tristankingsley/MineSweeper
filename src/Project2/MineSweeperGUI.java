package Project2;


import javax.swing.*;
import java.awt.*;

public class MineSweeperGUI {

	public static void main (String[] args)
	{
		JFrame frame = new JFrame ("Mine Sweeper!");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		MineSweeperPanel panel = new MineSweeperPanel();
		//JScrollPane container = new JScrollPane(panel);
		//frame.setJMenuBar(panel.addMenuBar());
		frame.getContentPane().add(panel);
		frame.setSize(900, 700);
		frame.setVisible(true);
		frame.setLocation(150,30);
	}
}


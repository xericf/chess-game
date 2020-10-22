package engine.scenes;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;

import engine.components.Board;

public class Play extends JPanel{
	
	private Board board;
	//this.getComponentAt will be useful for getting the piece being taken
	public Play() {
		board = new Board();
		setLayout(new BorderLayout());
		add(board, BorderLayout.CENTER);	
	}
	
}

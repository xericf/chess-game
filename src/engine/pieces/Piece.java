package engine.pieces;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import engine.components.Board;
import engine.components.Square;

public abstract class Piece {
	/**
	 * This abstract class will be implemented by all the piece types such as the King, Queen, Bishop, etc.
	 * */
	private Color color;
	private BufferedImage pic;
	private Square square;
	
	public Piece(Square startSquare, Color color, String imgLocation) {
		/**
		 * @param imgLocation - will be a path like /resources/piece.png
		 * */
		square = startSquare;
		this.color = color;
		
		try {
			File f = new File(imgLocation);
			pic = ImageIO.read(f);
		} catch (Exception e) {
			System.out.println("Error, the picture for " + imgLocation + " was not found!");
		}
	}
	
	public void draw(Graphics g) {
		
	}
	
	public abstract ArrayList getLegalMoves(Board board); // will implement later as it's abstract
	
}

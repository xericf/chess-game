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
	private BufferedImage pic;
	protected Square square;
	private int team;
	private int size = 80;
	
	public Piece(Square startSquare, int color, String imgLocation) {
		/**
		 * @param imgLocation - will be a path like /resources/piece.png
		 * */
		square = startSquare;
		this.team = color;
		try {
			File f = new File(imgLocation);
			pic = ImageIO.read(f);
		} catch (Exception e) {
			System.out.println("Error, the picture for " + imgLocation + " was not found!");
		}
	}
	
	public boolean move(Square end) {
		/**
		 * This function will simply move a piece to a selected square.
		 * @param end - Then square the piece will end up on.
		 * */
		if(end == null) return false;
		square.setDisplayPiece(true); // set it to true again to allow for the next piece to be shown
		end.setPiece(square.getPiece()); // Will just reference to this object... could also replace with the this keyword.
		square.setPiece(null);
		square = end; // reassign the currentSquare to the finishing square.
		return true;
	}
	
	public int getTeam() {
		return this.team;
	}
	
	public BufferedImage getImage() {
		// This will be used for getting the image of the piece for dragging and stuff.
		return pic;
	}
	
	public void draw(Graphics g, int x, int y) {
		/**
		 * This method will draw on a JComponent's g class using their respective BufferedImage picture.
		 * */
		g.drawImage(pic, x, y, size, size, null);
	}
	
	// Probably will need a "ConsiderKing" function that checks to see if the piece is pinned, or if castling is not allowed.
	public abstract ArrayList<Square> getLegalMoves(Board board); // will implement later in other classes as it's abstract
	
}

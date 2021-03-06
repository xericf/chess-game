package engine.pieces;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import engine.components.Board;
import engine.components.MoveHandler;
import engine.components.Square;

public abstract class Piece {
	/**
	 * This abstract class will be implemented by all the piece types such as the King, Queen, Bishop, etc.
	 * */
	private BufferedImage pic;
	protected Square square;
	private int team;
	private int size = 80;
	protected boolean isAlive = true;
	
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
	
	public Piece(Square startSquare, int color, boolean isAlive) {
		square = startSquare;
		this.team = color;
		this.isAlive = isAlive;
	}
	
	public boolean move(Square end, MoveHandler mh) {
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

	public void setAlive(boolean a) {
		this.isAlive = a;
	}
	
	public boolean isAlive() {
		return this.isAlive;
	}
	
	public int getTeam() {
		return this.team;
	}
	
	public void setSquare(Square s) {
		this.square = s;
	}
	
	public Square getSquare() {
		return this.square;
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
	
	public abstract ArrayList<Square> getLegalMoves(MoveHandler mh); // will implement later in other classes as it's abstract
	public abstract Piece clone();
	public abstract float getValue();
	
}

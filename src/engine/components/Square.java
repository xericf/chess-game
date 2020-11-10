package engine.components;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JComponent;

import engine.pieces.Piece;

public class Square extends JComponent {
	
	private int y; // this will be which row the square is in
	private int x; // this will be which column the square is in
	private int squareSize;
	private boolean displayPiece; // will be used to see if the piece is currently being dragged.
	private Piece piece; // will be N, K, Q, Etc.
	private Color color;
	private Board board;
	
	public Square(int x, int y, int squareSize, Color color, Board b) {
		/**
		 * @param y - row of square
		 * @param x - col of square
		 * @param color - a memory reference to a Color object that is stored in the Board class
		 * **/
		this.y = y;
		this.x = x;
		this.board = b;
		this.squareSize = squareSize;
		this.displayPiece = false;
		this.piece = null; // empty square
		this.color = color;
		
	}
	
	public Board getBoard() {
		return this.board;
	}
	
	public int[] getPosition() {
		return new int[] {x, y};
	}
	
	public void setPiece(Piece pt) {
		/**
		 * @param pt - Piece class of piece.
		 * */
		if(pt == null) {
			this.piece = null;
			displayPiece = true;
		} else {
			displayPiece = true;
			this.piece = pt;
		}
		repaint();
	}
	
	public void setDisplayPiece(boolean has) {
		this.displayPiece = has;
	}
	
	public Piece getPiece() {
		/**
		 * Will just return the piece type
		 * */
		return this.piece;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		/**
		 * This method draws the color of the square
		 * */
		super.paintComponent(g);
		g.setColor(this.color);
		g.fillRect(x*squareSize, y*squareSize, squareSize, squareSize);
		
		if(displayPiece && this.piece != null) { // if there's a piece in there, then draw it.
			piece.draw(g, x*squareSize, y*squareSize);
		}
		
	}
	
}

package engine.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import engine.pieces.Pawn;
import engine.pieces.Piece;
import engine.pieces.Rook;

public class Board extends JPanel implements MouseListener, MouseMotionListener {

	private Square[][] squares;
	private Color squareColor1;
	private Color squareColor2;
	private int squareSize = 80;
	
	private int pieceX = 0;
	private int pieceY = 0;
	private Square pieceSquare= null;
	
	public Board() {
		squares = new Square[8][8]; // create an 8x8 board
		boolean colorSwitch = false;
		squareColor1 = new Color(204, 102, 0);
		squareColor2 = new Color(255, 193, 128);
		for(int x = 0; x< 8; x++) { // initialize all of the squares
			for(int y = 0; y < 8; y++) {
				if(colorSwitch) {
					squares[x][y] = new Square(x, y, squareSize, squareColor1);
				} else {
					squares[x][y] = new Square(x, y, squareSize, squareColor2);
				}
				colorSwitch = !colorSwitch;
			}
			colorSwitch = !colorSwitch; // this is to offset the column color by 1 
		}
		initPieces();
		addMouseListener(this); // attach the implemented mouse listener methods from the interface to the JPanel of Board
		addMouseMotionListener(this);
	}
	
	public void initPieces() {
		/**
		 * This function will put all of the pieces into their respective squares for both white and black.
		 * */
		//white pieces initialize, 1= black, 0 = white;
		for(int i = 0; i < 8; i++) {
			squares[i][6].setPiece(new Pawn(squares[i][6], 0, "resources/wpawn.png"));
		}
		squares[0][7].setPiece(new Rook(squares[0][7], 0, "resources/wrook.png"));
		squares[7][7].setPiece(new Rook(squares[7][7], 0, "resources/wrook.png"));
		squares[1][7].setPiece(new Rook(squares[1][7], 0, "resources/wknight.png"));
		squares[6][7].setPiece(new Rook(squares[6][7], 0, "resources/wknight.png"));
		squares[2][7].setPiece(new Rook(squares[2][7], 0, "resources/wbishop.png"));
		squares[5][7].setPiece(new Rook(squares[5][7], 0, "resources/wbishop.png"));
		squares[3][7].setPiece(new Rook(squares[3][7], 0, "resources/wqueen.png"));
		squares[4][7].setPiece(new Rook(squares[4][7], 0, "resources/wking.png"));
		
		//black pieces initialize, 1= black, 0 = white;
		for(int i = 0; i < 8; i++) {
			squares[i][1].setPiece(new Pawn(squares[i][1], 1, "resources/bpawn.png"));
		}
		squares[0][0].setPiece(new Rook(squares[0][0], 1, "resources/brook.png"));
		squares[7][0].setPiece(new Rook(squares[7][0], 1, "resources/brook.png"));
		squares[1][0].setPiece(new Rook(squares[1][0], 1, "resources/bknight.png"));
		squares[6][0].setPiece(new Rook(squares[6][0], 1, "resources/bknight.png"));
		squares[2][0].setPiece(new Rook(squares[2][0], 1, "resources/bbishop.png"));
		squares[5][0].setPiece(new Rook(squares[5][0], 1, "resources/bbishop.png"));
		squares[3][0].setPiece(new Rook(squares[3][0], 1, "resources/bqueen.png"));
		squares[4][0].setPiece(new Rook(squares[4][0], 1, "resources/bking.png"));
		
		repaint(); // I'm not really sure if this is necessary since it drew without this method, but this is just to be sure it repaints
	}
	
	private Square getSquare(int x, int y) {
		Square s = null;
		try {
			s = squares[(int) (Math.floor(x/squareSize))][(int) (Math.floor(y/squareSize))];
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Square out of bounds");
		}
		return s; 
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(int x = 0; x< 8; x++) { // initialize all of the squares
			for(int y = 0; y < 8; y++) {
				squares[x][y].paintComponent(g);
			}
		}
        if(pieceSquare != null) {
        	// This will draw the piece being held
        	System.out.println();
        	pieceSquare.getPiece().draw(g, pieceX-(squareSize/2), pieceY-(squareSize/2));
        }
    }

	@Override
	public void mouseDragged(MouseEvent m) {
		// TODO Auto-generated method stub
		pieceX = m.getPoint().x;
		pieceY = m.getPoint().y;
		
		repaint();
		System.out.println("X: " + pieceX + " Y: " + pieceY);
		
	}

	@Override
	public void mousePressed(MouseEvent m) {
		// TODO Auto-generated method stub
		int x = m.getPoint().x;
		int y = m.getPoint().y;
		Square sq = getSquare(x, y);
		if(sq.getPiece() != null) {
			this.pieceSquare = sq;
			pieceSquare.setDisplayPiece(false);
		}
	}

	@Override
	public void mouseReleased(MouseEvent m) {
		int x = m.getPoint().x;
		int y = m.getPoint().y;
		// TODO Auto-generated method stub
		Square sq = getSquare(x, y);
		if(sq == pieceSquare) {
			repaint();
		} else if(pieceSquare != null) { // if they are not the same object and a piece is selected
			sq.setPiece(pieceSquare.getPiece());
			pieceSquare.setPiece(null);
			pieceSquare.setDisplayPiece(true);
			pieceSquare = null;
			repaint();
		}
	}
	

	@Override
	public void mouseMoved(MouseEvent m) {
		// TODO Auto-generated method stud
	}

	@Override
	public void mouseClicked(MouseEvent m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
	
	}
	
	public Square[][] getSquaresArray() {
		return squares;
	}

}

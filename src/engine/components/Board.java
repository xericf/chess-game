package engine.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import engine.pieces.Bishop;
import engine.pieces.King;
import engine.pieces.Knight;
import engine.pieces.Pawn;
import engine.pieces.Piece;
import engine.pieces.Queen;
import engine.pieces.Rook;

public class Board extends JPanel implements MouseListener, MouseMotionListener {

	private Square[][] squares;
	private Color squareColor1;
	private Color squareColor2;
	private int squareSize = 80;
	private int pieceX = 0;
	private int pieceY = 0;
	private Square pieceSquare = null;
	
	private Cursor defCursor = new Cursor(Cursor.DEFAULT_CURSOR);
	private Cursor grabCursor = new Cursor(Cursor.HAND_CURSOR);
	private Condition condition;
	public AI ai;
	private int turn;
	private boolean isChecked;
	private int turnNumber;
	
	public Board() {
		squares = new Square[8][8]; // create an 8x8 board
		boolean colorSwitch = false;
		squareColor1 = new Color(204, 102, 0);
		squareColor2 = new Color(255, 193, 128);
		turn = 0;
		isChecked = false;
		for (int x = 0; x < 8; x++) { // initialize all of the squares
			for (int y = 0; y < 8; y++) {
				if (colorSwitch) {
					squares[x][y] = new Square(x, y, squareSize, squareColor1, this);
				} else {
					squares[x][y] = new Square(x, y, squareSize, squareColor2, this);
				}
				colorSwitch = !colorSwitch;
			}
			colorSwitch = !colorSwitch; // this is to offset the column color by 1
		}
		initPieces();
		Piece[] bp = new Piece[16];
		Piece[] wp = new Piece[16];
		for(int i = 0; i < 16; i++) {
			bp[i] = squares[i%8][i/8].getPiece(); // this is simply taking advantage of integer flooring to get the row and column of each piece
			wp[i] = squares[i%8][i/8 + 6].getPiece();
		}
		condition = new Condition((King) squares[4][7].getPiece(), (King) squares[4][0].getPiece(), bp, wp, this);
		ai = new AI(wp, bp, this, condition);
		addMouseListener(this); // attach the implemented mouse listener methods from the interface to the JPanel of Board
		addMouseMotionListener(this);
	}

	public void initPieces() {
		/**
		 * This function will put all of the pieces into their respective squares for
		 * both white and black.
		 */
		// white pieces initialize, 1= black, 0 = white;
		for (int i = 0; i < 8; i++) {
			squares[i][6].setPiece(new Pawn(squares[i][6], 0, "resources/wpawn.png"));
		}
		squares[0][7].setPiece(new Rook(squares[0][7], 0, "resources/wrook.png"));
		squares[7][7].setPiece(new Rook(squares[7][7], 0, "resources/wrook.png"));
		squares[1][7].setPiece(new Knight(squares[1][7], 0, "resources/wknight.png"));
		squares[6][7].setPiece(new Knight(squares[6][7], 0, "resources/wknight.png"));
		squares[2][7].setPiece(new Bishop(squares[2][7], 0, "resources/wbishop.png"));
		squares[5][7].setPiece(new Bishop(squares[5][7], 0, "resources/wbishop.png"));
		squares[3][7].setPiece(new Queen(squares[3][7], 0, "resources/wqueen.png"));
		squares[4][7].setPiece(new King(squares[4][7], 0, "resources/wking.png"));

		// black pieces initialize, 1= black, 0 = white;
		for (int i = 0; i < 8; i++) {
			squares[i][1].setPiece(new Pawn(squares[i][1], 1, "resources/bpawn.png"));
		}
		squares[0][0].setPiece(new Rook(squares[0][0], 1, "resources/brook.png"));
		squares[7][0].setPiece(new Rook(squares[7][0], 1, "resources/brook.png"));
		squares[1][0].setPiece(new Knight(squares[1][0], 1, "resources/bknight.png"));
		squares[6][0].setPiece(new Knight(squares[6][0], 1, "resources/bknight.png"));
		squares[2][0].setPiece(new Bishop(squares[2][0], 1, "resources/bbishop.png"));
		squares[5][0].setPiece(new Bishop(squares[5][0], 1, "resources/bbishop.png"));
		squares[3][0].setPiece(new Queen(squares[3][0], 1, "resources/bqueen.png"));
		squares[4][0].setPiece(new King(squares[4][0], 1, "resources/bking.png"));

		repaint(); // I'm not really sure if this is necessary since it drew without this method,
					// but this is just to be sure it repaints
	}
	
	public int getTurnNumber() {
		return this.turnNumber;
	}
	
	public Square getSquareFromCoord(int x, int y) {
		Square s = null;
		try {
			s = squares[(int) (Math.floor(x / squareSize))][(int) (Math.floor(y / squareSize))];
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Square out of bounds");
		}
		return s;
	}
	
	public Square getSquare(int col, int row) {
		Square s = null;
		try {
			s = squares[col][row];
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Square out of bounds");
		}
		return s;
	}
	
	public boolean AttemptMove(Square start, Square end) {
		Piece p = start.getPiece();
		Piece savedPiece = end.getPiece(); // this piece will be if the move is not allowed, and it will replace the
		// end square with it again if the king is still in check.
		ArrayList<Square> legalMoves = p.getLegalMoves(this);
		if(legalMoves == null) return false;
		for(int i = 0; i < legalMoves.size(); i++) {
			if(legalMoves.get(i) == end) {
				p.move(end);
				if(condition.inCheck(turn)) { 
					// Essentially this will reverse the move if the king is put in check or already in check from the move.
					end.setPiece(savedPiece);
					start.setPiece(p);
					p.setSquare(start); // IMPORTANT MUST SET THE SQUARES BACK OR ELSE IT THINKS ITS POSITIONS ARE DIFFERENT
					if(savedPiece != null) {
						savedPiece.setSquare(end);
					}
					return false;
				}
				// Insurmountably important as when pieces are considered for their legal moves in the detectors, it needs to consider if the piece is on the board or not.
				if(savedPiece != null) savedPiece.setAlive(false);  
				
				// Pawn promotion check
				int yCoord = end.getPosition()[1];
				if(p instanceof Pawn && (yCoord == 0 || yCoord == 7)) {
					Piece newPiece = checkPawnPromotion(end);
					end.setPiece(newPiece); //VERY important to get the piece on the board and rendered, this will fully remove all traces of the pawn piece that used to be there
					condition.replacePiece(p, newPiece, p.getTeam());// reassign the piece to the new Piece that is created, needs to be reassigned to work with the conditions piece array
				}
				turnNumber++;
				turn = turn == 1 ? 0 : 1; // This flips the turn to the other player, important that it's last.
				return true;
			}
		}
		
		return false;
	}
	
	public Piece checkPawnPromotion(Square s) {
		/**
		 * @returns Rook - Rook promotion
		 * @returns Bishop - Bishop Promotion
		 * @returns Knight - Knight Promotion
		 * @returns Queen - Queen Promotion
		 * */
		String prefix = turn == 0 ? "w" : "b"; // white or black that will be added to image string
		String[] buttons = {"Rook", "Bishop", "Knight", "Queen"};
		int option =  JOptionPane.showOptionDialog(this,
				"Choose the piece you want to promote to:",
				"Pawn Promotion",
				 JOptionPane.DEFAULT_OPTION,
				 JOptionPane.PLAIN_MESSAGE,
				null,
				buttons, // give the buttons
				buttons[3] // This is the default selection for the dialog -- will be queen if you don't choose anything
				);
		Piece data = null;
		switch(option) {
			case 0:
				data = new Rook(s, turn, "resources/"+ prefix + "rook.png");
				break;
			case 1:
				data = new Bishop(s, turn, "resources/"+ prefix + "bishop.png");
				break;	
			case 2:
				data = new Knight(s, turn, "resources/"+ prefix + "knight.png");
				break;
			case 3:
				data = new Queen(s, turn, "resources/"+ prefix + "queen.png");
				break;
		}
		return data;
	}
	
	public int getTurn() {
		return this.turn;
	}
	
	public boolean isChecked() {
		return this.isChecked;
	}
	
	public Condition getConditionObj() {
		return this.condition;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int x = 0; x < 8; x++) { // initialize all of the squares
			for (int y = 0; y < 8; y++) {
				squares[x][y].paintComponent(g);
			}
		}
		if (pieceSquare != null) {
			// This will draw the piece being held
			pieceSquare.getPiece().draw(g, pieceX - (squareSize / 2), pieceY - (squareSize / 2));
		}
	}

	@Override
	public void mouseDragged(MouseEvent m) {
		// TODO Auto-generated method stub
		pieceX = m.getPoint().x;
		pieceY = m.getPoint().y;
		repaint(); // this will call the paintComponent function again and will draw the moved piece using pieceX and pieceY
	}

	@Override
	public void mousePressed(MouseEvent m) {
		// TODO Auto-generated method stub
		int x = m.getPoint().x;
		int y = m.getPoint().y;
		Square sq = getSquareFromCoord(x, y);
		if (sq.getPiece() != null && sq.getPiece().getTeam() == turn) {
			this.pieceSquare = sq;
			pieceSquare.setDisplayPiece(false);
			setCursor(grabCursor);
		}
	}

	@Override
	public void mouseReleased(MouseEvent m) {
		int x = m.getPoint().x;
		int y = m.getPoint().y;
		// TODO Auto-generated method stub
		Square sq = getSquareFromCoord(x, y);
		if (sq == pieceSquare || sq == null) {
			pieceSquare.setDisplayPiece(true);
			pieceSquare = null;
		} else if (pieceSquare != null) { // if they are not the same object and a piece is selected
			boolean result = AttemptMove(pieceSquare, sq); // If the move was allowed/done or not.
			if(result) {
				switch(condition.checkWin(turn)) {
				case 0:
					break;
				case 1:
					System.out.println("Checkmate");
					break;
				case 2:
					System.out.println("Stalemate");
					break;
				}
			} else {
				pieceSquare.setDisplayPiece(true);
			}
			pieceSquare = null;
		}
		setCursor(defCursor); // set cursor back to default.
		repaint();
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

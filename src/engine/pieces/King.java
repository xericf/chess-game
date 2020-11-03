package engine.pieces;

import java.util.ArrayList;

import engine.components.Board;
import engine.components.Square;
import engine.components.Condition;

public class King extends Piece {
	
	public boolean hasMoved;
	
	public King(Square startSquare, int color, String imgLocation) {
		super(startSquare, color, imgLocation);
		hasMoved = false;
	}
	
	@Override
	public boolean move(Square end) {
		/**
		 * We override this method to incorporate the special rule of chess where the king can't castle if the king has moved already
		 * */
		if(end == null) return false;
		square.setDisplayPiece(true); // set it to true again to allow for the next piece to be shown
		end.setPiece(square.getPiece()); // Will just reference to this object... could also replace with the this keyword.
		square.setPiece(null);
		square = end; // reassign the currentSquare to the finishing square.
		int[] p = end.getPosition();
		Board b = square.getBoard();
		if(!hasMoved) {
			// If end square points to one of these x positions, that means the rook is still on the default square and there's no checks to prevent castling because of the getLegalMoves function
			if(p[0] == 2) {
				Square rookSquare = b.getSquare(0, p[1]);
				Square destSquare = b.getSquare(3, p[1]);
				rookSquare.getPiece().move(destSquare);
			} else if(p[0] == 6) {
				Square rookSquare = b.getSquare(7, p[1]);
				Square destSquare = b.getSquare(5, p[1]);
				rookSquare.getPiece().move(destSquare);
			}
		}
		hasMoved = true;
		return true;
	}
	
	public boolean isSquareSafe(Board board, Square s, int team) {;
	
		if(Condition.getKnightThreats(board, s, team).size() != 0
				|| Condition.getDiagonalThreats(board, s, team).size() != 0
				|| Condition.getStraightThreats(board, s, team).size() != 0) {
			return false;
		}
		return true;
	}
	
	@Override
	public ArrayList<Square> getLegalMoves(Board board) {
		ArrayList<Square> moves = new ArrayList<Square>();
		Square[][] squares = board.getSquaresArray();
		int[] position = this.square.getPosition(); // specifying this in reference to the protected square Square value
		int x = position[0];
		int y = position[1];
		int team = this.getTeam();
		
		for(int i = -1; i <= 1; i++) {
			for(int j = -1; j <= 1; j++) {
				try {
					Square s = squares[x+i][y+j];
					if(s != this.square && (s.getPiece() == null || s.getPiece().getTeam() != team)) { // need to check if it's not this.square to get rid of its current square
						if(isSquareSafe(board, s, team)) {
							moves.add(s);
						}
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					continue; // Simply continue if it is out of bounds onto the next possible move
				}
			}
		}
		
		if(!hasMoved && !board.isChecked()) { // If the king has not moved, and is also not currently in check.
			// king-side castling
			boolean clear = true; 
			// You can use x+1 as the starting point because in the default position, the king is always in the same square.
			for(int i = x+1; i < 7; i++) { // obviously offset x by 1 to exclude the king's square.
				if(squares[i][y].getPiece() == null) {
					// check threats.
				} else {
					clear = false;
					break;
				}
			}
			Square s = squares[7][y];
			if(clear && s.getPiece() instanceof Rook && !((Rook) s.getPiece()).hasMoved) { // needs to be casted to rook to get the hasMoved property
				moves.add(squares[6][y]); // This is just to change where you must drag and drop the king in order to castle.
			}
			
			// Queen side castling
			clear = true; 
			// You can use x+1 as the starting point because in the default position, the king is always in the same square.
			for(int i = x-1; i > 0; i--) { // obviously offset x by 1 to exclude the king's square.
				if(squares[i][y].getPiece() == null) {
					// check threats.
				} else {
					clear = false;
					break;
				}
			}
			s = squares[0][y];
			if(clear && s.getPiece() instanceof Rook && !((Rook) s.getPiece()).hasMoved) { // needs to be casted to rook to get the hasMoved property
				moves.add(squares[2][y]); // This is just to change where you must drag and drop the king in order to castle.
			}
		}
		
		return moves;
	}
	
}

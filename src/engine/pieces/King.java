package engine.pieces;

import java.util.ArrayList;

import engine.components.Board;
import engine.components.Square;

public class King extends Piece {
	
	public boolean hasMoved;
	
	public King(Square startSquare, int color, String imgLocation) {
		super(startSquare, color, imgLocation);
		hasMoved = false;
	}
	
	@Override
	public boolean move(Square end) {
		hasMoved = true;
		square.setDisplayPiece(true); // set it to true again to allow for the next piece to be shown
		end.setPiece(square.getPiece()); // Will just reference to this object... could also replace with the this keyword.
		square.setPiece(null);
		square = end; // reassign the currentSquare to the finishing square.
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
						moves.add(s);
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					continue; // Simply continue if it is out of bounds onto the next possible move
				}
			}
		}
		
		if(!hasMoved) {
			// Will be for castling
		}
		
		return moves;
	}

}

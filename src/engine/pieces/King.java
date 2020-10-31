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
		// TODO Auto-generated method stub
		return null;
	}

}

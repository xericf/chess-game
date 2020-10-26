package engine.pieces;

import java.util.ArrayList;

import engine.components.Board;
import engine.components.Square;

public class Pawn extends Piece {

	public Pawn(Square startSquare, int team, String imgLocation) {
		super(startSquare, team, imgLocation); // will just call the Piece constructor..
	}

	@Override
	public ArrayList getLegalMoves(Board board) {
		return null;
	}

}

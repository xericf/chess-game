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
		ArrayList<Square> moves = new ArrayList<Square>();
		Square[][] sqaures = board.getSquaresArray();
		int[] position = this.square.getPosition(); // specifying this in reference to the protected square Square value
		for(int y = position[1]; y < position[1] + 2; y++) { // check 2 moves forward
			
		}
		return null;
	}

}

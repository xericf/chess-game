package engine.pieces;

import java.util.ArrayList;

import engine.components.Board;
import engine.components.Square;

public class Pawn extends Piece {

	public Pawn(Square startSquare, int team, String imgLocation) {
		super(startSquare, team, imgLocation); // will just call the Piece constructor..
	}

	@Override
	public ArrayList<Square> getLegalMoves(Board board) {
		/**
		 * @param board - The board that the piece is on.
		 * This will get all the legal moves of the pawn piece.
		 * */
		
		ArrayList<Square> moves = new ArrayList<Square>();
		Square[][] squares = board.getSquaresArray();
		int[] position = this.square.getPosition(); // specifying this in reference to the protected square Square value
		int team = this.getTeam();
		if(team == 0) { // 0 is white
			int offset = position[1] == 6 ? 2 : 1;  // if white's pawn are on their starting square, they can potentially move twice
			for(int y = position[1]-1; y >= position[1] - offset; y--) { // check 2 moves backward (up) in the y position
				// Excludes the square that it's on (position[1]), that's why there's a -1 in the first column of the for loop.
				if(squares[position[0]][y].getPiece() == null) {
					moves.add(squares[position[0]][y]);
				} else {
					break;
				}
			}
			Square upRight = position[0] + 1 > 7 ? null : squares[position[0] + 1][position[1] -1];
			Square upLeft = position[0] -1 < 0 ? null :squares[position[0] - 1][position[1] - 1];
			if(upRight != null && upRight.getPiece() != null && upRight.getPiece().getTeam() != team) { 
				// The extremely neat thing about this is that it doesn't throw a nullPointerException if upRight is null because it will automatically stop the if statement if upRight == null
				moves.add(upRight);
			} 
			if(upLeft != null && upLeft.getPiece() != null && upLeft.getPiece().getTeam() != team) {
				moves.add(upLeft);
			}
		} else {
			int offset = position[1] == 1 ? 2 : 1; // if black's pawn are on their starting square, they can potentially move twice
			for(int y = position[1]+1; y <= position[1] + offset; y++) { // check 2 moves forward (down) in the y position
				if(squares[position[0]][y].getPiece() == null) {
					moves.add(squares[position[0]][y]);
				} else {
					break;
				}
			}
			Square downRight = position[0] + 1 > 7 ? null : squares[position[0] + 1][position[1] + 1];
			Square downLeft = position[0] -1 < 0 ? null : squares[position[0] - 1][position[1] + 1];
			if(downRight != null && downRight.getPiece() != null && downRight.getPiece().getTeam() != team) {
				moves.add(downRight);
			} 
			if(downLeft != null && downLeft.getPiece() != null && downLeft.getPiece().getTeam() != team) {
				moves.add(downLeft);
			}
		}
		
		return moves;
	}

}

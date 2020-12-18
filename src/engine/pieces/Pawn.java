package engine.pieces;

import java.util.ArrayList;
import java.util.Arrays;

import engine.components.Board;
import engine.components.Condition;
import engine.components.Square;

public class Pawn extends Piece {
	public int turnMoved = -1;
	public Pawn(Square startSquare, int team, String imgLocation) {
		super(startSquare, team, imgLocation); // will just call the Piece constructor..
	}
	
	@Override
	public boolean move(Square end) {
		if(end == null) return false;
		square.setDisplayPiece(true); // set it to true again to allow for the next piece to be shown
		end.setPiece(square.getPiece()); // Will just reference to this object... could also replace with the this keyword.
		square.setPiece(null);
		square = end; // reassign the currentSquare to the finishing square.
		if(turnMoved == -1) turnMoved = end.getBoard().getTurnNumber();
		return true;
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
		int turnNumber = board.getTurnNumber();
		
		System.out.println(this);
		System.out.println(team);
		
		if(team == 0) { // 0 is white
			int offset = position[1] == 6 ? 2 : 1;  // if white's pawn are on their starting square, they can potentially move twice
			for(int y = position[1]-1; y >= position[1] - offset; y--) { // check 2 moves backward (up) in the y position
				// Excludes the square that is on (position[1]), that's why there's a -1 in the first column of the for loop.
				if(squares[position[0]][y].getPiece() == null) {
					moves.add(squares[position[0]][y]);
				} else {
					break;
				}
			}
			Square upRight = null, upLeft = null, right = null, left = null;
			if(position[0] + 1 <= 7) {
				right = squares[position[0] + 1][position[1]];
				upRight = squares[position[0] + 1][position[1] -1];
			}
			if(position[0] - 1 >= 0) {
				upLeft = squares[position[0] - 1][position[1] - 1];
				left = squares[position[0] - 1][position[1]];
			}
			if(upRight != null) {
				Piece urp = upRight.getPiece();
				if(urp != null && urp.getTeam() != team) {
					moves.add(upRight);
				}
			} else if(right != null) { // en passant
				Piece rp = right.getPiece();
				if(rp != null && rp.getTeam() != team && rp instanceof Pawn && ((Pawn) rp).turnMoved == turnNumber-1) {
					moves.add(right);
				}
			}
			if(upLeft != null) {
				Piece ulp = upLeft.getPiece();
				if(ulp != null && ulp.getTeam() != team) {
					moves.add(upLeft);
				}
			} else if(left != null) { // en passant
				Piece lp = left.getPiece();
				if(lp != null && lp.getTeam() != team && lp instanceof Pawn && ((Pawn) lp).turnMoved == turnNumber-1) {
					
				}
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

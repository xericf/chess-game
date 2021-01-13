package engine.pieces;

import java.util.ArrayList;
import java.util.Arrays;

import engine.components.Board;
import engine.components.Condition;
import engine.components.MoveHandler;
import engine.components.Square;

public class Pawn extends Piece {
	public int turnMoved = -1;
	private int team;
	private Square startSquare;
	public Pawn(Square startSquare, int team, String imgLocation) {
		super(startSquare, team, imgLocation); // will just call the Piece constructor..
	}
	public Pawn(Square startSquare, int team, int turnMoved, boolean isAlive) {
		super(startSquare, team, isAlive);
		this.turnMoved = turnMoved;
		this.startSquare = startSquare;
		this.team = team;
	}
	@Override
	public boolean move(Square end, MoveHandler mh) {
		if(end == null) return false;
		int[] dp = end.getPosition(); // destination position
		int[] cp = this.square.getPosition(); // current position
		if((dp[0] == cp[0]+1 || dp[0] == cp[0]-1) && dp[1] == cp[1]) { 
			// POTENTIAL ERROR IN @Condition.HasMove(), it will try to see if it has the move of enpassanting a pawn checking the king, but it will still render it as an inviable move due to the pawn not being removed from enpassant
			square.setDisplayPiece(true); // set it to true again to allow for the next piece to be shown
			int offsetY = this.getTeam() == 0 ? -1 : 1; // Move up the board if white, down if black.
			Square newEnd = mh.getSquare(dp[0], cp[1]+offsetY);
			newEnd.setPiece(square.getPiece()); // Will just reference to this object... could also replace with the this keyword.
			square.setPiece(null);
			end.setPiece(null);
			square = newEnd; // reassign the currentSquare to the finishing square.
		} else {
			square.setDisplayPiece(true); // set it to true again to allow for the next piece to be shown
			end.setPiece(square.getPiece()); // Will just reference to this object... could also replace with the this keyword.
			square.setPiece(null);
			square = end; // reassign the currentSquare to the finishing square.
		}
		if(turnMoved == -1) turnMoved = mh.getTurnNumber();
		return true;
	}
	
	
	@Override
	public ArrayList<Square> getLegalMoves(MoveHandler mh) {
		/**
		 * @param board - The board that the piece is on.
		 * This will get all the legal moves of the pawn piece.
		 * */
		ArrayList<Square> moves = new ArrayList<Square>();
		Square[][] squares = mh.getSquaresArray();
		int[] position = this.square.getPosition(); // specifying this in reference to the protected square Square value
		int team = this.getTeam();
		int turnNumber = mh.getTurnNumber();
		
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
				if(urp != null && urp.getTeam() != team) { // This if statement is mutually exclusive with enpassant because in a normal game a piece can never be behind a pawn on its first move
					moves.add(upRight);
				} else if(right != null) { // en passant
					Piece rp = right.getPiece();
					if(rp != null && rp.getTeam() != team && rp instanceof Pawn && ((Pawn) rp).turnMoved == turnNumber-1) { 
						moves.add(right);
					}
				}
			} 
			if(upLeft != null) {
				Piece ulp = upLeft.getPiece();
				if(ulp != null && ulp.getTeam() != team) {
					moves.add(upLeft);
				} else if(left != null) { // en passant
					Piece lp = left.getPiece();
					if(lp != null && lp.getTeam() != team && lp instanceof Pawn && ((Pawn) lp).turnMoved == turnNumber-1) {
						moves.add(left);
					}
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
			Square downRight = null, downLeft = null, left = null, right = null;
			if(position[0] + 1 <= 7) {
				right = squares[position[0] + 1][position[1]];
				downRight = squares[position[0] + 1][position[1] +1];
			}
			if(position[0] - 1 >= 0) {
				downLeft = squares[position[0] - 1][position[1] + 1];
				left = squares[position[0] - 1][position[1]];
			}
			if(downRight != null) {
				Piece drp = downRight.getPiece();
				if(drp != null && drp.getTeam() != team) {
					moves.add(downRight);
				} else if(right != null) {
					Piece rp = right.getPiece();
					if(rp != null && rp.getTeam() != team && rp instanceof Pawn && ((Pawn) rp).turnMoved == turnNumber-1) { 
						moves.add(right);
					}
				}
			} 
			if(downLeft != null) {
				Piece dlp = downLeft.getPiece();
				if(dlp != null && dlp.getTeam() != team) {
					moves.add(downLeft);
				} else if(left != null) {
					Piece lp = left.getPiece();
					if(lp != null && lp.getTeam() != team && lp instanceof Pawn && ((Pawn) lp).turnMoved == turnNumber-1) {
						moves.add(left);
					}
				}
			}
		}
		return moves;
	}

	@Override
	public float getValue() {
		// TODO Auto-generated method stub
		return 10.0f;
	}
	@Override
	public Pawn clone() {
		// TODO Auto-generated method stub
		return new Pawn(startSquare, team, turnMoved, isAlive);
	}

}

package engine.pieces;

import java.util.ArrayList;

import engine.components.Board;
import engine.components.Square;

public class Knight extends Piece {

	public Knight(Square startSquare, int color, String imgLocation) {
		super(startSquare, color, imgLocation);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<Square> getLegalMoves(Board board) {
		ArrayList<Square> moves = new ArrayList<Square>();
		Square[][] squares = board.getSquaresArray();
		int[] position = this.square.getPosition(); // specifying this in reference to the protected square Square value
		int x = position[0];
		int y = position[1];
		int team = this.getTeam();
		if(x + 2 <= 7 && y + 1 <= 7) {
			Square s = squares[x+2][y+1];
			if(s.getPiece() == null || s.getPiece().getTeam() != team) { 
				moves.add(s);
			}
		}
		if(x + 2 <= 7 && y - 1 >= 0) {
			Square s = squares[x+2][y-1];
			if(s.getPiece() == null || s.getPiece().getTeam() != team) { 
				moves.add(s);
			}
		}
		if(x - 2 >= 0 && y + 1 <= 7) {
			Square s = squares[x-2][y+1];
			if(s.getPiece() == null || s.getPiece().getTeam() != team) { 
				moves.add(s);
			}
		}
		if(x - 2 >= 0 && y - 1 >= 0) {
			Square s = squares[x-2][y-1];
			if(s.getPiece() == null || s.getPiece().getTeam() != team) { 
				moves.add(s);
			}
		}
		if(x + 1 <= 7 && y + 2 <= 7) {
			Square s = squares[x+1][y+2];
			
			if(s.getPiece() == null || s.getPiece().getTeam() != team) { 
				moves.add(s);
			}
		}
		if(x + 1 <= 7 && y - 2 >= 0) {
			Square s = squares[x+1][y-2];
			if(s.getPiece() == null || s.getPiece().getTeam() != team) { 
				moves.add(s);
			}
		}
		if(x - 1 >= 0 && y + 2 <= 7) {
			Square s = squares[x-1][y+2];
			if(s.getPiece() == null || s.getPiece().getTeam() != team) { 
				moves.add(s);
			}
		}
		if(x - 1 >= 0 && y - 2 >= 0) {
			Square s = squares[x-1][y-2];
			if(s.getPiece() == null || s.getPiece().getTeam() != team) { 
				moves.add(s);
			}
		}
		
		return moves;
	}

}

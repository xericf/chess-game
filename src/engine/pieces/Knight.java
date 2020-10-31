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
		int vectors[][] = {
				{2, 1},
				{2, -1},
				{-2, 1},
				{-2, -1},
				{1, 2},
				{1, -2},
				{-1, 2},
				{-1, -2},
		};

		for(int i = 0; i < vectors.length; i++) {
			int vx = vectors[i][0];
			int vy = vectors[i][1];
			
			if(!(x + vx > 7 || x + vx < 0 || y + vy > 7 || y + vy < 0)) {
				Square s = squares[x+vx][y+vy];
				if(s.getPiece() == null || s.getPiece().getTeam() != team) {
					// This works since s.getPiece() == null if it's true will go immediately into the body instead of running the next part of the if statement, which would throw a NullPointException
					moves.add(s);
				}
			}
		}
		
		return moves;
	}

}

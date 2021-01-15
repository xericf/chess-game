package engine.pieces;

import java.util.ArrayList;
import java.util.Arrays;

import engine.components.Board;
import engine.components.MoveHandler;
import engine.components.Square;

public class Bishop extends Piece {
	
	private int color;
	private Square startSquare;
	private float[] bishopOffsets = {
			// These are offsets for the valuations of the knight for certain key squares. Knights are better in the central 16 squares, hence they have an offset that makes it desirable
			0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
			0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
			0.0f, 0.0f, 0.5f, 0.5f, 0.5f, 0.5f, 0.0f, 0.0f,
			0.0f, 0.0f, 0.5f, 0.5f, 0.5f, 0.5f, 0.0f, 0.0f,
			0.0f, 0.0f, 0.5f, 0.5f, 0.5f, 0.5f, 0.0f, 0.0f,
			0.0f, 0.0f, 0.5f, 0.5f, 0.5f, 0.5f, 0.0f, 0.0f,
			0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
			0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
	};
	
	public Bishop(Square startSquare, int color, String imgLocation) {
		super(startSquare, color, imgLocation);
	}
	
	public Bishop(Square startSquare, int color, boolean isAlive) {
		super(startSquare, color, isAlive);
		this.startSquare = startSquare;
		this.color = color;
	}

	@Override
	public ArrayList<Square> getLegalMoves(MoveHandler mh) {
		ArrayList<Square> moves = new ArrayList<Square>();
		Square[][] squares = mh.getSquaresArray();
		int[] position = this.square.getPosition(); // specifying this in reference to the protected square Square value
		int x = position[0];
		int y = position[1];
		int team = this.getTeam();
		int vectorDistance[] = { // Corresponds to x,y vectors. Will be the distance each vector could possibly travel.
				// It's 0 indexed, that's why it's 7-x and 7-y for the distance. If there is no distance, it must be on the borders.
				Math.min(7-x, 7-y), // 1, 1
				Math.min(7-x, y), // 1, -1
				Math.min(x, 7-y), // -1, 1
				Math.min(x, y) // -1, -1
		};
		int vectors[][] = { // Will be used to preserve code so that we could simply use one for loop.
				{1,   1},
				{1,  -1},
				{-1,  1},
				{-1, -1}
		};
		for(int i = 0; i < vectors.length; i++) {
			for(int j = 0; j < vectorDistance[i]; j++) {
				Square s = squares[x + (vectors[i][0]*(j+1))][y + (vectors[i][1] * (j+1))]; // j+1 will equal the magnitude of the vector since vectorDistance will be 0 indexed.
				if(s.getPiece() == null) {
					moves.add(s);
				} else if (s.getPiece().getTeam() != team){
					moves.add(s); // if the piece is not on the same team, we can't capture the pieces behind it.
					break;
				} else {
					break; // if the piece is on the same team, we must break the loop.
				}
			}
		}
		
		return moves;
	}

	@Override
	public float getValue() {
		// TODO Auto-generated method stub
		return 30.0f;
	}

	@Override
	public Bishop clone() {
		// TODO Auto-generated method stub
		return new Bishop(startSquare, getTeam(), isAlive);
	}

}

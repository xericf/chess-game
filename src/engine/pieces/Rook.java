package engine.pieces;

import java.util.ArrayList;
import java.util.Arrays;

import engine.components.Board;
import engine.components.MoveHandler;
import engine.components.Square;

public class Rook extends Piece {
	
	public boolean hasMoved;
	private Square startSquare;
	private int color;
	
	public Rook(Square startSquare, int color, String imgLocation) {
		super(startSquare, color, imgLocation);
		hasMoved = false;
	}
	public Rook(Square startSquare, int color, boolean hasMoved, boolean isAlive) {
		super(startSquare, color, isAlive);
		this.hasMoved = hasMoved;
		this.startSquare = startSquare;
		this.color = color;
	}
	
	@Override
	public boolean move(Square end, MoveHandler mh) {
		/**
		 * We override this method to incorporate the special rule of chess where the king can't castle if the rook has moved already
		 * */
		if(end == null) return false;
		square.setDisplayPiece(true); // set it to true again to allow for the next piece to be shown
		end.setPiece(square.getPiece()); // Will just reference to this object... could also replace with the this keyword.
		square.setPiece(null);
		square = end; // reassign the currentSquare to the finishing square.
		hasMoved = true;
		return true;
	}
	
	@Override
	public ArrayList<Square> getLegalMoves(MoveHandler board) {
		ArrayList<Square> moves = new ArrayList<Square>();
		Square[][] squares = board.getSquaresArray();
		int[] position = this.square.getPosition(); // specifying this in reference to the protected square Square value
		int x = position[0];
		int y = position[1];
		int team = this.getTeam();
		int vectorDistance[] = { // Corresponds to x,y vectors. Will be the distance each vector could possibly travel.
				7-x, // 1, 0
				7-y, // 0, 1
				x, // -1, 0
				y // 0, -1
		};
		int vectors[][] = { // Will be used to preserve code so that we could simply use one for loop.
				{1,   0},
				{0,  1},
				{-1,  0},
				{0, -1}
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
		return 50.0f;
	}
	@Override
	public Rook clone() {
		// TODO Auto-generated method stub
		return new Rook(startSquare, getTeam(), hasMoved, isAlive);
	}

}

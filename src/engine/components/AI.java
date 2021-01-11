package engine.components;

import engine.pieces.Piece;

public class AI {

	private Board b;
	private Piece[] wp;
	private Piece[] bp;
	private Condition c;

	public AI(Piece[] wp, Piece[] bp, Board b, Condition c) {
		/**
		 * @Desc this class is for the chess AI. It uses the minimax algorithm with alpha-beta pruning to find the best moves.
		 * @param wp - White pieces
		 * */
		this.b = b;
		this.wp = wp;
		this.bp = bp;
		this.c = c;
	}
	
	public float evalPosition() {
		//Calculate 
		float wScore = 0f;
		float bScore = 0f;
		for(int i = 0; i < wp.length; i++) {
			if(wp[i].isAlive()) {
				wScore += wp[i].getValue(); // This getValue function that's abstract is extremely good because it makes things incredibly organized
			}
		}
		for(int i = 0; i < bp.length; i++) {
			if(bp[i].isAlive()) {
				bScore -= bp[i].getValue();
			}
		}
		return wScore + bScore;
	}
	
	public float minimax(int depth, float alpha, float beta, boolean maximizingPlayer) {
		if(depth == 0) {
			return 0f; // returns a static evaluation of the position without any more calculating
		}
		
		if (maximizingPlayer) { // is white if it's true
			float maxEval = -10000f;
		} else { // is black
			float minEval = 10000f;
		}
		
		return 0f;
	}
}

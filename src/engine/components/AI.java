package engine.components;

import java.util.ArrayList;

import engine.pieces.Piece;

public class AI {

	private MoveHandler mainHandler;


	public AI(MoveHandler mh) {
		/**
		 * @Desc this class is for the chess AI. It uses the minimax algorithm with alpha-beta pruning to find the best moves.
		 * @param wp - White pieces
		 * */
		this.mainHandler = mh;
	}
	
	public float evalPosition(MoveHandler currentPosition) {
		//Calculate 
		Piece[] wp = currentPosition.wp;
		Piece[] bp = currentPosition.bp;
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
	
	public float minimax(MoveHandler mh, int depth, float alpha, float beta, boolean maximizingPlayer) {
		if(depth == 0) {
			return evalPosition(mh); // returns a static evaluation of the position without any more calculating
		}

		if (maximizingPlayer) { // is white if it's true
			float maxEval = -10000f;
			Piece[] wp = mh.wp;
			for(int i = 0; i < wp.length; i++) {
				ArrayList<Square> legalMoves = wp[i].getLegalMoves(mh);
				int pos[] = wp[i].getSquare().getPosition();
				for(int j = 0; j < legalMoves.size(); j++) {
					MoveHandler newHandler = mh.clone();
					Square[][] squares = newHandler.getSquaresArray();
					newHandler.setAIMode(true);
					if(!newHandler.ComputerMove(squares[pos[0]][pos[1]], legalMoves.get(j))) continue;
					float curEval = minimax(newHandler, depth-1, 0, 0, !maximizingPlayer);
					if(maxEval < curEval) maxEval = curEval;
				}	
			}
			return maxEval;
		} else { // is black
			float minEval = 10000f;
			Piece[] bp = mh.bp;
			for(int i = 0; i < bp.length; i++) {
				ArrayList<Square> legalMoves = bp[i].getLegalMoves(mh);
				int pos[] = bp[i].getSquare().getPosition();
				for(int j = 0; j < legalMoves.size(); j++) {
					MoveHandler newHandler = mh.clone();
					Square[][] squares = newHandler.getSquaresArray();
					newHandler.setAIMode(true);
					if(!newHandler.ComputerMove(squares[pos[0]][pos[1]], legalMoves.get(j))) continue;
					float curEval = minimax(newHandler, depth-1, 0, 0, !maximizingPlayer);
					if(minEval > curEval) minEval = curEval;
				}	
			}
			return minEval;
		}
	}
}

package engine.components;

import java.util.ArrayList;

import engine.pieces.Piece;

public class AI {

	private MoveHandler mainHandler;

	public AI(MoveHandler mh) {
		/**
		 * @Desc this class is for the chess AI. It uses the minimax algorithm with alpha-beta pruning to find the best moves.
		 * */
		this.mainHandler = mh;
	}
	
	public void playBestMove(int depth, boolean isMaximizing) {
		int[] endSquarePos = null;
		int[] startSquarePos = null;
		if(isMaximizing) { // white
			Piece[] wp = mainHandler.wp;
			float maxEval = -10000f;
			for(int i = 0; i < wp.length; i++) {
				if(!wp[i].isAlive()) continue;
				ArrayList<Square> legalMoves = wp[i].getLegalMoves(mainHandler);
				int pos[] = wp[i].getSquare().getPosition();
				for(int j = 0; j < legalMoves.size(); j++) {
					MoveHandler newHandler = mainHandler.clone(); // Must clone so that we are playing different imaginary boards that don't relate to the main board
					Square[][] squares = newHandler.getSquaresArray(); // get the new square memory references
					int ePos[] = legalMoves.get(j).getPosition(); // must assign new squares to the new handler
					newHandler.setAIMode(true);

					if(!newHandler.ComputerMove(squares[pos[0]][pos[1]], squares[ePos[0]][ePos[1]])) continue;
					float curEval = minimax(newHandler, depth, -10000f, 10000f, !isMaximizing);
					if(curEval > maxEval) {
						maxEval = curEval;
						endSquarePos = ePos;
						startSquarePos = pos;
					}
				}
			}
		} else {
			Piece[] bp = mainHandler.bp;
			float minEval = 10000f;
			for(int i = 0; i < bp.length; i++) {
				if(!bp[i].isAlive()) continue;
				ArrayList<Square> legalMoves = bp[i].getLegalMoves(mainHandler);
				int pos[] = bp[i].getSquare().getPosition();
				for(int j = 0; j < legalMoves.size(); j++) {
					MoveHandler newHandler = mainHandler.clone(); // Must clone so that we are playing different imaginary boards that don't relate to the main board
					Square[][] squares = newHandler.getSquaresArray(); // get the new square memory references
					int ePos[] = legalMoves.get(j).getPosition(); // must assign new squares to the new handler
					newHandler.setAIMode(true);

					if(!newHandler.ComputerMove(squares[pos[0]][pos[1]], squares[ePos[0]][ePos[1]])) continue;
					float curEval = minimax(newHandler, depth, -10000f, 10000f, !isMaximizing);
					if(curEval < minEval) {
						minEval = curEval;
						endSquarePos = ePos;
						startSquarePos = pos;
					}
				}
			}
		}
		Square start = mainHandler.getSquare(startSquarePos[0], startSquarePos[1]);
		Square end = mainHandler.getSquare(endSquarePos[0], endSquarePos[1]);
		mainHandler.AttemptMove(start, end);
	}
	
	public float evalPosition(MoveHandler currentPosition) {
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
			float maxEval = -100000f;
			Piece[] wp = mh.wp;
			for(int i = 0; i < wp.length; i++) {
				if(!wp[i].isAlive()) continue;
				ArrayList<Square> legalMoves = wp[i].getLegalMoves(mh);
				int pos[] = wp[i].getSquare().getPosition();
				for(int j = 0; j < legalMoves.size(); j++) {
					MoveHandler newHandler = mh.clone(); // Must clone so that we are playing different imaginary boards that don't relate to the main board
					Square[][] squares = newHandler.getSquaresArray(); // get the new square memory references
					int ePos[] = legalMoves.get(j).getPosition(); // must assign new squares to the new handler
					newHandler.setAIMode(true);
					if(!newHandler.ComputerMove(squares[pos[0]][pos[1]], squares[ePos[0]][ePos[1]])) continue;
					float curEval = minimax(newHandler, depth-1, alpha, beta, !maximizingPlayer);
					maxEval = Math.max(curEval, maxEval);
					alpha = Math.max(curEval, alpha);
					if (beta <= alpha) {
						break;
					}
				}	
			}
			return maxEval;
		} else { // is black
			float minEval = 100000f;
			Piece[] bp = mh.bp;
			for(int i = 0; i < bp.length; i++) {
				if(!bp[i].isAlive()) continue;
				ArrayList<Square> legalMoves = bp[i].getLegalMoves(mh);
				int pos[] = bp[i].getSquare().getPosition();
				for(int j = 0; j < legalMoves.size(); j++) {
					MoveHandler newHandler = mh.clone();
					Square[][] squares = newHandler.getSquaresArray();
					int ePos[] = legalMoves.get(j).getPosition();
					newHandler.setAIMode(true);
					if(!newHandler.ComputerMove(squares[pos[0]][pos[1]], squares[ePos[0]][ePos[1]])) continue;
					float curEval = minimax(newHandler, depth-1, alpha, beta, !maximizingPlayer);
					beta = Math.min(curEval, beta);
					minEval = Math.min(curEval, minEval);
					if(beta <= alpha) {
						break;
					}
				}	
			}
			return minEval;
		}
	}
}

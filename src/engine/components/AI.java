package engine.components;

import engine.pieces.Piece;

public class AI {

	private Board b;
	private int depth = 4;
	private int pieceEval[];
	
	public AI(Board b) {
		this.b = b;
	}
	
	public void setDepth(int depth) {
		this.depth = depth;
	}
	
}

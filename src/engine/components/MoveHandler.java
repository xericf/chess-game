package engine.components;

import java.util.ArrayList;

import engine.pieces.King;
import engine.pieces.Piece;

public class MoveHandler {
	private Square[][] squares;
	private int turn;
	private boolean isChecked;
	private int turnNumber;
	
	public King wk;
	public King bk;
	public Piece[] bp;
	public Piece[] wp;
	
	public MoveHandler(Square[][] squares, int turn,int turnNumber, boolean isChecked,  King wk, King bk, Piece[] blackPieces, Piece[] whitePieces) {
		this.squares = squares;
		this.turn = turn;
		this.isChecked = false;
		this.turnNumber = turnNumber;
		
		this.wk = wk;
		this.bk = bk;
		this.bp = blackPieces;
		this.wp = whitePieces;

	}
	
	public MoveHandler clone() {
		Square newSquares[][] = squares.clone();
		Piece[] newBp = bp.clone();
		Piece[] newWp = wp.clone();
		King newBk = bk.clone();
		King newWk = wk.clone();
		return new MoveHandler(newSquares, turn, turnNumber, isChecked, newWk, newBk, newBp, newWp);
	}
	
	public boolean inCheck() {
		// I guess this function will only be used for the main mh and only used when the opponent checks the king?
		King k = turn == 0 ? wk : bk;
		Square ks = k.getSquare();
		Square[][] arr = squares;
		if(Condition.getKnightThreats(arr, ks, turn).size() != 0
				|| Condition.getDiagonalThreats(arr, ks, turn).size() != 0
				|| Condition.getStraightThreats(arr, ks, turn).size() != 0
				|| Condition.getPawnThreats(arr, ks , turn).size() != 0
				|| Condition.getKingThreats(arr, ks, turn).size() != 0) {
			return true;
		}
		return false;
	}
	
	public int checkWin() {
		/**
		 * @desc - checks whether or not the selected team is in checkmate OR stalemate based on their piece's legal moves.
		 * @returns: 0 = in progress game, 1 = checkmated king, 2 = stalemated
		 * */
		
		Piece[] fp = turn == 0 ? wp : bp; // acronym for "friendly pieces"

		for(int i = 0; i < fp.length; i++) {
			if(fp[i].isAlive() && HasMove(fp[i])) return 0; // has move therefore game is not done
		}
		if(!inCheck()) return 2; // if it's not in check, it's stalemate
		return 1; // checkmate
	}
	
	public void replacePiece(Piece oldPiece, Piece newPiece, int team) {
		/**
		 * @desc - extremely important function that will handle configuring the piece array to work with pawn promotions.
		 * @param oldPiece - piece that needs to be replaced in the array
		 * @param newPiece - piece that will replace the selected piece
		 * @param team - the team of the friendy piece
		 * */
		Piece[] fp = team == 0 ? wp : bp; // acronym for "friendly pieces"
		for(int i = 0; i < fp.length; i++) {
			if(oldPiece == fp[i]) { // Check to see if the memory reference matches the one that needs to be replaced.
				fp[i] = newPiece;
			}
		}
	}
	
	private boolean HasMove(Piece p) {
		Square start = p.getSquare();
		ArrayList<Square> legalMoves = p.getLegalMoves(this);
		if(legalMoves == null) return false;
		for(int i = 0; i < legalMoves.size(); i++) {
			Square end = legalMoves.get(i);
			Piece savedPiece = end.getPiece(); 
			p.move(end, this);
			if(inCheck()) { // Essentially this will reverse the move if the king is put in check or already in check from the move.
				end.setPiece(savedPiece);
				start.setPiece(p);
				p.setSquare(start); 
				if(savedPiece != null) savedPiece.setSquare(end); 
			} else {
				end.setPiece(savedPiece);
				start.setPiece(p);
				p.setSquare(start); 
				if(savedPiece != null) savedPiece.setSquare(end); 
				return true;
			}
		}
		return false;
	}
	
	public void incrementTurn() {
		turnNumber ++;
		turn = turn == 1 ? 0 : 1;
	}
	
	public int getTurn() {
		return this.turn;
	}
	
	public int getTurnNumber() {
		return this.turnNumber;
	}

	public Square[][] getSquaresArray() {
		// TODO Auto-generated method stub
		return squares;
	}
	
	public Square getSquare(int col, int row) {
		Square s = null;
		s = squares[col][row];
		return s;
	}
	
	public boolean isChecked() {
		return this.isChecked;
	}
	
	public Piece[] getPieceArray(int team) {
		Piece[] arr = team == 0 ? wp : bp;
		return arr;
	}
	
	public King getKing(int team) {
		return team == 0 ? wk : bk;
	}
	
}

package engine.components;

import java.util.ArrayList;
import java.util.Arrays;

import engine.pieces.Bishop;
import engine.pieces.King;
import engine.pieces.Knight;
import engine.pieces.Pawn;
import engine.pieces.Piece;
import engine.pieces.Queen;
import engine.pieces.Rook;

public class Condition {
	
	public King wk;
	public King bk;
	public Piece[] bp;
	public Piece[] wp;
	public Board board;
	
	public Condition(King wk, King bk, Piece[] blackPieces, Piece[] whitePieces, Board b) {
		this.wk = wk;
		this.bk = bk;
		this.bp = blackPieces;
		this.wp = whitePieces;
		this.board = b;
	}
	
	public Piece[] getPieceArray(int team) {
		Piece[] arr = team == 0 ? wp : bp;
		return arr;
	}
	
	
	public boolean inCheck(int team) {
		// I guess this function will only be used for the main board and only used when the opponent checks the king?
		King k = team == 0 ? wk : bk;
		Square ks = k.getSquare();
		Square[][] arr = board.getSquaresArray();
		if(Condition.getKnightThreats(arr, ks, team).size() != 0
				|| Condition.getDiagonalThreats(arr, ks, team).size() != 0
				|| Condition.getStraightThreats(arr, ks, team).size() != 0
				|| Condition.getPawnThreats(arr, ks ,team).size() != 0
				|| Condition.getKingThreats(arr, ks, team).size() != 0) {
			return true;
		}
		
		return false;
	}
	
	public int checkWin(int team) {
		/**
		 * @desc - checks whether or not the selected team is in checkmate OR stalemate based on their piece's legal moves.
		 * @returns: 0 = in progress game, 1 = checkmated king, 2 = stalemated
		 * */
		
		Piece[] fp = team == 0 ? wp : bp; // acronym for "friendly pieces"
		for(int i = 0; i < fp.length; i++) {
			if(fp[i].isAlive() && HasMove(fp[i], team)) return 0; // has move therefore game is not done
		}
		if(!inCheck(team)) return 2; // if it's not in check, it's stalemate
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
	
	private boolean HasMove(Piece p, int team) {
		Square start = p.getSquare();
		ArrayList<Square> legalMoves = p.getLegalMoves(board);
		if(legalMoves == null) return false;
		for(int i = 0; i < legalMoves.size(); i++) {
			Square end = legalMoves.get(i);
			Piece savedPiece = end.getPiece(); 
			p.move(end);
			if(inCheck(team)) { // Essentially this will reverse the move if the king is put in check or already in check from the move.
				end.setPiece(savedPiece);
				start.setPiece(p);
				p.setSquare(start); // IMPORTANT MUST SET THE SQUARES BACK OR ELSE IT THINKS ITS POSITIONS ARE DIFFERENT
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
	
	public static ArrayList<Square> getDiagonalThreats(Square[][] squares, Square currentSquare, int team){
		/**
		 * @description - Will return an ArrayList of threats that could come from the diagonal. Used to check for checkmate and castling rights. 
		 * @param b- will simply be the board
		 * @param currentSquare - the current square that could be threatened
		 * @param team - The team that is friendly, so threats must come from different team values.
		 * */
		
		ArrayList<Square> threats = new ArrayList<Square>();

		int[] position = currentSquare.getPosition(); // specifying this in reference to the protected square Square value
		int x = position[0];
		int y = position[1];
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
					continue;
				} 
				Piece p = s.getPiece();
				if (s.getPiece().getTeam() != team && (p instanceof Bishop || p instanceof Queen)){ // if it is either a queen or a bishop on the diagonal, it is considered a threat
					threats.add(s); // if the piece is not on the same team, we must break the loop and consider the threat.
					break;
				} else {
					break; // if it is on friendly team or not the correct piece.
				}
			}
		}
		
		return threats;
	}
	
	public static ArrayList<Square> getPawnThreats(Square[][] squares, Square currentSquare, int team){ 
		ArrayList<Square> threats = new ArrayList<Square>();
		
		int[] position = currentSquare.getPosition(); // specifying this in reference to the protected square Square value
		int x = position[0];
		int y = position[1];
		
		if(team == 0) { // white
			Square cs = x > 0 && y > 0 ? squares[x-1][y-1] : null;
			if(cs != null &&  cs.getPiece() != null && cs.getPiece() instanceof Pawn && cs.getPiece().getTeam() != team) {
				threats.add(cs);
			}
			cs = x < 7 && y > 0 ? squares[x+1][y-1] : null;
			if(cs != null  &&  cs.getPiece() != null && cs.getPiece() instanceof Pawn && cs.getPiece().getTeam() != team) {
				threats.add(cs);
			}
		} else if(team == 1) { // black
			Square cs = x > 0 && y < 7 ? squares[x-1][y+1] : null;
			if(cs != null && cs.getPiece() != null && cs.getPiece() instanceof Pawn && cs.getPiece().getTeam() != team) {
				threats.add(cs);
			}
			cs = x < 7 && y < 7 ? squares[x+1][y+1] : null;
			if(cs != null  && cs.getPiece() != null && cs.getPiece() instanceof Pawn && cs.getPiece().getTeam() != team) {
				threats.add(cs);
			}
		}
		
		return threats;
	}
	
	public static ArrayList<Square> getKingThreats(Square[][] squares, Square currentSquare, int team) {
		ArrayList<Square> threats = new ArrayList<Square>();
		int[] position = currentSquare.getPosition(); // specifying this in reference to the protected square Square value
		int x = position[0];
		int y = position[1];
		
		for(int i = -1; i <= 1; i++) {
			for(int j = -1; j <= 1; j++) {
				try {
					Square s = squares[x+i][y+j];
					if(s != currentSquare && s.getPiece() != null && s.getPiece().getTeam() != team && s.getPiece() instanceof King) { // need to check if it's not this.square to get rid of its current square
						threats.add(s);
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					continue; // Simply continue if it is out of bounds onto the next possible move
				}
			}
		}
		
		return threats;
	}
	
	public static ArrayList<Square> getStraightThreats(Square[][] squares, Square currentSquare, int team){
		/**
		 * @description - Will return an ArrayList of threats that could come from straight lines. Used to check for checkmate and castling rights. 
		 * @param b- will simply be the board
		 * @param currentSquare - the current square that could be threatened
		 * @param team - The team that is friendly, so threats must come from different team values.
		 * */
		ArrayList<Square> threats = new ArrayList<Square>();

		int[] position = currentSquare.getPosition(); // specifying this in reference to the protected square Square value
		int x = position[0];
		int y = position[1];
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
					continue;
				}
				Piece p = s.getPiece();
				if (p.getTeam() != team && (p instanceof Rook || p instanceof Queen)){
					threats.add(s); // if the piece is not on the same team, we can't capture the pieces behind it.
					break;
				} else {
					break; // if it is on friendly team or not the correct piece
				}
			}
		}
		return threats;
	}
	
	public static ArrayList<Square> getKnightThreats(Square[][] squares, Square currentSquare, int team){
		ArrayList<Square> threats = new ArrayList<Square>();
		int[] position = currentSquare.getPosition(); // specifying this in reference to the protected square Square value
		int x = position[0];
		int y = position[1];
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
				Piece p = squares[x+vx][y+vy].getPiece();
				if(p != null && p.getTeam() != team && p instanceof Knight) {
					threats.add(squares[x+vx][y+vy]);
				}
			}
		}
		
		return threats;
	}
	
	public static boolean PinnedPiece(Square kingSquare, Square pieceSquare) {
		/**
		 * @description - This function will check to see if a piece is being pinned on a friendly king.
		 * @param kingSquare - The square the FRIENDLY king currently resides on.
		 * @param pieceSquare - The square the selected piece resides on.
		 * */
		return false;
	}
}

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
	
	public static ArrayList<Square> getDiagonalThreats(Square[][] squares, Square currentSquare, int team){
		/**
		 * @description - Will return an ArrayList of threats that could come from the diagonal. Used to check for checkmate and castling rights. 
		 * @param b- will simply be the mh
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
		 * @param b- will simply be the MoveHandler
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
}

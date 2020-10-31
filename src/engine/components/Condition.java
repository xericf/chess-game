package engine.components;

import java.util.ArrayList;

import engine.pieces.Bishop;
import engine.pieces.Knight;
import engine.pieces.Piece;
import engine.pieces.Queen;
import engine.pieces.Rook;

public class Condition {

	
	public static ArrayList<Square> getDiagonalThreats(Board b, Square currentSquare, int team){
		/**
		 * @description - Will return an ArrayList of threats that could come from the diagonal. Used to check for checkmate and castling rights. 
		 * @param b- will simply be the board
		 * @param currentSquare - the current square that could be threatened
		 * @param team - The team that is friendly, so threats must come from different team values.
		 * */
		
		ArrayList<Square> threats = new ArrayList<Square>();
		Square[][] squares = b.getSquaresArray();
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
					break; // if the piece is on the same team, we must break the loop.
				}
			}
		}
		
		return threats;
	}
	
	public static ArrayList<Square> getStraightThreats(Board b, Square currentSquare, int team){
		/**
		 * @description - Will return an ArrayList of threats that could come from straight lines. Used to check for checkmate and castling rights. 
		 * @param b- will simply be the board
		 * @param currentSquare - the current square that could be threatened
		 * @param team - The team that is friendly, so threats must come from different team values.
		 * */
		ArrayList<Square> threats = new ArrayList<Square>();
		Square[][] squares = b.getSquaresArray();
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
					break; // if the piece is on the same team, we must break the loop.
				}
			}
		}
		return threats;
	}
	
	public static ArrayList<Square> getKnightThreats(Board b, Square currentSquare, int team){
		ArrayList<Square> threats = new ArrayList<Square>();
		Square[][] squares = b.getSquaresArray();
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
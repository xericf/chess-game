package engine.components;

import java.security.SecureRandom;

import engine.pieces.Piece;

public class Zobrist {
	
	public static long[][][] zNumbers = new long[2][6][64]; // This symbolizes a 3d array with a unique id for each square that has a certain type of piece and piece color on it.
	public static long[] zCastle = new long[4]; // this array symbolizes the direction that any particular king castled in. 
	public static long zBlackMove; // This is used if it is black's move.
	public static long rng64() {
		/**
		 * @desc - generates a random long number
		 * @returns - a random number that is 64 bit (long) 
		 * **/
		SecureRandom random = new SecureRandom(); // This is better than Math.random() as it distributes numbers more evenly and makes it seem more random
		return random.nextLong();
	}
	
	public static void fillZNumbers() {
		/**
		 * @desc this function fills the zNumbers array with a unique id depending on the square, piecetype, and color of the piece.
		 * */
		//color of the piece
		for(int color = 0; color < 2; color++) {
			// Piece type {0 = Pawn, 1 = Rook, 2 = Knight, 3 = bishop, 4 = Queen, 5 = King}
			for(int pieceType = 0; pieceType < 6; pieceType++) {
				for(int square = 0; square < 64; square++) {
					zNumbers[color][pieceType][square] = rng64();
				}
			}
		}
		for(int castleType = 0; castleType < 4; castleType++) {
			zCastle[castleType] = rng64();
		}
		zBlackMove = rng64();
	}
	
	public static long genZHash(Piece[] whitePieces, Piece[] blackPieces) {
		/**
		 * @desc generates a Zobrist hash for a unique chess position.
		 * */
		long zKey = 0;
		
		for(int i = 0; i < whitePieces.length; i++) {
			int[] xy = whitePieces[i].getSquare().getPosition();
			
		}
		
		return zKey;
	}
}

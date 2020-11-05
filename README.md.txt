This is a chess game for my grade 12 computer science HL IA

Notable topics to talk about:
- Abstract class making the efficiency for the code better.

references:
https://iconscout.com/contributors/jemismali/icons

TODO:
- Reduce the amount of repaints there are using logic.
- INCLUDE KING AND PAWN THREATS IN CONDITION CLASS.
- In the condition class methods for checking the threats of a square, the loop will not break if it sees a teamed piece first, this is to provide a really easy way to check if a 
piece is pinned to the king, simply run all of the methods onto the king, and then reverse the vectors for each of the found threats
and figure out if a piece lies between the king and a threat. If it is, deny moving the piece IN THE WRONG DIRECTION. You could potentially
do this by making a testMove function, which will see if the threat kills the king after an attempted move.

Remember, no legal moves on king and it is in checvk means checkmate, if it is not in check then it is stalemate.

The way the castling will be set up is that it will only consider blocking checks from the queen and bishop in the diagonalThreats function, however
the enemy king could also block castling. So maybe later implement that. Also pawns need to be considered.

Checking for stalemate should be easy, just check each piece's legal moves each turn end.
For checkmate, Check legal moves of each piece and see if it covers all of the legal moves of the king, then see if there could be any blocks or captures that could prevent mate.

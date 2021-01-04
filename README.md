This is a chess game for my grade 12 computer science HL IA

Example Outputs:

https://gyazo.com/437a1444f3512c067f1db3506d1f7d84
https://gyazo.com/2f1268d28d68d087d01106f67b011242
https://gyazo.com/49ec3139bbb1a7cca58c40c68f7588d3

Notable topics to talk about:
- Abstract class making the efficiency for the code better.

references:
https://iconscout.com/contributors/jemismali/icons
https://www.youtube.com/watch?v=gyLCFfrLGIM&ab_channel=LogicCrazyChess
https://www.freecodecamp.org/news/simple-chess-ai-step-by-step-1d55a9266977/


TODO:
- Reduce the amount of repaints there are using logic.
Remember, no legal moves on king and it is in chevk means checkmate, if it is not in check then it is stalemate.

Checking for stalemate should be easy, just check each piece's legal moves each turn end.
For checkmate, Check legal moves of each piece and see if it covers all of the legal moves of the king, then see if there could be any blocks or captures that could prevent mate.

-Probably need to remove DisplayPiece function because it's a bit useless and may be expensive for the AI (Unless i just don't use DisplayPiece in AI since it doesn't need to render until it moves...)
-Absolutely need to remove the use of the try..catch function in the King because it will be very expensive when making the AI.
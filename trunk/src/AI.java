import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;


public class AI {
	/*		 GARBAGE
	  
	 public enum strategy{safe,aggresive,defensive,suicide};

	private ListIterator<Move> itMove;
	private ListIterator<Piece> itPiece;
	
	private Piece.color computerPlayer; 
	private strategy stratego;
	private int pileHigh;
	private ArrayList[] pile;
	private Move bestMove;
	
	private Piece[][] baseBoard;
	private Piece[][] testBoard;
	
	private ArrayList<Piece> baseBlackPiece;
	private ArrayList<Piece> baseWhitePiece;
	
	private ArrayList<Piece> testBlackPiece;
	private ArrayList<Piece> testWhitePiece;

	
	AI(Piece.color color,strategy stratego,int pileHigh)
	{
		this.computerPlayer = color;
		this.stratego = stratego;
		this.pileHigh = pileHigh-1;
		
		//Pile of ArrayList
		pile = new ArrayList[pileHigh]; 
		
		//Fill the Array with ArrayList
		for(int i=0;i < pile.length;i++)
		{
			pile[i] = new ArrayList<Move>();
		}
		
		baseBoard = new Piece[8][8];
		testBoard = new Piece[8][8];

	}
	
	public Move computerMove(Piece[][] chessBoard)
	{		
		
		for(int k=0;k<pileHigh;k++)
		{
			
		}
		
		return bestMove;
	}


	
	


	public void buildLists(Piece chessBoard[][])
	{
		Piece.color colorFlag;
		
		//Clear PieceList/MoveList
		whiteMoveList.removeAll(whiteMoveList);
		blackMoveList.removeAll(blackMoveList);
		whitePieceList.removeAll(whitePieceList);
		blackPieceList.removeAll(blackPieceList);

		
		//Cycle
		//Move Through Board
		for (int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				//Skip Null Tiles
				if (chessBoard[i][j] == null)
				{
					continue;
				}
				
				//Set ColorFlag
				if (chessBoard[i][j].getColor() == Piece.color.white)
				{
					colorFlag = Piece.color.white;
				}
				else
				{
					colorFlag = Piece.color.black;
				}
				
				
				addPieceList(chessBoard[i][j],colorFlag);
				addMoveList(chessBoard[i][j],chessBoard, colorFlag);
				
				
			}
		}
		
	}
	
	public void addPieceList(Piece p, Piece.color color)
	{

		//Add Piece To List
		if (color == Piece.color.white)
		{
			whitePieceList.add(p);
		}
		else
		{
			blackPieceList.add(p);

		}
		
	}
	
	public void addMoveList(Piece p, Piece chessBoard[][], Piece.color color)
	{
		tempMoveList.removeAll(tempMoveList);
		
		p.genMoves(chessBoard);
		
		tempMoveList = p.g
		
		//Go Through Move List of Piece
		itMove = tempMoveList.listIterator();
		while (itMove.hasNext())
		{
			Move m = itMove.next();
			if(color == Piece.color.white)
			{
				whiteMoveList.add(m);

			}
			else
			{
				blackMoveList.add(m);

			}
		}
		
		
	}
	
	//Based on the chessBoard Layout, calculate the strengths of piece positions
	public void analyzeScore()
	{
			//Kill - 
			//Safe*
			//PossibleKill
			//Protect*
		
		//Go through All White Pieces
		itPiece = whitePieceList.listIterator();
		while (itPiece.hasNext())
		{
			Piece p = itPiece.next();
			protectScore(p,computerPlayer);
			underAttackScore(p,computerPlayer);
			//possibleKill(p,computerPlayer);
		}
		
		//Go through All Black Pieces
		itPiece = blackPieceList.listIterator();
		while (itPiece.hasNext())
		{
			Piece p = itPiece.next();
			protectScore(p,computerPlayer);
			underAttackScore(p,computerPlayer);
			//possibleKill(p,computerPlayer);
		}
		
		
	}
	
	public int killScore()
	{
		return 0;
	}
	
	public void underAttackScore(Piece p, Piece.color computerPlayer)
	{
		//Decide the attacking pieces
		if (computerPlayer == Piece.color.white)
			itMove = blackMoveList.listIterator();
		else
			itMove = whiteMoveList.listIterator();
		
		//Go through all moves
		while (itMove.hasNext())
		{
			Move m = itMove.next();
			
			//Opponent piece can move kill mine
			if(m.getDestR() == p.getSrcR() && m.getDestC() == p.getSrcC())
			{
				//underAttackMatrix(p.getName(),m.getName(),p.getColor());
			}
		}

	}
	
	public void underAttackMatrix(Piece.name pieceName, Piece.name moveName,Piece.color color)
	{
		int score = 0; //temp hold score
		score = 1;
		//Add to protect score to correct color
		if (color == Piece.color.white)
		{
			whiteScoreUnderAttack += score; 
		}
		else
		{
			blackScoreUnderAttack += score; 
		}
	}
	
	//From position, can attack other pieces
	public void possibleKillScore(Piece p, Piece.color computerPlayer)
	{
		
		//Find computer color, to use its move list
		if (computerPlayer == Piece.color.white)
			itMove = whiteMoveList.listIterator();
		else
			itMove = blackMoveList.listIterator();
		
		//Go through all moves 
		while (itMove.hasNext())
		{
			Move m = itMove.next();
		}
		


	}
	
	//Is guarding other pieces.
	public void protectScore(Piece p, Piece.color computerPlayer)
	{
		//Decide the protecting pieces
		if (p.getColor() == Piece.color.white)
			itMove = whiteMoveList.listIterator();
		else
			itMove = blackMoveList.listIterator();
		
		//Go through all moves 
		while (itMove.hasNext())
		{
			Move m = itMove.next();
			
			//If ANother Piece Move to My Piece i.e Move can protect Piece
			if(m.getDestR() == p.getSrcR() && m.getDestC() == p.getSrcC())
			{
				//Find piece protect score from matrix
				protectMatrix(p.getName(),m.getName(),p.getColor());
			}
			
		}
	}
	
	//Values If One Piece Protects Another
	public void protectMatrix( Piece.name pieceName, Piece.name moveName,Piece.color color)
	{	
		int score = 0; //hold score temporary
		
		//Find which piece doing protect
		if (moveName == Piece.name.pawn)
			score += 2; //Pawns protect worth 2 points
		else if (moveName == Piece.name.king)
			score += 0; //King protect worth 0 points
		else
			score += 1; //All other piece 1 point
		
		//Add to protect score of the correct color
		if (color == Piece.color.white)
			whiteScoreProtect += score; 
		else
			blackScoreProtect += score; 
	}	
*/	
}

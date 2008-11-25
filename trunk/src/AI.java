import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;


public class AI {
	
	public enum Strategy{Bastille,GenghisKhan,OptimisticHunter,Necrophobic,Territorial,Illusive};
					//   Protect -   Kill   -   PossibleKill  -  Safe   -   Position  - Escape
	
	private Piece.Color computerColor;
	private int IQ;  //How many moves to think through
	private Strategy strategy;
	private Matrix matrix;
	
	AI(Strategy strategy,int IQ)
	{
		this.computerColor = Piece.Color.black;
		this.matrix = new Matrix();
		this.strategy = strategy;
		this.IQ = IQ;
	}
	
	
	public void makeMove(Game g,Board b)
	{
/*
		ArrayList<Piece> pieceList;
		ArrayList<Move> moveList;
		Iterator<Piece> itPiece;
		Iterator<Move> itMove;
		Board baseBoard;
		Board testBoard;
		Piece p;
		Move m;
		Move bestMove;
		Score s;

		
		//Copy original board
		baseBoard = new Board(b);
		
		//Set move score to 0
		bestMove= new Move();
		bestMove.setScore(s = new Score());
		
		//Get Computer Player Pieces
		pieceList = baseBoard.getPieceList(computerColor);
		
		//Go through each piece
		itPiece = pieceList.listIterator();
		while (itPiece.hasNext())
		{
			 p = itPiece.next();
			 
			 	//Get the piece's movelist
			 	moveList = p.genMoves(baseBoard);
			 	
			 	//Go through each move
				itMove = moveList.listIterator();
				while (itMove.hasNext())
				{
					m = itMove.next();
					
					//Make a copy of base Board
					testBoard = new Board(baseBoard);
					
					testBoard.makeMove(m);
					
					//Get the score of board with move
					s = this.getBoardScore(b,testBoard,m);
					
					//Find the best score of this move or best move
					if(s.isBetter(bestMove.getScore(), strategy))
					{
						//Set new best move 
						bestMove = m;
						
						//Update best move score
						bestMove.setScore(s);
					}
					
				}//Move loop	 
		} //Piece loop
		
		return bestMove; //Return or just make it?
*/
		Move m = this.randomMove(b,g);
		
		g.click(m.getSrcR(), m.getSrcC());
		g.click(m.getDestR(), m.getDestC());
	
	}
	
	public Move randomMove(Board b,Game g)
	{
		ArrayList<Move> moveList = new ArrayList<Move>();
		ArrayList<Piece> pieceList;
		Iterator<Piece> itPiece;
		Piece p;
		Move m;
		int numMoves,moveNum;
		
		pieceList = b.getPieceList(computerColor);
		
		itPiece = pieceList.listIterator();
	
		while (itPiece.hasNext())
		{
			p = itPiece.next();
			moveList.addAll(p.genMoves(b));
		}
		
		numMoves = moveList.size(); //Draw -  = 0
		
		while(true)
		{
			int srcR,srcC,destR,destC;
			
			moveNum = (int)(Math.random() * numMoves);
			
			m = moveList.get(moveNum);
			
			
			if (g.validateSrc(m,b))
			{
				if(g.validateDest(m,b))
					break;
			}
			
		}

		
		return m;
	}
	
	public Score getBoardScore(Board b,Board B,Move m)
	{
		Score s;
		int protectScore=0,killScore=0,possibleKillScore=0,dieScore=0,positionScore=0,escapeScore=0;
			
		protectScore = this.getProtectScore(B);
		killScore = this.getKillScore(b,m);
/*		possibleKillScore = this.possibleKillScore();
		dieScore = this.getDieScore();
		positionScore = this.getPositionScore();
		escapeScore = this.getEscapeScore();
*/
		
		s = new Score(protectScore,killScore,possibleKillScore,dieScore,positionScore,escapeScore);
		
		return s;
	}
	
	public int getKillScore(Board b, Move m)
	{
		int srcR,srcC,destR,destC;
		Piece p1,p2;
		Piece.Name n1,n2;
		int killValue;
		
		srcR = m.getSrcR();
		srcC = m.getSrcC();
		destR = m.getDestR();
		destC = m.getDestC();
				
		if(b.getPiece(destR, destC) != null)
		{
			p1 = b.getPiece(srcR, srcC);
			p2 = b.getPiece(destR, destC);
			n1 = p1.getName();
			n2 = p2.getName();
	
			killValue = matrix.getKillValue(n1,n2 );
			
			return killValue;
		}
		
		return 0;
	}
	
	public int getProtectScore(Board b)
	{
		//Get protect score each color and subtract computer - enemy
		
		ArrayList<Piece> pieceList1,pieceList2;
		Iterator<Piece> itPiece1,itPiece2;
		Piece p1,p2;
		int protectScore=0,whiteScore=0,blackScore=0;
		Piece.Color currentColor;
		
		//Go through the white then black pieces
		for(int i=0;i<2;i++)
		{
			//Decide if using black or white pieces, and initialize score
			if(i==0)
				currentColor = Piece.Color.white;
			else
				currentColor = Piece.Color.black;
		
			//Zero out temp score
			protectScore =0;
		
			//Get 2 piece list based on currentColor
			pieceList1 = b.getPieceList(currentColor);
			pieceList2 = b.getPieceList(currentColor);
			
			//Setup two iterators through list
			itPiece1 = pieceList1.listIterator();
			itPiece2 = pieceList2.listIterator();
		
			//Go through each piece to every other piece of the same color
			while (itPiece1.hasNext())
			{
				p1 = itPiece1.next();
				
				while (itPiece2.hasNext())
				{
					p2 = itPiece2.next();
					 
					//Protect if piece1 legally move to piece2's source
					if(p1.legalMove(p2.getSrcR(), p2.getSrcC(), b))
					{
						//Add to protectScore the value from score matrix
						protectScore += matrix.getProtectValue(p1.getName(), p2.getName());
					}
					
				}//Piece2 loop
			}//Piece1 loop
		
			//Add to correct color score
			if(i==0)	
				whiteScore = protectScore;
			else
				blackScore = protectScore;
			
		} //for
		
		//Find the different between computer player score and enemy
		if(computerColor == Piece.Color.white)
			protectScore = whiteScore - blackScore;
		else
			protectScore = blackScore - whiteScore;
		
		return protectScore;
	}
	
	
	
	
	
	
	
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

import java.util.ArrayList;
import java.util.Iterator;

final public class King extends Piece
{

	
	public King(Piece.Color color, int srcR,int srcC)
	{
		super(color,srcR,srcC);
		
		this.name = Name.king;
	}
	
	public King(Piece p)
	{
		this(p.getColor(),p.getSrcR(),p.getSrcC());
	}
	
	public  boolean legalMove(int destR, int destC, Board b)
	{
		Piece p;
		King k;
		Move m;
		
		int testR = srcR;
		int testC = srcC;
		
		// False If Step Is More Than Two
		if (Math.abs(srcR - destR) > 1 || Math.abs(srcC - destC) > 1)
		{
			return false;
		}
		
		while(true)
		{
			//West
			if(srcR == destR && srcC > destC)
			{
				testC--;
			}
			
			//East
			else if(srcR == destR && srcC < destC)
			{
				testC++;
			}
			
			//North
			else if(srcR > destR && srcC == destC)
			{
				testR--;
			}
			
			//South
			else if(srcR < destR && srcC == destC)
			{
				testR++;
			}
				
			else if (srcR > destR && srcC < destC) //NorthEast
			{
				testR--;
				testC++;
			}
			
			else if (srcR > destR && srcC > destC) //NorthWest
			{
				testR--;
				testC--;
			}
			
			else if (srcR < destR && srcC > destC) //SouthWest
			{
				testR++;
				testC--;
			}
			
			else if (srcR < destR && srcC < destC) //SouthEast
			{
				testR++;
				testC++;
			}
			
			//If Fell Off Board Try Next Direction
			if( !(testR < 8 && testR > -1 && testC < 8 && testC > -1) )
			{
				return false;
			}
			
			
			//Copy board
			Board B = new Board(b);
			
			//Create new move
			m = new Move(srcR,srcC,testR,testC);
			
			//Make move on copy board
			B.makeMove(m);
			
			//Get King 
			k = B.getKing(color);
			
			//In check?
			if(k.inCheck(B))
			{
				break;
			}
			
			
			//Square Occupied
			if ( (p = b.getPiece(testR, testC)) != null)
			{	
				//If Piece Has My Color
				if (p.getColor() == color)
				{
					return false;
				}
				
				//If piece not my color 
				if (p.getColor() != color )
				{
					//Not reach destination
					if( !(testR == destR && testC == destC))
						return false;
					
					//Reached destination
					if( testR == destR && testC == destC)
						return true;
				}
			}
			
			//Reach destination
			if(testR == destR && testC == destC)
			{
				return true;
			}
		}
		
		return false;

	}
	
	public ArrayList<Move> genMoves(Board b)
	{
		ArrayList<Move> moveList = new ArrayList<Move>();
		Piece p;
		King k;
		Move m;
		int direction =0;
		
		while(direction <8)
		{
			int testR = srcR;
			int testC = srcC;
			int i=0;
			while(i < 1)
			{
				if (direction == 0) //North
				{
					testR--;
				}
				else if (direction == 1) //South
				{
					testR++;
				}
				else if (direction == 2) //East
				{
					testC++;
				}
				else if (direction == 3) //West
				{
					testC--;
				}
				else if (direction == 4) //NorthEast
				{
					testR--;
					testC++;
				}
				else if (direction == 5) //NorthWest
				{
					testR--;
					testC--;
				}
				else if (direction == 6) //SouthWest
				{
					testR++;
					testC--;
				}
				else if (direction == 7) //SouthEast
				{
					testR++;
					testC++;
				}
				else
				{
					break;
				}
				
				//If Fell Off Board Try Next Direction
				if( !(testR < 8 && testR > -1 && testC < 8 && testC > -1) )
				{
					break;
				}
				
			
				//Copy board
				Board B = new Board(b);
				
				//Create new move
				m = new Move(srcR,srcC,testR,testC);
				
				//Make move on copy board
				B.makeMove(m);
				
				//Get King 
				k = B.getKing(color);
				
				//In check?
				if(k.inCheck(B))
				{
					break;
				}
		
				
				//Square Occupied
				if ( (p = b.getPiece(testR, testC)) != null)
				{
					//If Piece Has My Color
					if (p.getColor() == color)
					{
						break;
					}
					
					//If Piece Not My Color
					if (p.getColor() != color)
					{
						moveList.add(m);
						break;
					}
				}
				else //Square empty
				{
					moveList.add(m);
				}
				
				i++;
				
			}
			
			direction++;
		}
		
		return moveList;
	}
	

	
	public boolean inCheck(Board b)
	{
		ArrayList<Piece> pieceList;
		Piece enemy;
		Iterator<Piece> itPiece; 
		Piece.Color enemyColor;
		
		if(color == Piece.Color.white)
			enemyColor = Piece.Color.black;
		else
			enemyColor = Piece.Color.white;

		
		//Get enemy piece list
		pieceList = b.getPieceList(enemyColor);

		//See if any piece can legally capture 
	    itPiece = pieceList.listIterator();
		while (itPiece.hasNext())
		{
			 enemy = itPiece.next();
			 
			//If the enemy piece can legal move to king
			if( enemy.legalMove(srcR, srcC, b))
				return true; 
		}
		
		return false;	
	}
	
	public boolean inCheckMate(Board b)
	{
		ArrayList<Move> moveList;
		ArrayList<Piece> fellowPieces;
		
		Iterator<Piece> itPiece;
		Iterator<Move> itMove;
		
		Move m;
		Piece p;
		King king;
		Board B;
		
		//In check
		if (this.inCheck(b))
		{
			//Generate king move list
			moveList = this.genMoves(b);
			
			//King has no moves - Then Check If Block
			if(moveList.isEmpty())
			{
				//Get all friendly pieces
				fellowPieces = b.getPieceList(color);
				
				//Check the moves of each fellow piece, to see if it removes check
				itPiece = fellowPieces.listIterator();
				while (itPiece.hasNext())
				{
					p = itPiece.next();
					
					//Get moves of this piece
					moveList = p.genMoves(b);
					
					//Try the move to see if it removes check
					itMove = moveList.listIterator();
					while(itMove.hasNext())
					{
						m = itMove.next();
						
						//Copy board
						B = new Board(b);
						
						//Make the move
						B.makeMove(m);
						
						//Get the King
						king = B.getKing(color);
						
						//If king still in check, try another block
						if(king.inCheck(B))
							continue;
						else //Out of check = no check mate
							return false;
						
					} //Move Loop
				} //Piece Loop
				return true;
			}
			
			return false;
		}
		
		return false;
	}

}
import java.util.ArrayList;

final public class Rook extends Piece
{

	public Rook(Piece.Color color, int srcR,int srcC)
	{
		super(color,srcR,srcC);
		
		name = Name.rook;
	}
	
	public Rook(Piece p)
	{
		this(p.getColor(),p.getSrcR(),p.getSrcC());
	}
	
	public  boolean legalMove(int destR, int destC, Board b)
	{
		Piece p;
		int testR = srcR;
		int testC = srcC;

		while(true)
		{
			
			//North Move
			if(srcR > destR && srcC == destC)
			{
				testR--;
									
			}
			//South Move
			else if (srcR < destR && srcC == destC)
			{
				testR++;
				
			}
			//West Move
			else if (srcR == destR && srcC > destC)
			{
				testC--;
			}
			//East Move
			else if (srcR ==destR && srcC < destC)
			{
				testC++;
			}
			
			//If Fell Off Board Try Next Direction
			if( !(testR < 8 && testR > -1 && testC < 8 && testC > -1) )
			{
				return false;
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
	}
	
	public ArrayList<Move> genMoves(Board b)
	{

		ArrayList<Move> moveList = new ArrayList<Move>();
		Piece p;
		Move m;
		
		int direction =0;
		
		while(direction <4)
		{
			int testR = srcR;
			int testC = srcC;
			
			while(true)
			{
				if (direction == 0) //North
				{
					testR--;
				}
				else if (direction == 1) //South
				{
					testR++;
				}
				else if (direction == 2) //West
				{
					testC--;
				}
				else if (direction == 3) //East
				{
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
				
				//Square Occupied
				if ((p = b.getPiece(testR, testC)) != null)
				{
					//If Piece Has My Color
					if (p.getColor() == color)
					{
						break;
					}
					
					//If Piece Not My Color
					if (p.getColor() != color)
					{
						m = new Move(srcR,srcC,testR,testC);
						moveList.add(m);				
						break;
					}
				}
				else //Square empty
				{
					m = new Move(srcR,srcC,testR,testC);
					moveList.add(m);			
				}
		
			}
			
			direction++;
		}

		return moveList;
	}
}
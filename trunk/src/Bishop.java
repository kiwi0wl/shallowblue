import java.util.ArrayList;

final public class Bishop extends Piece
{
	public Bishop(Piece.Color color, int srcR, int srcC,Piece.Side side)
	{
		super(color,srcR,srcC);
		this.name = Name.bishop;	
		this.side = side;

	}
	
	public Bishop(Piece p)
	{
		this(p.getColor(),p.getSrcR(),p.getSrcC(),p.getSide());
	}
	
	public  boolean legalMove(int destR, int destC, Board b)
	{
			Piece p;
			int testR = srcR;
			int testC = srcC;
			
			while(true)
			{
				//North/South/East/West
				if(srcR == destR && srcC == destC)
				{
					return false;
				}
					
				if (srcR > destR && srcC < destC) //NorthEast
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

		int direction =0;
		
		while(direction <4)
		{
			int testR = srcR;
			int testC = srcC;
			
			while(true)
			{
				if (direction == 0) //NorthEast
				{
					testR--;
					testC++;
				}
				else if (direction == 1) //NorthWest
				{
					testR--;
					testC--;
				}
				else if (direction == 2) //SouthWest
				{
					testR++;
					testC--;
				}
				else if (direction == 3) //SouthEast
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
						Move m = new Move(srcR,srcC,testR,testC);
						moveList.add(m);
						break;
					}
				}
				else //Square empty
				{
					Move m = new Move(srcR,srcC,testR,testC);
					moveList.add(m);
				}
		
			}
			
			direction++;
		}
		
		return moveList;
	}
}

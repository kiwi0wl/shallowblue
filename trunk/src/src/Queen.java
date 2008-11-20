import java.util.ArrayList;

final public class Queen extends Piece
{
	public Queen(Piece.Color color, int srcR,int srcC)
	{
		super(color,srcR,srcC);
		name = Name.queen;
	}
	

	public  boolean legalMove(int destR, int destC, Board b)
	{
		Piece p;
		int testR = srcR;
		int testC = srcC;

		while(true)
		{
			
			//NorthEast Move
			if (srcR > destR && srcC < destC)
			{
				testR--;
				testC++;
			}
			//NorthWest Move
			else if (srcR > destR && srcC > destC)
			{
				testR--;
				testC--;
			}
			//SouthWest Move
			else if (srcR < destR && srcC > destC)
			{
				testR++;
				testC--;
			}
			//SouthEast Move
			else if (srcR < destR && srcC < destC)
			{
				testR++;
				testC++;
			}
			
			//North Move
			else if(srcR > destR && srcC == destC)
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
			else if (srcR == destR && srcC < destC)
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
		
		while(direction <8)
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
				else if (direction == 4) //North
				{
					testR--;
				}
				else if (direction == 5) //South
				{
					testR++;
				}
				else if (direction == 6) //West
				{
					testC--;
				}
				else if (direction == 7) //East
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
						m = new Move(srcR,srcC,testR,testC);
						moveList.add(m);
						break;
					}
				}
				else //Square empty
				{
					m = new Move(srcR,srcC,testR,testC);
					moveList.add(m);				}
		
			}
			
			direction++;
		}

		return moveList;
	}
	
}
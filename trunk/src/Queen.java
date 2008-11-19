import java.util.ArrayList;

final public class Queen extends Piece
{
	public Queen(Piece.Color color, int srcR,int srcC)
	{
		super(color,srcR,srcC);
		name = Name.queen;
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

	public  boolean legalMove(int destR, int destC, Board b)
	{
		Piece p;
		int testR = srcR;
		int testC = srcC;

		while(true)
		{
			
			if(srcR == destR && srcC > destC)//West
			{
				testC--;
			}
			
			if(srcR == destR && srcC < destC)//East
			{
				testC++;
			}
			
			if(srcR > destR && srcC == destC)//North
			{
				testR--;
			}
			
			if(srcR < destR && srcC == destC)//South
			{
				testR++;
			}
				
			if (srcR > destR && srcC < destC) //NorthEast
			{
				testR--;
				testC++;
			}
			
			if (srcR > destR && srcC > destC) //NorthWest
			{
				testR--;
				testC--;
			}
			
			if (srcR < destR && srcC > destC) //SouthWest
			{
				testR++;
				testC--;
			}
			
			if (srcR < destR && srcC < destC) //SouthEast
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
			if ( !(b.isEmpty(testR, testC)))
			{
				p = b.getPiece(testR, testC);
				
				//If Piece Has My Color
				if (p.getColor() == color)
				{
					return false;
				}
				
				//If Piece Not My Color
				if (p.getColor() != color)
				{
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
	
}
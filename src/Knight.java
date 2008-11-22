import java.util.ArrayList;

final public class Knight extends Piece 
{
	public Knight(Piece.Color color, int srcR,int srcC)
	{
		super(color,srcR,srcC);
		
		name = Name.knight;
	}
	
	public boolean legalMove(int destR,int destC, Board b)
	{
		/*Move m;
		
		m = new Move(srcR,srcC,destR,destC);
		
		if (this.legalMove(m, b))
			return true;
		else
			return false;
		*/
		
		int testR = srcR;
		int testC = srcC;
		
		// Illegal if moving in the same row or column
		if (srcR == destR || srcC == destC)
		{
			return false;
		}
		//illegal for moves outside the possible range
		else if (Math.abs(srcR - destR)> 2 && Math.abs(srcC - destC)> 2)
		{
			return false;
		}
		else if (Math.abs(srcC - destC)==1 && Math.abs(srcR - destR)!=2)
		{
			return false;
		}
		else if (Math.abs (srcR - destR)== 1 && Math.abs (srcC - destC) ==1)
		{
			return false;
		}
		
		else if (Math.abs (srcR - destR)== 2 && Math.abs (srcC - destC)==2)
		{
			return false;
		}
		
		else if (Math.abs(srcR - destR)==1 && Math.abs(srcC - destC) !=2)
		{
			return false;
		}
		
		else if (Math.abs(srcR - destR) == 2 && Math.abs(srcC - destC) != 1)
		{
			return false;
		}
		else if (Math.abs(srcC - destC) == 2 && Math.abs(srcR - destR) > 2)
		{
			return false;
		}
		//While On the Board
		while (testR < 8 && testC < 8)
		{
			//legal points
			if (Math.abs(srcR - destR)==1 && Math.abs(srcC - destC) ==2)
			{
				return true;
			}
			
			//legal points
			else if (Math.abs(srcR - destR) == 2 && Math.abs(srcC - destC) == 1)
			{
				return true;
			}
			
			//If Hit Destination Return True
			if (testR == destR && testC == destC)
			{
				return true;
			}
			
			//If Square has Something In it, return false
			if ( !(b.isEmpty(testR, testC)))
			{
				return false;
			}
		}
		return false;
	
	}
	
	public ArrayList<Move> genMoves(Board b)
	{		
		ArrayList<Move> moveList = new ArrayList<Move>();
		Piece p;
		
		int direction =0;
		
		while(direction <8)
		{
			int testR = srcR;
			int testC = srcC;
			
				if (direction == 0) //North-East
				{
					testR -= 2;
					testC++;
				}
				else if (direction == 1) //North-West
				{
					testR-=2;
					testC--;
				}
				else if (direction == 2) //South-East
				{
					testR+=2;
					testC++;
				}
				else if (direction == 3) //South-West
				{
					testR+=2;
					testC--;
				}
				else if (direction == 4) //West-North
				{
					testR--;
					testC-=2;
				}
				else if (direction == 5) //West-South
				{
					testR++;
					testC-=2;
				}
				else if (direction == 6) //East-North
				{
					testR--;
					testC+=2;
				}
				else if (direction == 7) //East-South
				{
					testR++;
					testC+=2;
				}
				else
				{
					direction++;
					continue;
				}
				
				//If Fell Off Board Try Next Direction
				if( !(testR < 8 && testR > -1 && testC < 8 && testC > -1) )
				{
					direction++;
					continue;
				}
				
				//Square Occupied
				if ( (p = b.getPiece(testR, testC)) != null)
				{

					//If Piece Has My Color
					if (p.getColor() == color)
					{
						direction++;
						continue;	
					}
					
					//If Piece Not My Color
					if (p.getColor() != color)
					{
						Move m = new Move(srcR,srcC,testR,testC);
						moveList.add(m);
						direction++;
						continue;
					}
				}
				else //Square empty
				{
					Move m = new Move(srcR,srcC,testR,testC);
					moveList.add(m);
				}
			
			direction++;
		}
		return moveList;
	}
	

	
}
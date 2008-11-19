import java.util.ArrayList;

final public class Knight extends Piece 
{
	public Knight(Piece.Color color, int srcR,int srcC)
	{
		super(color,srcR,srcC);
		
		name = Name.knight;
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
				if (!(b.isEmpty(testR, testC)))
				{
					p = b.getPiece(testR, testC);

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
			
			direction++;
		}
		return moveList;
	}
	
	public boolean legalMove(int destR,int destC, Board b)
	{
		return true;
	}
	
}
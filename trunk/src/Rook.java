import java.util.ArrayList;

final public class Rook extends Piece
{
	public Rook(Piece.Color color, int srcR,int srcC)
	{
		super(color,srcR,srcC);
		
		name = Name.rook;
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
	
	public  boolean legalMove(int destR, int destC, Board b)
	{
		return true;
	}
}
import java.util.ArrayList;

final public class Pawn extends Piece
{
	private boolean firstMove;
	private String upOrDown;
	
	public Pawn(Piece.Color color, int srcR,int srcC)
	{
		super(color,srcR,srcC);
		
		name = Name.pawn;

		
		if (color == Piece.Color.black)
			upOrDown = "down";
		else
			upOrDown = "up";
		
		firstMove = true;
		
	}
	
	public Pawn(Piece p)
	{
		this(p.getColor(),p.getSrcR(),p.getSrcC());
	}
	
	public boolean legalMove(int destR,int destC, Board b)
	{
	Move m;
		
		m = new Move(srcR,srcC,destR,destC);
		
		if (this.legalMove(m, b))
			return true;
		else
			return false;
	}
	
	public ArrayList<Move> genMoves(Board b)
	{
		ArrayList<Move> moveList = new ArrayList<Move>();
		Piece p;
		
		int direction =0;
		
		//First move or not
		if (srcR == 6 && color == Piece.Color.white || srcR == 1 && color == Piece.Color.black )
			firstMove = true;
		else
			firstMove = false;
		
		while(direction <4)
		{
			int testR = srcR;
			int testC = srcC;
				
				if (upOrDown == "up")
				{
					if (direction == 0) //One Move 
					{
						testR--;
					}
					else if (direction == 1) //Two Move 
					{
						if (firstMove)
						{
							testR-=2;

						}
						else
						{
							direction++;
							continue;
						}
					}
					else if (direction == 2) //Diagonal -East
					{
						testR--;
						testC++;
					}
					else if (direction == 3) //Diagonal - West
					{
						testR--;
						testC--;
					}
					else
					{
						direction++;
						continue;
					}
				}
				else
				{
					if (direction == 0) //One Move 
					{
						testR++;
					}
					else if (direction == 1) //Two Move 
					{
						if (firstMove)
						{
							testR+=2;

						}
						else
						{
							direction++;
							continue;
						}
					}
					else if (direction == 2) //Diagonal -East
					{
						testR++;
						testC++;
					}
					else if (direction == 3) //Diagonal - West
					{
						testR++;
						testC--;
					}
					else
					{
						direction++;
						continue;
					}
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
						//If my piece in diagnol path
						if(direction == 2 || direction ==3 )
						{
						}
						
						direction++;
						continue;
					}
					
					//If Piece Not My Color
					if (p.getColor() != color)
					{
						if(direction == 0 || direction == 1)
						{
							direction++;
							continue;
						}
						
						Move m = new Move(srcR,srcC,testR,testC);
						moveList.add(m);
						direction++;
						continue;
					}
				}
				else //Square empty
				{
					if(direction == 2 || direction == 3)
					{
						direction++;
						continue;
					}
					Move m = new Move(srcR,srcC,testR,testC);
					moveList.add(m);
				}
		
	
			direction++;
		}
		return moveList;		
	}
	

}
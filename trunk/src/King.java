import java.util.ArrayList;
import java.util.Iterator;

final public class King extends Piece
{

	
	public King(Piece.Color color, int srcR,int srcC)
	{
		super(color,srcR,srcC);
		
		name = Name.king;
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
				
				//If tile under attack
				if(this.isUnderAttack(b))
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
				
				i++;
				
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
		
		int i=0;
		while(i<0)
		{
			//West
			if(srcR == destR && srcC > destC)
			{
				testC--;
			}
			
			//East
			if(srcR == destR && srcC < destC)
			{
				testC++;
			}
			
			//North
			if(srcR > destR && srcC == destC)
			{
				testR--;
			}
			
			//South
			if(srcR < destR && srcC == destC)
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
			
			//If tile under attack
			if(this.isUnderAttack(b))
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
			i++;
		}
		return false;
	}
	
	public boolean inCheck(Board b)
	{
		//If King Under Attack
		if(this.isUnderAttack(b))
			return true;
		else
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
						king = (King) b.getPiece(Piece.Name.king, color,Piece.Side.invalid);
						
						//If king still in check, try another block
						if(king.inCheck(B))
						{
							continue;
						}
						else //Out of check = no check mate
						{
							return false;
						}
					} //Move Loop
				} //Piece Loop
				return true;
			}
			
			return false;
		}
		
		return false;
	}

}
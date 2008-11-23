import java.util.ArrayList;
import java.util.Iterator;

public abstract  class Piece
{
	protected int srcR,srcC;
	protected Name name;
	protected Color color;
	protected Side side;
	
	public enum Name{pawn,knight,bishop,rook,queen,king,invalid};
	public enum Color{black,white,invalid};
	public enum Side{queenside,kingside,invalid};
	
//Constructor	
	public Piece(Piece.Color color, int srcR, int srcC)
	{
		this.color = color;
		this.srcR = srcR;
		this.srcC = srcC;
	}

//Mutator
	public void setSrcC(int srcC)
	 {
		 this.srcC = srcC;
	 }
	 
	 public void setSrcR(int srcR)
	 {
		 this.srcR = srcR;
	 }

	
//Accessor 
	 public int getSrcC()
	 {
		 return srcC;
	 }
	 
	 public int getSrcR()
	 {
		 return srcR;
	 }
	
	public Piece.Color getColor()
	{
		return color;
	}
	
	public Piece.Side getSide()
	{
		return side;
	}
	

	public Piece.Name getName()
	{
		return name;
	}
	
//Methods	
	
	public abstract ArrayList <Move> genMoves(Board b);
	
	public abstract boolean legalMove(int destR, int destC, Board b);
	
	protected boolean legalMove(Move move, Board b)
	{
		ArrayList<Move> moveList;
		Iterator<Move> itMove;
		
		moveList = genMoves(b); //Find the Moves
		
		//See If the imported move, is in the move list
		itMove = moveList.listIterator();
		while (itMove.hasNext())
		{
			Move m = itMove.next();
			if (move.compareTo(m))
			{
				return true;
			}	
		}
		return false;
	}

	public Piece.Color getEnemyColor()
	{
		//Get color of attacker
		if(color == Piece.Color.white)
			return Piece.Color.black;
		else
			return Piece.Color.white;
	}

	
	//Is this src/destination square under attack by enemy 
	public boolean isSrcUnderAttack(Board b)
	{
		ArrayList<Piece> pieceList;
		Piece enemy;
		Iterator<Piece> itPiece; 
		Piece.Color enemyColor;
		
		//Find enemy color
		enemyColor = this.getEnemyColor();
		
		//Get enemy piece list
		pieceList = b.getPieceList(enemyColor);

		//See if any piece can legally capture 
	    itPiece = pieceList.listIterator();
		while (itPiece.hasNext())
		{
			 enemy = itPiece.next();
			 
			//If the enemy piece can legal move to src
			if( enemy.legalMove(srcR, srcC, b))
				return true; 
		}
		
		return false;	
	}
	
	public boolean isUnderAttackAfterMove(Board b,int destR,int destC)
	{
		ArrayList<Piece> pieceList;
		Piece enemy;
		Iterator<Piece> itPiece; 
		Piece.Color enemyColor;
		Board B;
		
		//Board
		B = new Board(b);
		
		B.makeMove(srcR,srcC,destR,destC);
		
		//Find enemy color
		enemyColor = this.getEnemyColor();
		
		//Get enemy piece list
		pieceList = B.getPieceList(enemyColor);

		//See if any piece can legally capture 
	    itPiece = pieceList.listIterator();
		while (itPiece.hasNext())
		{
			 enemy = itPiece.next();
			 
			//If the enemy piece can legal move to dest
			if( enemy.legalMove(destR, destC, b))
				return true; 
		}
		
		return false;	
	}
	
}
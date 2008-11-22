import java.util.ArrayList;
import java.util.Iterator;

public abstract  class Piece
{
	protected int srcR,srcC;
	protected Name name;
	protected  Color color;
	protected Side side;
	
	public enum Name{pawn,knight,bishop,rook,queen,king,invalid};
	public enum Color{black,white,invalid};
	public enum Side{queenside,kingside,invalid};
	
	
	public Piece(Piece.Color color, int srcR, int srcC)
	{
		this.color = color;
		this.srcR = srcR;
		this.srcC = srcC;
		this.setSide(srcC);
	}
	
//Copy Constructor
	public Piece(Piece p)
	{
		this(p.getColor(),p.getSrcR(),p.getSrcC());
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
	
//Methods	
	
	public abstract ArrayList <Move> genMoves(Board b);
	
	public abstract boolean legalMove(int destR, int destC, Board b);
	
	public void setSide(int srcC)
	{
		if(srcC == 3 || srcC == 4)
			side = Side.invalid;
		else if (srcC >= 0 || srcC <= 2)
			side = Side.queenside;
		else if (srcC >=5 || srcC <= 7)
			side = Side.kingside;
	}
	
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

	 

	public Piece.Name getName()
	{
		return name;
	}
	
	public Piece.Color getEnemyColor()
	{
		//Get color of attacker
		if(color == Piece.Color.white)
			return Piece.Color.black;
		else
			return Piece.Color.white;
	}
	
	//Is this square under attack by enemy 
	public boolean isUnderAttack(Board b)
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
			if( enemy.legalMove(srcR, srcC, b))
			{
				return true;
			}
		}
		
		return false;	
	}
	
}
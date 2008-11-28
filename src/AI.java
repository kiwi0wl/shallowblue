import java.util.ArrayList;
import java.util.Iterator;


public class AI {
	
	private Piece.Color computerColor;
	
	AI(int IQ)
	{
		this.computerColor = Piece.Color.black;
	}
	
	
	public Move getMove(Board b)
	{
		Move m;
		
		m = this.randomMove(b);
		
		return m;

	}
	
	public Move randomMove(Board b)
	{
		ArrayList<Move> moveList = new ArrayList<Move>();
		ArrayList<Piece> pieceList;
		Iterator<Piece> itPiece;
		Piece p;
		Move m;
		int numMoves,moveNum;
		
		pieceList = b.getPieceList(computerColor);
		
		itPiece = pieceList.listIterator();
	
		while (itPiece.hasNext())
		{
			p = itPiece.next();
			moveList.addAll(p.genMoves(b));
		}
		
		numMoves = moveList.size(); 
		
		while(true)
		{			
			moveNum = (int)(Math.random() * numMoves);
			
			m = moveList.get(moveNum);
			
			if(b.isValidSrc(m, computerColor) && b.isValidDest(m, computerColor))
				break;	
		}

		return m;
	}
}

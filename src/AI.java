import java.util.ArrayList;
import java.util.Iterator;


public class AI {
	
	private Piece.Color computerColor;
	
	AI(Piece.Color color)
	{
		this.computerColor = color;
	}
	
	
	//Return a valid move
	public Move getMove(Board b)
	{
		Move m;
		
		m = this.randomMove(b);
		
		return m;

	}
	
	//Pick a random move for this color
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
	
		//Add each piece moves, to movelist
		while (itPiece.hasNext())
		{
			p = itPiece.next();
			moveList.addAll(p.genMoves(b));
		}
		
		numMoves = moveList.size(); 
		
		//Find a random and valid move
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

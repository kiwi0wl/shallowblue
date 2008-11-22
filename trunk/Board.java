import java.util.ArrayList;
import java.util.Iterator;
	
public class Board
{
	private Piece chessBoard[][];

	
	public Board()
	{	
		//Create Board With Pieces On It
		chessBoard = new Piece[8][8];
		
		//Black
		chessBoard[0][0] = new Rook(Piece.Color.black,0,0);
		chessBoard[0][1] = new Knight(Piece.Color.black,0,1);
		chessBoard[0][2] = new Bishop(Piece.Color.black,0,2);
		chessBoard[0][3] = new Queen(Piece.Color.black,0,3);
		chessBoard[0][4] = new King(Piece.Color.black,0,4);
		chessBoard[0][5] = new Bishop(Piece.Color.black,0,5);
		chessBoard[0][6] = new Knight(Piece.Color.black,0,6);
		chessBoard[0][7] = new Rook(Piece.Color.black,0,7);
		
		//White
		chessBoard[7][0] = new Rook(Piece.Color.white,7,0);
		chessBoard[7][1] = new Knight(Piece.Color.white,7,1);
		chessBoard[7][2] = new Bishop(Piece.Color.white,7,2);
		chessBoard[7][3] = new Queen(Piece.Color.white,7,3);
		chessBoard[7][4] = new King(Piece.Color.white,7,4);
		chessBoard[7][5] = new Bishop(Piece.Color.white,7,5);
		chessBoard[7][6] = new Knight(Piece.Color.white,7,6);
		chessBoard[7][7] = new Rook(Piece.Color.white,7,7);

		//Set pawns up and blank spaces
		for (int i = 0; i < 8; i++)
		{
			chessBoard[6][i] = new Pawn(Piece.Color.white,6,i);
			chessBoard[1][i] = new Pawn(Piece.Color.black,1,i);
			chessBoard[2][i] = null;
			chessBoard[3][i] = null;
			chessBoard[4][i] = null;
			chessBoard[5][i] = null;
		}
	}
	
	//Copy Constructor
	public Board(Board b)
	{
		this();
	}
	
	public boolean isEmpty(int r, int c)
	{
		if (chessBoard[r][c] == null)
			return true;
		else
			return false;
	}
	
	//Return piece by position
	public Piece getPiece(int r, int c)
	{
		if (chessBoard[r][c] == null)
			return null;
		else
			return chessBoard[r][c];
	}
	
	//Return piece by name and color and side
	public Piece getPiece(Piece.Name name,Piece.Color color, Piece.Side side)
	{
		ArrayList<Piece> pieceList;
		Iterator<Piece> itPiece;
		Piece p;
		
		pieceList = getPieceList(color);
		
		itPiece = pieceList.listIterator();
		while (itPiece.hasNext())
		{
			 p = itPiece.next();
			if(p.getName() == name && p.getColor() == color && p.getSide() == side)
			{
				return p;
			}
		}
		
		return null;
	}
	
	//Move pieces on the board array
	public void makeMove(int srcR, int srcC, int destR, int destC)
	{
		
		//Move piece on board array
		chessBoard[destR][destC] = chessBoard[srcR][srcC];
		
		//Set board source to empty
		chessBoard[srcR][srcC] = null;
		
		//Update piece position
		chessBoard[destR][destC].setSrcC(destC);
		chessBoard[destR][destC].setSrcR(destR);
		
		//Update piece side
		chessBoard[destR][destC].setSide(srcC);
	}
	
	//Move pieces on the board array
	public void makeMove(Move m)
	{
		int srcR,srcC,destR,destC;
		srcR = m.getSrcR();
		srcC = m.getSrcC();
		destR = m.getDestR();
		destC = m.getDestC();
		
		this.makeMove(srcR, srcC, destR, destC);
	}
	
	//Return list of all pieces, or by color
	public ArrayList<Piece> getPieceList(Piece.Color color)
	{
		ArrayList<Piece> pieceList = new ArrayList<Piece>();
		
		for (int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				//Skip Null Tiles
				if (chessBoard[i][j] == null)
					continue;
				else if ( color == Piece.Color.invalid )
				{
					pieceList.add(chessBoard[i][j]);
				}
				else if(chessBoard[i][j].getColor() == color)
				{
					pieceList.add(chessBoard[i][j]);
				}
			}
		}
		
		return pieceList;
	}
}